package test;

import invpendlearn.InvPendLearningEngine;

public class Test {

	public static void main(String[] args) {
		
		/*SpatialIndex si = new RTree();
		si.init(null);
		
		final Rectangle[] rects = new Rectangle[2];
		rects[0] = new Rectangle(1.0f, 1.0f, 1.0f, 1.0f);
		rects[1] = new Rectangle(2.0f, 2.0f, 2.0f, 2.0f);

		si.add(rects[0], 0);
		si.add(rects[1], 1);
		
		Point p1 = new Point(1.5f, 1.5f);
		Point p2 = new Point(0.45f, 0.75f);
	
		si.nearest(
				p1,      // the point for which we want to find nearby rectangles
				  new TIntProcedure() {         // a procedure whose execute() method will be called with the results
				    public boolean execute(int i) {
				      System.out.println("Rectangle " + i + " " + rects[i] + ", distance=" + rects[i].distance(p1));
				      return true;              // return true here to continue receiving results
				    }
				  },                            // the number of nearby rectangles to find
				  Float.MAX_VALUE               // Don't bother searching further than this. MAX_VALUE means search everything
				);
		si.nearestN(
				p1,      // the point for which we want to find nearby rectangles
				  new TIntProcedure() {         // a procedure whose execute() method will be called with the results
				    public boolean execute(int i) {
				      System.out.println("Rectangle " + i + " " + rects[i] + ", distance=" + rects[i].distance(p1));
				      return true;              // return true here to continue receiving results
				    }
				  },
				  3,                            // the number of nearby rectangles to find
				  Float.MAX_VALUE               // Don't bother searching further than this. MAX_VALUE means search everything
				);
		
		InvPendState state11 = new InvPendState(1.0, 1.0);
		InvPendState state22 = new InvPendState(2.0, 2.0);
		InvPendState staten1n1 = new InvPendState(-1.0, -1.0);
		InvPendState staten12 = new InvPendState(1.0, 1.0);
		InvPendState state1n2 = new InvPendState(1.0, 1.0);
		
		InvPendModel model = new InvPendModel();
		model.setGacc(10);
		model.setLength(1);
		model.setMass(1);
		model.setMu(0.03);
		model.setTimeunit(0.2);
		
		System.out.println(model.isReachableByOneStep(state11, state1n2));
		System.out.println(model.isReachableByOneStep(state22, state1n2));
		System.out.println(model.isReachableByOneStep(staten1n1, state1n2));
		System.out.println(model.isReachableByOneStep(staten12, state1n2));
		System.out.println(model.isReachableByOneStep(state1n2, state11));
		
		InvPendState s = (InvPendState) model.getNextState(state11, new InvPendAction(3.0, 3.0));
		System.out.println(s.getAngle() + " " + s.getAngularVelocity());
		s.setAngle(1.6);
		s.setAngularVelocity(3.2);
		System.out.println(model.isReachableByOneStep(state11, s));
		
		String csvFile = "1_1.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		
		try {

			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			while ((line = br.readLine()) != null) {

			        // use comma as separator
				String[] data = line.split(cvsSplitBy);
				if(!data[2].equalsIgnoreCase("0.0") && !data[3].equalsIgnoreCase("0.0")) {
					s.setAngle(Double.parseDouble(data[2]));
					s.setAngularVelocity(Double.parseDouble(data[3]));
					System.out.println(model.isReachableByOneStep(state11, s));
				}
			}
			s.setAngle(1.4);
			s.setAngularVelocity(3.0);
			System.out.println(model.isReachableByOneStep(state11, s));
			s.setAngle(1.5);
			s.setAngularVelocity(2.5);
			System.out.println(model.isReachableByOneStep(state11, s));
			s.setAngle(1.5);
			s.setAngularVelocity(3.0);
			System.out.println(model.isReachableByOneStep(state11, s));
			s.setAngle(1.4);
			s.setAngularVelocity(2.5);
			System.out.println(model.isReachableByOneStep(state11, s));
			s.setAngle(1.42);
			s.setAngularVelocity(2.7);
			System.out.println(model.isReachableByOneStep(state11, s));
			
		}  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");*/
		
		/*InvPendModel model = new InvPendModel();
		model.setGacc(10);
		model.setLength(1);
		model.setMass(1);
		model.setMu(0.03);
		model.setTimeunit(0.2);
		
		InvPendState state11 = new InvPendState(1.0, 1.0);
		InvPendState state1425 = new InvPendState(1.4, 2.5);
		InvPendState state153 = new InvPendState(1.5, 3.0);
		InvPendState state143 = new InvPendState(1.4, 3.0);
		InvPendState state162 = new InvPendState(1.6, 2.0);
		InvPendState state11 = new InvPendState(1.0, 1.0);
		InvPendState staten1n2 = new InvPendState(-1.0, -2.0);
		InvPendState state12 = new InvPendState(1.0, 2.0);
		InvPendState state1n1 = new InvPendState(1.0, -1.0);
		InvPendState state22 = new InvPendState(2.0, 2.0);
		
		System.out.println(System.currentTimeMillis());
		System.out.println(model.isReachableByOneStep(state11, state1425));
		System.out.println(System.currentTimeMillis());
		System.out.println(model.isReachableByOneStep(state11, state153));
		System.out.println(System.currentTimeMillis());
		System.out.println(model.isReachableByOneStep(state11, state143));
		System.out.println(System.currentTimeMillis());
		System.out.println(model.isReachableByOneStep(state11, state162));
		System.out.println(System.currentTimeMillis());*/
		//System.out.println(model.isReachableByOneStep(state11, state11));
		//System.out.println(model.isReachableByOneStep(staten1n2, state11));
		//System.out.println(model.isReachableByOneStep(state12, state11));
		//System.out.println(model.isReachableByOneStep(state1n1, state11));
		//System.out.println(model.isReachableByOneStep(state22, state11));
		
		/*InvPendGraphCreator grcr = new InvPendGraphCreator();
		InvPendModel model = new InvPendModel(10,1,1,0.03,0.2);
		InvPendGraphConfiguration config = new InvPendGraphConfiguration(1.0, 1.0, 0.2, 0.2, 10, 10);
		grcr.setConfig(config);
		grcr.createGraph(model);
		
		RTree tree = new RTree();
		tree.init(null);
		
		Rectangle[] rects = new Rectangle[100];
		ArrayList<InvPendGraphNode> nodes = grcr.getGraph().getNodes();
		for(int i = 0; i < 100; i++) {
			InvPendGraphNode n = nodes.get(i);
			float x = (float) n.getState().getAngle();
			float y = (float) n.getState().getAngVel();
			Rectangle r = new Rectangle(x,y,x,y);
			System.out.println(x+" "+y);
			rects[i] = r;
			tree.add(r, i);
		}
		
		for (int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).getOut().size() != 0) {
				System.out.println("Angle: "+nodes.get(i).getState().getAngle()+"  AngVel: "+nodes.get(i).getState().getAngle());
				for (int j = 0; j < nodes.get(i).getOut().size(); j++) {
					System.out.println("Reachable state - Angle: "+nodes.get(i).getOut().get(j).getState().getAngle()+"  AngVel: "+nodes.get(i).getOut().get(j).getState().getAngVel());
				}
			}
		}
		
		Point p50 = new Point(rects[49].maxX+100, rects[49].maxY+100);
		int[] rectInd = new int[3];
		int count = 0;
		ArrayList<Integer> rectis = new ArrayList<Integer>();
		tree.nearestN(
				p50,      // the point for which we want to find nearby rectangles
				new TIntProcedure() {         // a procedure whose execute() method will be called with the results
					public boolean execute(int i) {
						System.out.println("Rectangle " + i + " " + rects[49] + ", distance=" + rects[49].distance(p50));
						rectis.add(i);
						return true;              // return true here to continue receiving results
				    }
				},
				3,                            // the number of nearby rectangles to find
				Float.MAX_VALUE               // Don't bother searching further than this. MAX_VALUE means search everything
		);
		
		System.out.println(rectis.get(0)+" "+rectis.get(1)+" "+rectis.get(2));
		System.out.println("maxX:"+p50.x+" minX:"+p50.x+" maxY:"+p50.y+" minY:"+p50.y);
		System.out.println("maxX:"+rects[rectis.get(0)].maxX+" minX:"+rects[rectis.get(0)].minX+" maxY:"+rects[rectis.get(0)].maxY+" minY:"+rects[rectis.get(0)].minY);
		System.out.println("maxX:"+rects[rectis.get(1)].maxX+" minX:"+rects[rectis.get(1)].minX+" maxY:"+rects[rectis.get(1)].maxY+" minY:"+rects[rectis.get(1)].minY);
		System.out.println("maxX:"+rects[rectis.get(2)].maxX+" minX:"+rects[rectis.get(2)].minX+" maxY:"+rects[rectis.get(2)].maxY+" minY:"+rects[rectis.get(2)].minY);*/
		
		/*InvPendGraph graph = grcr.getGraph();
		InvPendGraphNode node30 = graph.getNodes().get(30);
		InvPendState s30 = node30.getState();
		
		for (int i = 0; i < graph.getNodes().size(); i++) {
			System.out.println(i+": IN: "+graph.getNodes().get(i).getIn().size()+" OUT: "+graph.getNodes().get(i).getOut().size());
		}
		
		System.out.println(-23.5 % (2*Math.PI));
		System.out.println(s30.getAngle()+" "+s30.getAngle());
		
		System.out.println("INs: " + node30.getIn().size());
		for (int i = 0; i<node30.getIn().size(); i++) {
			System.out.println(node30.getIn().get(i).getState().getAngle()+" "+node30.getIn().get(i).getState().getAngVel());
		}
		
		System.out.println("OUTs: " + node30.getOut().size());
		for (int i = 0; i<node30.getOut().size(); i++) {
			System.out.println(node30.getOut().get(i).getState().getAngle()+" "+node30.getOut().get(i).getState().getAngVel());
		}*/
		
		InvPendLearningEngine engine = new InvPendLearningEngine();
		engine.TdLamda(0.5, 100, 100, 10, 50);
	}
}
