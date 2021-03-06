package primitives;

import static primitives.Util.isZero;

public class Ray {
    Point3D point;
    Vector direction;
    private static final double DELTA = 0.1;

    /******************constructors*****************/
    /**
     * constructor
     * @param v
     * @param p
     */
    public Ray(Vector v,Point3D p){
        point = p;
        direction = v.normalize();
    }

    public Ray(Vector l,Vector n,Point3D point){
        this.point=point.add(n.scale(n.dotProduct(l) > 0 ? DELTA : -DELTA));
        this.direction=l;
    }
    /**
     * copy constructor
     * @param other
     */
    public Ray(Ray other){
        this.direction = other.direction;
        this.point = other.point;
    }
        /********************getters****************/
    public Point3D getTargetPoint(double length) {
        return isZero(length ) ? point : point.add(direction.scale(length));
    }

    public Vector getDirection() {
        return direction;
    }

    public Point3D getPoint() {
        return point;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ray other=(Ray)obj;
        return (this.point.equals(other.point)&&this.direction.equals(other.direction));
    }
}
