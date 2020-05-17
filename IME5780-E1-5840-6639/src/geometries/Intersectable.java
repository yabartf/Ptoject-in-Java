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
            if (obj.getClass() == Triangle.class)
                return triangeEquals((Triangle) this.geometry, (Triangle) obj);
            if (obj.getClass() == Sphere.class)
                return sphereEquals((Sphere) this.geometry, (Sphere) obj);
            if (obj.getClass() == Plane.class)
                return planeEquals((Plane) this.geometry, (Plane) obj);
            return false;


        }

        private boolean triangeEquals(Triangle triangle, Triangle other) {
            for (int i = 0; i < triangle._vertices.size(); i++) {
                if (!triangle._vertices.get(i).equals(other._vertices.get(i)))
                    return false;
            }
            return true;
        }

        private boolean sphereEquals(Sphere sphere, Sphere other) {
            return sphere._radius == other._radius && sphere.center.equals(other.center);
        }

        private boolean planeEquals(Plane plane, Plane other) {
            return plane.normal.equals(other.normal) && plane.pointInPlane.equals(other.pointInPlane);
        }

    }
}
