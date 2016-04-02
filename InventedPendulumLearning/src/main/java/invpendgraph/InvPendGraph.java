package invpendgraph;

import java.util.ArrayList;

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
}
