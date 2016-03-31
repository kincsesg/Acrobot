package invpendlearn;

import java.util.ArrayList;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;

import invpendgraph.InvPendGraph;
import invpendgraph.InvPendGraphConfiguration;
import invpendgraph.InvPendGraphCreator;
import invpendgraph.InvPendGraphNode;
import rx.Observable;

public class InvPendStateRepo {
	private InvPendGraph graph;
	private RTree<String, Point> tree;
	private int size;
	
	//1.0, 1.0, 0.2, 0.2, 10, 10
	public InvPendStateRepo(InvPendModel model, double angleMin, double angVelMin, double angleStep, double angVelStep, int angleCount, int angVelCount) {
		this.size = angleCount * angVelCount;
		InvPendGraphCreator grcr = new InvPendGraphCreator();
		InvPendGraphConfiguration config = new InvPendGraphConfiguration(angleMin, angVelMin, angleStep, angVelStep, angleCount, angVelCount);
		grcr.setConfig(config);
		grcr.createGraph(model);
		this.graph = grcr.getGraph();
		
		RTree<String, Point> tree = RTree.star().create();
		ArrayList<InvPendGraphNode> nodes = grcr.getGraph().getNodes();
		for(int i = 0; i < this.size; i++) {
			InvPendGraphNode n = nodes.get(i);
			float x = (float) n.getState().getAngle();
			float y = (float) n.getState().getAngVel();
			tree = tree.add(Integer.toString(i), Geometries.point(x,y));
		}
		
		this.tree = tree;
	}
	
	public ArrayList<InvPendGraphNode> getNNearestNodes(InvPendState state, int N) {
		Point p = Geometries.point((float)state.getAngle(), (float)state.getAngVel());
		Observable<Entry<String, Point>> results = tree.nearest(p, Double.POSITIVE_INFINITY, N);
		Iterable<Entry<String, Point>> iterRes = results.toBlocking().toIterable();
		ArrayList<InvPendGraphNode> resNodes = new ArrayList<InvPendGraphNode>();
		
		for (Entry<String, Point> e : iterRes) {
			p = e.geometry();
			InvPendState s = new InvPendState(p.x(), p.y());
			InvPendGraphNode n = this.graph.searchNode(s, 0.00001);
			resNodes.add(n);
		}
		
		return resNodes;
	}
	
	public InvPendGraph getGraph() {
		return graph;
	}
}