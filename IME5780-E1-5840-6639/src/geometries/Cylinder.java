package geometries;


import primitives.*;

import java.util.ArrayList;
import java.util.List;

public class Cylinder extends RadialGeometry{
    // the height of the clynder
    double height;
    Point3D begin;

    /***
     * \
     * @param radius the radius of the clyinder go to the super class radial gemetry
     * @param hei the hight of the cylinder
     * @throws IllegalArgumentException if hight is less the 0
     */
    Cylinder(double radius,double hei){
    super(radius);
    if (hei<0)
        throw new IllegalArgumentException("height must be bigger then 0");
    this.height=hei;
    }
    public Cylinder(Point3D beg, double radius, double hei, Material mat, Color col){
        super(col,mat,radius);
        height = hei;
        begin = beg;
    }
/******************getters***************/
    @Override
    public double get_radius() {
        return super.get_radius();
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        List<GeoPoint> list = new ArrayList<>();
        Point3D rayP = ray.getPoint();
        Vector rayV = ray.getDirection();

        for (GeoPoint p : super.findIntersections(ray)) {
            double d = Math.abs(rayV.dotProduct(p.point.subtract(rayP)));
            //if point is in the range
            if (Util.usubtract(height / 2, d) >= 0.0)
                list.add(new GeoPoint(this, p.point));
        }

        //get upper plane intersections
        Point3D upperPoint = rayP.add(rayV.scale(height / 2));
        Plane upperPlane = new Plane(rayV,upperPoint);
        for (GeoPoint p : upperPlane.findIntersections(ray)) {
            //if point is in the range
            if (Util.usubtract(_radius, upperPoint.distance(p.point)) >= 0)
                list.add(new GeoPoint(this, p.point));
        }

        //get under plane intersections
        Point3D underPoint = rayP.subtract(rayV.scale(height / 2).getPoint()).getPoint();
        Plane underPlane = new Plane(rayV,underPoint);
        for (GeoPoint p : underPlane.findIntersections(ray)) {
            //if point is in the range
            if (Util.usubtract(_radius, underPoint.distance(p.point)) >= 0)
                list.add(new GeoPoint(this, p.point));
        }

        return list;

    }
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        return null;
    }

    @Override
    public Box getBox() {
        return null;
    }
}
