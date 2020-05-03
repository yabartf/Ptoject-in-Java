package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;


public class Plane implements Geometry {
    Vector normal;
    Point3D pointInPlane;

    /***************getters***************/

    public Vector getNormal(Point3D point){
        return normal.normalized();
    }
    public Vector getNormal(){
        return normal.normalized();
    }

    public Plane(Vector normal, Point3D point) {
        this.normal = normal;
        this.pointInPlane = point;
    }

    public Point3D getPointInPlane() {
        return pointInPlane;
    }

    /***************constructors***************/

    public Plane(Point3D x,Point3D y,Point3D z) {
        Vector one = x.subtract(y);
        Vector two = x.subtract(z);
        normal = one.crossProduct(two);
        pointInPlane = x;
    }


    /**
     * find Intsersections
     * @param ray
     * @return list of Intsersections
     */

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        if (ray.getPoint()==this.pointInPlane)
            return null;
        double isParallel=ray.getDirection().dotProduct(normal);
        if(isParallel==0)
            return null;
        double t=(this.normal.dotProduct(this.pointInPlane.subtract(ray.getPoint())))/(this.normal.dotProduct(ray.getDirection()));

        if(t>0) {
            return List.of(ray.getTargetPoint(t));
        }
        return null;
    }
}
