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
     * @param normal to the plane
     * @param point in plane
     */
    public Plane(Vector normal, Point3D point) {
        this(Color.BLACK,normal,point);
    }

    /**
     * constructor thar generate a plane with color and material
     * @param _emmission the color of the plane
     * @param _matirial of the plane
     * @param normal to the plane
     * @param pointInPlane
     */
    public Plane(Color _emmission, Material _matirial, Vector normal, Point3D pointInPlane) {
        super(_emmission, _matirial);
        this.normal = normal;
        this.pointInPlane = pointInPlane;
        createBox();
    }

    /**
     * constructor that generate plane with color
     * @param objectColor color of the plane
     * @param normal to the plane
     * @param point in the plane
     */
    public Plane(Color objectColor, Vector normal, Point3D point) {
        this(objectColor,new Material(0,0,0),normal,point);
        _emmission=objectColor;

    }

    /**
     * constructor that generate plane withe 3 points inside the plane
     * @param x point 1
     * @param y point 2
     * @param z point 3
     */
    public Plane(Point3D x,Point3D y,Point3D z) {
        Vector one = x.subtract(y);
        Vector two = x.subtract(z);
        normal = one.crossProduct(two);
        pointInPlane = x;
        createBox();
    }

    /**
     * constructor thar generate plane with color using 3 points inside the plane
     * @param objColor color of the plane
     * @param x point 1
     * @param y point 2
     * @param z point 3
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
        //if the ray starts at the same point tht define the plane there are no intersections
        if (ray.getPoint().equals(this.pointInPlane))
            return null;
        // if the ray is orthogonal the the normal of the plane the ray is parallel to the plane
        double isParallel=ray.getDirection().dotProduct(normal);
        if(Util.alignZero(isParallel)==0)
            return null;
        double t=(this.normal.dotProduct(this.pointInPlane.subtract(ray.getPoint())))/(this.normal.dotProduct(ray.getDirection()));
        // if the distance if less then max and t>0(the ray starts before the plane)
        if(Util.alignZero(t-max)<=0&&t>0) {
            return List.of(new GeoPoint(this,ray.getTargetPoint(t)));
        }
        return null;
    }

    /**
     * plane is an infinite object
     */
    void createBox(){
        _box=new Box(new Point3D(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY),
                new Point3D(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY));
    }
    @Override
    public Box getBox() {
        return _box;
    }
}
