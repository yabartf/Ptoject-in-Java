package geometries;

import primitives.*;

import java.util.List;

public class Sphere extends RadialGeometry{
    Point3D _center;

    /***************constructor***************/
    /**
     * constructor
     * @param emmision
     * @param material
     * @param _radius
     * @param center
     */
    public Sphere(Color emmision, Material material, double _radius, Point3D center) {
        super(emmision, material, _radius);
        this._center = center;
        createBox();
    }

    /**
     *  constructor
     * @param center
     * @param radius
     */
    public Sphere(Point3D center,double radius){
        super(radius);
        this._center =center;
    }

    /**
     * constructor
     * @param objColor
     * @param center
     * @param radius
     */
    public Sphere(Color objColor,Point3D center,double radius){
        this(center, radius);
        _emmission=objColor;
    }


    /***************getters***************/

    @Override
    public Vector getNormal(Point3D point) {
        return (point.subtract(_center)).normalized();
    }

    @Override
    public double get_radius() {
        return super.get_radius();
    }

    public Point3D getCenter() {
        return _center;
    }

    @Override
    public Box getBox() {
        return _box;
    }

    /**
     * find Intsersections
     * @param ray
     * @return list of Intsersections.
     */

    @Override
    public List <GeoPoint> findIntersections(Ray ray, double max) {
        if (ray.getPoint().equals(this._center))
            return List.of(new GeoPoint(this,ray.getTargetPoint(_radius)));
        Vector u = _center.subtract(ray.getPoint());
        double tm = ray.getDirection().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared() - tm * tm);
        if (d > _radius ||Util.isZero(d-_radius) )
            return null;
        double th = Math.sqrt(_radius * _radius - d * d);
        double t1 = Util.alignZero(tm + th), t2 = Util.alignZero(tm - th);
        //if (t2 <= 0 && t1 <= 0)
           // return null;
        if (Util.alignZero(t1-max) <=0 &&t1 > 0&&t2<=0)
            return List.of(new GeoPoint(this,(ray.getTargetPoint(t1))));
        if (Util.alignZero(t2-max)<=0&&t2 > 0&&t1<=0)
            return List.of(new GeoPoint(this,(ray.getTargetPoint(t2))));
        if(Util.alignZero(t1-max)<=0&&t1>0&&Util.alignZero(t2-max)<=0&&t2>0)
            return List.of(new GeoPoint(this,ray.getTargetPoint(t1)),new GeoPoint(this,ray.getTargetPoint(t2)));
        return null;
    }


    private void createBox(){
        double xmin, xmax, ymin, ymax, zmin, zmax;
        xmin = this._center.get_x() - this._radius;
        xmax = this._center.get_x() + this._radius;
        ymin = this._center.get_y() - this._radius;
        ymax = this._center.get_y() + this._radius;
        zmin = this._center.get_z() - this._radius;
        zmax = this._center.get_z() + this._radius;
        this._box = new Box(new Point3D(xmin, ymin, zmin),new Point3D(xmax, ymax ,zmax));
    }
}
