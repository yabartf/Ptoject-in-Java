package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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
        return null;
    }

    public Ray getLegate() {
        return legate;
    }
}
