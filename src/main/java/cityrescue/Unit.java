package cityrescue;

import cityrescue.enums.UnitType;

public class Unit {
    public final int id;
    public final UnitType type;
    public final int stationId;
    public int x;
    public int y;

    public boolean assigned;
    public int incidentId;
    public String status;
    public int workTicksRemaining;

    public Unit(int id, UnitType type, int stationId, int x, int y) {
        this.id = id;
        this.type = type;
        this.stationId = stationId;
        this.x = x;
        this.y = y;
        this.assigned = false;
        this.incidentId = -1;
        this.status = "IDLE";
        this.workTicksRemaining = 0;
    }
}
