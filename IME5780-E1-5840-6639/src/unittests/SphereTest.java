package unittests;

import geometries.*;
import org.junit.Test;

import static org.junit.Assert.*;
import primitives.*;

public class SphereTest {

    @Test
    public void testConstructor() {
        try {
            new Sphere(new Point3D(-2, -5, 7), 1);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct sphere");
        }
        try {
            new Sphere(new Point3D(2, -3, 1), -2);
            fail("Constructed a sphere withe negative radius");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(new Point3D(0, 0, 0), 5);


        assertEquals("Bad normal to sphere", new Vector(1, 0, 0), sp.getNormal(new Point3D(5, 0, 0)));
    }
}