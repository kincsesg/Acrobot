package invpendgraph;

import java.util.ArrayList;

import invpendlearn.InvPendModel;
import invpendlearn.InvPendState;
import rlgraph.RLGraph;
import rlgraph.RLGraphCreator;
import rltools.RLEnvironmentModel;

public class InvPendGraphCreator extends RLGraphCreator {
	private InvPendGraph graph;
	private InvPendGraphConfiguration config;

	public InvPendGraph getGraph() {
		return graph;
	}

	public void setGraph(InvPendGraph graph) {
		this.graph = graph;
	}

	public InvPendGraphConfiguration getConfig() {
		return config;
	}

	public void setConfig(InvPendGraphConfiguration config) {
		this.config = config;
	}

	public void createGraph(RLEnvironmentModel m) {
		System.out.println(config.getAngleMin()+" "+config.getAngVelMin());
		System.out.println(config.getAngleCount()+" "+config.getAngVelCount()+" "+config.getAngleStep()+" "+config.getAngVelStep());
		
		InvPendModel model = (InvPendModel) m;
		
		ArrayList<InvPendGraphNode> nodes = new ArrayList<InvPendGraphNode>();
		
		for (int anglei = 0; anglei < config.getAngleCount(); anglei++) {
			for (int angveli = 0; angveli < config.getAngVelCount(); angveli++) {
				double angle = config.getAngleMin() + anglei * config.getAngleStep();
				double angVel = config.getAngVelMin() + angveli * config.getAngVelStep();
				InvPendState s = new InvPendState(angle, angVel);
				InvPendGraphNode node = new InvPendGraphNode(s);
				nodes.add(node);
			}
		}
		System.out.println(System.currentTimeMillis());
		System.out.println(nodes.size());
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = i+1; j < nodes.size(); j++) {
				System.out.println(System.currentTimeMillis());
				System.out.println(i);
				System.out.println(j);
				InvPendGraphNode inode = nodes.get(i); 
				InvPendGraphNode jnode = nodes.get(j);
				InvPendState istate = inode.getState();
				InvPendState jstate = jnode.getState();
				
				boolean in = model.isReachableByOneStep(jstate, istate);
				boolean out = model.isReachableByOneStep(istate, jstate);			
				System.out.println("i<-j: "+in+" i->j: "+out);
				
				if (in) {
					inode.addIn(jnode);
				}
				
				if (out) {
					inode.addOut(jnode);
				}
			}
		}

		this.graph = new InvPendGraph(nodes);
	}
}