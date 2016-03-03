package invpendlearn;

import rltools.RLAction;

public class InvPendAction implements RLAction {
	private double POSMAXLIMIT = 5.0;
	private double NEGMAXLIMIT = -5.0;
	private double max;
	private double min;

	public double getPOSMAXLIMIT() {
		return POSMAXLIMIT;
	}

	public double getNEGMAXLIMIT() {
		return NEGMAXLIMIT;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}
	
	public InvPendAction() {
		this.min = 0.0;
		this.max = 0.0;
	}	

	public InvPendAction(double min, double max) {
		this.min = min;
		this.max = max;
	}	
}
