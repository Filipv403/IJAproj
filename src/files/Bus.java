package files;

import java.util.List;
import java.util.AbstractMap;
import files.Line;

/**
 * @author xvacla30
 */
public class Bus {
    private int id;
    private String type;
    private String carrier;
    private List <AbstractMap.SimpleImmutableEntry<Street,Stop>> map_list;

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

    public void addLine(List <AbstractMap.SimpleImmutableEntry<Street,Stop>> map_list){
        this.map_list = map_list;
    }

    @Override
    public String toString(){
        return "#"+this.id + " " + this.type + " " + this.carrier + " ";
    }
}