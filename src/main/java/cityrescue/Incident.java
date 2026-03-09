package cityrescue;

import cityrescue.enums.IncidentType;

public class Incident {
    public final int id;
    public final IncidentType type;
    public int severity;
    public final int x;
    public final int y;
    public int assignedUnitId;
    public String status;

    public Incident(int id, IncidentType type, int severity, int x, int y) {
        this.id = id;
        this.type = type;
        this.severity = severity;
        this.x = x;
        this.y = y;
        this.assignedUnitId = -1;
        this.status = "REPORTED";
    }
}
