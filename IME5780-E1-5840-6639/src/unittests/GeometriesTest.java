package unittests;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @org.junit.jupiter.api.Test
    void add() {
        // =============== Boundary Values Tests ==================

    }

    @org.junit.jupiter.api.Test
    void findIntsersections() {
        Ray ray = new Ray(new Vector(1,0,0.25), new Point3D(2,0,0));
        Triangle triangle = new Triangle(new Point3D(6,-2,0), new Point3D(6,2,0),new Point3D(6,0,3));
        Sphere sphere = new Sphere(new Point3D(3.5,0.5,0),1);
        Plane plane = new Plane(new Point3D(7,-2,0),new Point3D(7,2,0),new Point3D(7,0,3));

        // =============== Boundary Values Tests ==================
        // TC:01 empty list
        Geometries geometries = new Geometries();
        assertNull(geometries.findIntsersections(ray),"not null");

        // TC:02 all...
        geometries.add(triangle,sphere,plane);
        assertEquals(4,geometries.findIntsersections(ray).size(),"2 intersetions(2 in sphere)");

        // TC:03 list size = 1
        ray = new Ray(new Vector(1,0,0.25), new Point3D(6.5,0,0.25));
        assertEquals(geometries.findIntsersections(ray).size(),1,"1 intersection(only plane)");

        // TC:04 list size = 0
        ray = new Ray(new Vector(-1,0,0.25), new Point3D(2,0,0));
        assertNull(geometries.findIntsersections(ray),"not null");

        // ============ Equivalence Partitions Tests ==============
        // TC:05 list size = 2
        ray = new Ray(new Vector(1,0,0.25), new Point3D(5,0,0));
        assertEquals(geometries.findIntsersections(ray).size(),2,"2 intersetions");

    }
}