package invpendgraph;

import java.util.ArrayList;

import invpendlearn.InvPendState;
import rlgraph.RLGraphNode;

public class InvPendGraphNode extends RLGraphNode{
	private InvPendState state;
	private ArrayList<InvPendGraphNode> in = new ArrayList<InvPendGraphNode>();
	private ArrayList<InvPendGraphNode> out = new ArrayList<InvPendGraphNode>();
	
	
	public InvPendGraphNode(InvPendState state) {
		this.state = state;
		this.in = new ArrayList<InvPendGraphNode>();
		this.out = new ArrayList<InvPendGraphNode>();
	}

	public InvPendState getState() {
		return state;
	}

	public void setState(InvPendState state) {
		this.state = state;
	}

	public ArrayList<InvPendGraphNode> getIn() {
		return in;
	}

	public void setIn(ArrayList<InvPendGraphNode> in) {
		this.in = in;
	}

	public ArrayList<InvPendGraphNode> getOut() {
		return out;
	}

	public void setOut(ArrayList<InvPendGraphNode> out) {
		this.out = out;
	}
	
	public void addIn(InvPendGraphNode n) {
		in.add(n);
	}
	
	public void removeIn(InvPendGraphNode n) {
		in.remove(n);
	}
	
	public void addOut(InvPendGraphNode n) {
		out.add(n);
	}
	
	public void removeOut(InvPendGraphNode n) {
		out.remove(n);
	}
	
	public boolean outContains (InvPendGraphNode n1) {
		boolean ret = false;
		for (InvPendGraphNode n2 : this.out) {
			if (n2.getState().sameState(n1.getState(), 0.00001)) {
				ret = true;
			}				
		}
		
		return ret;		
	}
	
	public boolean inContains (InvPendGraphNode n1) {
		boolean ret = false;
		for (InvPendGraphNode n2 : this.in) {
			if (n2.getState().sameState(n1.getState(), 0.00001)) {
				ret = true;
			}				
		}
		
		return ret;
	}
	
	public void outRemove (InvPendGraphNode n1) {
		for (InvPendGraphNode n2 : this.out) {
			if (n2.getState().sameState(n1.getState(), 0.00001)) {
				this.out.remove(n2);
			}				
		}
	}
	
	public void inRemove (InvPendGraphNode n1) {
		for (InvPendGraphNode n2 : this.in) {
			if (n2.getState().sameState(n1.getState(), 0.00001)) {
				this.in.remove(n2);
			}				
		}
	}
}
