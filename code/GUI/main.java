package GUI;

import dataStructure.*;
import utils.Point3D;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;

public class main {

    public static void main(String[] args) {

      /*  DGraph g = new DGraph();
        NodeData n1 = new NodeData(1, new Point3D(3,6, 0));
        g.addNode(n1);
        g.addNode(new NodeData(2, new Point3D(500, 400)));
        g.addNode(new NodeData(3, new Point3D(900, -200)));
        g.addNode(new NodeData(4, new Point3D(405, 350)));
        g.addNode(new NodeData(5, new Point3D(200, -100)));
        g.addNode(new NodeData(6, new Point3D(300, 600)));
        g.connect(1,2,7);
        g.connect(2,3,2);
        g.connect(3,4,1);
        g.connect(4,5,3);
        g.connect(5,6,1);
        g.connect(5,3,4);

        Graph_GUI gui = new Graph_GUI(g);*/
        //  gui.initGraph();
        //  gui.Paint(g);

        graph g = new DGraph();
        NodeData n1 = new NodeData(1, new Point3D(3,6, 0));
        g.addNode(n1);
        g.addNode(new NodeData(2, new Point3D(1000, 1000)));
        g.addNode(new NodeData(3, new Point3D(900, -200)));
        g.addNode(new NodeData(4, new Point3D(405, 500)));
        g.addNode(new NodeData(5, new Point3D(200, -100)));
        g.addNode(new NodeData(6, new Point3D(300, 600)));
        /*g.connect(1,2,7);
        g.connect(2,3,2);
        g.connect(3,4,1);
        g.connect(4,5,3);
        g.connect(5,6,1);
        g.connect(5,3,4);*/
        Collection<node_data> s = g.getV();
        for (node_data node1 : s) {
            for (node_data node2 : s) {
                if (node1.getKey() != node2.getKey())
                    g.connect(node1.getKey(), node2.getKey(), 0);
            }
        }
        Graph_GUI app = new Graph_GUI(g);
      //  app.setVisible(true);

    }
}
