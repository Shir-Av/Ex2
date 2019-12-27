package algorithms;

import java.io.*;
import java.util.List;
import java.util.PriorityQueue;

import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.graph;
import dataStructure.node_data;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author
 *
 */
public class Graph_Algo implements graph_algorithms{
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
	public void init(String file_name)
	{
		try
		{
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream in = new ObjectInputStream(file);

			graph_algo = (graph)in.readObject();

			in.close();
			file.close();

			System.out.println("Object has been deserialized");

		}

		catch(IOException ex)
		{
			System.out.println("IOException is caught");
		}

		catch(ClassNotFoundException ex)
		{
			System.out.println("ClassNotFoundException is caught");
		}


	}

	@Override
	public void save(String file_name) {
		try
		{
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(graph_algo);

			out.close();
			file.close();

			System.out.println("Object has been serialized");
		}
		catch(IOException ex)
		{
			System.out.println("IOException is caught");
		}


	}

	@Override
	public boolean isConnected() {

		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
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

}