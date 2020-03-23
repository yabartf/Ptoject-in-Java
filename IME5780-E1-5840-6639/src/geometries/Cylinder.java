package geometries;


import primitives.Point3D;
import primitives.Vector;

public class Cylinder extends RadialGeometry implements Geometry {
    double height;

    Cylinder(double radius,double hei){
    super(radius);
    if (hei<0)
        throw new IllegalArgumentException("height must be bigger then 0");
    this.height=hei;
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
}
