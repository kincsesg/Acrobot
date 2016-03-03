package rltools;

public abstract class RLEnvironmentModel {
	public abstract RLState getNextState(RLState currentState, RLAction a);
	
	public abstract RLReward calcReward(RLState s);
	
	public abstract Boolean isReachableByOneStep(RLState start, RLState end);	
}
