package geometries;

import primitives.*;
public class Sphere extends RadialGeometry implements Geometry{
    Point3D center;
    /***************constructor***************/

    public Sphere(Point3D c,double radius){
        super(radius);
        this.center=c;
    }

    /***************getters***************/

    @Override
    public Vector getNormal(Point3D point) {
        return (point.subtract(center)).normalized();
    }

    @Override
    public double get_radius() {
        return super.get_radius();
    }

    public Point3D getCenter() {
        return center;
    }
}
