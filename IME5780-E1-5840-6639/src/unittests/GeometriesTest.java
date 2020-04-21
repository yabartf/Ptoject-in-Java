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
        Ray ray = new Ray(new Vector(0,2,2), new Point3D(2,0,0));
        Triangle triangle = new Triangle(new Point3D(3,0,0), new Point3D(0.51,0.78,0),new Point3D(2,1.07,1.07));
        Sphere sphere = new Sphere(new Point3D(2.1,0.47,0.47),0.3);
        Plane plane = new Plane(new Vector(-1.01,-3.83,0.37), new Point3D(2,1.66,1.66));

        // =============== Boundary Values Tests ==================
        // TC:01 empty list
        Geometries geometries = new Geometries();
        assertNull(geometries.findIntsersections(ray));

        // TC:02 all..
        geometries.add(triangle,sphere,plane);
        assertEquals(geometries.findIntsersections(ray).size(),3);

        // TC:03 list size = 1
        ray = new Ray(new Vector(0.1,0.68,0),new Point3D(0.65,1.31,0));
        assertEquals(geometries.findIntsersections(ray).size(),1);

        // TC:04 list size = 0
        ray = new Ray(new Vector(1,1,1),new Point3D( -3,-3,-4));
        assertEquals(geometries.findIntsersections(ray).size(),0);

        // ============ Equivalence Partitions Tests ==============
        // TC:05 list size = 2
        ray = new Ray(new Vector(0.24,0.25,0.5), new Point3D(2.16,1.35,0));
        assertEquals(geometries.findIntsersections(ray).size(),2);

    }
}