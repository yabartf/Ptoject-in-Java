package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

public class Square extends Polygon{
    public Triangle tri1, tri2;
    public  Square(Color objColor, Material material, Point3D p1, Point3D p2, Point3D p3){
        super(objColor,  material, p1,p2,p3);
        tri1 = new Triangle(objColor,material,p1,p2,p3);
        Point3D mid = new Point3D((p2.get_x()+p3.get_x())/2,(p2.get_y()+p3.get_y())/2,(p2.get_z()+p3.get_z())/2);
        Vector toP4 = mid.subtract(p1);
        Point3D p4 = p1.add(toP4.scale(2));
        tri2 = new Triangle(objColor,material,p2,p3,p4);
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        List<GeoPoint> list1 = tri1.findIntersections(ray);
        List<GeoPoint> list2 = tri2.findIntersections(ray);
        return list1 == null? list2 : list1;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return this.findIntersections(ray,Double.POSITIVE_INFINITY);
    }
}
