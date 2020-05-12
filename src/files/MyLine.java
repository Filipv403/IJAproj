package files;

import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;

public class MyLine implements Line {

	private String id;
	List <AbstractMap.SimpleImmutableEntry<Street,Stop>> map_list = new ArrayList <AbstractMap.SimpleImmutableEntry<Street,Stop>>();

	public MyLine(){
		this.id = "";
	}

	public MyLine(String id){
		this.id = id;
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

    public ArrayList<Coordinate> getRoute(Stop prevStop, Stop nextStop) {
		boolean startFound = false;
		ArrayList<Coordinate> route = new ArrayList<>();
		for (int i = 0; i < map_list.size(); i++) {
			if (!startFound) {
				if (map_list.get(i).getValue().equals(prevStop)) {
					startFound = true;
					route.add(prevStop.getCoordinate());
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
    }
}