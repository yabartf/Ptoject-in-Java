package unittests;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import java.util.Random;

public class Depth_of_field {
    @Test
    public void trianglesTransparentSphere1() {
        Scene scene = new Scene("Depth of field");
        scene.setCamera(new Camera(new Point3D(0, 0, -2000), new Vector(0, 0, 1), new Vector(0, -1, 0),5));
        scene.setViewPlaneDistance(1000);
        scene.setFocalPlaneDistance(80);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        Point3D upRightCorner = new Point3D(120, -150, 0);
        Point3D upLeftCorner = new Point3D(-120, -150, 0);
        Point3D downRightCorner = new Point3D(150, 150, 0);
        Point3D downLeftCorner = new Point3D(-150, 150, 0);
        double radiusOfBalls = 15;
        Random rand = new Random();
        Sphere[] balls = new Sphere[11];
        for (int i = 0; i < 10; i++){
            balls[i] = new Sphere(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)),new Material(0.5, 0.5, 60),radiusOfBalls,//
                     new Point3D(upRightCorner.get_x()/2 - (i*radiusOfBalls*2+radiusOfBalls),upRightCorner.get_y()/3,-25));
        }
       // scene.addArreyGeometries(balls);
        scene.addGeometries( //
                new Triangle(new Color(java.awt.Color.GREEN), new Material(0.5, 0.5, 60), //
                        upRightCorner, downRightCorner, downLeftCorner), //
                new Triangle(new Color(java.awt.Color.GREEN), new Material(0.5, 0.5, 60), //
                        upRightCorner, upLeftCorner, downLeftCorner), //
                 balls[0],balls[1],balls[2],balls[3]);
        scene.addGeometries(balls[4],balls[5],balls[6],balls[7],balls[8],balls[9]);//balls[10]);
        scene.addLights(/*new SpotLight(new Color(700, 400, 400), //
                new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),*/
                new PointLight(new Color(java.awt.Color.RED),new Point3D(-0,0,-40) ,1,4E-5, 2E-7));


        ImageWriter imageWriter = new ImageWriter("snooker table", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();

        render.renderImage(true,0);
        render.writeToImage();
    }


}
