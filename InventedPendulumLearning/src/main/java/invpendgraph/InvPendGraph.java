package invpendgraph;

import java.util.ArrayList;

import invpendlearn.InvPendState;
import rlgraph.RLGraph;

public class InvPendGraph extends RLGraph {
	private ArrayList<InvPendGraphNode> nodes;
	
	public InvPendGraph(ArrayList<InvPendGraphNode> nodes) {
		this.nodes = nodes;
	}
		
	public ArrayList<InvPendGraphNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<InvPendGraphNode> nodes) {
		this.nodes = nodes;
	}
	
	public void addNode(InvPendGraphNode n) {
		nodes.add(n);
	}
	
	public void removeNode(InvPendGraphNode n) {
		nodes.remove(n);
	}

	public InvPendGraphNode searchNode(InvPendState s, double treshold) {
		InvPendGraphNode res = null;
		for (InvPendGraphNode n : nodes) {
			if (Math.abs(n.getState().getAngle()-s.getAngle()) <= treshold && Math.abs(n.getState().getAngVel()-s.getAngVel()) <= treshold) {
				res = n;
			}
		}
		
		return res;
	}
}
