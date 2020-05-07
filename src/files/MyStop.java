package files;

public class MyStop implements Stop {
	private String id;
    private Coordinate c;
	private Street s;

    public MyStop(String id) {
        this.id = id;
    }

    public MyStop(String id, Coordinate c) {
        this.id = id;
        this.c = c;
    }

    public String getId(){
        return this.id;
    }

    public Coordinate getCoordinate(){
        if(c != null){
            return this.c;
        }
        return null;
    }


    public void setStreet(Street s){
		this.s = s;
    }


    public Street getStreet(){
		return this.s;
    }
    //nastaví výchozí zastávku
    public static Stop defaultStop(String id, Coordinate c){
        Stop stop = new MyStop(id, c);
        return stop;
    }

    @Override
    public boolean equals(Object o){
        Stop stop = (Stop) o;
        return id.equals(stop.getId());
    }

    @Override
    public String toString(){
        return "stop(" + id + ")";
    }
}