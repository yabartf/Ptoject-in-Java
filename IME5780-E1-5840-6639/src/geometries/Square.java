package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

public class Square extends Polygon{
    /**
     * constructor
     * @param objColor
     * @param material
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     */
    public  Square(Color objColor, Material material, Point3D p1, Point3D p2, Point3D p3, Point3D p4){
        super(objColor,  material, p1, p2, p3, p4);
    }

    /**
     * find intersections
     * @param ray
     * @return list of geo point
     */
    public List<GeoPoint> findIntersections(Ray ray){
        return this.findIntersections(ray,Double.POSITIVE_INFINITY);
    }

    /**
     * find intersections
     * @param ray
     * @param max
     * @return list of geo point
     */
    public List<GeoPoint> findIntersections(Ray ray,double max) {

        List<GeoPoint> intersections = _plane.findIntersections(ray, max);
        if (intersections == null)
            return null;

        Vector v = ray.getDirection();
        Point3D p0 = ray.getPoint();

        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        Vector v3 = _vertices.get(2).subtract(p0);
        Vector v4 = _vertices.get(3).subtract(p0);

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;
        double s3 = v.dotProduct(v3.crossProduct(v4));
        if (isZero(s3)) return null;
        double s4 = v.dotProduct(v4.crossProduct(v1));
        if (isZero(s4)) return null;

        return ((s1 > 0 && s2 > 0 && s3 > 0 && s4 > 0) || (s1 < 0 && s2 < 0 && s3 < 0 && s4 < 0)) ? List.of(new GeoPoint(this,intersections.get(0).point)) : null;
    }
}
