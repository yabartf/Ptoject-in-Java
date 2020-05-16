package geometries;

import primitives.*;

import java.util.List;

public interface Intersectable {
    List<GeoPoint> findIntersections(Ray ray);
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;
        public GeoPoint(Geometry g,Point3D p){
            this.geometry=g;
            this.point=p;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            return true;

        }
    }

}
