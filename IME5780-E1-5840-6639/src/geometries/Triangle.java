package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

public class Triangle extends Polygon{

    /***************constructor***************/
    /**
     * constructor
     * @param objColor
     * @param material
     * @param vertices
     */
    public Triangle(Color objColor, Material material, Point3D... vertices) {
        super(objColor,  material, vertices);
    }

    /**
     * constructor
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Point3D p1,Point3D p2,Point3D p3) {
        super(p1,p2,p3);
    }

    /**
     * constructor
     * @param objColor
     * @param p1
     * @param p2
     * @param p3
     */
    public Triangle(Color objColor,Point3D p1,Point3D p2,Point3D p3){
        super(objColor,p1,p2,p3);
    }


    /**
     * find Intsersections
     * @param ray
     * @return list of Intsersections
     */
    public List<GeoPoint> findIntersections(Ray ray){
        return this.findIntersections(ray,Double.POSITIVE_INFINITY);
    }
    public List<GeoPoint> findIntersections(Ray ray,double max) {
        List<GeoPoint> intersections = _plane.findIntersections(ray, max);
        if (intersections == null)
            return null;

        Vector v = ray.getDirection();
        Point3D p0 = ray.getPoint();

        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        Vector v3 = _vertices.get(2).subtract(p0);

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) return null;

        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? List.of(new GeoPoint(this,intersections.get(0).point)) : null;
    }
}
