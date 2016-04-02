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

	public InvPendModel getModel() {
		return model;
	}

	public void setModel(InvPendModel model) {
		this.model = model;
	}

	public InvPendStateRepo getStateRepo() {
		return stateRepo;
	}

	public void setStateRepo(InvPendStateRepo stateRepo) {
		this.stateRepo = stateRepo;
	}

	public InvPendTrajectory getTrajectory() {
		return trajectory;
	}

	public void setTrajectory(InvPendTrajectory trajectory) {
		this.trajectory = trajectory;
	}

	public InvPendUtilComputer getUtilComp() {
		return utilComp;
	}

	public void setUtilComp(InvPendUtilComputer utilComp) {
		this.utilComp = utilComp;
	}

	public InvPendLearningEngine() {
		//this.model = new InvPendModel(10, 1, 1, 0.03, 0.2);
		this.model = new InvPendModel(10, 1, 0.1, 0.03, 0.2);
		//this.stateRepo = new InvPendStateRepo(model, -Math.PI, - 5.0, 0.1, 0.1, 62, 100);
		this.stateRepo = new InvPendStateRepo(model, -Math.PI, - 5.0, 0.1, 0.1, 10, 10);
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
			InterpolatedUtil intU = utilComp.interpolateUtil(startState, nodes); //intU - U, ratios, deltaU
			trajectory.add(intU, nodes, startState);
			
			currS = startState;
			for (int step = 0; step < stepCount; step++) {
				perform_a = chooseActionTdLambda(currS, actionPool);
				InvPendState nextS = (InvPendState) model.getNextState(currS, perform_a);
				
				nodes = stateRepo.getNNearestNodes(nextS, 3);
				intU = utilComp.interpolateUtil(nextS, nodes);
				trajectory.add(intU, nodes, nextS);
				
				currS = nextS;			
			}
			System.out.println(trajectory.getSize());
			for (int i = 0; i < trajectory.getSize(); i++) {
				System.out.println("i: " + i);	
				double newU = 0.0;
				ArrayList<Double> rews = trajectory.getRewards(i, learningHorizon); 
				ArrayList<InvPendGraphNode> refrNodes = trajectory.getNodes(i);
				InterpolatedUtil iu = utilComp.interpolateUtil(trajectory.getState(i), refrNodes);
				for (int j = 0; j < Math.min(learningHorizon, trajectory.getSize()-i); j++) {
					System.out.println("j: " + j);
					newU = newU + (1-lambda) * Math.pow(lambda, j) * rews.get(j);
					System.out.println("newU: " + newU);
				}
				trajectory.getState(i).setUtil(newU);//nem tudom van-e érteleme elrakni egy nem gráfbeli állapot u-ját, valszeg nincs				
				
				ArrayList<Double> ratios = iu.getRatios();
				for (int k = 0; k<3; k++) {
					double prevU = refrNodes.get(k).getState().getUtil();
					refrNodes.get(k).getState().setUtil(prevU + ratios.get(k) * iu.getU());
				}			
			}			
			currS = startState;
		}
	}
	
	public InvPendAction simulateActionToTake(InvPendState currS, int actionPool){
		return chooseActionTdLambda(currS, actionPool);
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
