package dataStructure;

import java.awt.*;

public class EdgeData implements edge_data {
    private int src;
    private int dest;
    private double eWeight;
    private String info;
    private Color tag;

    public EdgeData ()
    {
        this.src = -1;
        this.dest = -1;
        this.eWeight = 0;
        this.info = null;
        this.tag = Color.BLACK;
    }
    public EdgeData (NodeData src, NodeData dest, double eWeight)
    {
        this.src = src.getKey();
        this.dest = dest.getKey();
        this.eWeight = eWeight;
        this.info = null;
        this.tag = Color.BLACK;
    }
    public EdgeData (EdgeData e)
    {
        this.src = e.src;
        this.dest = e.dest;
        this.eWeight = e.eWeight;
        this.info = e.info;
        this.tag = e.tag;
    }
    public EdgeData(int s, int d, double weight) {
        this.src = s;
        this.dest = d;
        this.eWeight = weight;
        this.info = "";
        this.tag = Color.BLACK;
    }


    @Override
    public int getSrc()
    {
        return this.src;
    }

    @Override
    public int getDest()
    {
        return this.dest;
    }

    @Override
    public double getWeight()
    {
        return this.eWeight;
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
        String s = "Src: "+ this.src +"dest: "+ this.dest + " , Weight: "+ this.eWeight + " , Info: "+ this.info + " , Tag: "+ this.tag;
        return s;
    }
}
