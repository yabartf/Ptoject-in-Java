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
    public void snookerTable() {
        Point3D upRightCorner = new Point3D(120, -250, 0);
        Point3D upLeftCorner = new Point3D(-120, -250, 0);
        Point3D downRightCorner = new Point3D(120, 250, 0);
        Point3D downLeftCorner = new Point3D(-120, 250, 0);
        double radiusOfBalls = 15;

        Scene scene = new Scene("Depth of field");
        scene.setCamera(new Camera(new Point3D(0, 200, -2100), new Vector(0, 0, 1), new Vector(0, -1, 0),5));
        scene.setViewPlaneDistance(1000);
        scene.setFocalPlaneDistance(80);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        Triangle[] borders = new Triangle[8];
        borders[0] = new Triangle(new Color(0,112,112), new Material(0.5, 0.5, 60), //
                upRightCorner, downRightCorner, new Point3D(downRightCorner.get_x(),downRightCorner.get_y(),50));
        borders[1] = new Triangle(new Color(0,112,112), new Material(0.5, 0.5, 60), //
                upRightCorner,new Point3D(downRightCorner.get_x(),downRightCorner.get_y(),100) , new Point3D(upRightCorner.get_x(),upRightCorner.get_y(),50));
        borders[2] = new Triangle(new Color(0,112,112), new Material(0.5, 0.5, 60), //
                upRightCorner, upLeftCorner, new Point3D(downRightCorner.get_x(),upRightCorner.get_y(),50));
        borders[3] = new Triangle(new Color(0,112,112), new Material(0.5, 0.5, 60), //
                upLeftCorner,new Point3D(upLeftCorner.get_x(),upLeftCorner.get_y(),100) , new Point3D(downRightCorner.get_x(),upRightCorner.get_y(),50));
        borders[4] = new Triangle(new Color(0,112,112), new Material(0.5, 0.5, 60), //
                upLeftCorner, downLeftCorner, new Point3D(upLeftCorner.get_x(),upLeftCorner.get_y(),50));
        borders[5] = new Triangle(new Color(0,112,112), new Material(0.5, 0.5, 60), //
                downLeftCorner,new Point3D(upLeftCorner.get_x(),upLeftCorner.get_y(),100) , new Point3D(downLeftCorner.get_x(),downLeftCorner.get_y(),50));

        scene.addGeometries(borders[0],borders[1],borders[2],borders[3],borders[4],borders[5]);

        addBalls(upRightCorner,downRightCorner,upLeftCorner,downLeftCorner,radiusOfBalls,scene);

        scene.addGeometries( //
                new Triangle(new Color(0,112,0), new Material(0.5, 0.5, 60), //
                        upRightCorner, downRightCorner, downLeftCorner), //
                new Triangle(new Color(0,112,0), new Material(0.5, 0.5, 60), //
                        upRightCorner, upLeftCorner, downLeftCorner));

        scene.addLights(new PointLight(new Color(java.awt.Color.RED),new Point3D(-0,0,-40) ,1,4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("snooker table", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();

        render.renderImage(true,0);
        render.writeToImage();
    }
public void addBalls(Point3D upRight, Point3D downRight, Point3D upLeft, Point3D downLeft, double radiusOfBalls, Scene scene){
        Random rand = new Random();
        Sphere[] balls = new Sphere[11];

        for (int i = 0; i < 4; i++){
            balls[i] = new Sphere(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)),new Material(0.5, 0.5, 60,0,0.6),radiusOfBalls,//
                    new Point3D(upRight.get_x()/2 - (i*radiusOfBalls*2+radiusOfBalls),upRight.get_y()/2,-25));
        }
        for (int i = 4 , j =0; i < 7; i++, j++){
            balls[i] = new Sphere(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)),new Material(0.5, 0.5, 60,0,0.6),radiusOfBalls,//
                    new Point3D(upRight.get_x()/2 - radiusOfBalls - (j*radiusOfBalls*2+radiusOfBalls),upRight.get_y()/2+radiusOfBalls*2,-25));
        }
        for (int i = 7, j = 0; i < 9; i++, j++){
            balls[i] = new Sphere(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)),new Material(0.5, 0.5, 60,0,0.6),radiusOfBalls,//
                    new Point3D(upRight.get_x()/2 - radiusOfBalls*2 - (j*radiusOfBalls*2+radiusOfBalls),upRight.get_y()/2+radiusOfBalls*4,-25));
        }
        for (int i = 9, j = 0; i < 10; i++, j++){
            balls[i] = new Sphere(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)),new Material(0.5, 0.5, 600,0,0.6),radiusOfBalls,//
                    new Point3D(upRight.get_x()/2 - radiusOfBalls*3 - (j*radiusOfBalls*2+radiusOfBalls),upRight.get_y()/2+radiusOfBalls*6,-25));
        }
        balls[10] = new Sphere(new Color(java.awt.Color.WHITE),new Material(0.5, 0.5, 60,0,0.6),radiusOfBalls,//
                    new Point3D(0,downLeft.get_y()*4/5,-25));
        for (int i = 0; i < 11; i++){
            scene.addGeometries(balls[i]);
        }

    }

}
