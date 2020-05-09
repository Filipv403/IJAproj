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

	public void setId(String id){
		this.id = id;
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
        return "" + this.id + "";
    }
}