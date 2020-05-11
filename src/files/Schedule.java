package files;

import java.time.LocalTime;
import java.util.*;

public class Schedule {
    private List<AbstractMap.SimpleImmutableEntry<LocalTime,Stop>> schedule;
    private int busID;

    public Schedule(int bus) {
        this.schedule = new ArrayList<>();
        this.busID = bus;
    }

    public boolean addStop(LocalTime time, Stop stop) {

        if (schedule.isEmpty()) {
            schedule.add(new AbstractMap.SimpleImmutableEntry<>(time, stop));
            return true;
        }

        if (time.isAfter(schedule.get(schedule.size() - 1).getKey())) {
            schedule.add(new AbstractMap.SimpleImmutableEntry<>(time, stop));
            return true;
        }

        return false;
    }

    public LocalTime getTime(Stop stop) {
        for (AbstractMap.SimpleImmutableEntry<LocalTime, Stop> myStop : schedule) {
            if (myStop.getValue().equals(stop))
                return myStop.getKey();
        }

        return null;
    }

    public Stop getNextStop(LocalTime time) {
        for (AbstractMap.SimpleImmutableEntry<LocalTime, Stop> stop : schedule) {
            if (stop.getKey().isAfter(time)) {
                return stop.getValue();
            }
        }

        return null;
    }

    public Stop getPreviousStop(LocalTime time) {
        for (int i = schedule.size(); i-- > 0; ) {
            if (schedule.get(i).getKey().isBefore(time)){
                return schedule.get(i).getValue();
            }
        }

        return null;
    }

    public int getBusID() {
        return busID;
    }

    public boolean isOnRoute(LocalTime currentTime) {
        return getPreviousStop(currentTime) != null && getNextStop(currentTime) != null;
    }
}
