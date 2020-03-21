package geometries;

import primitives.Point3D;
import primitives.Vector;


public class Plane implements Geometry {
    Vector normal;
    Point3D pointInPlane;

    public Vector getNormal(Point3D point){
        return normal;
    }
    public Vector getNormal(){
        return normal;
    }

    public Plane(Vector normal, Point3D point) {
        this.normal = normal;
        this.pointInPlane = point;
    }

    public Plane(Point3D x,Point3D y,Point3D z) {
        Vector one = x.subtract(y);
        Vector two = x.subtract(z);
        normal = one.crossProduct(two);
        pointInPlane = x;
    }

}
