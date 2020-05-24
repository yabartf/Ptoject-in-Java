package primitives;

import java.util.Objects;

public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;
    public static final Point3D ZERO = new Point3D(0,0,0);

    /**
     * distance methods
     * @param othr
     * @return
     */
    public double distanceSquared(Point3D othr){
        return (othr._x._coord-this._x._coord)*(othr._x._coord-this._x._coord) +
                (othr._y._coord-this._y._coord)*(othr._y._coord-this._y._coord) +
                (othr._z._coord-this._z._coord)*(othr._z._coord-this._z._coord);
    }

    /**
     * calc distance
     * @param other
     * @return
     */
    public double distance(Point3D other){
        return Math.sqrt(distanceSquared(other));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return Util.isZero(_x.get()-point3D._x.get()) &&
                Util.isZero(_y.get()- point3D._y.get()) &&
                Util.isZero(_z.get()- point3D._z.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(_x, _y, _z);
    }

    @Override
    public String toString() {
        return "("+_x+","+_y+","+_z+")";
    }


    /**
     * Mathematical operations
     * @param point
     * @return
     */
    public Vector subtract(Point3D point){
        return new Vector(this._x._coord - point._x._coord,this._y._coord - point._y._coord,this._z._coord - point._z._coord);
    }

    /**
     * add vector
     * @param vec
     * @return
     */
    public Point3D add(Vector vec){
        Point3D temp = vec.point;
        return new Point3D(_x._coord + temp._x._coord,_y._coord + temp._y._coord,_z._coord + temp._z._coord);
    }


    /*****************************geters*****************************/

    public double get_x() { return  _x.get(); }

    public double get_y() {return _y.get(); }

    public double get_z(){return _z.get(); }

    /*****************************constructors*****************************/
    /**
     * constructor
     * @param _x
     * @param _y
     * @param _z
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }

    /**
     * constructor
     * @param _x
     * @param _y
     * @param _z
     */
    public Point3D(double _x, double _y, double _z) {
        this._x = new Coordinate(_x);
        this._y = new Coordinate(_y);
        this._z = new Coordinate(_z);
    }

    /**
     * copy constructor
     * @param other
     */
    public Point3D(Point3D other) {
        this._x = other._x;
        this._y = other._y;
        this._z = other._z;
    }
}
