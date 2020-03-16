package primitives;

import java.util.Objects;

public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;
    static final Point3D ZERO = new Point3D(0,0,0);

    public double distanceSquared(Point3D othr){
        return (othr._x._coord-this._x._coord)*(othr._x._coord-this._x._coord)+(othr._y._coord-this._y._coord)*(othr._y._coord-this._y._coord)
        +(othr._z._coord-this._z._coord)*(othr._z._coord-this._z._coord);
    }

    public double distance(Point3D other){
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return Objects.equals(_x, point3D._x) &&
                Objects.equals(_y, point3D._y) &&
                Objects.equals(_z, point3D._z);
    }

    @Override
    public String toString() {
        return "("+_x+","+_y+","+_z+")";
    }

    /********************** not definition ***************************/

    public Vector subtract(Point3D point){
        return null;
    }

    public Point3D add(Vector vec){
        return null;
    }


    /*****************************geters*****************************/
    public Coordinate get_x() {
        return _x;
    }

    public Coordinate get_y() {
        return _y;
    }

    public Coordinate get_z() {
        return _z;
    }
    /*****************************constructors*****************************/
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }
    public Point3D(double _x, double _y, double _z) {
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    public Point3D(Point3D other) {
        this._x = other._x;
        this._y = other._y;
        this._z = other._z;
    }
}
