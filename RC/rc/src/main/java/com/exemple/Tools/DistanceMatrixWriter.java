package main.java.com.exemple.Tools;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DistanceMatrixWriter {


    public static void writeDistanceMatrixToFile(ArrayList<Point> points, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            int size = points.size();
            DecimalFormat df = new DecimalFormat("000000.00");
            writer.write("\t\t\t");
            for (int i = 1; i <= size; i++) {
                writer.write("Point   " + i + "\t");
            }
            writer.write("\n");

            for (int i = 0; i < size; i++) {

                writer.write("Point " + (i + 1) + "\t");
                if(i<9)
                {
                    writer.write("\t");
                }
                for (int j = 0; j < size; j++) {
                    double distance = calculateDistance(points.get(i), points.get(j));
                    writer.write(df.format (distance) + "\t");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double calculateDistance(Point p1, Point p2) {
        int deltaX = p2.x - p1.x;
        int deltaY = p2.y - p1.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
