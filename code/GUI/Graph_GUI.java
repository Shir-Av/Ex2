package GUI;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.*;
import utils.Point3D;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class Graph_GUI  extends JFrame implements ActionListener, MouseListener{
    public graph g1;
    private graph_algorithms graphAlgo;

    public Graph_GUI()
    {
        this.g1 = new DGraph();
    }

    public Graph_GUI(graph g) {
        this.g1 = g;
        initGraph(g1);
    }

    public void initGraph(graph g) {
        JFrame frame = new JFrame();
        this.setTitle("my_graph");
        this.setMenuBar(createMenuBar());
        this.setSize(1500, 900);
        //this.setBounds(50,50 , 600, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setContentPane(pane);
        this.setVisible(true);
    }

    private  MenuBar createMenuBar() {
        MenuBar MenuBar = new MenuBar();
        Menu File = new Menu("File");
        File.addActionListener(this);
        MenuBar.add(File);
        Menu Algorithms = new Menu("Algorithms");
        Algorithms.addActionListener(this);
        MenuBar.add(Algorithms);
        MenuItem Load = new MenuItem("Load");
        Load.addActionListener(this);
        File.add(Load);
        MenuItem Save = new MenuItem("Save");
        Save.addActionListener(this);
        File.add(Save);
        MenuItem initFile = new MenuItem("inittoFile");
        initFile.addActionListener(this);
        Algorithms.add(initFile);
        MenuItem isConnected = new MenuItem("isConnected");
        isConnected.addActionListener(this);
        Algorithms.add(isConnected);
        MenuItem shortestPathDist = new MenuItem("shortestPathDist");
        isConnected.addActionListener(this);
        Algorithms.add(shortestPathDist);
        MenuItem shortestPath = new MenuItem("shortestPath");
        isConnected.addActionListener(this);
        Algorithms.add(shortestPath);
        MenuItem TSP = new MenuItem("TSP");
        isConnected.addActionListener(this);
        Algorithms.add(TSP);
        this.addMouseListener(this);
        return MenuBar;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String str = e.getActionCommand();
        if (str.equals("Save")) save();
        else if(str.equals("Load")) Load();
        else  if (str.equals("init")) init();
        else if (str.equals("isConnected")) isConnected();
        else if (str.equals("shortestPathDist")) shortestPathDist();
        else if (str.equals("shortestPath")) shortestPathList();
        else if (str.equals("TSP")) TSP();
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.Draw(g);
    }
//works - need to fix print of (weight, info, key, yellow mark)
    public void Draw(Graphics g) {
        Graphics2D g1 = (Graphics2D) g;
        Point3D minP = minPoint();
        Point3D maxp = maxPoint();
        for (node_data n : this.g1.getV()) // This method return a pointer (shallow copy) for the  collection representing all the nodes in the graph
        {
            Point3D currNodeScaledData = ScaleToFrame(n.getLocation(),minP,maxp);

            g.setColor(Color.RED);
            g.fillOval(currNodeScaledData.ix(), currNodeScaledData.iy(), 15, 15); //draw a point in the x,y location
            String keyName = "";
            keyName += n.getKey(); //sets a string with the key of each point
            g.setColor(Color.BLUE);
            g.setFont(new Font("deafult", Font.BOLD, 20));
            g.drawString(keyName, currNodeScaledData.ix()-5,currNodeScaledData.iy()-5);
            String tagInfoWeight = "";
            tagInfoWeight += ("(tag: " + n.getTag() +" \n"+ "info: " + n.getInfo() + "\n"+" weight: " + n.getWeight()+ ")");
            String loc = "";
            loc += n.getLocation();
            g.setColor(Color.BLACK);
            g.setFont(new Font("deafult", Font.BOLD, 14));
            g.drawString(tagInfoWeight+"\n", currNodeScaledData.ix() +25, currNodeScaledData.iy());
            g.drawString("("+loc+")", currNodeScaledData.ix() +25, currNodeScaledData.iy()+20);
            // Draw all edges came out of the node:
            if (this.g1.getE(n.getKey()) != null) {
                for (edge_data e : this.g1.getE(n.getKey())) //return a pointer (shallow copy) for the collection representing all the edges getting out of the given node
                {
                    Point3D destNodeScaledData = ScaleToFrame(this.g1.getNode(e.getDest()).getLocation(),minP,maxp);

                    double xSrc = currNodeScaledData.x();
                    double ySrc = currNodeScaledData.y();
                    double xDest = destNodeScaledData.x();
                    double yDest = destNodeScaledData.y();

                    g.setColor(Color.BLUE);
                    g.drawLine((int) xSrc, (int) ySrc, (int) xDest, (int) yDest);
                    g.setColor(Color.BLACK);
                    //draws the weight of each edge
                    double xMid = (xSrc + xDest) / 2;
                    double yMid = (ySrc + yDest) / 2;
                    g.setFont(new Font("deafult", Font.BOLD, 20));
                    g.drawString("" + e.getWeight(), (int) xMid, (int) yMid);
                    //draws a yellow point to indicate the direction of the edge
                    g.setColor(Color.YELLOW);
                    double xPoint = 0;
                    double yPoint = 0;
                    if (xSrc < xDest && ySrc < yDest)
                    {
                        xPoint = xSrc + (Math.abs(xSrc-xDest)*0.8);
                        yPoint = ySrc + (Math.abs(ySrc-yDest)*0.8);
                    }
                    else if (ySrc > yDest && xSrc > xDest)
                    {
                        xPoint = xSrc - (Math.abs(xSrc-xDest)*0.8);
                        yPoint = ySrc - (Math.abs(ySrc-yDest)*0.8);
                    }
                    else if (ySrc > yDest && xSrc < xDest)
                    {
                        xPoint = xSrc + (Math.abs(xSrc-xDest)*0.8);
                        yPoint = ySrc - (Math.abs(ySrc-yDest)*0.85);
                    }
                    else if (ySrc < yDest && xSrc > xDest)
                    {
                        xPoint = xSrc - (Math.abs(xSrc-xDest)*0.95);
                        yPoint = ySrc + (Math.abs(ySrc-yDest)*0.8);
                    }
                    g.fillOval((int) xPoint, (int) yPoint, 30, 30);
                }
            }
        }
    }
//works
    private Point3D minPoint(){
        if (this.g1.getV() == null) {
            return null;
        }
        double min_x = this.g1.getV().iterator().next().getLocation().x();
        double min_y = this.g1.getV().iterator().next().getLocation().y();
        for (node_data n : this.g1.getV()){
            if (n.getLocation().x() < min_x){
                min_x = n.getLocation().x();
            }
            if(n.getLocation().y() < min_y){
                min_y = n.getLocation().y();
            }
        }
        return new Point3D(min_x,min_y);
    }
//works
    private Point3D maxPoint(){
        if (this.g1.getV() == null) {
            return null;
        }
        double max_x = this.g1.getV().iterator().next().getLocation().x();
        double max_y = this.g1.getV().iterator().next().getLocation().y();
        for (node_data n : this.g1.getV()){
            if (n.getLocation().x() > max_x){
                max_x = n.getLocation().x();
            }
            if(n.getLocation().y() > max_y){
                max_y = n.getLocation().y();
            }
        }
        return new Point3D(max_x,max_y);
    }
    //works
    private Point3D ScaleToFrame(Point3D location,Point3D minPoint,Point3D maxPoint)
    {
        double r_min_x = minPoint.x();
        double r_max_x = maxPoint.x();
        double r_min_y = minPoint.y();
        double r_max_y = maxPoint.y();

        double t_min_x = 100;
        double t_max_x = this.getWidth()-100;
        double t_min_y = 100;
        double t_max_y = this.getHeight()-50;

        double x = location.x();
        double y = location.y() ;

        double res_x = ((x-r_min_x)/(r_max_x-r_min_x)) * (t_max_x - t_min_x) +t_min_x;
        double res_y = ((y-r_min_y)/(r_max_y-r_min_y)) * (t_max_y - t_min_y) +t_min_y;

        return new Point3D(res_x,res_y);
    }

    //??
    @Override
    public void mouseClicked(MouseEvent e) {
       /* String keyS = JOptionPane.showInputDialog(this, "Please insert node key");
        int x = e.getX();
        int y = e.getY();
        Point3D p = new Point3D(x, y);
        try {
            int key = Integer.parseInt(keyS);
            node_data newNode = new NodeData(key);
            newNode.setLocation(p);
            g1.addNode(newNode);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }*/
    }
    @Override
    public void mousePressed(MouseEvent e) {

}

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void init()
    {

    }

    public graph copy() {
        return null;
    }
// init from a file
    //need to check
// noe working -problem in: graph ga = (graph) in.readObject(); in Graph_Algo
    public void Load()  {
        JFileChooser chooser = new JFileChooser();
        Graph_Algo g = new Graph_Algo();
        int returnFile = chooser.showOpenDialog(this);
        if(returnFile == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                File SelectedFile = chooser.getSelectedFile();
                g.init(SelectedFile.getAbsolutePath());
                this.g1 = g.copy();
                repaint();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
// check save and then init from file and see if works
    public void save()
    {
        JFileChooser fileChooser = new JFileChooser();
        graph_algorithms ga = new Graph_Algo();
        ga.init(this.g1);
        JFileChooser Choose = new JFileChooser();
        Choose.setDialogTitle("Save a file");
        int userSelection = Choose.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File Save = Choose.getSelectedFile();
            String FileName= Save.getAbsolutePath();
            ga.save(FileName);
            System.out.println("Save as file: " + Save.getAbsolutePath());
        }

    }
       /* int returnFile = fileChooser.showSaveDialog(fileChooser);
        if (returnFile == JFileChooser.APPROVE_OPTION)
            {
            File file = fileChooser.getSelectedFile();
            if (file == null)
            {
                return;
            }
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getParentFile(), file.getName() + ".txt");
            }
            try
            {
                ga.save(fileChooser.getSelectedFile() + ".txt");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            fileChooser.setVisible(true);
           }
    }
*/
 // works
    public boolean isConnected()
    {
        graph_algorithms graphAlgo = new Graph_Algo();
        graphAlgo.init(g1);
        boolean ans = graphAlgo.isConnected();
        if(ans)
        {
            JOptionPane.showMessageDialog(null,"The graph is connected", "isConnected", JOptionPane.QUESTION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "The graph is not connected", "isConnected", JOptionPane.INFORMATION_MESSAGE);
        }
        return ans;
    }
// works
    public void shortestPathDist()
    {
        graph_algorithms graphAlgo = new Graph_Algo();
        graphAlgo.init(g1);
        String srcNode = JOptionPane.showInputDialog(this, "insert source node");
        String destNode = JOptionPane.showInputDialog(this, "insert destination node");
        try {
            int src = Integer.parseInt(srcNode);
            int dest = Integer.parseInt(destNode);
            double distPath = graphAlgo.shortestPathDist(src, dest);
            if (distPath == Double.MAX_VALUE) {
                JOptionPane.showMessageDialog(this, "There isn't a shortest path distance", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "The shortest path distance is: " + distPath, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
//works
    public void shortestPathList()
    {
        graph_algorithms graphAlgo = new Graph_Algo();
        graphAlgo.init(g1);
        String srcTemp = JOptionPane.showInputDialog(this, "insert source node");
        String destTemp = JOptionPane.showInputDialog(this,"insert destination node");
        List <node_data> nodeList;
        try
        {
            int src = Integer.parseInt(srcTemp);
            int dst = Integer.parseInt(destTemp);
            nodeList = graphAlgo.shortestPath(src,dst);
            String result = "";
            if(nodeList != null)
            {
                for(int i = 0; i < nodeList.size()-1; i++)
                    result += nodeList.get(i)+" >>> ";
                result += nodeList.get(nodeList.size()-1);
            }
            else
            {
                result = "There are no path between the tow given nodes";
            }
            JOptionPane.showMessageDialog(this, "The shortest path is: \n " + result, "Message", JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this, "ERROR: something went wrong " + ex.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }

    }
//works
    public void TSP()
    {
            graph_algorithms graphAlgo = new Graph_Algo();
            graphAlgo.init(g1);
            String nodesPath = JOptionPane.showInputDialog(this, "enter the amount of target nodes");
            List<Integer> targetsNode = new LinkedList<Integer>();
            List<node_data> tempAns;
            try {
                int amount = Integer.parseInt(nodesPath);
                for (int i = 0; i < amount; i++) {
                    String targetString = JOptionPane.showInputDialog(this, "Please insert target node: " + " -- " + i);
                    int target = Integer.parseInt(targetString);
                    targetsNode.add(target);
                }
                Graph_Algo newTspNode = new Graph_Algo();
                newTspNode.init(g1);
                tempAns = newTspNode.TSP(targetsNode);
                String result = "";
                if (tempAns != null) {
                    for (int i = 0; i < tempAns.size() - 1; i++)
                        result += tempAns.get(i) + " >> ";
                        result += tempAns.get(tempAns.size() - 1);
                } else {
                    result = "there are no path between this given targets";
                }
                JOptionPane.showMessageDialog(this, "The shortest path is:\n " + result, "Message", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ERROR: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
}