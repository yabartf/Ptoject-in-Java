package geometries;


import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Cylinder extends RadialGeometry  {
    // the height of the clynder
    double height;

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
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
