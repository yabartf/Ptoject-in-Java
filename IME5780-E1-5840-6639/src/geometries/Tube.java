package geometries;

import primitives.*;

import java.util.List;


public class Tube extends RadialGeometry implements Geometry{
    Ray legate;

    /***************constructor***************/

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
    public List<Point3D> findIntsersections(Ray ray) {
        return null;
    }
}
