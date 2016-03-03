package invpendgraph;

import rlgraph.RLGraphConfiguration;

public class InvPendGraphConfiguration extends RLGraphConfiguration {
	private double angleMin;
	//private double angleMax;
	private double angVelMin;
	//private double angVelMax;
	private double angleStep;
	private double angVelStep;
	private int angleCount;
	private int angVelCount;
	//private int roundConst;

	public InvPendGraphConfiguration(double angleMin, double angVelMin, double angleStep, double angVelStep, int angleCount, int angVelCount) {
		this.angleMin = angleMin;
		this.angVelMin = angVelMin;
		this.angleStep = angleStep;
		this.angVelStep = angVelStep;
		this.angleCount = angleCount;
		this.angVelCount = angVelCount;
	}

	public double getAngleMin() {
		return angleMin;
	}

	public void setAngleMin(double angleMin) {
		this.angleMin = angleMin;
	}

	/*public double getAngleMax() {
		return angleMax;
	}*/

	/*public void setAngleMax(double angleMax) {
		this.angleMax = angleMax;
	}*/

	public double getAngVelMin() {
		return angVelMin;
	}

	public void setAngVelMin(double angVelMin) {
		this.angVelMin = angVelMin;
	}

	/*public double getAngVelMax() {
		return angVelMax;
	}*/

	/*public void setAngVelMax(double angVelMax) {
		this.angVelMax = angVelMax;
	}*/

	public double getAngleStep() {
		return angleStep;
	}

	public double getAngVelStep() {
		return angVelStep;
	}
	
	public int getAngleCount() {
		return angleCount;
	}

	public int getAngVelCount() {
		return angVelCount;
	}
	
	public void setAngleStep(double angleStep) {
		this.angleStep = angleStep;
	}
	
	public void setAngVelStep(double angVelStep) {
		this.angVelStep = angVelStep;
	}

	public void setAngleCount(int angleCount) {
		this.angleCount = angleCount;
	}
	
	public void setAngVelCount(int angVelCount) {
		this.angVelCount = angVelCount;
	}
	
	/*public int getRoundConst() {
		return roundConst;
	}*/
}
