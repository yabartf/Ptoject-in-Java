package unittests;

import geometries.*;
import org.junit.Test;

import static org.junit.Assert.*;
import primitives.*;

import java.util.List;

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
    public void testFindIntersections() {
        Sphere sphere = new Sphere( new Point3D(1, 0, 0),1d);
        Vector vector= new Vector(3, 1, 0);
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntsersections(new Ray( new Vector(1, 1, 0),new Point3D(-1, 0, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntsersections(new Ray(vector,
              new Point3D(-1, 0, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).get_x() > result.get(1).get_x())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)

        result=sphere.findIntsersections(new Ray(vector,new Point3D(0.6651530771650466, 0.555051025721682, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", p2, result.get(0));
        // TC04: Ray starts after the sphere (0 points)
        result=sphere.findIntsersections(new Ray(vector,new Point3D(1.83484692283495, 0.944948974278318, 0)));
        assertEquals("Wrong number of points", null, result);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result=sphere.findIntsersections(new Ray(vector,p1));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", p2, result.get(0));
        // TC12: Ray starts at sphere and goes outside (0 points)
        result=sphere.findIntsersections(new Ray(vector,p2));
        assertEquals("Wrong number of points", null, result);
        // **** Group: Ray's line goes through the center
        vector=new Vector(0,1,0);
        p1 = new Point3D(1, -1, 0);
        p2 = new Point3D(1, 1, 0);

        // TC13: Ray starts before the sphere (2 points)

        result=sphere.findIntsersections(new Ray(vector,new Point3D(1,-2,0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).get_y() > result.get(1).get_y())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);
        // TC14: Ray starts at sphere and goes inside (1 points)
        result=sphere.findIntsersections(new Ray(vector,p1));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", p2, result.get(0));
        // TC15: Ray starts inside (1 points)
        result=sphere.findIntsersections(new Ray(vector,new Point3D(1,0.5,0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", p2, result.get(0));
        // TC16: Ray starts at the center (1 points)
        result=sphere.findIntsersections(new Ray(new Vector(1,1,0),sphere.getCenter()));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", new Point3D(1.7071067811865475,0.7071067811865475,0), result.get(0));
        // TC17: Ray starts at sphere and goes outside (0 points)
        result=sphere.findIntsersections(new Ray(vector,p2));
        assertEquals("Wrong number of points", null, result);
        // TC18: Ray starts after sphere (0 points)
        result=sphere.findIntsersections(new Ray(vector,new Point3D(1,2,0)));
        assertEquals("Wrong number of points", null, result);
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        p1=new Point3D(0.7071067812,0.7071067812,0);
        vector=new Vector(1,-1,0);
        // TC19: Ray starts before the tangent point
        result=sphere.findIntsersections(new Ray(vector,new Point3D(0.7071067811865475,1.7071067811865475,0)));
        assertEquals("Wrong number of points", null, result);
        // TC20: Ray starts at the tangent point
        result=sphere.findIntsersections(new Ray(vector,new Point3D(1.7071067811865475,0.7071067811865475,0)));
        assertEquals("Wrong number of points", null, result);
        // TC21: Ray starts after the tangent point
        result=sphere.findIntsersections(new Ray(vector,new Point3D(2.7071067811865475,0.29289321881345254,0)));
        assertEquals("Wrong number of points", null, result);
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result=sphere.findIntsersections(new Ray(new Vector(0,1,0),new Point3D(-1,0,0)));
        assertEquals("Wrong number of points", null, result);
    }

    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Sphere sp = new Sphere(new Point3D(1, 1, 1), 5);


        assertEquals("Bad normal to sphere", new Vector(1, 0, 0), sp.getNormal(new Point3D(6, 1, 1)));
    }
}