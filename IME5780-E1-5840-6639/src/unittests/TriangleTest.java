package unittests;

import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TriangleTest {

    @Test
    public void testConstractor(){
        try {
            new Triangle(new Point3D(0,0,0),new Point3D(0,2,4),new Point3D(7,7,7));
        }
        catch (IllegalArgumentException e){
            fail("Failed constructing a correct triangle");
        }
    }
    @Test
    public void getNormal() {
        Triangle tr=new Triangle(new Point3D(0,0,0),new Point3D(0,1,2),new Point3D(6,5,7));
        double sqrt=Math.sqrt(21);
        double cons=sqrt/21;
        Vector n=new Vector(-cons,4*cons,-2*cons);
        assertEquals("bad normal to triangle",tr.getNormal(new Point3D(3,4,4)),n);
    }

    @Test
    public void findIntsersections() {
        Triangle triangleTest = new Triangle(new Point3D(2,0,0),new Point3D(0,2,0),new Point3D(-2,0,0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray is inside triangle
        Ray rayTest = new Ray(new Vector(0.6,0,3), new Point3D(0.6,0,-0.5));
        assertEquals(true,triangleTest.findIntsersections(rayTest));
        // =============== Boundary Values Tests ==================
    }

}