package primitives;


import java.util.Objects;

public class Vector {
    Point3D point;

    /*****************************constructors*****************************/
    public Vector(Coordinate _x, Coordinate _y, Coordinate _z) {
        if (_x._coord==0 & _y._coord==0 & _z._coord==0)
            throw new IllegalArgumentException("zero vector");
        point = new Point3D(_x,_y,_z);
    }
    public Vector(double _x, double _y, double _z) {
        if (_x==0 & _y==0 & _z==0)
            throw new IllegalArgumentException("zero vector");
        point = new Point3D(_x,_y,_z);
    }

    public Vector(Point3D other) {
        if (other.equals(Point3D.ZERO))
            throw new IllegalArgumentException("zero vector");
        point = new Point3D(other);
    }

    public Vector(Vector other) {
        point = new Point3D(other.point);
    }

    /**
     * Mathematical operations
     * @param other
     * @return
     */

    public Vector add(Vector other){
        return new Vector(this.point._x._coord + other.point._x._coord,
                this.point._y._coord + other.point._y._coord,
                this.point._z._coord + other.point._z._coord);
    }

    public Vector substract(Vector other){
        return new Vector(this.point._x._coord - other.point._x._coord,
                this.point._y._coord - other.point._y._coord,
                this.point._z._coord - other.point._z._coord);
    }

    /**
     * multiplication in scalar
     * @param scalar
     * @return
     */
    public Vector scale(double scalar){
        return new Vector(this.point._x._coord * scalar,this.point._y._coord * scalar, this.point._z._coord * scalar);
    }

    /**
     * multiplication in other vector
     * @param other
     * @return
     */
    public double dotProduct(Vector other){
        return this.point._x._coord * other.point._x._coord + this.point._y._coord * other.point._y._coord +
                this.point._z._coord * other.point._z._coord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(point, vector.point);
    }

    /**
     * calculate normal vector
     * @param other
     * @return Vector
     */
    public Vector crossProduct(Vector other){
        return new Vector(this.point._y._coord * other.point._z._coord - this.point._z._coord * other.point._y._coord,
                this.point._z._coord * other.point._x._coord - this.point._x._coord * other.point._z._coord,
                this.point._x._coord * other.point._y._coord - this.point._y._coord * other.point._x._coord);
    }

    /**********************calculate length method********************/

    public double lengthSquared(){
        return (point._x._coord * point._x._coord) + (point._y._coord * point._y._coord) + (point._z._coord * point._z._coord);
    }

    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**************************normalized methods***********************/
    
    public Vector normalize(){
        point = this.scale(1/length()).point;
        return this;
    }

    public Vector normalized(){
        return new Vector(this).normalize();
    }
}
