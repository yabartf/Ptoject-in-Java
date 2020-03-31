package primitives;

public class Ray {
    Point3D point;
    Vector direction;

    /******************constructors*****************/

    Ray(Vector v,Point3D p){
        point = p;
        direction = v.normalize();
    }
    Ray(Ray other){
        this.direction = other.direction;
        this.point = other.point;
    }
}
