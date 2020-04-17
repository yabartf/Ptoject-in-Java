package unittests;

import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        Point3D p=new Point3D(0.5,0.5,0);
        Vector vector=new Vector(1,1,1);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray is inside triangle
        List<Point3D> result=triangleTest.findIntsersections(new Ray(vector,new Point3D(-0.5,-0.5,-1)));
        assertEquals("Ray crosses triangle",p,result.get(0));
        //TC02 נקודה בחוץ בזווית בין הצלעות
        result=triangleTest.findIntsersections(new Ray(vector,new Point3D(-5,-2,-1)));
        assertEquals("ray doesn't crosses the triangle",null,result);
        //TC03 נקודה בחוץ ליד הצלע\
        result=triangleTest.findIntsersections(new Ray(vector,new Point3D(-3,0,-1)));
        assertEquals("ray doesn't crosses the triangle",null,result);
        // =============== Boundary Values Tests ==================
        //TC04 Ray begins inside the triangle
        result=triangleTest.findIntsersections(new Ray(vector,new Point3D(0.5,0.5,0)));
        assertEquals("ray doesn't crosses the triangle",null,result);
        //TC05 קרן מתחילה בחוץ בין הצלעות
        result=triangleTest.findIntsersections(new Ray(vector,new Point3D(0.5,0.5,0)));
        assertEquals("ray doesn't crosses the triangle",null,result);
        //TC06 קרן מתחילה בחוץ ליד הצלע
        result=triangleTest.findIntsersections(new Ray(vector,new Point3D(-2,1,0)));
        assertEquals("ray doesn't crosses the triangle",null,result);
        //TC07 Ray line is on the side of the triangle
        result=triangleTest.findIntsersections(new Ray(vector,new Point3D(-2,0,-1)));
        assertEquals("ray doesn't crosses the triangle",null,result);
        //TC08 Ray line is on a vertex of the triangle
        result=triangleTest.findIntsersections(new Ray(vector,new Point3D(-3,-1,-1)));
        assertEquals("ray doesn't crosses the triangle",null,result);
        //TC09 Ray line is on the continuance of the triangle's side
        result=triangleTest.findIntsersections(new Ray(vector,new Point3D(-4,-2,-1)));
        assertEquals("ray doesn't crosses the triangle",null,result);
        //TC10 Ray starts on the side of the triangle
        result=triangleTest.findIntsersections(new Ray(vector,new Point3D(-1,1,0)));
        assertEquals("ray doesn't crosses the triangle",null,result);
        //TC11 Ray starts on a vertex of the triangle
        result=triangleTest.findIntsersections(new Ray(vector,new Point3D(-2,0,0)));
        assertEquals("ray doesn't crosses the triangle",null,result);
        //TC12 Ray starts on the continuance of the triangle's side
        result=triangleTest.findIntsersections(new Ray(vector,new Point3D(-3,-1,0)));
        assertEquals("ray doesn't crosses the triangle",null,result);
    }

}