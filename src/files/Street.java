package files;

import java.util.ArrayList;
import java.util.List;
import files.MyStreet;


/**
 * @author xvacla30
 */
public interface Street {
	
	public String getId();
    
	public List<Coordinate> getCoordinates();

    public List<Stop> getStops();

	public boolean addStop(Stop stop);

	public Coordinate begin();

	public Coordinate end();

	public boolean follows(Street s);
	
	java.util.List<Stop> list_stop = new ArrayList<>();
	static java.util.List<Coordinate> list_tmp_c = new ArrayList<>();
	static java.util.List<Coordinate> list_c = new ArrayList<>();
	
	public static Street defaultStreet(String id, Coordinate... c){
		Street street = new MyStreet(id, c);
		for(int i=0; i < c.length; i++){
			list_tmp_c.add(c[i]);
			list_c.add(c[i]);
		}
		if(list_tmp_c.size() == 2){
			if (list_tmp_c.get(0).getX() == list_tmp_c.get(1).getX() || list_tmp_c.get(0).getY() == list_tmp_c.get(1).getY() || list_tmp_c.get(0).getX() == list_tmp_c.get(1).getY()
				|| list_tmp_c.get(0).getY() == list_tmp_c.get(1).getX()){
				list_tmp_c.clear();
				return street;
			}
		}else if(list_tmp_c.size() == 3){
			if (list_tmp_c.get(0).getX() == list_tmp_c.get(1).getX() || list_tmp_c.get(0).getY() == list_tmp_c.get(1).getY()
				|| list_tmp_c.get(0).getX() == list_tmp_c.get(1).getY() || list_tmp_c.get(0).getY() == list_tmp_c.get(1).getX()){
				if (list_tmp_c.get(1).getX() == list_tmp_c.get(2).getX() || list_tmp_c.get(1).getY() == list_tmp_c.get(2).getY()
					|| list_tmp_c.get(1).getX() == list_tmp_c.get(2).getY() || list_tmp_c.get(1).getY() == list_tmp_c.get(2).getX()) {
					list_tmp_c.clear();
					return street;
				}
			}
		}
		list_tmp_c.clear();
		list_c.clear();
		return null;
	}
}
