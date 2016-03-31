package invpendlearn;

import java.util.ArrayList;

import invpendgraph.InvPendGraphNode;
import utils.InterpolatedUtil;
import utils.Point2D;

public class InvPendUtilComputer {
	
	public InterpolatedUtil interpolateUtil(InvPendState s, ArrayList<InvPendGraphNode> nodes) {
		int edgeCount = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				InvPendGraphNode inode = nodes.get(i);
				InvPendGraphNode jnode = nodes.get(j);
				if (inode.outContains(jnode) || inode.inContains(jnode)) {
					edgeCount++;
				}
			}
		}
		
		double u1 = nodes.get(0).getState().getUtil();
		double u2 = nodes.get(1).getState().getUtil();
		double u3 = nodes.get(2).getState().getUtil();
		
		Point2D tp = new Point2D();
		Point2D p1 = new Point2D();
		Point2D p2 = new Point2D();
		Point2D p3 = new Point2D();
		
		tp.x = s.getAngle();
		tp.y = s.getAngVel();
		p1.x = nodes.get(0).getState().getAngle();
		p1.y = nodes.get(0).getState().getAngVel();
		p2.x = nodes.get(1).getState().getAngle();
		p2.y = nodes.get(1).getState().getAngVel();
		p3.x = nodes.get(2).getState().getAngle();
		p3.y = nodes.get(2).getState().getAngVel();
		
		double d_tpp1 = tp.eucDist(p1);
		double d_tpp2 = tp.eucDist(p2);
		double d_tpp3 = tp.eucDist(p3);
		
		Point2D clp1 = getClosestPoint(p1, p2, tp);
		Point2D clp2 = getClosestPoint(p2, p3, tp);
		Point2D clp3 = getClosestPoint(p3, p1, tp);
		
		double d_tpclp1 = tp.eucDist(clp1);
		double d_tpclp2 = tp.eucDist(clp2);
		double d_tpclp3 = tp.eucDist(clp3);
		
		double d_clp1p1 = clp1.eucDist(p1);
		double d_clp1p2 = clp1.eucDist(p2);
		double d_clp2p2 = clp2.eucDist(p2);
		double d_clp2p3 = clp2.eucDist(p3);
		double d_clp3p1 = clp3.eucDist(p1);
		double d_clp3p3 = clp3.eucDist(p3);
		
		InterpolatedUtil result = new InterpolatedUtil();
		double U = 0.0;
		ArrayList<Double> ratios = new ArrayList<Double>(); 
		double deltaU = 0.0;
		
		switch (edgeCount) {
			case 0:
				// 0 edge
				 U = min(d_tpp1, d_tpp2, d_tpp3);
				if (U == d_tpp1) {
					ratios.add(1.0);
					ratios.add(0.0);
					ratios.add(0.0);
				} else if(U == d_tpp2) {
					ratios.add(0.0);
					ratios.add(1.0);
					ratios.add(0.0);
				} else if(U == d_tpp3) {
					ratios.add(0.0);
					ratios.add(0.0);
					ratios.add(1.0);
				}
			break;	
			case 1:
				// 1 edge
				U = (u3 * d_clp3p1 + u1 * d_clp3p3)/(d_clp3p1 + d_clp3p3);
				ratios.add(d_clp3p3/(d_clp3p1 + d_clp3p3));
				ratios.add(0.0);
				ratios.add(d_clp3p1/(d_clp3p1 + d_clp3p3));
			break;	
			case 2:
				// 1 edge
				U = (u2 * d_clp2p3 + u3 * d_clp2p2)/(d_clp2p3 + d_clp2p2); 
				ratios.add(0.0);
				ratios.add(d_clp2p3/(d_clp2p3 + d_clp2p2));
				ratios.add(d_clp2p2/(d_clp2p3 + d_clp2p2));
			break;	
			case 3:
				// 2 edge
				if (d_tpclp2 <= d_tpclp3) {
					U = (u2 * d_clp2p3 + u3 * d_clp2p2)/(d_clp2p3 + d_clp2p2);
					ratios.add(0.0);
					ratios.add(d_clp2p3/(d_clp2p3 + d_clp2p2));
					ratios.add(d_clp2p2/(d_clp2p3 + d_clp2p2));
				} else {
					U = (u3 * d_clp3p1 + u1 * d_clp3p3)/(d_clp3p1 + d_clp3p3);
					ratios.add(d_clp3p3/(d_clp3p1 + d_clp3p3));
					ratios.add(0.0);
					ratios.add(d_clp3p1/(d_clp3p1 + d_clp3p3));
				}
			break;		
			case 4:
				// 1 edge
				U = (u1 * d_clp1p2 + u2 * d_clp1p1)/(d_clp1p2+d_clp1p1);
				ratios.add(d_clp1p2/(d_clp1p2+d_clp1p1));
				ratios.add(d_clp1p1/(d_clp1p2+d_clp1p1));
				ratios.add(0.0);
			break;	
			case 5:
				// 2 edge
				if (d_tpclp1 <= d_tpclp3) {
					U = (u1 * d_clp1p2 + u2 * d_clp1p1)/(d_clp1p2+d_clp1p1);
					ratios.add(d_clp1p2/(d_clp1p2+d_clp1p1));
					ratios.add(d_clp1p1/(d_clp1p2+d_clp1p1));
					ratios.add(0.0);
				} else {
					U = (u3 * d_clp3p1 + u1 * d_clp3p3)/(d_clp3p1+d_clp3p3);
					ratios.add(d_clp3p3/(d_clp3p1 + d_clp3p3));
					ratios.add(0.0);
					ratios.add(d_clp3p1/(d_clp3p1 + d_clp3p3));
				}
			break;	
			case 6:
				// 2 edge
				if (d_tpclp1 <= d_tpclp2) {
					U = (u1 * d_clp1p2 + u2 * d_clp1p1)/(d_clp1p2+d_clp1p1);
					ratios.add(d_clp1p2/(d_clp1p2+d_clp1p1));
					ratios.add(d_clp1p1/(d_clp1p2+d_clp1p1));
					ratios.add(0.0);
				} else {
					U = (u2 * d_clp2p3 + u3 * d_clp2p2)/(d_clp2p3+d_clp2p2);
					ratios.add(0.0);
					ratios.add(d_clp2p3/(d_clp2p3 + d_clp2p2));
					ratios.add(d_clp2p2/(d_clp2p3 + d_clp2p2));
				}
			break;	
			case 7:
				// 3 edge
				double alfa = 3*(d_clp1p2/(d_clp1p2+d_clp1p1) + d_clp3p3/(d_clp3p1+d_clp3p3));
				double beta = 3*(d_clp2p3/(d_clp2p3+d_clp2p2) + d_clp1p1/(d_clp1p2+d_clp1p1));
				double gamma = 3*(d_clp2p2/(d_clp2p3+d_clp2p2) + d_clp3p1/(d_clp3p1+d_clp3p3));

				U = alfa*u1 + beta*u2 + gamma*u3;
				ratios.add(alfa);
				ratios.add(beta);
				ratios.add(gamma);
			break;
		}
		
		deltaU = U - s.getUtil();
		
		result.setU(U);
		result.setRatios(ratios);
		result.setDeltaU(deltaU);
		
		return result;
	}
	
	private double min(double a, double b, double c) {
		if (a <= b & a <= c) {
			return a;
		} else if (b <= a & b <= c) {
			return b;
		} else {
			return c;
		}
	}
	
	private Point2D getClosestPoint(Point2D A, Point2D B, Point2D P) { 
		//http://stackoverflow.com/questions/3120357/get-closest-point-to-a-line
		Point2D aToP = new Point2D();
		Point2D aToB = new Point2D();
		Point2D result = new Point2D();
		
		aToP.x = P.x - A.x;	//Storing vector A->P
		aToP.y = P.y - A.y;
		aToB.x = B.x - A.x; //Storing vector A->B
		aToB.x = B.y - A.y;
		
		double aToB2 = Math.pow(aToB.x, 2) + Math.pow(aToB.y, 2); //Basically finding the squared magnitude of aToB


		double aToP_dot_aToB = aToP.x*aToB.x + aToP.y*aToB.y; //The dot product of a_to_p and a_to_b

		double t = aToP_dot_aToB/aToB2;	//The normalized "distance" from a to your closest point

		result.x = A.x + aToB.x*t;	//Add the distance to A, moving towards B
		result.y = A.y + aToB.y*t;
		
		return result;		
	}	
}
