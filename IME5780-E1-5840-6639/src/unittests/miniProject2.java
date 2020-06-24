package unittests;

import elements.AmbientLight;
import elements.Camera;
import geometries.Cylinder;
import geometries.Square;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class miniProject2 {
    @Test
    public void room(){
        double height = 450;
        double width = 2000;
        double length = 2300;
        Point3D rightUp = new Point3D(width/2,-length/2,0);
        Point3D rightDown = new Point3D(width/2,length/2,0);
        Point3D leftUp = new Point3D(-width/2,-length/2,0);
        Point3D leftDown = new Point3D(-width/2,length/2,0);
        Material wallsMaterial = new Material(0.5, 0.5, 60,0,0.3);
        Scene scene = new Scene("mini project2");
        scene.setCamera(new Camera(new Point3D(0, length/2, -height*2), new Vector(0, -1, 1), new Vector(0, -1, -1),2));
        scene.setViewPlaneDistance(50);
        scene.setFocalPlaneDistance(180);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.00));
        Square forwardWall = new Square(new Color(java.awt.Color.WHITE),wallsMaterial,rightUp,leftUp,new Point3D(rightUp.get_x(),rightUp.get_y(),-height));
        Square rightWall = new Square(new Color(java.awt.Color.BLUE),wallsMaterial,rightDown,rightUp,new Point3D(rightDown.get_x(),rightDown.get_y(),-height));
        Square leftWall = new Square(new Color(java.awt.Color.RED),wallsMaterial,leftDown,leftUp,new Point3D(leftDown.get_x(),leftDown.get_y(),-height));
        Square floor = new Square(new Color(java.awt.Color.YELLOW),wallsMaterial,leftDown,leftUp,rightDown);
        rightTribune(rightDown,rightUp,300,height, scene);
        leftTribune(leftDown,leftUp,300,height, scene);
        scene.addGeometries(forwardWall,rightWall,leftWall,floor);
        ImageWriter imageWriter = new ImageWriter("room", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();

        render.renderImage(0,1);
        render.writeToImage();
    }

    private void rightTribune(Point3D rightDown, Point3D rightUp, int size, double height, Scene scene) {

        Color tColor = new Color(java.awt.Color.GREEN);
        Material tMaterial = new Material(0.5, 0.5, 60,0,0.3);
        int someTribuns = size/20;
        Square[] tribuns = new Square[someTribuns];

        for(int i = 0; i < someTribuns; i++) {
            try {
                tribuns[i] = new Square(tColor, tMaterial, new Point3D(rightUp.get_x(), rightUp.get_y(), - height + (i * height/someTribuns)),//
                        new Point3D(rightDown.get_x(), rightDown.get_y(), - height + (i * height/someTribuns)),//
                        new Point3D(rightUp.get_x() - i * size/someTribuns, rightUp.get_y(), - height + (i * height/someTribuns)));
                scene.addGeometries(tribuns[i]);
            }
            catch (IllegalArgumentException e){
                if(e.getMessage() != "zero vector")
                    throw new IllegalArgumentException();
            }
        }
    }
    private void leftTribune(Point3D leftDown, Point3D leftUp, int size, double height, Scene scene) {

        Color tColor = new Color(java.awt.Color.GREEN);
        Material tMaterial = new Material(0.5, 0.5, 60,0,0.3);
        int someTribuns = size/20;
        Square[] tribuns = new Square[someTribuns];

        for(int i = 0; i < someTribuns; i++) {
            try {
                tribuns[i] = new Square(tColor, tMaterial, new Point3D(leftUp.get_x(), leftUp.get_y(), - height + (i * height/someTribuns)),//
                        new Point3D(leftDown.get_x(), leftDown.get_y(), - height + (i * height/someTribuns)),//
                        new Point3D(leftUp.get_x() + i * size/someTribuns, leftUp.get_y(), - height + (i * height/someTribuns)));
                scene.addGeometries(tribuns[i]);
            }
            catch (IllegalArgumentException e){
                if(e.getMessage() != "zero vector")
                    throw new IllegalArgumentException();
            }
        }
    }
}
