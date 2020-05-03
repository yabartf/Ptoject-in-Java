package unittests;

import geometries.Plane;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    public void testFindIntersections() {

        // ============ Equivalence Partitions Tests ==============

        Plane plane=new Plane(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(1,1,0));
        List<Point3D> resolt=plane.findIntersections(new Ray(new Vector(1,1,1),new Point3D(0,-1,-2)));
        assertEquals("ray crosses the plane",1,resolt.size());
        assertEquals("ray crosses the plane",new Point3D(2,1,0),resolt.get(0));
        //TC02
        //need to check if not perallel
        resolt=plane.findIntersections(new Ray(new Vector(1,2,0),new Point3D(1,-1,1)));
        assertEquals("ray doesn't crosses the plane",null,resolt);

        // =============== Boundary Values Tests ==================
        // TC03 ray parallel to palne and not in the plane
        resolt=plane.findIntersections(new Ray(new Vector(1,2,0),new Point3D(1,-1,1)));
        assertEquals("ray doesn't crosses the plane",null,resolt);
        // TC04 ray parallel to palne and in the plane
        resolt=plane.findIntersections(new Ray(new Vector(1,2,0),new Point3D(1,-1,0)));
        assertEquals("ray doesn't crosses the plane",null,resolt);
        //TC05 ray is orthogonal to the plane p0 is before the plane
        resolt=plane.findIntersections(new Ray(new Vector(0,0,1),new Point3D(1,2,-2)));
        assertEquals("ray crosses the plane",1,resolt.size());
        assertEquals("ray crosses the plane",new Point3D(1,2,0),resolt.get(0));
        //TC06 ray is orthogonal to the plane p0 is in the plane
        resolt=plane.findIntersections(new Ray(new Vector(1,2,0),new Point3D(0,0,1)));
        assertEquals("ray doesn't crosses the plane",null,resolt);
        //TC07 ray is orthogonal to the plane p0 is after the plane
        resolt=plane.findIntersections(new Ray(new Vector(1,2,2),new Point3D(0,0,1)));
        assertEquals("ray doesn't crosses the plane",null,resolt);
        //TC08 ray is neither orthogonal nor parallel to the plane p0 is in the plane
        resolt=plane.findIntersections(new Ray(new Vector(1,1,1),new Point3D(1,2,0)));
        assertEquals("ray doesn't crosses the plane",null,resolt);
        //TC08 ray is neither orthogonal nor parallel to the plane p0 is the plane point
        resolt=plane.findIntersections(new Ray(new Vector(1,1,1),plane.getPointInPlane()));
        assertEquals("ray doesn't crosses the plane",null,resolt);
    }

    @Test
    public void testGetNormal() {
        Plane pl=new Plane(new Point3D(0,0,0),new Point3D(3,3,0),new Point3D(2,1,0));
        Vector n=new Vector(0,0,-1);
        Vector g=pl.getNormal(new Point3D(1,2,3));
        assertEquals("bad normal to plane",pl.getNormal(new Point3D(3,4,4)),n);
    }
}