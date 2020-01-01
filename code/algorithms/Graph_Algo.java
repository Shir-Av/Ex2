package algorithms;

import java.io.*;
import java.util.*;

import GUI.Graph_GUI;
import dataStructure.*;
import utils.Point3D;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author
 *
 */
public class Graph_Algo implements graph_algorithms {
	public graph graph_algo;

	public Graph_Algo() {
		this.graph_algo = new DGraph();
	}

	public Graph_Algo(graph graph_algo) {
		this.graph_algo = graph_algo;
	}

	@Override
	public void init(graph g) {
		this.graph_algo = g;

	}

	@Override
	public void init(String file_name) {
		try {
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream in = new ObjectInputStream(file);

			this.graph_algo = (graph) in.readObject();

			in.close();
			file.close();

			System.out.println("Object has been deserialized");

		} catch (IOException ex) {
			System.out.println("IOException is caught");
		} catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException is caught");
		}

	}

	@Override
	public void save(String file_name) {
		try {
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(this.graph_algo);

			out.close();
			file.close();

			System.out.println("Object has been serialized");
		} catch (IOException ex) {
			System.out.println("IOException is caught");
		}

	}

	@Override
	public boolean isConnected() {
		if (this.hasEdges()) {
			for (node_data n : this.graph_algo.getV()) {
				this.initTag();
				int src = n.getKey();
				ArrayList<Integer> newCol = new ArrayList<Integer>();
				if (!hasAllPaths(src, newCol)) {
					return false;
				}
			}
		}
		return true;
	}

	private void initTag() {
		for (node_data n : this.graph_algo.getV()) {
			n.setTag(0);
		}
	}

	private boolean hasEdges() {
		for (node_data n : this.graph_algo.getV()) {
			int i = n.getKey();
			Collection<edge_data> temp = this.graph_algo.getE(i);
			if (temp == null) {
				return false;
			}
		}
		return true;
	}

	private boolean hasAllPaths(int src, ArrayList<Integer> newCol) {
		Collection<edge_data> temp = this.graph_algo.getE(src);
		this.graph_algo.getNode(src).setTag(1);
		if (!newCol.contains(src)) {
			newCol.add(src);
		}

		if (newCol.size() == this.graph_algo.nodeSize()){
			return true;
		}
		for (edge_data e : temp)
		{
			node_data d = this.graph_algo.getNode(e.getDest());
			if (!newCol.contains(d.getKey()))
			{
				newCol.add(d.getKey());
			}
		}
		for (int i=0 ; i<newCol.size() ; i++){
			node_data d = this.graph_algo.getNode(newCol.get(i));
			if (d.getTag() == 0)
			{
				return hasAllPaths(d.getKey(), newCol);
			}
		}
		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest)
	{
		this.initTag(); //set all tags to 0
		double weight;
		node_data srcNode = this.graph_algo.getNode(src);
		node_data destNode = this.graph_algo.getNode(dest);
		srcNode.setWeight(0);

		while (srcNode.getKey() != destNode.getKey() && srcNode.getTag() == 0)
		{
			Collection <edge_data> edges = this.graph_algo.getE(srcNode.getKey());
			if(!edges.isEmpty())
			{
				for (edge_data e: edges)
				{
					node_data tmpDest = this.graph_algo.getNode(e.getDest());
					weight = srcNode.getWeight() + e.getWeight();
					if(weight < tmpDest.getWeight() && tmpDest.getTag() == 0)
					{
						tmpDest.setInfo("" + srcNode.getKey());
						tmpDest.setWeight(weight);
					}
				}
			}
			srcNode.setTag(1);
			srcNode = minWeight();

		}
		return srcNode.getWeight();
	}

	private node_data minWeight () // returns the node with the min weight and tag 0 in the collection
	{
		Collection <node_data> node = this.graph_algo.getV();
		node_data tmpN = new NodeData();
		for(node_data n: node)
		{
			if (n.getTag() == 0 && n.getWeight() < tmpN.getWeight())
			{
				tmpN = n;
			}
		}
		return tmpN;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public graph copy() {

		return null;
	}

	public static void main(String[] args) {

		//is conected
		graph g = new DGraph();
		graph_algorithms ga = new Graph_Algo();
		NodeData n1 = new NodeData(1, new Point3D(3, 6, 0));
		g.addNode(n1);
		g.addNode(new NodeData(2, new Point3D(1000, 1000)));
		g.addNode(new NodeData(3, new Point3D(900, -200)));
		g.addNode(new NodeData(4, new Point3D(405, 500)));
		g.addNode(new NodeData(5, new Point3D(200, -100)));
		g.addNode(new NodeData(6, new Point3D(300, 600)));
		g.connect(1, 2, 7);
		g.connect(1, 5, 7);
		g.connect(2, 1, 2);
		g.connect(2, 4, 2);
		g.connect(3, 4, 1);
		g.connect(3, 2, 1);
		g.connect(4, 3, 3);
		g.connect(5, 1, 1);
		g.connect(5, 2, 4);
		g.connect(5, 6, 4);
		g.connect(6, 1, 4);
		ga.init(g);
		System.out.println(ga.isConnected());

		//shortest path

		graph g1 = new DGraph();
		graph_algorithms ga1 = new Graph_Algo();

		g1.addNode(new NodeData(0, new Point3D(1000, 1000)));
		g1.addNode(new NodeData(1, new Point3D(900, -200)));
		g1.addNode(new NodeData(2, new Point3D(405, 500)));
		g1.addNode(new NodeData(3, new Point3D(200, -100)));
		g1.addNode(new NodeData(4, new Point3D(300, 600)));
		g1.connect(0, 1, 10);
		g1.connect(0, 4, 5);
		g1.connect(1, 4, 2);
		g1.connect(1, 2, 1);
		g1.connect(2, 3, 4);
		g1.connect(3, 0, 7);
		g1.connect(3, 2, 6);
		g1.connect(4, 1, 3);
		g1.connect(4, 2, 9);
		g1.connect(4, 3, 2);

		ga1.init(g1);
		System.out.println(ga1.shortestPathDist(0,3));





	}
}