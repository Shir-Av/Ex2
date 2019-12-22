package dataStructure;

import java.awt.*;

public class EdgeData implements edge_data {
    private NodeData src;
    private NodeData dest;
    private double eWeight;
    private String info;
    private Color tag;

    public EdgeData ()
    {
        this.src = new NodeData();
        this.dest = new NodeData();
        this.eWeight = 0;
        this.info = null;
        this.tag = Color.BLACK;
    }
    public EdgeData (NodeData src, NodeData dest, double eWeight)
    {
        this.src = src;
        this.dest = dest;
        this.eWeight = eWeight;
        this.info = null;
        this.tag = Color.BLACK;
    }
    public EdgeData (EdgeData e)
    {
        this.src = new NodeData(e.src);
        this.dest = new NodeData(e.dest);
        this.eWeight = e.eWeight;
        this.info = e.info;
        this.tag = e.tag;
    }

    @Override
    public int getSrc()
    {
        return this.src.getKey();
    }

    @Override
    public int getDest()
    {
        return this.dest.getKey();
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
        String s = "Src: "+ this.src.getKey() +"dest: "+ this.dest.getKey() + " , Weight: "+ this.eWeight + " , Info: "+ this.info + " , Tag: "+ this.tag;
        return s;
    }
}
