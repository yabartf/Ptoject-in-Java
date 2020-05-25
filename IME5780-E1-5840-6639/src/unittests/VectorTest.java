package unittests;
import primitives.Vector;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VectorTest {

    @org.junit.Test
    public void add() {
        // ============ Equivalence Partitions Tests ==============

        Vector v1 = new Vector(1.0, 1.0, 1.0);
        Vector v2 = new Vector(-1.0, -1.0, -1.5);

        v1 = v1.add(v2);
        assertEquals(new Vector(0.0,0.0,-0.5),v1);

        v2 = v2.add(v1);
        assertEquals(new Vector(-1.0, -1.0, -2.0),v2);

        // =============== Boundary Values Tests ==================

        try {
            new Vector(1,1,1).add(new Vector(-1,-1,-1));
            fail("Didn't throw divide by zero exception!");
        }
        catch (IllegalArgumentException e) { }

    }

    @org.junit.Test
    public void substract() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,1,1);

        assertEquals(new Vector(0,1,2),(v1.substract(v2)));

        // =============== Boundary Values Tests ==================

        try {
            v1.substract(new Vector(1,2,3));
            fail("Didn't throw divide by zero exception!");
        }
        catch (IllegalArgumentException e) { }

    }

    @org.junit.Test
    public void scale() {
        Vector v1 = new Vector(1,2,3);
        // TC01: simple test
        assertTrue(new Vector(3,6,9).equals(v1.scale(3)));
        // TC02: check if the     
        if (!new Vector(1,2,3).equals(v1))
            fail("v1 has changed!");
        try {
            v1.scale(0);
            fail("Didn't throw divide by zero exception!");
        }
        catch (IllegalArgumentException e) { }
    }

    @org.junit.Test
    public void dotProduct() {
        Vector v1 = new Vector(1, 3, 5);
        Vector v2 = new Vector(2, 3, -3);
        double result = v1.dotProduct(v2);
        assertEquals(result, -4, 1e-8);
        assertEquals(0, new Vector(1, 2, 0).dotProduct(new Vector(0, 0, 1)),1e-10);
    }

    @org.junit.Test
    public void crossProduct() {
        Vector v1 = new Vector(3.5, -5.0, 10.0);
        Vector v2 = new Vector(2.5,7,0.5);
        Vector v3 = v1.crossProduct(v2);

        assertEquals( 0, v3.dotProduct(v2), 1e-10);
        assertEquals( 0, v3.dotProduct(v1), 1e-10);

        Vector v4 = v2.crossProduct(v1);

        try {
            v3.add(v4);
            fail("Vector (0,0,0) not valid");
        }
        catch  (IllegalArgumentException e) { }
//        assertTrue(v3.length() >84);
        assertEquals(84,v3.length(),0.659);
    }

    @org.junit.Test
    public void lengthSquared() {
        Vector v = new Vector(2,-4,6);
        assertEquals(56,v.lengthSquared(),0.3);
        assertEquals(v.lengthSquared(),new Vector(-2,4,-6).lengthSquared(),1e-10);
    }

    @org.junit.Test
    public void length() {
        Vector v = new Vector(2,-4,6);
        assertEquals(7.48,v.length(),0.03);
        assertEquals(v.length(),new Vector(-2,4,-6).length(),1e-10);
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
            assertEquals("zero vector", ex.getMessage());
        }
        assertTrue(true);
    }

    @org.junit.Test
    public void normalized() {
        Vector v = new Vector(3.5, -5, 10);
        v.normalized();
        if(v.length() == 1)
            fail("v has changed!");
        assertEquals(1,v.normalized().length(),1e-10);
    }
}