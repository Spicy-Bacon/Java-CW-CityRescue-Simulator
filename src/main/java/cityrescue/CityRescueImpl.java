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

    private int width;
    private int height;
    private boolean[][] blocked;
    private int tick;
    private Station[] stations = new Station[20];
    private Unit[] units = new Unit[50];
    private Incident[] incidents = new Incident[200];
    private int stationCount = 0;
    private int unitCount = 0;
    private int incidentCount = 0;
    private int nextStationId = 1;
    private int nextUnitId = 1;
    private int nextIncidentId = 1;

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private boolean canHandle(Unit unit, Incident incident) {
        if (unit.type == UnitType.POLICE_CAR && incident.type == IncidentType.CRIME) {
            return true;
        }
        if (unit.type == UnitType.FIRE_ENGINE && incident.type == IncidentType.FIRE) {
            return true;
        }
        if (unit.type == UnitType.AMBULANCE && incident.type == IncidentType.MEDICAL) {
            return true;
        }
        return false;
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

    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException();
        }

        this.width = width;
        this.height = height;
        this.blocked = new boolean[width][height];
        this.tick = 0;
        stations = new Station[20];
        units = new Unit[50];
        incidents = new Incident[200];
        stationCount = unitCount = incidentCount = 0;
        nextStationId = nextUnitId = nextIncidentId = 1;
    }

    @Override
    public int[] getGridSize() {
        return new int[] { width, height };
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        if (!inBounds(x, y)) {
            throw new InvalidLocationException();
        }
        blocked[x][y] = true;
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        if (!inBounds(x, y)) {
            throw new InvalidLocationException();
        }
        blocked[x][y] = false;
    }

    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameException("Invalid station name");
        }
        if (!inBounds(x, y)) {
            throw new InvalidLocationException();
        }

        int id = nextStationId++;
        stations[stationCount++] = new Station(id, name.trim(), x, y);
        return id;
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getStationIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        if (type == null) {
            throw new InvalidUnitException("Invalid unit type");
        }

        Station station = null;
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].id == stationId) {
                station = stations[i];
                break;
            }
        }
        if (station == null) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }

        int id = nextUnitId++;
        units[unitCount++] = new Unit(id, type, stationId, station.x, station.y);
        return id;
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        for (int i = 0; i < unitCount; i++) {
            if (units[i].id == unitId) {
                Unit unit = units[i];
                return "U#" + unit.id
                        + " TYPE=" + unit.type
                        + " LOC=(" + unit.x + "," + unit.y + ")"
                        + " STATUS=" + unit.status;
            }
        }
        throw new IDNotRecognisedException("Unit ID not recognised");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        if (type == null) {
            throw new InvalidSeverityException("Invalid incident type");
        }
        if (!inBounds(x, y)) {
            throw new InvalidLocationException();
        }

        int id = nextIncidentId++;
        incidents[incidentCount++] = new Incident(id, type, severity, x, y);
        return id;
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        for (int i = 0; i < incidentCount; i++) {
            if (incidents[i].id == incidentId) {
                Incident incident = incidents[i];
                return "I#" + incident.id
                        + " TYPE=" + incident.type
                        + " SEV=" + incident.severity
                        + " LOC=(" + incident.x + "," + incident.y + ")"
                        + " STATUS=" + incident.status
                        + " UNIT=" + incident.assignedUnitId;
            }
        }
        throw new IDNotRecognisedException("Incident ID not recognised");
    }

    @Override
    public void dispatch() {
        for (int i = 0; i < incidentCount; i++) {
            Incident incident = incidents[i];

            if (incident.assignedUnitId != -1) {
                continue;
            }

            Unit bestUnit = null;
            int bestDistance = Integer.MAX_VALUE;

            for (int j = 0; j < unitCount; j++) {
                Unit unit = units[j];

                if (unit.assigned) {
                    continue;
                }

                if (!canHandle(unit, incident)) {
                    continue;
                }

                int distance = manhattanDistance(unit.x, unit.y, incident.x, incident.y);

                if (bestUnit == null
                        || distance < bestDistance
                        || (distance == bestDistance && unit.id < bestUnit.id)) {
                    bestUnit = unit;
                    bestDistance = distance;
                }
            }

            if (bestUnit != null) {
                bestUnit.assigned = true;
                bestUnit.incidentId = incident.id;
                bestUnit.status = "EN_ROUTE";
                incident.assignedUnitId = bestUnit.id;
                incident.status = "DISPATCHED";
            }
        }
    }

    @Override
    public void tick() {
        tick++;

        for (int i = 0; i < unitCount; i++) {
            Unit unit = units[i];

            if (!unit.assigned) {
                continue;
            }

            Incident incident = findIncident(unit.incidentId);
            if (incident == null) {
                continue;
            }

            if (unit.status.equals("EN_ROUTE")) {
                if (unit.x < incident.x) {
                    unit.x++;
                } else if (unit.x > incident.x) {
                    unit.x--;
                } else if (unit.y < incident.y) {
                    unit.y++;
                } else if (unit.y > incident.y) {
                    unit.y--;
                }

                if (unit.x == incident.x && unit.y == incident.y) {
                    unit.status = "AT_SCENE";
                    incident.status = "IN_PROGRESS";

                    if (unit.type == UnitType.AMBULANCE) {
                        unit.workTicksRemaining = 2;
                    } else if (unit.type == UnitType.POLICE_CAR) {
                        unit.workTicksRemaining = 3;
                    } else if (unit.type == UnitType.FIRE_ENGINE) {
                        unit.workTicksRemaining = 4;
                    }
                }
            } else if (unit.status.equals("AT_SCENE")) {
                unit.workTicksRemaining--;

                if (unit.workTicksRemaining <= 0) {
                    unit.status = "IDLE";
                    unit.assigned = false;
                    unit.incidentId = -1;

                    incident.status = "RESOLVED";
                }
            }
        }
    }

    @Override
    public String getStatus() {
        StringBuilder sb = new StringBuilder();

        sb.append("TICK=").append(tick).append("\n");
        sb.append("INCIDENTS\n");
        sb.append("UNITS\n");

        return sb.toString();
    }
}
