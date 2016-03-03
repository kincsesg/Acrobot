package invpendlearn;

import rltools.RLState;

public class InvPendState extends RLState {
	private double angle;
	private double angularVelocity;
	private double[] uncertainity;
	private double utility;
	private double reward;

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		if (angle < -Math.PI) {
			this.angle = Math.PI - (angle % Math.PI);
		} else if (angle > Math.PI) {
			this.angle = (angle % Math.PI) - Math.PI;
		} else {
			this.angle = angle;
		}
	}

	public double getAngVel() {
		return angularVelocity;
	}

	public void setAngVel(double angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	public double[] getUncertainity() {
		return uncertainity;
	}

	public void setUncertainity(double[] uncertaintiy) {
		this.uncertainity = uncertaintiy;
	}

	public double getUtil() {
		return utility;
	}

	public void setUtil(double util) {
		this.utility = util;
	}

	public double getReward() {
		return reward;
	}

	public void setReward(double r) {
		this.reward = r;
	}

	public InvPendState() {
		angle = 0.0;
		angularVelocity = 0.0;
		uncertainity = new double[] { 0.0, 0.0 };
		utility = 0.0;
		reward = 1.0;
	}

	public InvPendState(double ang, double angVel) {
		if (ang < -Math.PI) {
			this.angle = Math.PI - (ang % Math.PI);
		} else if (ang > Math.PI) {
			this.angle = (ang % Math.PI) - Math.PI;
		} else {
			this.angle = ang;
		}

		this.angularVelocity = angVel;
		this.uncertainity = new double[] { 0.0, 0.0 };
		this.utility = 0.0;

		if (Math.PI - Math.abs(this.angle) < Math.PI / 36) {
			//this.reward = Math.PI / 36 - (Math.PI - Math.abs(this.angle)) / Math.PI / 36 - 0.1 * Math.abs(this.angularVelocity);
			this.reward = Math.cos(this.angle) * -1;
		} else {
			//ne legyen negativ jutalom vagy csak nagyon nagyon kicsi
			//this.reward = -0.1;
			this.reward = 0.0;
		}
	}

	public InvPendState(double ang, double angVel, double[] unc) {
		if (ang < -Math.PI) {
			this.angle = Math.PI - (ang % Math.PI);
		} else if (ang > Math.PI) {
			this.angle = (ang % Math.PI) - Math.PI;
		} else {
			this.angle = ang;
		}

		this.angularVelocity = angVel;
		this.uncertainity = unc;
		this.utility = 0.0;
		
		if (Math.PI - Math.abs(this.angle) < Math.PI / 36) {
			//this.reward = Math.PI / 36 - (Math.PI - Math.abs(this.angle)) / Math.PI / 36 - 0.1 * Math.abs(this.angularVelocity);
			this.reward = Math.cos(this.angle) * -1;
		} else {
			//ne legyen negativ jutalom vagy csak nagyon nagyon kicsi
			//this.reward = -0.1;
			this.reward = 0.0;
		}
	}

	public InvPendState(double ang, double angVel, double[] unc, double util) {
		if (ang < -Math.PI) {
			this.angle = Math.PI - (ang % Math.PI);
		} else if (ang > Math.PI) {
			this.angle = (ang % Math.PI) - Math.PI;
		} else {
			this.angle = ang;
		}

		this.angularVelocity = angVel;
		this.uncertainity = unc;
		this.utility = util;
		
		if (Math.PI - Math.abs(this.angle) < Math.PI / 36) {
			//this.reward = Math.PI / 36 - (Math.PI - Math.abs(this.angle)) / Math.PI / 36 - 0.1 * Math.abs(this.angularVelocity);
			this.reward = Math.cos(this.angle) * -1;
		} else {
			//ne legyen negativ jutalom vagy csak nagyon nagyon kicsi
			//this.reward = -0.1;
			this.reward = 0.0;
		}
	}
	
	public InvPendState(double ang, double angVel, double[] unc, double util, double rew) {
		if (ang < -Math.PI) {
			this.angle = Math.PI - (ang % Math.PI);
		} else if (ang > Math.PI) {
			this.angle = (ang % Math.PI) - Math.PI;
		} else {
			this.angle = ang;
		}

		this.angularVelocity = angVel;
		this.uncertainity = unc;
		this.utility = util;
		this.reward = rew;
	}

	public double eucDist(InvPendState s) {
		return Math.sqrt(Math.pow(this.angle - s.getAngle(), 2) + Math.pow(this.angularVelocity - s.getAngVel(), 2));
	}
	
	public boolean sameState(InvPendState s, double treshold) {
		if (Math.abs(this.angle - s.getAngle()) <= treshold && Math.abs(this.angularVelocity - s.getAngVel()) <= treshold) {
			return true;
		}
		
		return false;
	}
}