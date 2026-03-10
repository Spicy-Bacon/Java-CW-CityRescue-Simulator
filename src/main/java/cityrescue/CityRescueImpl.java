package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {

    private static final int MAX_STATIONS = 20;
    private static final int MAX_UNITS = 50;
    private static final int MAX_INCIDENTS = 200;

    private CityMap cityMap;
    private int tick;
    private Station[] stations = new Station[MAX_STATIONS];
    private Unit[] units = new Unit[MAX_UNITS];
    private Incident[] incidents = new Incident[MAX_INCIDENTS];
    private int stationCount = 0;
    private int unitCount = 0;
    private int incidentCount = 0;
    private int nextStationId = 1;
    private int nextUnitId = 1;
    private int nextIncidentId = 1;

    private boolean inBounds(int x, int y) {
        return cityMap != null && cityMap.inBounds(x, y);
    }

    private Station findStation(int stationId) {
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].id == stationId) {
                return stations[i];
            }
        }
        return null;
    }

    private int countUnitsAtStation(int stationId) {
        int count = 0;
        for (int i = 0; i < unitCount; i++) {
            if (units[i].stationId == stationId) {
                count++;
            }
        }
        return count;
    }

    private int countObstacles() {
        if (cityMap == null) {
            return 0;
        }
        return cityMap.countObstacles();
    }

    private void removeStationAtIndex(int index) {
        for (int i = index; i < stationCount - 1; i++) {
            stations[i] = stations[i + 1];
        }
        stations[stationCount - 1] = null;
        stationCount--;
    }

    private void removeUnitAtIndex(int index) {
        for (int i = index; i < unitCount - 1; i++) {
            units[i] = units[i + 1];
        }
        units[unitCount - 1] = null;
        unitCount--;
    }

    private int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private Incident findIncident(int incidentId) {
        for (int i = 0; i < incidentCount; i++) {
            if (incidents[i].id == incidentId) {
                return incidents[i];
            }
        }
        return null;
    }

    private Unit findUnit(int unitId) {
        for (int i = 0; i < unitCount; i++) {
            if (units[i].id == unitId) {
                return units[i];
            }
        }
        return null;
    }

    private void moveUnitOneStep(Unit unit, Incident incident) {
        int[][] moves = {
            {0, -1},
            {1, 0},
            {0, 1},
            {-1, 0}
        };

        int currentDistance = manhattanDistance(unit.x, unit.y, incident.x, incident.y);

        for (int[] move : moves) {
            int nx = unit.x + move[0];
            int ny = unit.y + move[1];

            if (!inBounds(nx, ny)) {
                continue;
            }
            if (cityMap.isBlocked(nx, ny)) {
                continue;
            }

            int newDistance = manhattanDistance(nx, ny, incident.x, incident.y);
            if (newDistance < currentDistance) {
                unit.x = nx;
                unit.y = ny;
                return;
            }
        }

        for (int[] move : moves) {
            int nx = unit.x + move[0];
            int ny = unit.y + move[1];

            if (!inBounds(nx, ny)) {
                continue;
            }
            if (cityMap.isBlocked(nx, ny)) {
                continue;
            }

            unit.x = nx;
            unit.y = ny;
            return;
        }
    }

    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException();
        }

        this.cityMap = new CityMap(width, height);
        this.tick = 0;
        stations = new Station[MAX_STATIONS];
        units = new Unit[MAX_UNITS];
        incidents = new Incident[MAX_INCIDENTS];
        stationCount = unitCount = incidentCount = 0;
        nextStationId = nextUnitId = nextIncidentId = 1;
    }

    @Override
    public int[] getGridSize() {
        if (cityMap == null) {
            return new int[] { 0, 0 };
        }
        return new int[] { cityMap.getWidth(), cityMap.getHeight() };
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        if (!inBounds(x, y)) {
            throw new InvalidLocationException();
        }
        cityMap.setBlocked(x, y, true);
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        if (!inBounds(x, y)) {
            throw new InvalidLocationException();
        }
        cityMap.setBlocked(x, y, false);
    }

    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameException("Invalid station name");
        }
        if (!inBounds(x, y) || cityMap.isBlocked(x, y)) {
            throw new InvalidLocationException();
        }
        if (stationCount >= MAX_STATIONS) {
            throw new CapacityExceededException("Maximum number of stations reached");
        }

        int id = nextStationId++;
        stations[stationCount++] = new Station(id, name.trim(), x, y);
        return id;
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        int index = -1;
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].id == stationId) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }

        if (countUnitsAtStation(stationId) > 0) {
            throw new IllegalStateException("Station still owns units");
        }

        removeStationAtIndex(index);
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        Station station = findStation(stationId);
        if (station == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }
        if (maxUnits <= 0 || maxUnits < countUnitsAtStation(stationId)) {
            throw new InvalidCapacityException("Invalid station capacity");
        }

        station.capacity = maxUnits;
    }

    @Override
    public int[] getStationIds() {
        int[] ids = new int[stationCount];
        for (int i = 0; i < stationCount; i++) {
            ids[i] = stations[i].id;
        }
        return ids;
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        if (type == null) {
            throw new InvalidUnitException("Invalid unit type");
        }

        Station station = findStation(stationId);
        if (station == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }
        if (station.capacity > 0 && countUnitsAtStation(stationId) >= station.capacity) {
            throw new IllegalStateException("Station has no free capacity");
        }
        if (unitCount >= MAX_UNITS) {
            throw new CapacityExceededException("Maximum number of units reached");
        }

        int id = nextUnitId++;
        Unit unit;
        if (type == UnitType.AMBULANCE) {
            unit = new Ambulance(id, stationId, station.x, station.y);
        } else if (type == UnitType.FIRE_ENGINE) {
            unit = new FireEngine(id, stationId, station.x, station.y);
        } else if (type == UnitType.POLICE_CAR) {
            unit = new PoliceCar(id, stationId, station.x, station.y);
        } else {
            throw new InvalidUnitException("Invalid unit type");
        }
        units[unitCount++] = unit;
        return id;
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        int index = -1;
        for (int i = 0; i < unitCount; i++) {
            if (units[i].id == unitId) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }

        Unit unit = units[index];
        if (unit.status == UnitStatus.EN_ROUTE || unit.status == UnitStatus.AT_SCENE) {
            throw new IllegalStateException("Unit is busy");
        }

        removeUnitAtIndex(index);
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        Unit unit = findUnit(unitId);
        if (unit == null) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }

        Station station = findStation(newStationId);
        if (station == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }

        if (unit.status != UnitStatus.IDLE) {
            throw new IllegalStateException("Unit must be IDLE");
        }

        if (station.capacity > 0 && countUnitsAtStation(newStationId) >= station.capacity) {
            throw new IllegalStateException("Destination station full");
        }

        unit.stationId = newStationId;
        unit.x = station.x;
        unit.y = station.y;
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        Unit unit = findUnit(unitId);
        if (unit == null) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }

        if (outOfService) {
            if (unit.status != UnitStatus.IDLE) {
                throw new IllegalStateException("Unit must be IDLE to go out of service");
            }
            unit.status = UnitStatus.OUT_OF_SERVICE;
        } else {
            unit.status = UnitStatus.IDLE;
        }
    }

    @Override
    public int[] getUnitIds() {
        int[] ids = new int[unitCount];
        for (int i = 0; i < unitCount; i++) {
            ids[i] = units[i].id;
        }
        return ids;
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        Unit unit = findUnit(unitId);
        if (unit == null) {
            throw new IDNotRecognisedException("Unit ID not recognised");
        }

        String incidentText = unit.incidentId == -1 ? "-" : String.valueOf(unit.incidentId);
        String result = "U#" + unit.id
                + " TYPE=" + unit.getUnitType()
                + " HOME=" + unit.stationId
                + " LOC=(" + unit.x + "," + unit.y + ")"
                + " STATUS=" + unit.status
                + " INCIDENT=" + incidentText;

        if (unit.status == UnitStatus.AT_SCENE) {
            result += " WORK=" + unit.workTicksRemaining;
        }

        return result;
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        if (type == null || severity < 1 || severity > 5) {
            throw new InvalidSeverityException("Invalid incident severity/type");
        }
        if (!inBounds(x, y) || cityMap.isBlocked(x, y)) {
            throw new InvalidLocationException();
        }
        if (incidentCount >= MAX_INCIDENTS) {
            throw new CapacityExceededException("Maximum number of incidents reached");
        }

        int id = nextIncidentId++;
        incidents[incidentCount++] = new Incident(id, type, severity, x, y);
        return id;
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        Incident incident = findIncident(incidentId);
        if (incident == null) {
            throw new IDNotRecognisedException("Incident ID not recognised");
        }

        if (incident.status != IncidentStatus.REPORTED && incident.status != IncidentStatus.DISPATCHED) {
            throw new IllegalStateException("Incident cannot be cancelled in this state");
        }

        if (incident.status == IncidentStatus.DISPATCHED && incident.assignedUnitId != -1) {
            Unit unit = findUnit(incident.assignedUnitId);
            if (unit != null) {
                unit.status = UnitStatus.IDLE;
                unit.assigned = false;
                unit.incidentId = -1;
                unit.workTicksRemaining = 0;
            }
        }

        incident.assignedUnitId = -1;
        incident.status = IncidentStatus.CANCELLED;
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        Incident incident = findIncident(incidentId);
        if (incident == null) {
            throw new IDNotRecognisedException("Incident ID not recognised");
        }
        if (newSeverity < 1 || newSeverity > 5) {
            throw new InvalidSeverityException("Invalid severity");
        }
        if (incident.status == IncidentStatus.RESOLVED || incident.status == IncidentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot escalate resolved or cancelled incident");
        }

        incident.severity = newSeverity;
    }

    @Override
    public int[] getIncidentIds() {
        int[] ids = new int[incidentCount];
        for (int i = 0; i < incidentCount; i++) {
            ids[i] = incidents[i].id;
        }
        return ids;
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        Incident incident = findIncident(incidentId);
        if (incident == null) {
            throw new IDNotRecognisedException("Incident ID not recognised");
        }

        String unitText = incident.assignedUnitId == -1 ? "-" : String.valueOf(incident.assignedUnitId);

        return "I#" + incident.id
                + " TYPE=" + incident.type
                + " SEV=" + incident.severity
                + " LOC=(" + incident.x + "," + incident.y + ")"
                + " STATUS=" + incident.status
                + " UNIT=" + unitText;
    }

    @Override
    public void dispatch() {
        for (int i = 0; i < incidentCount; i++) {
            Incident incident = incidents[i];

            if (incident.status != IncidentStatus.REPORTED) {
                continue;
            }

            Unit bestUnit = null;
            int bestDistance = Integer.MAX_VALUE;

            for (int j = 0; j < unitCount; j++) {
                Unit unit = units[j];

                if (unit.assigned) {
                    continue;
                }

                if (unit.status == UnitStatus.OUT_OF_SERVICE) {
                    continue;
                }

                if (!unit.canHandle(incident.type)) {
                    continue;
                }

                int distance = manhattanDistance(unit.x, unit.y, incident.x, incident.y);

                if (bestUnit == null
                        || distance < bestDistance
                        || (distance == bestDistance && unit.id < bestUnit.id)
                        || (distance == bestDistance && unit.id == bestUnit.id
                        && unit.stationId < bestUnit.stationId)) {
                    bestUnit = unit;
                    bestDistance = distance;
                }
            }

            if (bestUnit != null) {
                bestUnit.assigned = true;
                bestUnit.incidentId = incident.id;
                bestUnit.status = UnitStatus.EN_ROUTE;
                incident.assignedUnitId = bestUnit.id;
                incident.status = IncidentStatus.DISPATCHED;
            }
        }
    }

    @Override
    public void tick() {
        tick++;

        for (int i = 0; i < unitCount; i++) {
            Unit unit = units[i];
            if (unit.status == UnitStatus.EN_ROUTE) {
                Incident incident = findIncident(unit.incidentId);
                if (incident != null) {
                    moveUnitOneStep(unit, incident);
                }
            }
        }

        for (int i = 0; i < unitCount; i++) {
            Unit unit = units[i];
            if (unit.status == UnitStatus.EN_ROUTE) {
                Incident incident = findIncident(unit.incidentId);
                if (incident != null && unit.x == incident.x && unit.y == incident.y) {
                    unit.status = UnitStatus.AT_SCENE;
                    unit.workTicksRemaining = unit.getTicksToResolve(incident.severity);
                    incident.status = IncidentStatus.IN_PROGRESS;
                }
            }
        }

        for (int i = 0; i < unitCount; i++) {
            Unit unit = units[i];
            if (unit.status == UnitStatus.AT_SCENE) {
                unit.workTicksRemaining--;
            }
        }

        for (int i = 0; i < incidentCount; i++) {
            Incident incident = incidents[i];
            if (incident.assignedUnitId != -1 && incident.status == IncidentStatus.IN_PROGRESS) {
                Unit unit = findUnit(incident.assignedUnitId);
                if (unit != null && unit.status == UnitStatus.AT_SCENE && unit.workTicksRemaining <= 0) {
                    incident.status = IncidentStatus.RESOLVED;
                    unit.status = UnitStatus.IDLE;
                    unit.assigned = false;
                    unit.incidentId = -1;
                    unit.workTicksRemaining = 0;
                }
            }
        }
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder();

        sb.append("TICK=").append(tick).append("\n");
        sb.append("STATIONS=").append(stationCount)
                .append(" UNITS=").append(unitCount)
                .append(" INCIDENTS=").append(incidentCount)
                .append(" OBSTACLES=").append(countObstacles())
                .append("\n");
        sb.append("INCIDENTS\n");
        for (int i = 0; i < incidentCount; i++) {
            sb.append(viewIncidentSafe(incidents[i])).append("\n");
        }
        sb.append("UNITS\n");
        for (int i = 0; i < unitCount; i++) {
            sb.append(viewUnitSafe(units[i])).append("\n");
        }

        return sb.toString();
    }

    private String viewUnitSafe(Unit unit) {
        String incidentText = unit.incidentId == -1 ? "-" : String.valueOf(unit.incidentId);

        String result = "U#" + unit.id
                + " TYPE=" + unit.getUnitType()
                + " HOME=" + unit.stationId
                + " LOC=(" + unit.x + "," + unit.y + ")"
                + " STATUS=" + unit.status
                + " INCIDENT=" + incidentText;

        if (unit.status == UnitStatus.AT_SCENE) {
            result += " WORK=" + unit.workTicksRemaining;
        }

        return result;
    }

    private String viewIncidentSafe(Incident incident) {
        String unitText = incident.assignedUnitId == -1 ? "-" : String.valueOf(incident.assignedUnitId);

        return "I#" + incident.id
                + " TYPE=" + incident.type
                + " SEV=" + incident.severity
                + " LOC=(" + incident.x + "," + incident.y + ")"
                + " STATUS=" + incident.status
                + " UNIT=" + unitText;
    }
}
