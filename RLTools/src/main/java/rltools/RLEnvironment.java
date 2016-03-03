package rltools;

public abstract class RLEnvironment {
	public abstract RLState observe();
	
	public abstract void executeAction(RLAction a);
}
