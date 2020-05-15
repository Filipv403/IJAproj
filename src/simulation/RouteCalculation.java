package simulation;

import com.sun.javafx.geom.Vec2d;
import files.Coordinate;
import files.Street;

import javax.swing.plaf.IconUIResource;
import java.time.Duration;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class RouteCalculation {
    public static void updateTimes(List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route) {
        int[] idx;
        while ((idx = routeBetween(route)) != null) {
            LocalTime startTime = route.get(idx[0]).getValue();
            LocalTime endTime = route.get(idx[1]).getValue();
            long routeDuration = Duration.between(startTime, endTime).getSeconds();
            double routeL = routeLenght(route, idx[0], idx[1]);

            for (int i = idx[0] + 1; i < idx[1]; i++) {
                startTime = startTime.plusSeconds((long) (routeDuration * (routeLenght(route, i-1, i) / routeL)));
                route.get(i).setValue(startTime);
            }
        }
    }

    private static int[] routeBetween(List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route) {
        int[] idx = new int[2];
        boolean gap = false;
        for (int i = 1; i < route.size(); i++) {
            if (route.get(i).getValue() == null) {
                gap = true;
            } else {
                if (gap) {
                    idx[1] = i;
                    return idx;
                } else {
                    idx[0] = i;
                }
            }
        }

        return null;
    }

    public static ArrayList<Long> computeDelay(List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route, List<Street> streets) {
        if (route == null)
            return null;

        ArrayList<Long> delay = new ArrayList<>();
        long curentDelay = 0;
        delay.add(curentDelay);

        updateTimes(route);

        for (int i = 0; i < route.size() - 1; i++) {
            if (streets.get(i).getTraffic() == 1) {
                route.get(i + 1).setValue(route.get(i + 1).getValue().plusSeconds(curentDelay));
                delay.add(curentDelay);
            } else {
                long dur = Duration.between(route.get(i).getValue(), route.get(i + 1).getValue()).getSeconds();
                long newDelay =(long) (((streets.get(i).getTraffic() - 1) / 2.0) * dur);

                curentDelay += newDelay;
                route.get(i + 1).setValue(route.get(i + 1).getValue().plusSeconds(curentDelay));
                delay.add(curentDelay);
            }
        }

        return delay;
    }

    public static double routeLenght(List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route, int startIdx, int endIdx) {
        double l = 0;
        for (int i = startIdx; i < endIdx; i++) {
            double tmp = sqrt(pow(route.get(i).getKey().diffX(route.get(i+1).getKey()), 2) + pow(route.get(i).getKey().diffY(route.get(i+1).getKey()), 2));
            l += tmp;
        }

        return l;
    }



    public static Vec2d getPosition(Coordinate nextPos, Coordinate prevPos, double currentLenght) {
        double totalL = sqrt(pow(nextPos.diffX(prevPos), 2) + pow(nextPos.diffY(prevPos), 2));
        double k = currentLenght / totalL;

        Coordinate vect = new Coordinate(nextPos.diffX(prevPos), nextPos.diffY(prevPos));
        double x = prevPos.getX() + k * vect.getX();
        double y = prevPos.getY() + k * vect.getY();

        return new Vec2d(x, y);

        /*double l = 0;
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

        } */

        //return new Vec2d(0, 0);
    }
}
