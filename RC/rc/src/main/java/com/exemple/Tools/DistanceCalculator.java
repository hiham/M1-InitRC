package main.java.com.exemple.Tools;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DistanceCalculator {

    public static void writeDistancesToFile(ArrayList<Point> points, String filePath) {
        DecimalFormat df = new DecimalFormat("####0.00");
        try (FileWriter writer = new FileWriter(filePath)) {
            for (int i = 0; i < points.size() - 1; i++) {
                double distance = calculateDistance(points.get(i), points.get(i+1));
                String line = "Distance entre le "  + printPoint(points.get (i),i+1) + "et le " + printPoint(points.get (i+1),i + 2) + ": " + df.format (distance) + "\n";
                writer.write(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double calculateDistance(Point p1, Point p2) {
        int deltaX = p2.x - p1.x;
        int deltaY = p2.y - p1.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private static String printPoint(Point p1 , int index)
    {
        StringBuilder out = new StringBuilder ();
        out.append ("Point ").append (index).append ("(").append (p1.x).append (",").append (p1.y).append (") ");
        return out.toString ();
    }

}
