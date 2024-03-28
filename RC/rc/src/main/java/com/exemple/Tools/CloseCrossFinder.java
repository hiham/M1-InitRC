package main.java.com.exemple.Tools;
import java.awt.Point;
import java.util.ArrayList;

public class CloseCrossFinder {

    public static Cross returnClosestCross (ArrayList<Cross> crosses, int targetX, int targetY) {
        if (crosses.isEmpty()) {
            return null;
        }
        int closestIndex = -1;
        double closestDistance = Double.MAX_VALUE;
        for (int i = 0; i < crosses.size(); i++) {
            double distance = calculateDistance(crosses.get(i).getCenter (), targetX, targetY);
            if (distance < closestDistance) {
                if(isPointInsideCircle (crosses.get (i).getCenter (),10,targetX,targetY))
                {
                    closestIndex = i;
                    closestDistance = distance;
                }
            }
        }
        return closestIndex != -1 ?  crosses.get(closestIndex) : null;
    }

    private static double calculateDistance(Point point, int targetX, int targetY) {
        int deltaX = point.x - targetX;
        int deltaY = point.y - targetY;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private static boolean isPointInsideCircle (Point center, int radius, int pointX, int pointY) {
        double distance = Math.sqrt(Math.pow(pointX - center.x, 2) + Math.pow(pointY - center.y, 2));
        return distance < radius;
    }

}
