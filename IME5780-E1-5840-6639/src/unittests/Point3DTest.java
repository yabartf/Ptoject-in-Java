package unittests;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static junit.framework.TestCase.*;

public class Point3DTest {

    @Test
    public void distanceSquared() {
        Point3D point1 = new Point3D(1,1,1);
        Point3D point2 = new Point3D(2,2,2);
        assertEquals(3, point1.distanceSquared(point2),1e-10);
        assertEquals(0, point2.distanceSquared(point2),1e-10);
    }

    @Test
    public void distance() {
        Point3D point1 = new Point3D(1,1,1);
        Point3D point2 = new Point3D(2,-2,0);
        assertEquals(Math.sqrt(11), point1.distance(point2),1e-10);
        assertEquals(0, point2.distance(point2),1e-10);
    }

    @Test
    public void subtract() {
       Point3D point1 = new Point3D(1,2,3);
       assertTrue(new Point3D(2,4,6).subtract(point1).equals(new Vector(point1)));
       try {
           point1.subtract(point1);
           fail("Didn't throw divide by zero exception");
       }
       catch (IllegalArgumentException e)
           {
               assertTrue(e.getMessage() != null);
           }
    }

    @Test
    public void add() {
        Point3D point1 = new Point3D(1,4,1);
        assertTrue(point1.equals(new Point3D(0,2,3).add(new Vector(1,2,-2))));

    }
}