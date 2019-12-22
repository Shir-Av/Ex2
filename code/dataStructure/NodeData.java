package dataStructure;

import utils.Point3D;

import java.awt.*;

public class NodeData implements node_data {
    private int key;
    private Point3D location;
    private double nWeight;
    private String info;
    private Color tag;

    public NodeData ()
    {
        this.key = 0;
        this.location = Point3D.ORIGIN;
        this.nWeight = Integer.MAX_VALUE;
        this.info = null;
        this.tag = Color.BLACK;
    }
    public NodeData (NodeData n)
    {
        this.key = n.key;
        this.location = n.location;
        this.nWeight = n.nWeight;
        this.info = n.info;
        this.tag = n.tag;
    }
    public NodeData (int key, Point3D location, double weight, String info)
    {
        this.key = key;
        this.location = location;
        this.nWeight = weight;
        this.info = info;
        this.tag = Color.BLACK;
    }

    @Override
    public int getKey()
    {
        return this.key;
    }

    @Override
    public Point3D getLocation()
    {
        return this.location;
    }

    @Override
    public void setLocation(Point3D p)
    {
        Point3D p1 = new Point3D(p);
        this.location = p1;
    }

    @Override
    public double getWeight()
    {
        return this.nWeight;
    }

    @Override
    public void setWeight(double w)
    {
        this.nWeight = w;
    }

    @Override
    public String getInfo()
    {
        return this.info;
    }

    @Override
    public void setInfo(String s)
    {
        this.info = s;
    }

    @Override
    public int getTag()
    {
        return this.tag.getRGB();
    }

    @Override
    public void setTag(int t)
    {
        this.tag = new Color(t);
    }
    public String toString()
    {
        String s = "Id: "+ this.key +" , Location: "+ this.location + " , Weight: "+ this.nWeight + " , Info: "+ this.info + " , Tag: "+ this.tag;
        return s;
    }
}
