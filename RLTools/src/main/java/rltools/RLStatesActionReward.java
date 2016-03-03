package rltools;

public class RLStatesActionReward {
	private RLState startState;
	private RLState endState;
	private RLAction action;
	private RLReward reward;
	
	public RLState getStartState() {
		return startState;
	}
	
	public void setStartState(RLState startState) {
		this.startState = startState;
	}
	
	public RLState getEndState() {
		return endState;
	}
	
	public void setEndState(RLState endState) {
		this.endState = endState;
	}
	
	public RLAction getAction() {
		return action;
	}
	
	public void setAction(RLAction action) {
		this.action = action;
	}
	
	public RLReward getReward() {
		return reward;
	}
	
	public void setReward(RLReward reward) {
		this.reward = reward;
	}	
}
