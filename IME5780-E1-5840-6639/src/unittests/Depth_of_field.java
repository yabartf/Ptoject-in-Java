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
        Point3D upRightCorner = new Point3D(120, -200, 0);
        Point3D upLeftCorner = new Point3D(-120, -200, 0);
        Point3D downRightCorner = new Point3D(120, 200, 0);
        Point3D downLeftCorner = new Point3D(-120, 200, 0);
        double radiusOfBalls = 15;

        Scene scene = new Scene("Depth of field");
        scene.setCamera(new Camera(new Point3D(0, 200, -45), new Vector(0, -1, 1), new Vector(0, -1, -1),2));
        scene.setViewPlaneDistance(50);
        scene.setFocalPlaneDistance(180);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.00));

        Color triangleColor = new Color(80,25,0);
        Triangle[] borders = new Triangle[8];
        Material triangleMaterial = new Material(0.5, 0.5, 60,0,0.3);

        borders[0] = new Triangle(triangleColor,triangleMaterial , //
                upRightCorner, downRightCorner, new Point3D(downRightCorner.get_x()+5,downRightCorner.get_y(),-50));
        borders[1] = new Triangle(triangleColor,triangleMaterial, //
                upRightCorner,new Point3D(downRightCorner.get_x()+5,downRightCorner.get_y(),-50) , new Point3D(upRightCorner.get_x()+5,upRightCorner.get_y(),-50));
        borders[2] = new Triangle(triangleColor, triangleMaterial, //
                upRightCorner, upLeftCorner, new Point3D(upRightCorner.get_x()+5,upRightCorner.get_y(),-50));
        borders[3] = new Triangle(triangleColor, triangleMaterial, //
                upLeftCorner,new Point3D(upLeftCorner.get_x()-5,upLeftCorner.get_y(),-50) , new Point3D(upRightCorner.get_x()-5,upRightCorner.get_y(),-50));
        borders[4] = new Triangle(triangleColor, triangleMaterial, //
                upLeftCorner, downLeftCorner, new Point3D(upLeftCorner.get_x()-5,upLeftCorner.get_y(),-50));
        borders[5] = new Triangle(triangleColor, triangleMaterial, //
                downLeftCorner,new Point3D(upLeftCorner.get_x()-5,upLeftCorner.get_y(),-50) , new Point3D(downLeftCorner.get_x()-5,downLeftCorner.get_y(),-50));

        scene.addGeometries(borders[0],borders[1],borders[2],borders[3],borders[4],borders[5]);

        addBalls(upRightCorner,downRightCorner,upLeftCorner,downLeftCorner,radiusOfBalls,scene);

        scene.addGeometries( //
                new Triangle(new Color(0,70,0), new Material(0.5, 0.5, 60,0.1,0), //
                        upRightCorner, downRightCorner, downLeftCorner), //
                new Triangle(new Color(0,70,0), new Material(0.5, 0.5, 60,0.1,0), //
                        upRightCorner, upLeftCorner, downLeftCorner));

        scene.addLights(new PointLight(new Color(java.awt.Color.WHITE),new Point3D(-0,0,10) ,1,4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("snooker table", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();

        render.renderImage(1,1);
        render.writeToImage();
    }
public void addBalls(Point3D upRight, Point3D downRight, Point3D upLeft, Point3D downLeft, double radiusOfBalls, Scene scene){
    Vector moveLight = new Vector(0,2,-1);
    Color lightColor = new Color(java.awt.Color.WHITE);
    Material sphereMaterial = new Material(0.5, 0.5, 60,0.1,0);

        Sphere[] balls = new Sphere[11];
        for (int i = 0; i < 4; i++){
            Point3D temp = new Point3D(upRight.get_x()/2 - (i*radiusOfBalls*2+radiusOfBalls),upRight.get_y()/2,-radiusOfBalls);
            balls[i] = new Sphere(new Color(java.awt.Color.BLUE),sphereMaterial,radiusOfBalls,//
                    temp);

        }
        for (int i = 4 , j =0; i < 7; i++, j++){
            Point3D temp = new Point3D(upRight.get_x()/2 - radiusOfBalls - (j*radiusOfBalls*2+radiusOfBalls),upRight.get_y()/2+radiusOfBalls*2,-radiusOfBalls);
            balls[i] = new Sphere(new Color(java.awt.Color.GREEN),sphereMaterial,radiusOfBalls,//
                    temp);

        }
        for (int i = 7, j = 0; i < 9; i++, j++){
            Point3D temp =new Point3D(upRight.get_x()/2 - radiusOfBalls*2 - (j*radiusOfBalls*2+radiusOfBalls),upRight.get_y()/2+radiusOfBalls*4,-radiusOfBalls);
            balls[i] = new Sphere(new Color(java.awt.Color.RED),sphereMaterial,radiusOfBalls,//
                    temp);

        }
        for (int i = 9, j = 0; i < 10; i++, j++){
            Point3D temp = new Point3D(upRight.get_x()/2 - radiusOfBalls*3 - (j*radiusOfBalls*2+radiusOfBalls),upRight.get_y()/2+radiusOfBalls*6,-radiusOfBalls);
            balls[i] = new Sphere(new Color(java.awt.Color.MAGENTA),sphereMaterial,radiusOfBalls,//
                    temp);
        }
        Point3D temp = new Point3D(0,downLeft.get_y()*4/5,-radiusOfBalls);
        balls[10] = new Sphere(new Color(java.awt.Color.WHITE),sphereMaterial,radiusOfBalls,//
                    temp);
        SpotLight spot = new SpotLight(lightColor,new Point3D(0,downLeft.get_y()*4/5+radiusOfBalls/2,-radiusOfBalls+1),moveLight.scale(-1),1, 4E-5, 2E-7);
        SpotLight spot1 = new SpotLight(lightColor,new Point3D(0,195,-15),new Vector(0,-1,0.31),1, 4E-5, 2E-7);
        //SpotLight spot2 = new SpotLight(lightColor,new Point3D(0,130,-15),new Vector(0,1,0.31),1, 4E-5, 2E-7);
        PointLight point = new PointLight(lightColor,new Point3D(0,0,100),1, 4E-5, 2E-7);
        scene.addLights(spot,spot1,point);
        for (int i = 0; i < 11; i++){
            scene.addGeometries(balls[i]);

        }

}

}
