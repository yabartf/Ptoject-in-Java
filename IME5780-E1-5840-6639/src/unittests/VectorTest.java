package unittests;
import primitives.Vector;

import static java.lang.System.out;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VectorTest {

    @org.junit.Test
    public void add() {
        Vector v1 = new Vector(1.0, 1.0, 1.0);
        Vector v2 = new Vector(-1.0, -1.0, -1.5);

        v1 = v1.add(v2);
        assertEquals(new Vector(0.0,0.0,-0.5),v1);

        v2 = v2.add(v1);
        assertEquals(new Vector(-1.0, -1.0, -2.0),v2);
    }

    @org.junit.Test
    public void substract() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,1,1);
        if(!new Vector(0,1,2).equals(v1.substract(v2)))
            out.println("problam in substracting vectors");
    }

    @org.junit.Test
    public void scale() {
        Vector v1 = new Vector(1,2,3);
        if(!new Vector(3,6,9).equals(v1.scale(3)))
            out.println("problam in scale method");
    }

    @org.junit.Test
    public void dotProduct() {
        Vector v1 = new Vector(1,3,5);
        Vector v2 = new Vector(2,3,-3);
        double result = v1.dotProduct(v2);
        assertEquals("dot product method",result,-4,1e-8);
    }

    @org.junit.Test
    public void crossProduct() {
        Vector v1 = new Vector(3.5, -5.0, 10.0);
        Vector v2 = new Vector(2.5,7,0.5);
        Vector v3 = v1.crossProduct(v2);

        assertEquals( 0, v3.dotProduct(v2), 1e-10);
        assertEquals( 0, v3.dotProduct(v1), 1e-10);

        Vector v4 = v2.crossProduct(v1);

        System.out.println(v3.toString());
        System.out.println(v4.toString());

        try {
            v3.add(v4);
            fail("Vector (0,0,0) not valid");
        }
        catch  (IllegalArgumentException e)
        {
            assertTrue(e.getMessage()!= null);
        }
//        assertTrue(v3.length() >84);
        assertEquals(84,v3.length(),0.659);
    }

    @org.junit.Test
    public void lengthSquared() {
    }

    @org.junit.Test
    public void length() {
    }

    @org.junit.Test
    public void normalize() {
        Vector v = new Vector(3.5, -5, 10);
        v.normalize();
        assertEquals(1, v.length(), 1e-10);

        try {
            Vector v1 = new Vector(0, 0, 0);
            v.normalize();
            fail("Didn't throw divide by zero exception!");
        } catch (IllegalArgumentException ex) {
          //  assertEquals("Point3D(0.0,0.0,0.0) not valid for vector head", ex.getMessage());
        }
        assertTrue(true);
    }

    @org.junit.Test
    public void normalized() {
    }
}