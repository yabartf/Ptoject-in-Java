package geometries;

import primitives.*;

import java.util.List;


public class Tube extends RadialGeometry{
    Ray legate;

    @Override
    public Box getBox() {
        return null;
    }
/***************constructor***************/
    /**
     * constructor
     * @param leg
     * @param rad
     */
    public Tube(Ray leg,double rad){
        super(rad);
        legate=leg;
    }

    /***************getters***************/

    @Override
    public double get_radius() {
        return super.get_radius();
    }

    @Override
    public Vector getNormal(Point3D point) {

        double t=legate.getDirection().dotProduct(point.subtract(legate.getPoint()));
        Point3D o=legate.getTargetPoint(t);
        Vector n=point.subtract(o).normalized();
        return n;
    }

    public Ray getLegate() {
        return legate;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        return null;
    }
}
