package primitives;

import static primitives.Util.isZero;

public class Ray {
    Point3D point;
    Vector direction;

    /******************constructors*****************/

    public Ray(Vector v,Point3D p){
        point = p;
        direction = v.normalize();
    }
    public Ray(Ray other){
        this.direction = other.direction;
        this.point = other.point;
    }

    public Point3D getTargetPoint(double length) {
        return isZero(length ) ? point : point.add(direction.scale(length));
    }

    public Vector getDirection() {
        return direction;
    }

    public Point3D getPoint() {
        return point;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ray other=(Ray)obj;
        return (this.point.equals(other.point)&&this.direction.equals(other.direction));
    }
}
