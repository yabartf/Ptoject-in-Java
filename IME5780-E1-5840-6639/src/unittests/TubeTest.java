package unittests;

import org.junit.Test;
import primitives.*;
import geometries.*;

import static org.junit.Assert.*;

public class TubeTest {
    @Test
    public void testConstractor() {
        try {
            new Tube(new Ray(new Vector(3, 7,5), new Point3D(2, 4, 5)), 4);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct tube");
        }
        try {
            new Tube(new Ray(new Vector(3, 7, 5), new Point3D(2, 4, 5)), -4);
            fail("Constructed a tube withe negative radius");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testGetNormal() {
        Tube tube= new Tube(new Ray(new Vector(1, 0, 0), new Point3D(0, 0, 0)), 3);
        Point3D p=new Point3D(4,3,0);
        Vector n=new Vector(0,1,0);
        assertEquals("Bad normal to tube",n,tube.getNormal(new Point3D(3,3,0)));
    }
}