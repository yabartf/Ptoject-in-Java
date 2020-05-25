package geometries;

import primitives.*;

import java.util.List;

public interface Intersectable {
    List<GeoPoint> findIntersections(Ray ray);
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

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
            return sphere._radius == other._radius && sphere.center.equals(other.center);
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
