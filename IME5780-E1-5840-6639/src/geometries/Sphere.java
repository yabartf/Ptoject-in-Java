package geometries;

import primitives.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List <Point3D> findIntsersections(Ray ray) {
        try {
            Vector u = center.subtract(ray.getPoint());
            double tm = ray.getDirection().dotProduct(u);
            double d = Math.sqrt(u.lengthSquared() - tm * tm);
            if (d > _radius |Util.isZero(d-_radius) )
                return null;
            double th = Math.sqrt(_radius * _radius - d * d);
            double t1 = Util.alignZero(tm + th), t2 = Util.alignZero(tm - th);
            if (t1<=0&t2<=0)
                return null;
            if (t1 > 0&t2<=0)
               return List.of(ray.getTargetPoint(t1));
            if (t2 > 0&t1<=0)
                return List.of(ray.getTargetPoint(t2));
            return List.of((ray.getTargetPoint(t1)),ray.getTargetPoint(t2));
        }
        catch (IllegalArgumentException e){
         return List.of(ray.getTargetPoint(_radius));
        }

    }
}
