package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

public class Square extends Polygon{
    private Triangle tri1, tri2;
    private Point3D fourthPoint;
    public  Square(Color objColor, Material material, Point3D p1, Point3D p2, Point3D p3){
        super(objColor,  material, p1,p2,p3);
        tri1 = new Triangle(objColor,material,p1,p2,p3);
        Point3D mid = new Point3D((p2.get_x()+p3.get_x())/2,(p2.get_y()+p3.get_y())/2,(p2.get_z()+p3.get_z())/2);
        Vector toP4 = mid.subtract(p1);
        fourthPoint = p1.add(toP4.scale(2));
        tri2 = new Triangle(objColor,material,p2,p3,fourthPoint);
       // createBox();
    }
    public  Square(Color objColor, Material material, Point3D p1, Point3D p2, Point3D p3, Point3D p4){
        super(objColor,  material, p1, p2, p3, p4);
    }


    public Point3D getFourthPoint(){
        return fourthPoint;
    }

//    @Override
//    public List<GeoPoint> findIntersections(Ray ray, double max) {
//        List<GeoPoint> list1 = tri1.findIntersections(ray);
//        List<GeoPoint> list2 = tri2.findIntersections(ray);
//        return list1 == null? list2 : list1;
//    }
//
//    @Override
//    public List<GeoPoint> findIntersections(Ray ray) {
//        return this.findIntersections(ray,Double.POSITIVE_INFINITY);
//    }
    private void createBox(){
        double xmin =Double.POSITIVE_INFINITY, ymin = Double.POSITIVE_INFINITY, zmin = Double.POSITIVE_INFINITY,
                xmax = Double.NEGATIVE_INFINITY, ymax = Double.NEGATIVE_INFINITY, zmax = Double.NEGATIVE_INFINITY;
        List<Point3D> squereVertices = tri1._vertices;
        squereVertices.add(fourthPoint);
        for(var vertic :squereVertices){
            xmin = Math.min(xmin,vertic.get_x());
            ymin = Math.min(ymin,vertic.get_y());
            zmin = Math.min(zmin,vertic.get_z());
            xmax = Math.max(xmax, vertic.get_x());
            ymax = Math.max(ymax, vertic.get_y());
            zmax = Math.max(zmax, vertic.get_z());
        }
        _box = new Box(new Point3D(xmin, ymin, zmin), new Point3D(xmax, ymax, zmax));
    }
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
