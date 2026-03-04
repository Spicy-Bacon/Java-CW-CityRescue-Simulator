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

    private Station findStation(int stationId) {
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].id == stationId) {
                return stations[i];
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
        boolean found = false;
        for (int i = 0; i < stationCount; i++) {
            if (stations[i].id == stationId) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IDNotRecognisedException("Station ID not recognised");
        }

        int id = nextUnitId++;
        units[unitCount++] = new Unit(id, type, stationId);
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
                return "U#" + unitId;
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
                return "I#" + incidentId;
            }
        }
        throw new IDNotRecognisedException("Incident ID not recognised");
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
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
