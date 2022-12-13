public class Pel {
    private Location location;
    private int color;

    public Pel(Location p, int color) {
        this.location = p;
        this.color = color;
    }

    public Location getLocus() {
        return this.location;
    }

    public int getColor() {
        return this.color;
    }
}
