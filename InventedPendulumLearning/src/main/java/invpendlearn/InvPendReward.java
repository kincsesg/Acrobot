package invpendlearn;

import rltools.RLReward;

public class InvPendReward implements RLReward{
	private double value;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
