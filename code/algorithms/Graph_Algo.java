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

	public Graph_Algo(graph graph_algo)
	{
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

			graph ga = (graph) in.readObject();
			graph_algo = ga;

		//	this.graph_algo = (graph) in.readObject();
			System.out.println("Object has been deserialized");

			in.close();
			file.close();

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
		if(this.graph_algo.nodeSize() == 1)
		{
			return true;
		}
		if (this.hasEdges()) {
			for (node_data n : this.graph_algo.getV()) {
				this.initTag();
				int src = n.getKey();
				ArrayList<Integer> newCol = new ArrayList<Integer>();
				if (!hasAllPaths(src, newCol)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	private void initTag() {
		for (node_data n : this.graph_algo.getV()) {
			n.setTag(0);
			n.setWeight(Integer.MAX_VALUE);
			n.setInfo("");
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
		shortestPathDist(src, dest);
		List<node_data> path = new LinkedList<node_data>();

		node_data nodeDest = this.graph_algo.getNode(dest);
		while (nodeDest.getKey() != src)
		{
			path.add(nodeDest);
			nodeDest = this.graph_algo.getNode(Integer.parseInt(nodeDest.getInfo()));
		}
		path.add(this.graph_algo.getNode(src));
		Collections.reverse(path);
		return path;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets)
	{
		List  <node_data> TSPlist = new LinkedList<node_data>();
		int src = 0;
		int dest = 0;
		if (targets.size() == 0)
		{
			System.out.println("EROR: the targets list is empty");
			return null;
		}
		else if(targets.size() == 1)
		{
			src = targets.get(0);
			TSPlist.add(this.graph_algo.getNode(src));
			return TSPlist;
		}
		else
		{
			for (int i = 0; i < targets.size()-1; i++)
			{
				src = targets.get(i);
				dest = targets.get(i+1);
				node_data srcNode = this.graph_algo.getNode(src);
				node_data destNode = this.graph_algo.getNode(dest);
				if (this.graph_algo.getV().contains(srcNode) && this.graph_algo.getV().contains(destNode))
				{
					List <node_data> temp = new LinkedList<node_data>();
					temp = shortestPath(src, dest);
					for (int j = 0; j < temp.size()-1 ; j++)
					{
						//node_data node = temp.get(i);
						TSPlist.add(temp.get(j));
					}
				}
				else
				{
					System.out.println("EROR: some of the nodes does nor exist");
				}
			}
			TSPlist.add(this.graph_algo.getNode(targets.get(targets.size()-1)));
		}
		return TSPlist;
	}

	@Override
	public graph copy() {

		graph temp = new DGraph();
		Collection<node_data> Nodes = graph_algo.getV();

		for (node_data Node : Nodes) {
			temp.addNode(Node);
		}
		for (node_data Node : Nodes) {
			Collection<edge_data> Edges = graph_algo.getE(Node.getKey());

			for (edge_data Edge : Edges) {
				temp.connect(Edge.getSrc(), Edge.getDest(), Edge.getWeight());
			}
		}
		return temp;
	}

	public static void main(String[] args) {

		//is conected
		/*graph g = new DGraph();
		graph_algorithms ga = new Graph_Algo();
		NodeData n1 = new NodeData(1, new Point3D(3, 6, 0));
		g.addNode(n1);
		g.addNode(new NodeData(2, new Point3D(1000, 1000)));
		g.addNode(new NodeData(3, new Point3D(900, -200)));
		g.addNode(new NodeData(4, new Point3D(405, 500)));
		g.addNode(new NodeData(5, new Point3D(200, -100)));
		g.addNode(new NodeData(6, new Point3D(300, 600)));

		g.connect(6,4,9);
		g.connect(6,5,2);

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
		System.out.println(ga.isConnected());*/

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
		/*System.out.println(ga1.shortestPathDist(0,2));
		System.out.println(ga1.shortestPath(0,2));*/
		//System.out.println(ga1.isConnected());
		List <Integer> temp =new LinkedList<>();
		temp.add(0);
		temp.add(4);
		temp.add(3);
		temp.add(1);

		System.out.println(ga1.TSP(temp));





	}
}