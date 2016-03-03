package invpendlearn;

import java.util.ArrayList;

import gnu.trove.procedure.TIntProcedure;
import invpendgraph.InvPendGraph;
import invpendgraph.InvPendGraphConfiguration;
import invpendgraph.InvPendGraphCreator;
import invpendgraph.InvPendGraphNode;
import rtree.Point;
import rtree.RTree;
import rtree.Rectangle;

public class InvPendStateRepo {
	private InvPendGraph graph;
	private RTree tree;
	private Rectangle[] rects;
	private int size;
	
	//1.0, 1.0, 0.2, 0.2, 10, 10
	public InvPendStateRepo(InvPendModel model, double angleMin, double angVelMin, double angleStep, double angVelStep, int angleCount, int angVelCount) {
		this.size = angleCount * angVelCount;
		InvPendGraphCreator grcr = new InvPendGraphCreator();
		InvPendGraphConfiguration config = new InvPendGraphConfiguration(angleMin, angVelMin, angleStep, angVelStep, angleCount, angVelCount);
		grcr.setConfig(config);
		grcr.createGraph(model);
		this.graph = grcr.getGraph();
		
		RTree tree = new RTree();
		tree.init(null);		
		Rectangle[] rects = new Rectangle[this.size];
		ArrayList<InvPendGraphNode> nodes = grcr.getGraph().getNodes();
		for(int i = 0; i < this.size; i++) {
			InvPendGraphNode n = nodes.get(i);
			float x = (float) n.getState().getAngle();
			float y = (float) n.getState().getAngVel();
			Rectangle r = new Rectangle(x,y,x,y);
			rects[i] = r;
			tree.add(r, i);
		}
		
		this.rects = rects;
		this.tree = tree;
	}
	
	public ArrayList<InvPendGraphNode> getNNearestNodes(InvPendState state, int N) {
		Point p = new Point((float)state.getAngle(), (float)state.getAngVel());
		final ArrayList<Integer> rectIndecies = new ArrayList<Integer>();
		tree.nearestN(
				p,      // the point for which we want to find nearby rectangles
				new TIntProcedure() {         // a procedure whose execute() method will be called with the results
					public boolean execute(int i) {
						rectIndecies.add(i);
						return true;              // return true here to continue receiving results
				    }
				},
				N,                            // the number of nearby rectangles to find
				Float.MAX_VALUE               // Don't bother searching further than this. MAX_VALUE means search everything
		);
		
		ArrayList<InvPendGraphNode> resNodes = new ArrayList<InvPendGraphNode>();
		
		for (int i = 0; i<N; i++) {
			InvPendState s = new InvPendState();
			s.setAngle(rects[rectIndecies.get(i)].maxX);
			s.setAngVel(rects[rectIndecies.get(i)].maxY);
			//System.out.println(s.getAngle() + " " + s.getAngVel());
			InvPendGraphNode n = this.graph.searchNode(s, 0.00001);
			
			resNodes.add(n);
			//System.out.println(n.getState().getAngle() + " " + n.getState().getAngVel());
		}
		
		return resNodes;
	}
}
