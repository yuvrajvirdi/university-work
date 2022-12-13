public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getx() {
        return this.x;
    }

    public int gety() {
        return this.y;
    }

    public int compareTo(Location p) {
        // greater than other
        if (this.gety() > p.gety() || (this.gety() == p.gety() && this.getx() > p.getx())) {
            return 1;
        }

        // equal to other
        else if (this.getx() == p.getx() && this.gety() == p.gety()) {
            return 0;
        }

        // less than other
        else if (this.gety() < p.gety() || (this.gety() == p.gety() && this.getx() < p.getx())) {
            return -1;
        }

        else return -1;
    }
}
