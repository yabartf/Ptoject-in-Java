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
        Sphere sphere = new Sphere(new Point3D(0, 0, 3), 1);
        assertEquals("2 intersection", 2, numOfIntersections(sphere,cam));

        cam = new Camera(new Point3D(0, 0, -0.5), new Vector(0, -1, 0), new Vector(0, 0, 1));

        sphere = new Sphere(new Point3D(0, 0, 2.5), 2.5);
        assertEquals("18 intersections",18,numOfIntersections(sphere,cam));

        sphere=new Sphere(new Point3D(0,0,2),2);
        assertEquals("10 intersections",10,numOfIntersections(sphere,cam));

        sphere=new Sphere(new Point3D(0,0,2),4);
        assertEquals("9 intersections",9,numOfIntersections(sphere,cam));

        sphere=new Sphere(new Point3D(0,0,-1),0.5);
        assertEquals("0 intersectoins",0,numOfIntersections(sphere,cam));
    }

    public void cameraRaysPlaneIntersections(){
        Camera cam=new Camera(new Point3D(0,0,-0.5),new Vector(0, -1, 0), new Vector(0, 0, 1));

        Plane plane=new Plane(new Point3D(1,3,3),new Point3D(5,2,3),new Point3D(7,1,3));
        assertEquals("9 intersections",9,numOfIntersections(plane,cam));

        plane=new Plane(new Point3D(1,3,3),new Point3D(5,2,4),new Point3D(7,1,4));
        assertEquals("9 intersections",9,numOfIntersections(plane,cam));

        plane=new Plane(new Point3D(1,3,3),new Point3D(5,2,10),new Point3D(7,1,10));
        assertEquals("6 intersections",6,numOfIntersections(plane,cam));
    }

    public void cameraRaysTriangleIntersections(){
        Camera cam=new Camera(new Point3D(0,0,-0.5),new Vector(0, -1, 0), new Vector(0, 0, 1));

        Triangle triangle=new Triangle(new Point3D(0,-1,2),new Point3D(1,1,2),new Point3D(-1,1,2));
        assertEquals("1 intersections",1,numOfIntersections(triangle,cam));

        triangle=new Triangle(new Point3D(0,-20,2),new Point3D(1,1,2),new Point3D(-1,1,2));
        assertEquals("2 intersections",2,numOfIntersections(triangle,cam));

    }
    int numOfIntersections(Intersectable shape,Camera cam){
        int numOfIntersections = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = cam.constructRayThroughPixel(3, 3, i, j, 1, 3, 3);
                List intersection=shape.findIntsersections(ray);
                numOfIntersections+=intersection!= null? intersection.size() :0;
            }
        }
        return numOfIntersections;
    }
}