package cityrescue;

import cityrescue.enums.UnitType;

public class Unit {
    public final int id;
    public final UnitType type;
    public final int stationId;

    public Unit(int id, UnitType type, int stationId) {
        this.id = id;
        this.type = type;
        this.stationId = stationId;
    }
}
