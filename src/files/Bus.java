package files;

import java.util.List;
import java.util.AbstractMap;
import files.*;

/**
 * @author xvacla30
 */
public class Bus {
    private int id;
    private String type;
    private String carrier;
    private List <AbstractMap.SimpleImmutableEntry<Street,Stop>> map_list;
    private MyLine line;

    public Bus(){
        this.id=0;
        this.type="";
        this.carrier="";
    }

    public Bus(int id, String type, String carrier){
        this.id = id;
        this.type = type;
        this.carrier = carrier;
    }

    public int getId() {
        return this.id;
    }

    public String getType(){
        return this.type;
    }

    public String getCarrier(){
        return this.carrier;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setCarrier(String carrier){
        this.carrier = carrier;
    }

    public void addLine(MyLine line){
        this.line = line;
    }

    public void addLineList(List <AbstractMap.SimpleImmutableEntry<Street,Stop>> map_list){
        this.map_list = map_list;
    }

    public Coordinate getFirstStopCoordinate(){
        Coordinate c = new Coordinate();
        return this.line.getFirstStop().getCoordinate();
    }

    @Override
    public String toString(){
        return "#"+this.id + " " + this.type + " " + this.carrier + " " + this.line.getId();
    }
}