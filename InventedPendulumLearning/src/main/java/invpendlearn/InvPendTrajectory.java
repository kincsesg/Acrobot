package invpendlearn;

import java.util.ArrayList;

import invpendgraph.InvPendGraphNode;
import utils.InterpolatedUtil;

public class InvPendTrajectory {
	private ArrayList<ArrayList<InvPendGraphNode>> nears = new ArrayList<ArrayList<InvPendGraphNode>>();
	private ArrayList<ArrayList<Double>> ratios = new ArrayList<ArrayList<Double>>();
	private ArrayList<Double> deltaU = new ArrayList<Double>();
	private ArrayList<InvPendState> states = new ArrayList<InvPendState>();

	public InvPendTrajectory() {
		this.nears = new ArrayList<ArrayList<InvPendGraphNode>>();
		this.ratios = new ArrayList<ArrayList<Double>>();
		this.deltaU = new ArrayList<Double>();
		this.states = new ArrayList<InvPendState>();
	}
	
	public void add(ArrayList<InvPendGraphNode> nearTriplet, InvPendState s, ArrayList<Double> ratioTriplet, double dU) {
		this.nears.add(nearTriplet);
		this.ratios.add(ratioTriplet);
		this.deltaU.add(dU);
		this.states.add(s);
	}
	
	public void add(InvPendGraphNode near1, InvPendGraphNode near2, InvPendGraphNode near3, InvPendState s, ArrayList<ArrayList<Double>> r, double dU) {
		ArrayList<InvPendGraphNode> nearTriplet = new ArrayList<InvPendGraphNode>();
		nearTriplet.add(near1);
		nearTriplet.add(near2);
		nearTriplet.add(near3);
		this.nears.add(nearTriplet);
		this.ratios = r;
		this.deltaU.add(dU);
		this.states.add(s);
	}
	
	public void add(InterpolatedUtil intU, ArrayList<InvPendGraphNode> nearTriplet, InvPendState s) {
		this.nears.add(nearTriplet);
		this.ratios.add(intU.getRatios());
		this.deltaU.add(intU.getDeltaU());
		this.states.add(s);
	}
	
	public int getSize() {
		return this.states.size();
	}
	
	public InvPendState getState(int i) {
		return this.states.get(i);
	}
	
	public ArrayList<InvPendGraphNode> getNodes(int i) {
		return this.nears.get(i);
	}
	
	public ArrayList<Double> getRewards(int stateNum, int horizon) {
		ArrayList<Double> result = new ArrayList<Double>();
		
		for (int i = stateNum + 1; i <= stateNum + horizon; i++) {
			if (i < this.states.size()) {
				result.add(this.states.get(i).getReward());
			}
		}
		
		return result;
	}
}
