package simulation;

import com.sun.javafx.geom.Vec2d;
import files.Coordinate;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class RouteCalculation {

    public static double routeLenght(ArrayList<Coordinate> route) {
        double l = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            double tmp = sqrt(pow(route.get(i).diffX(route.get(i+1)), 2) + pow(route.get(i).diffY(route.get(i+1)), 2));
            l += tmp;
        }

        return l;
    }

    public static Vec2d getPosition(ArrayList<Coordinate> route, double currentLenght) {
        double l = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            double tmp = sqrt(pow(route.get(i).diffX(route.get(i+1)), 2) + pow(route.get(i).diffY(route.get(i+1)), 2));

            if (l + tmp < currentLenght) {
                l += tmp;
                continue;
            }

            double k = (currentLenght - l) / tmp;

            Coordinate vect = new Coordinate(route.get(i + 1).diffX(route.get(i)), route.get(i + 1).diffY(route.get(i)));
            Coordinate start = route.get(i);
            double x = start.getX() + k * vect.getX();
            double y = start.getY() + k * vect.getY();

            return new Vec2d(x, y);

        }

        return new Vec2d(0, 0);
    }
}
