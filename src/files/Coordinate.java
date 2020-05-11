package files;

/**
 * @author xvacla30
 */
public class Coordinate{

    private int x = 0;
    private int y = 0;

    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }
    //rozdíl souřadnic
    public int diffX(Coordinate c){
        int diffX = this.x - c.x;
        return diffX;
    }

    public int diffY(Coordinate c){
        int diffY = this.y - c.y;
        return diffY;
    }

    public static Coordinate create(int x, int y){
        if(x < 0 || y < 0){
            return null;
        }
        Coordinate coordinate = new Coordinate(x, y);
        return coordinate;
    }

    @Override
    public boolean equals(Object o){
        Coordinate co = (Coordinate) o;
        if (x != co.getX() || y != co.getY()) {
            return false;
        }
        return true;
    }
}
