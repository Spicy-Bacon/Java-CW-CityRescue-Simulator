package cityrescue;

public class Station {
    public final int id;
    public final String name;
    public final int x;
    public final int y;
    public int capacity;

    public Station(int id, String name, int x, int y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.capacity = 0;
    }
}
