package GUI;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;


public class Graph_GUI  extends JFrame implements ActionListener, MouseListener {
    public graph g1;

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
        this.setSize(1800, 1800);
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
        this.addMouseListener(this);
        return MenuBar;
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.Draw(g);
    }

    public void Draw(Graphics g) {
         Graphics2D g1 = (Graphics2D) g;
        for (node_data n : this.g1.getV()) // This method return a pointer (shallow copy) for the  collection representing all the nodes in the graph
        {
            g.setColor(Color.RED);
            g.fillOval(n.getLocation().ix(), n.getLocation().iy(), 20, 20); //draw a point in the x,y location
            String keyName = "";
            keyName += n.getKey(); //sets a string with the key of each point
            g.setFont(new Font("deafult", Font.BOLD, 14));
            g.drawString(keyName, n.getLocation().ix(), n.getLocation().iy());
            String tagInfoWeight = "";
            tagInfoWeight += ("(tag: " + n.getTag() +",info: " + n.getInfo() + ",weight: " + n.getWeight()+ ")");
            g.drawString(tagInfoWeight, n.getLocation().ix(), n.getLocation().iy());
            // Draw all edges came out of the node:
            if (this.g1.getE(n.getKey()) != null) {
                for (edge_data e : this.g1.getE(n.getKey())) //return a pointer (shallow copy) for the collection representing all the edges getting out of the given node
                {
                    double xSrc = n.getLocation().x();
                    double ySrc = n.getLocation().y();
                    double xDest = this.g1.getNode(e.getDest()).getLocation().x();
                    double yDest = this.g1.getNode(e.getDest()).getLocation().y();

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
        //*****check after we make algo****
    public void save() {
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

    public void load() {
        graph_algorithms g = new Graph_Algo();
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File SelectedFile = chooser.getSelectedFile();
                g.init(SelectedFile.getAbsolutePath());
                this.g1 = g.copy();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        chooser.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals("Save")) save();
        else if(str.equals("Load")) load();

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

}
