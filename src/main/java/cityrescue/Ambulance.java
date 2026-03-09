package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class Ambulance extends Unit {
    public Ambulance(int id, int stationId, int x, int y) {
        super(id, stationId, x, y);
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.AMBULANCE;
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.MEDICAL;
    }

    @Override
    public int getTicksToResolve(int severity) {
        return 2;
    }
}
