package utils;

import java.util.ArrayList;

public class InterpolatedUtil {
	private double U;
	private ArrayList<Double> ratios; 
	private double deltaU;
	
	public double getU() {
		return U;
	}
	public void setU(double u) {
		U = u;
	}
	public ArrayList<Double> getRatios() {
		return ratios;
	}
	public void setRatios(ArrayList<Double> ratios) {
		this.ratios = ratios;
	}
	public double getDeltaU() {
		return deltaU;
	}
	public void setDeltaU(double deltaU) {
		this.deltaU = deltaU;
	}
	
	public InterpolatedUtil(){
		this.U = 0.0;
		this.ratios = new ArrayList<Double>(); 
		this.deltaU = 0.0;
	}
	
	public InterpolatedUtil(double u, ArrayList<Double> r, double du){
		this.U = u;
		this.ratios = r; 
		this.deltaU = du;
	}
}