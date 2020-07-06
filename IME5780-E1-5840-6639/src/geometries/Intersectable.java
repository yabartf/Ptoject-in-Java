package geometries;

import primitives.*;

import java.util.List;

public interface Intersectable {
    default List<GeoPoint> findIntersections(Ray ray) {
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }
    List<GeoPoint> findIntersections(Ray ray, double max);
    void BVH(int deep);
    Box getBox();

    /**
     * an inside class that define a box
     * box is around avery intersectable
     */
    public class Box {
        Point3D _min;
        Point3D _max;
        Point3D mid;

        /**
         * constractor that generate a box with two points
         * @param min point
         * @param max point
         */
        public Box(Point3D min, Point3D max) {
            _max = max;
            _min = min;
            mid = new Point3D(max.get_x()+min.get_x()/2,max.get_y()+min.get_y()/2,max.get_z()+min.get_z()/2);
        }

        public Point3D get_min() {
            return _min;
        }

        public Point3D get_max() {
            return _max;
        }

        /**
         *
         * @param ray
         * @return true if the ray intersect withe the box and false if not
         */
        public boolean inBox(Ray ray) {
            double tmin = (_min.get_x() - ray.getPoint().get_x()) / ray.getDirection().getPoint().get_x();
            double tmax = (_max.get_x() - ray.getPoint().get_x()) / ray.getDirection().getPoint().get_x();
            if (tmin > tmax) {
                tmax = swap(tmin, tmin = tmax);
            }
            double tymin = (_min.get_y() - ray.getPoint().get_y()) / ray.getDirection().getPoint().get_y();
            double tymax = (_max.get_y() - ray.getPoint().get_y()) / ray.getDirection().getPoint().get_y();
            if(tymin > tymax)
                tymax = swap(tymin, tymin = tymax);
            if(tmin > tymax || tymin > tmax)
                return false;
            if (tymin > tmin)
                tmin = tymin;

            if (tymax < tmax)
                tmax = tymax;
            double tzmin = (_min.get_z() - ray.getPoint().get_z()) / ray.getDirection().getPoint().get_z();
            double tzmax = (_max.get_z() - ray.getPoint().get_z()) / ray.getDirection().getPoint().get_z();
            if (tzmin > tzmax)
                tzmax = swap(tzmin, tzmin = tzmax);
            if ((tmin > tzmax) || (tzmin > tmax))
                return false;
            return true;
        }

        public double swap(double a, double b) {
            return a;
        }

    }

    /**
     * inside class that define geo point
     * geo point is for intersection point
     * its hold the point of intersection and the object that the ray intesect with
     */
    public static class GeoPoint {
        public final Geometry geometry;
        public final Point3D point;

        /**
         * constractor that generate a geopoint with point and geometry
         * @param g the geometry
         * @param p the point
         */
        public GeoPoint(Geometry g, Point3D p) {
            this.geometry = g;
            this.point = p;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            GeoPoint geoPoint=(GeoPoint)obj;
            if (geoPoint.geometry.getClass() == Triangle.class)
                return triangeEquals((Triangle) this.geometry, (Triangle) geoPoint.geometry);
            if (geoPoint.geometry.getClass() == Sphere.class)
                return sphereEquals((Sphere) this.geometry, (Sphere) geoPoint.geometry);
            if (geoPoint.geometry.getClass() == Plane.class)
                return planeEquals((Plane) this.geometry, (Plane) geoPoint.geometry);
            return false;


        }

        /**
         * private case off triangle in equals
         * @param triangle
         * @param other
         * @return
         */
        private boolean triangeEquals(Triangle triangle, Triangle other) {
            for (int i = 0; i < triangle._vertices.size(); i++) {
                if (!triangle._vertices.contains(other._vertices.get(i)))
                    return false;
            }
            return true;
        }

        /**
         * private case off sphere in equals
         * @param sphere
         * @param other
         * @return
         */
        private boolean sphereEquals(Sphere sphere, Sphere other) {
            return sphere._radius == other._radius && sphere._center.equals(other._center);
        }

        /**
         * private case off plane in equals
         * @param plane
         * @param other
         * @return
         */
        private boolean planeEquals(Plane plane, Plane other) {
            return plane.normal.equals(other.normal) && plane.pointInPlane.equals(other.pointInPlane);
        }

    }
}
