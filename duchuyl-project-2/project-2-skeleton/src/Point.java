public class Point {
    /**
     * coordinate x and y
     */
    private final double x, y;
    public Point(String a, String b){
        this.x = Double.parseDouble(a);
        this.y = Double.parseDouble(b);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
