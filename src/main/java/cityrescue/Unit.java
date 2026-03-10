package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;

public abstract class Unit {
    public final int id;
    public int stationId;
    public int x;
    public int y;

    public boolean assigned;
    public int incidentId;
    public UnitStatus status;
    public int workTicksRemaining;

    public Unit(int id, int stationId, int x, int y) {
        this.id = id;
        this.stationId = stationId;
        this.x = x;
        this.y = y;
        this.assigned = false;
        this.incidentId = -1;
        this.status = UnitStatus.IDLE;
        this.workTicksRemaining = 0;
    }

    public abstract UnitType getUnitType();

    public abstract boolean canHandle(IncidentType type);

    public abstract int getTicksToResolve(int severity);
}
