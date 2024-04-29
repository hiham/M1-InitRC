package main.java.com.exemple.Tools;

import java.awt.*;
import java.awt.geom.Line2D;

public class Cross {

    private Line2D horizontalLine ;
    private Line2D verticaline;
    private Point center;
    private Point originalCenter;
    private Type type;

    public Cross(Line2D h,Line2D v)
    {
        horizontalLine = h;
        verticaline = v;
        type = Type.LIMITE;
        center = new Point ((int) verticaline.getX1 (),(int) horizontalLine.getY1 ());
        originalCenter = center;
    }

    public Cross(Line2D h,Line2D v,Point c)
    {
        horizontalLine = h;
        verticaline = v;
        type = Type.LIMITE;
        center = new Point ((int) verticaline.getX1 (),(int) horizontalLine.getY1 ());
        originalCenter = c;
    }

    public Cross(Cross cross)
    {
        this.horizontalLine = cross.horizontalLine;
        this.verticaline = cross.verticaline;
        this.center = new Point (cross.getCenter ().x,cross.getCenter ().y);
    }

    public Line2D getHorizontalLine () {
        return horizontalLine;
    }

    public Line2D getVerticaline () {
        return verticaline;
    }

    public Point getCenter () {
        return center;
    }

    public Point getOriginalCenter () {
        return originalCenter;
    }

    public Cross getCrossWithCenter(Point point)
    {
        return point.equals (this.center) ? this : null;
    }

    public void setType (Type type) {
        this.type = type;
    }

    public Type getType () {
        return type;
    }

    @Override
    public String toString () {
        return "Cross{" +
                "horizontalLine=" + horizontalLine +
                ", verticaline=" + verticaline +
                ", center=" + center +
                '}';
    }
}
