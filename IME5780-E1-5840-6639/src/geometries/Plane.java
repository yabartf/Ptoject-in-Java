package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;


public class Plane extends Geometry {
    Vector normal;
    Point3D pointInPlane;

    /***************getters***************/

    public Vector getNormal(Point3D point){
        return normal.normalized();
    }
    public Vector getNormal(){
        return normal.normalized();
    }
    public Point3D getPointInPlane() {
        return pointInPlane;
    }




    /***************constructors***************/
    /**
     * constructor
     * @param normal
     * @param point
     */
    public Plane(Vector normal, Point3D point) {
        this(Color.BLACK,normal,point);
    }

    /**
     * constructor
     * @param _emmission
     * @param _matirial
     * @param normal
     * @param pointInPlane
     */
    public Plane(Color _emmission, Material _matirial, Vector normal, Point3D pointInPlane) {
        super(_emmission, _matirial);
        this.normal = normal;
        this.pointInPlane = pointInPlane;
        createBox();
    }

    /**
     * constructor
     * @param objectColor
     * @param normal
     * @param point
     */
    public Plane(Color objectColor, Vector normal, Point3D point) {
        this(objectColor,new Material(0,0,0),normal,point);
        _emmission=objectColor;

    }

    /**
     * constructor
     * @param x
     * @param y
     * @param z
     */
    public Plane(Point3D x,Point3D y,Point3D z) {
        Vector one = x.subtract(y);
        Vector two = x.subtract(z);
        normal = one.crossProduct(two);
        pointInPlane = x;
        createBox();
    }

    /**
     * constructor
     * @param objColor
     * @param x
     * @param y
     * @param z
     */
    public Plane(Color objColor,Point3D x,Point3D y,Point3D z){
        this(x,y,z);
        _emmission=objColor;
    }

    /**
     * find Intsersections
     * @param ray
     * @return list of Intsersections
     */



    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        if (ray.getPoint().equals(this.pointInPlane))
            return null;
        double isParallel=ray.getDirection().dotProduct(normal);
        if(Util.alignZero(isParallel)==0)
            return null;
        double t=(this.normal.dotProduct(this.pointInPlane.subtract(ray.getPoint())))/(this.normal.dotProduct(ray.getDirection()));

        if(Util.alignZero(t-max)<=0&&t>0) {
            return List.of(new GeoPoint(this,ray.getTargetPoint(t)));
        }
        return null;
    }

    void createBox(){
        _box=new Box(new Point3D(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY),
                new Point3D(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY));
    }
    @Override
    public Box getBox() {
        return _box;
    }
}
