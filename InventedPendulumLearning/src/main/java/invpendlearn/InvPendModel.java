package invpendlearn;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.NoFeasibleSolutionException;
import org.apache.commons.math3.optim.linear.NonNegativeConstraint;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import rltools.RLAction;
import rltools.RLEnvironmentModel;
import rltools.RLReward;
import rltools.RLState;

public class InvPendModel extends RLEnvironmentModel {
	private double length;
	private double mass;
	private double gacc;
	private double mu;
	private double timeunit;
	private InvPendUtilComputer utilComp;

 	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getGacc() {
		return gacc;
	}

	public void setGacc(double gacc) {
		this.gacc = gacc;
	}

	public double getMu() {
		return mu;
	}

	public void setMu(double mu) {
		this.mu = mu;
	}

	public double getTimeunit() {
		return timeunit;
	}

	public void setTimeunit(double timeunit) {
		this.timeunit = timeunit;
	}
	
	public void setUtilComp(InvPendUtilComputer uc) {
		this.utilComp = uc;
	}
	
	public InvPendModel(double g, double l, double m, double mu, double tu) {
		this.gacc = g;
		this.length = l;
		this.mass = m;
		this.mu = mu;
		this.timeunit = tu;
	}
	
	@Override
	public RLState getNextState(RLState currS, RLAction a) {
		InvPendState nextS = simulate((InvPendState) currS, (InvPendAction) a, timeunit);
		return nextS;
	}	

	@Override
	public RLReward calcReward(RLState s) {
		InvPendState state = (InvPendState) s;
		InvPendReward r = new InvPendReward();
		r.setValue(state.getReward());
		
		return r;
	}

	public Boolean isReachableByOneStep(RLState s, RLState e) {
		InvPendState start = (InvPendState) s;
		InvPendState end = (InvPendState) e;
		InvPendAction a1 = new InvPendAction();
		InvPendAction a2 = new InvPendAction();
		double amin = a1.getNEGMAXLIMIT();
		double amax = a1.getPOSMAXLIMIT();
		ArrayList<Double> angles = new ArrayList<Double>();
		ArrayList<Double> angVels = new ArrayList<Double>();
		ArrayList<Double> midAngles = new ArrayList<Double>();
		ArrayList<Double> midAngVels = new ArrayList<Double>();
		ArrayList<InvPendState> nStates = new ArrayList<InvPendState>();
		for (double i = amin; i <= amax; i += (amax - amin) / 10) {
			a1.setMin(i);
			a1.setMax(i);
			for (double j = amin; j <= amax; j += (amax - amin) / 10) {
				if (i == amin || i == amax || j == amin || j == amax) {
					a2.setMin(j);
					a2.setMax(j);
					
					InvPendState newState = simulate(start, a1, timeunit/2);
					//System.out.println(start.getAngle() + " " + start.getAngVel() + " " + a1.getMin() + " " + a1.getMax() + " " + timeunit/2);
					//System.out.println(newState.getAngle() + " " + newState.getAngVel());
					//midAngles.add(newState.getAngle());
					//midAngVels.add(newState.getAngVel());
					
					newState = simulate(newState, a2, timeunit/2);
					//System.out.println(newState.getAngle() + " " + newState.getAngVel() + " " + a2.getMin() + " " + a2.getMax() + " " + timeunit/2);
					//System.out.println(newState.getAngle() + " " + newState.getAngVel());
					nStates.add(newState);
					angles.add(newState.getAngle());
					angVels.add(newState.getAngVel());
				}
			}
		}
		/*String fn = "2_2";
		String header = "mid_angles,mid_angvels,angles,avels";
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("e:/EGYETEM/MSC/2felev/önlab/next_state_space/" + fn + ".csv");
			fileWriter.append(header.toString());
			fileWriter.append("\n");
			
			for(int i = 0; i<angles.size(); i++) {
				fileWriter.append(String.valueOf(midAngles.get(i)));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(midAngVels.get(i)));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(angles.get(i)));
				fileWriter.append(",");
				fileWriter.append(String.valueOf(angVels.get(i)));
				fileWriter.append("\n");
			}
			
			fileWriter.flush();
			fileWriter.close();

		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
				
		double[] c = new double[nStates.size()];
		double[] ones = new double[angles.size()];
		for (int i = 0; i<ones.length; i++) {
			ones[i] = 1;
		}
		
		double[][] A = new double[2][angles.size()];
		double[] a = new double[angles.size()];
		double[] av = new double[angVels.size()];
		for (int i = 0; i<angles.size(); i++) {
			a[i] = angles.get(i);
			av[i] = angVels.get(i);
		}
		
		A[0] = a;
		A[1] = av;
				
		LinearObjectiveFunction f = new LinearObjectiveFunction(c, 0);
		ArrayList<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		
		constraints.add(new LinearConstraint(A[0], Relationship.EQ, end.getAngle()));
		constraints.add(new LinearConstraint(A[1], Relationship.EQ, end.getAngVel()));
		constraints.add(new LinearConstraint(ones, Relationship.EQ, 1));

		SimplexSolver solver = new SimplexSolver(0.00000000000001, 10, 0.00000000000000001);
		PointValuePair optSolution = null;
		try {
		optSolution = solver.optimize(f, new
                LinearConstraintSet(constraints),
                GoalType.MINIMIZE, new
                NonNegativeConstraint(true));
		} catch (NoFeasibleSolutionException exception) {};
		
        return optSolution != null;
	}

	private InvPendState simulate(InvPendState s, InvPendAction a, double tu) {
		double l = this.length;
		double m = this.mass;
		double g = this.gacc;
		double mu = this.mu;
		double dt = tu;

		double w = s.getAngVel();
		double fi = s.getAngle();

		double M = a.getMax();

		w += (l * m * g * Math.sin(fi) + M - mu * w) / (l * l * m) * dt;
		fi += w * dt;

		if (fi < -Math.PI)
			fi += 2 * Math.PI;
		if (fi > Math.PI)
			fi -= 2 * Math.PI;

		InvPendState nextState = new InvPendState();
		nextState.setAngle(fi);
		nextState.setAngVel(w);

		return nextState;
	}

}
