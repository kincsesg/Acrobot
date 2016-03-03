package invpendlearn;

import java.util.ArrayList;
import java.util.Random;

import invpendgraph.InvPendGraphNode;
import utils.InterpolatedUtil;

public class InvPendLearningEngine {
	private InvPendModel model;
	private InvPendStateRepo stateRepo;
	private InvPendTrajectory trajectory;
	private InvPendUtilComputer utilComp;

	public InvPendLearningEngine() {
		this.model = new InvPendModel(10, 1, 1, 0.03, 0.2);
		this.stateRepo = new InvPendStateRepo(model, 1.0, 1.0, 0.2, 0.2, 10, 10);
		this.trajectory = new InvPendTrajectory();
		this.utilComp = new InvPendUtilComputer();
	}

	public void TdLamda(double lambda, int eSize, int stepCount, int actionPool, int learningHorizon) {

		InvPendState startState = new InvPendState(0.0, 0.0);
		InvPendState currS = new InvPendState();
		InvPendAction perform_a = new InvPendAction();
		
		for (int e = 0; e < eSize; e++) {
			System.out.println(e + " episode");
			trajectory = new InvPendTrajectory();
			ArrayList<InvPendGraphNode> nodes = stateRepo.getNNearestNodes(startState, 3);
			System.out.println(nodes.size());
			System.out.println(nodes.get(0).getState().getAngle()+" "+nodes.get(0).getState().getAngVel());
			System.out.println(nodes.get(1).getState().getAngle()+" "+nodes.get(1).getState().getAngVel());
			System.out.println(nodes.get(2).getState().getAngle()+" "+nodes.get(2).getState().getAngVel());
			InterpolatedUtil intU = utilComp.interpolateUtil(startState, nodes); //intU - U, ratios, deltaU
			trajectory.add(intU, nodes, startState);
			
			for (int step = 0; step < stepCount; step++) {
				if (step == 0) {
					currS = startState;
				}
				
				perform_a = chooseActionTdLambda(currS, actionPool);
				InvPendState nextS = (InvPendState) model.getNextState(currS, perform_a);
				
				
				nodes = stateRepo.getNNearestNodes(nextS, 3);
				intU = utilComp.interpolateUtil(nextS, nodes);
				trajectory.add(intU, nodes, nextS);
				
				currS = nextS;
				
			}
			
			for (int i = 0; i < trajectory.getSize(); i++) {
				double newU = 0.0;
				ArrayList<Double> rews = trajectory.getRewards(i, learningHorizon); 
				ArrayList<InvPendGraphNode> refrNodes = trajectory.getNodes(i);
				InterpolatedUtil iu = utilComp.interpolateUtil(trajectory.getState(i), refrNodes);
				for (int j = i; j < i+learningHorizon; j++) {
					if (j < i+learningHorizon-1) {
						newU = newU + (1-lambda) * Math.pow(lambda, j) * rews.get(j);
					} else {
						newU = newU + (1-lambda) * Math.pow(lambda, j) * (rews.get(j) + utilComp.interpolateUtil(trajectory.getState(j), trajectory.getNodes(j)).getU());
					}
				}
				
				ArrayList<Double> ratios = iu.getRatios();
				for (int k = 0; k<3; k++) {
					double prevU = refrNodes.get(k).getState().getUtil();
					refrNodes.get(k).getState().setUtil(prevU + ratios.get(k) * iu.getU());
				}				
			}
			
			
			currS = startState;
		}
	}
	
	private InvPendAction chooseActionTdLambda(InvPendState currS, int actionPool) {
		InvPendAction a = new InvPendAction();
		double minA = a.getMin();
		double maxA = a.getMax();
		Random r = new Random();
		a.setMin(r.nextDouble() * maxA - minA);
		a.setMax(a.getMin());
		InvPendState nextS = (InvPendState) model.getNextState(currS, a);
		InterpolatedUtil nextSIU = utilComp.interpolateUtil(nextS, stateRepo.getNNearestNodes(nextS, 3));
		InvPendAction aTemp = new InvPendAction();
		InvPendState nextSTemp = new InvPendState();
		
		
		for (int i = 0; i<actionPool; i++) {
			aTemp.setMin(r.nextDouble() * maxA - minA);
			aTemp.setMax(a.getMin());
			nextSTemp = (InvPendState) model.getNextState(currS, aTemp);
			
			InterpolatedUtil nextSTempIU = utilComp.interpolateUtil(nextSTemp, stateRepo.getNNearestNodes(nextSTemp, 3));
			
			if(nextSTempIU.getU() > nextSIU.getU()) {
				a = aTemp;
				nextSIU = nextSTempIU;
			}			
		}
		
		return a;
	}
}
