package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class FireEngine extends Unit {
    public FireEngine(int id, int stationId, int x, int y) {
        super(id, stationId, x, y);
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.FIRE_ENGINE;
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.FIRE;
    }

    @Override
    public int getTicksToResolve(int severity) {
        return 4;
    }
}
