package files;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;

public class MyLine implements Line {

	private String id;
	List <AbstractMap.SimpleImmutableEntry<Street,Stop>> map_list = new ArrayList <AbstractMap.SimpleImmutableEntry<Street,Stop>>();
	private List<Detour> detours;

	public MyLine(){
		this.id = "";
		this.detours = new ArrayList<>();
	}

	public MyLine(String id){
		this.id = id;
		this.detours = new ArrayList<>();
	}

	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}

	public Stop getFirstStop(){
		return this.map_list.get(0).getValue();
	}

	public Stop getLastStop(){
		return this.map_list.get(map_list.size()-1).getValue();
	}

	public boolean addStop(Stop stop) {
        Street s = stop.getStreet();
		if(!(map_list.isEmpty())){
			AbstractMap.SimpleImmutableEntry<Street,Stop> end = map_list.get(map_list.size()-1);
			Street end_a = end.getKey(); //získá klíč poslední položky v seznamu
			if(s != null){
				if(!(s.follows(end_a))) { //pokud není null a je tam návaznost false jinak tru a přidá zastávku
					return false;
				}
				map_list.add(new AbstractMap.SimpleImmutableEntry<Street,Stop>(s, stop));
				return true;
			}//pokud null přidá zastávku
			map_list.add(new AbstractMap.SimpleImmutableEntry<Street,Stop>(s, stop));
			return true;
		}//přidá zastávku
		map_list.add(new AbstractMap.SimpleImmutableEntry<Street,Stop>(s, stop));
		return true;
	}
	
	public boolean addStreet(Street street) {
		if(!(map_list.isEmpty())){
			Street s = map_list.get(map_list.size()-1).getKey();
			if(!(street.follows(s))){
				return false;
			}//přidá ulici do seznamu
			map_list.add(new AbstractMap.SimpleImmutableEntry<Street,Stop>(street, null));
			return true;
		}
		return false;
	}
	public java.util.List<java.util.AbstractMap.SimpleImmutableEntry<Street,Stop>> getRoute() {
		List <AbstractMap.SimpleImmutableEntry<Street,Stop>> lists = Collections.unmodifiableList(map_list);
		return lists;
	}
	
	public static Line defaultLine(java.lang.String id) {
		Line line = new MyLine(id);
		return line;
	}
	
	@Override
    public String toString(){
		try {
			return "" + this.id + " pocatecni zastavka: " + getFirstStop().getId();
		} catch (Exception e) {
			return "" + this.id + "";
		} 
    }

    private Coordinate getEqualCoord(Street street1, Street street2) {
		if (street1.begin().equals(street2.begin()) || street1.begin().equals(street2.end()))
			return street1.begin();
		else if (street1.end().equals(street2.end()) || street1.end().equals(street2.begin()))
			return street1.end();
		else
			return null;
	}

	/*
    public ArrayList<Coordinate> getRoute(Stop prevStop, Stop nextStop) {
		boolean startFound = false;
		ArrayList<Coordinate> route = new ArrayList<>();
		for (int i = 0; i < map_list.size(); i++) {
			if (!startFound) {
				if (map_list.get(i).getValue().equals(prevStop)) {
					startFound = true;
					route.add(prevStop.getCoordinate());

					if (!prevStop.getStreet().equals(nextStop.getStreet()))
						route.add(getEqualCoord(prevStop.getStreet(), map_list.get(i + 1).getKey()));
				}
			} else {
				if (map_list.get(i).getValue() != null && map_list.get(i).getValue().equals(nextStop)) {
					route.add(nextStop.getCoordinate());
					break;
				} else {
					route.add(getEqualCoord(map_list.get(i).getKey(), map_list.get(i + 1).getKey()));
				}
			}
		}

		return route;
    } */

    public void highlight() {
		this.map_list.forEach(pair -> {
			pair.getKey().select();
			if (pair.getValue() != null)
				pair.getValue().select();
		});
    }

    private int[] getStopIdxs() {
    	int [] idxs = new int[2];
    	boolean haveStart = false;
    	boolean haveEnd = false;
    	for (int i = 0; i < map_list.size(); i++) {
    		if (!haveStart) {
				if (map_list.get(i).getValue() != null && map_list.get(i).getKey().isOpen()) {
					haveStart = true;
					idxs[0] = i;
				}
			} else {
    			if (map_list.get(i).getValue() != null) {
    				if (map_list.get(i).getKey().isOpen()) {
    					haveEnd = true;
    					idxs[1] = i;
					} else if (!hasDetour(map_list.get(i).getKey())) {
    					if (haveEnd)
    						return idxs;
    					else
    						return null;
					}
				} else {
					if (!map_list.get(i).getKey().isOpen() && !hasDetour(map_list.get(i).getKey()) && !haveEnd) {
						return null;
					}
				}
			}
		}

		if (haveEnd)
			return idxs;
		else
			return null;
	}

	private boolean hasDetour(Street street) {
    	return false;
    	/*
    	for (Detour detour : detours) {
    		if (detour.isReplacing(street))
    			return true;
		}

    	return false; */
	}

	public List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> getBusRoute(Schedule schedule) {
		List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route = new ArrayList<>();
		int jump;

		int[] stopIdxs = getStopIdxs();

		if (stopIdxs == null)
			return null;

		for (int i = stopIdxs[0]; i < stopIdxs[1]; i++) {
			if (map_list.get(i).getValue() == null) {
				if (!hasDetour(map_list.get(i).getKey())) {
					route.add(new AbstractMap.SimpleEntry<>(getEqualCoord(map_list.get(i).getKey(), map_list.get(i + 1).getKey()), null));
				} else {
					jump = setDetourRoute(map_list.get(i).getKey(), route);
					i += jump;
				}
			} else {
				if (!hasDetour(map_list.get(i).getValue().getStreet())) {
					route.add(new AbstractMap.SimpleEntry<>(map_list.get(i).getValue().getCoordinate(), schedule.getTime(map_list.get(i).getValue())));
					if (map_list.get(i + 1).getValue() == null || (map_list.get(i + 1).getValue() != null && !map_list.get(i + 1).getKey().equals(map_list.get(i).getKey())))
						route.add(new AbstractMap.SimpleEntry<>(getEqualCoord(map_list.get(i).getKey(), map_list.get(i + 1).getKey()), null));
				} else {
					jump = setDetourRoute(map_list.get(i).getValue().getStreet(), route);
					i += jump;
				}
			}
		}

		//add last stop
		route.add(new AbstractMap.SimpleEntry<>(map_list.get(stopIdxs[1]).getValue().getCoordinate(), schedule.getTime(map_list.get(stopIdxs[1]).getValue())));
		return route;
	}

	private int setDetourRoute(Street street, List<AbstractMap.SimpleEntry<Coordinate, LocalTime>> route) {
		for (Detour detour : detours) {
			if (detour.isReplacing(street)) {
				detour.getRoute(street, route);
				return detour.getJump();
			}
		}

		return 0;
	}

	public List<Street> getStreets() {
		List<Street> streets = new ArrayList<>();

		streets.add(map_list.get(0).getKey());

		for (int i = 1; i < map_list.size() - 1; i++) {
			if (map_list.get(i).getValue() == null) {
				streets.add(map_list.get(i).getKey());
			} else {
				streets.add(map_list.get(i).getKey());
				if (map_list.get(i - 1).getValue() == null || (map_list.get(i - 1).getValue() != null && !map_list.get(i - 1).getKey().equals(map_list.get(i).getKey())))
					streets.add(map_list.get(i).getKey());
			}
		}

		streets.add(map_list.get(map_list.size() - 1).getKey());

		return streets;
	}
}