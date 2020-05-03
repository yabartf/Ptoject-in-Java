package unittests;
import static org.junit.Assert.*;
import elements.Camera;
import geometries.*;
import org.junit.Test;
import primitives.*;

import java.util.List;

public class cameraRaysShapeIntersections {
    @Test
    public void cameraRaysSphereIntersections() {
        Camera cam = new Camera(new Point3D(0, 0, 0), new Vector(0, -1, 0), new Vector(0, 0, 1));
        //TC01: 1 camera ray crosses the sphere(2 intersection)
        Sphere sphere = new Sphere(new Point3D(0, 0, 3), 1);
        assertEquals("2 intersection", 2, numOfIntersections(sphere,cam));

        cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, -1, 0), new Vector(0, 0, 1));
        //TC02: the camera is outside the sphere and all of the ray crossing the sphere
        sphere = new Sphere(new Point3D(0, 0, 2.5), 2.5);
        assertEquals("18 intersections",18,numOfIntersections(sphere,cam));
        //TC03: all the ray beside the corners crossing the sphere
        sphere=new Sphere(new Point3D(0,0,2),2);
        assertEquals("10 intersections",10,numOfIntersections(sphere,cam));
        //TC04: the camera is inside the sphere(all rays intersect once with the sphere
        sphere=new Sphere(new Point3D(0,0,2),4);
        assertEquals("9 intersections",9,numOfIntersections(sphere,cam));
        //TC05: no intersection of the camera rays with the sphere
        sphere=new Sphere(new Point3D(0,0,-1),0.5);
        assertEquals("0 intersectoins",0,numOfIntersections(sphere,cam));
    }

    public void cameraRaysPlaneIntersections(){
        Camera cam=new Camera(new Point3D(0,0,-0.5),new Vector(0, -1, 0), new Vector(0, 0, 1));
        //TC01: the plane is ortogonal to the towards vertor of the camera(9 intersection)
        Plane plane=new Plane(new Point3D(1,3,3),new Point3D(5,2,3),new Point3D(7,1,3));
        assertEquals("9 intersections",9,numOfIntersections(plane,cam));
        //TC02: the plane have a little shift (still 9 intersection)
        plane=new Plane(new Point3D(1,3,3),new Point3D(5,2,4),new Point3D(7,1,4));
        assertEquals("9 intersections",9,numOfIntersections(plane,cam));
        //TC03: the place have a bif shipt the 3 lower rays dousnt intersect with the plane(6 intersection)
        plane=new Plane(new Point3D(1,3,3),new Point3D(5,2,10),new Point3D(7,1,10));
        assertEquals("6 intersections",6,numOfIntersections(plane,cam));
    }

    public void cameraRaysTriangleIntersections(){
        Camera cam=new Camera(new Point3D(0,0,-0.5),new Vector(0, -1, 0), new Vector(0, 0, 1));
        //TC01: only the ray that go through the center intersect with the triangle
        Triangle triangle=new Triangle(new Point3D(0,-1,2),new Point3D(1,1,2),new Point3D(-1,1,2));
        assertEquals("1 intersections",1,numOfIntersections(triangle,cam));
        //TC02: the ray that go through the center and the one above her intersect with the triangle
        triangle=new Triangle(new Point3D(0,-20,2),new Point3D(1,1,2),new Point3D(-1,1,2));
        assertEquals("2 intersections",2,numOfIntersections(triangle,cam));

    }

    /***
     * help function that returns the numbers of intersection of camera rays and the shape
     *the help function is for 3x3 screen
     * @param shape the shape that we check if there are intersection
     * @param cam the cmera
     * @return the number of intersections between all the camera ray and the shape
     */
    int numOfIntersections(Intersectable shape,Camera cam){
        int numOfIntersections = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = cam.constructRayThroughPixel(3, 3, i, j, 1, 3, 3);
                List intersection=shape.findIntersections(ray);
                numOfIntersections+=intersection!= null? intersection.size() :0;
            }
        }
        return numOfIntersections;
    }
}