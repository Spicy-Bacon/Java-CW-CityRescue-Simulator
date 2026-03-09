package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class PoliceCar extends Unit {
    public PoliceCar(int id, int stationId, int x, int y) {
        super(id, stationId, x, y);
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.POLICE_CAR;
    }

    @Override
    public boolean canHandle(IncidentType type) {
        return type == IncidentType.CRIME;
    }

    @Override
    public int getTicksToResolve(int severity) {
        return 3;
    }
}
