package cityrescue;

/**
 * Core grid model for the simulation world.
 * Stores dimensions and blocked cells and provides simple legality checks.
 */
public class CityMap {
    private final int width;
    private final int height;
    private final boolean[][] blocked;

    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.blocked = new boolean[width][height];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isBlocked(int x, int y) {
        return blocked[x][y];
    }

    public void setBlocked(int x, int y, boolean value) {
        blocked[x][y] = value;
    }

    public int countObstacles() {
        int count = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (blocked[x][y]) {
                    count++;
                }
            }
        }
        return count;
    }
}
