package unittests;

import geometries.*;
import org.junit.Test;
import primitives.*;

import static org.junit.Assert.*;

public class PlaneTest {

    @Test
    public void testConstractor(){
        try {
            new Plane(new Point3D(0, 0, 0), new Point3D(11, 8, 2), new Point3D(1, 3, 9));
        }
        catch (IllegalArgumentException e)
        {
            fail("Failed constructing a correct plane");
        }
    }
    @Test
    public void testGetNormal() {
        Plane pl=new Plane(new Point3D(0,0,0),new Point3D(3,3,0),new Point3D(2,1,0));
        Vector n=new Vector(0,0,-1);
        Vector g=pl.getNormal(new Point3D(1,2,3));
        assertEquals("bad normal to plane",pl.getNormal(new Point3D(3,4,4)),n);
    }
}