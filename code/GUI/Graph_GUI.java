package GUI;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.List;


public class Graph_GUI  extends JFrame implements ActionListener, MouseListener, graph_algorithms {
    public graph g1;
    private Graph_Algo graphAlgo;

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
        MenuItem Load = new MenuItem("Load");
        Load.addActionListener(this);
        File.add(Load);
        MenuItem Save = new MenuItem("Save");
        Save.addActionListener(this);
        File.add(Save);
        MenuItem init = new MenuItem("init");
        init.addActionListener(this);
        File.add(init);
        MenuItem isConnected = new MenuItem("isConnected");
        isConnected.addActionListener(this);
        File.add(isConnected);
        this.addMouseListener(this);
        return MenuBar;
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.Draw(g);
    }

    public void Draw(Graphics g) {
         Graphics2D g1 = (Graphics2D) g;
         Point3D minP = minPoint();
         Point3D maxP = maxPoint();
        for (node_data n : this.g1.getV()) // This method return a pointer (shallow copy) for the  collection representing all the nodes in the graph
        {
            Point3D currNodeScaledData = ScaleToFrame(n.getLocation(),minP,maxP);

            g.setColor(Color.RED);
            g.fillOval(currNodeScaledData.ix(), currNodeScaledData.iy(), 20, 20); //draw a point in the x,y location
            String keyName = "";
            keyName += n.getKey(); //sets a string with the key of each point
            g.setFont(new Font("deafult", Font.BOLD, 14));
            g.drawString(keyName, currNodeScaledData.ix(),currNodeScaledData.iy());
            String tagInfoWeight = "";
            tagInfoWeight += ("(tag: " + n.getTag() +",info: " + n.getInfo() + ",weight: " + n.getWeight()+ ")");
            g.drawString(tagInfoWeight, currNodeScaledData.ix(), currNodeScaledData.iy());
            // Draw all edges came out of the node:
            if (this.g1.getE(n.getKey()) != null) {
                for (edge_data e : this.g1.getE(n.getKey())) //return a pointer (shallow copy) for the collection representing all the edges getting out of the given node
                {
                    double xSrc = currNodeScaledData.x();
                    double ySrc = currNodeScaledData.y();

                    Point3D destNodeScaledData = ScaleToFrame(this.g1.getNode(e.getDest()).getLocation(),minP,maxP);

                    double xDest = destNodeScaledData.x();
                    double yDest = destNodeScaledData.y();

                    g.setColor(Color.BLUE);
                    g.drawLine((int) xSrc, (int) ySrc, (int) xDest, (int) yDest);
                    //  StdDraw.line(xSrc, ySrc, xDest, yDest); //line fron n (xy) to e.dest(xy)
                    g.setColor(Color.BLACK);
                    //draws the weight of each edge
                    double xMid = (xSrc + xDest) / 2;
                    double yMid = (ySrc + yDest) / 2;
                    g.drawString("" + e.getWeight(), (int) xMid, (int) yMid);
                    //draws a yellow point to indicate the direction of the edge
                    g.setColor(Color.YELLOW);
                    double xPoint = 0;
                    double yPoint = 0;
                    if (xSrc < xDest) {
                        xPoint = xSrc +  (xDest * (300/100));
                    } else {
                        xPoint = xSrc -  (xDest * (300/100));
                    }
                    if (ySrc < yDest) {
                        yPoint = ySrc + (yDest * (300/100));
                    } else {
                        yPoint = ySrc -  (yDest * (300/100));
                    }

                    g.fillOval((int) xPoint, (int) yPoint, 10, 10);
                }
            }
        }
    }

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
    //*****check after we make algo****

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals("Save")) save("file1");
        else if(str.equals("Load")) init("file1");
        else  if (str.equals("init")) init(g1);
        else if (str.equals("isConnected")) isConnected();

    }
    @Override
    public void mouseClicked(MouseEvent e) {

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

    @Override
    public void init(graph g)
    {

    }

    @Override
    public graph copy() {
        return null;
    }

    @Override
    public void init(String file_name)
    {
        Graph_Algo g = new Graph_Algo();
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
        {
            try
            {
                File SelectedFile=chooser.getSelectedFile();
                g.init(SelectedFile.getAbsolutePath());
               g1 = g.copy();
                repaint();

            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public void save(String file_name)
    {
            graph_algorithms ga = new Graph_Algo();
            JFileChooser fileChooser = new JFileChooser();
            int returnV = fileChooser.showSaveDialog(fileChooser);
            if (returnV == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                if (file == null) {
                    return;
                }
                if (!file.getName().toLowerCase().endsWith(".txt"))
                {
                    file = new File(file.getParentFile(), file.getName() + ".txt");
                }
                try {
                    ga.save(fileChooser.getSelectedFile() + ".txt");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            fileChooser.setVisible(true);
    }

    @Override
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

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public List<node_data> TSP(List<Integer> targets) {
        return null;
    }
}
