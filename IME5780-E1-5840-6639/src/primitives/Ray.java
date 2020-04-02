package primitives;

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

    public Vector getDirection() {
        return direction;
    }

    public Point3D getPoint() {
        return point;
    }
}
