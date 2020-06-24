package unittests;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.*;
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
        double height = 850;
        double width = 3000;
        double length = 3300;
        int tribunsSize = (int)width/10;
        Point3D rightUp = new Point3D(width/2,-length/2,0);
        Point3D rightDown = new Point3D(width/2,length/2,0);
        Point3D leftUp = new Point3D(-width/2,-length/2,0);
        Point3D leftDown = new Point3D(-width/2,length/2,0);
        Material wallsMaterial = new Material(0.5, 0.5, 60,0,0.3);
        Scene scene = new Scene("mini project2");
        scene.setCamera(new Camera(new Point3D(0, 0, -height*1.7), new Vector(0, -1, 1), new Vector(0, -1, -1),2));
        scene.setViewPlaneDistance(50);
        scene.setFocalPlaneDistance(180);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.00));
        Square forwardWall = new Square(new Color(java.awt.Color.WHITE),wallsMaterial,rightUp,leftUp,new Point3D(rightUp.get_x(),rightUp.get_y(),-height));
        Square rightWall = new Square(new Color(java.awt.Color.BLUE),wallsMaterial,rightDown,rightUp,new Point3D(rightDown.get_x(),rightDown.get_y(),-height));
        Square leftWall = new Square(new Color(java.awt.Color.RED),wallsMaterial,leftDown,leftUp,new Point3D(leftDown.get_x(),leftDown.get_y(),-height));
        Square floor = new Square(new Color(java.awt.Color.YELLOW),wallsMaterial,leftDown,leftUp,rightDown);
        Square[] borders = new Square[4];
        Point3D bordUpRight = new Point3D(rightUp.get_x()-tribunsSize-80,rightUp.get_y()+250,-0.1);
        Point3D bordDownRight = new Point3D(rightDown.get_x()-tribunsSize-80,rightDown.get_y()-180,-0.1);
        Point3D bordUpLeft = new Point3D(leftUp.get_x()+tribunsSize+80,leftUp.get_y()+250,-0.1);
        Point3D bordDownLeft = new Point3D(leftDown.get_x()+tribunsSize+80,leftDown.get_y()-180,-0.1);
        borders[0] = new Square(Color.BLACK,wallsMaterial,bordUpRight,
                bordDownRight, new Point3D(bordUpRight.get_x()-50,bordUpRight.get_y(),-0.1));
        borders[1] = new Square(Color.BLACK,wallsMaterial,bordUpLeft,
                bordDownLeft, new Point3D(bordUpLeft.get_x()+50,bordUpLeft.get_y(),-0.1));
        borders[2] = new Square(Color.BLACK,wallsMaterial,bordUpLeft,bordUpRight,
                new Point3D(bordUpLeft.get_x(),bordUpLeft.get_y()-50,-0.1));
        borders[3] = new Square(Color.BLACK,wallsMaterial,bordDownLeft,bordDownRight,
                new Point3D(bordDownLeft.get_x(),bordDownLeft.get_y()+50,-0.1));
        Geometries geoBorders = new Geometries(borders);
        gate(bordUpLeft.get_y(),width,scene);
        rightTribune(rightDown,rightUp,tribunsSize,height, scene);
        leftTribune(leftDown,leftUp,tribunsSize,height, scene);
        Sphere ball = new Sphere(new Color(255,255,255),wallsMaterial,100,new Point3D(0,0,-50));
        scene.addGeometries(forwardWall,rightWall,leftWall,floor,ball);
        scene.addGeometries(borders);
        scene.addLights(new SpotLight(new Color(java.awt.Color.BLUE),new Point3D(0,-length/2+20,-800),
                new Vector(0,1,1),1, 4E-5, 2E-7));
        ImageWriter imageWriter = new ImageWriter("room", 200, 200, 2000, 2000);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();

        render.renderImage(0,1);
        render.writeToImage();
    }

    private void gate(double y, double w, Scene scene) {
        int density = 10;
        int height = 550;
        double XRight = w/8;
        double XLeft = -w/8;
        Material tMaterial = new Material(0.5, 0.5, 60,0,0.3);
        Square[] sheet = new Square[density*2];
        Square[] gate = new Square[7];
        for (int i = 0; i < density; i++)
        {
            double x = XRight - w*i*2/80;
            sheet[i] = new Square(Color.BLACK,tMaterial,new Point3D(x,y-100,-0.1),
                    new Point3D(x - 30,y-100,-0.1),new Point3D(x,y-100,-height));
        }
        for (int i = 0; i < density; i++)
        {
            sheet[i+10] = new Square(Color.BLACK,tMaterial,
                    new Point3D(XRight,y-100,-i*height/(density)),
                    new Point3D(XLeft,y-100,-i*height/(density)),
                    new Point3D(XRight,y-100,-(i*height/(density))-30));
        }
        gate[0] = new Square(Color.BLACK,tMaterial,new Point3D(XRight,y,0),
        new Point3D(XRight-30,y,0),new Point3D(XRight,y,-height));
        gate[1] = new Square(Color.BLACK,tMaterial,new Point3D(XRight,y,-height),
                new Point3D(XRight,y,-height-30),
                new Point3D(XLeft,y,-height));
        gate[2] = new Square(Color.BLACK,tMaterial,new Point3D(XLeft,y,0),
                new Point3D(XLeft+30,y,0),
                new Point3D(XLeft,y,-height));
        gate[3] = new Square(Color.BLACK,tMaterial,new Point3D(XRight,y,-height),
                new Point3D(XRight,y,-height+30),
                new Point3D(XRight,y-100,-height));
        gate[4] = new Square(Color.BLACK,tMaterial,new Point3D(XLeft,y,-height),
                new Point3D(XLeft,y,-height+30),
                new Point3D(XLeft,y-100,-height));
        gate[5] = new Square(Color.BLACK,tMaterial,new Point3D(XRight,y-100,-height),
                new Point3D(XRight,y-100,0),
                new Point3D(XRight-30,y-100,-height));
        gate[6] = new Square(Color.BLACK,tMaterial,new Point3D(XLeft,y-100,-height),
                new Point3D(XLeft,y-100,0),
                new Point3D(XLeft+30,y-100,-height));
        scene.addGeometries(gate);
        scene.addGeometries(sheet);

    }

    private void rightTribune(Point3D rightDown, Point3D rightUp, int size, double height, Scene scene) {

        Color tColor = new Color(java.awt.Color.GREEN);
        Material tMaterial = new Material(0.5, 0.5, 60,0,0.3);
        int someTribuns = size/20;
        Square[] tribuns = new Square[someTribuns];
        Square[] benchs = new Square[someTribuns];
        for(int i = 0; i < someTribuns; i++) {
            try {
                Point3D temp = new Point3D(rightUp.get_x() - i * size/someTribuns, rightUp.get_y(), - height + (i * height/someTribuns));

                tribuns[i] = new Square(tColor, tMaterial, new Point3D(rightUp.get_x(), rightUp.get_y(), - height + (i * height/someTribuns)),//
                        new Point3D(rightDown.get_x(), rightDown.get_y(), - height + (i * height/someTribuns)),//
                        temp);
                benchs[i] = new Square(Color.BLACK,tMaterial,temp,
                        tribuns[i].fourthPoint,
                        new Point3D(rightUp.get_x()- i * size/someTribuns,rightUp.get_y(),- height + ((1+i) * height/someTribuns)));
              //  scene.addGeometries(tribuns[i],benchs[i]);
            }
            catch (IllegalArgumentException e){
                if(e.getMessage() != "zero vector")
                    throw new IllegalArgumentException();
            }
        }
        scene.addGeometries(benchs);
        scene.addGeometries(tribuns);
    }
    private void leftTribune(Point3D leftDown, Point3D leftUp, int size, double height, Scene scene) {

        Color tColor = new Color(java.awt.Color.GREEN);
        Material tMaterial = new Material(0.5, 0.5, 60,0,0.3);
        int someTribuns = size/20;
        Square[] tribuns = new Square[someTribuns];
        Square[] benchs = new Square[someTribuns];

        for(int i = 0; i < someTribuns; i++) {
            try {
                Point3D temp = new Point3D(leftUp.get_x() + i * size/someTribuns, leftUp.get_y(), - height + (i * height/someTribuns));

                tribuns[i] = new Square(tColor, tMaterial, new Point3D(leftUp.get_x(), leftUp.get_y(), - height + (i * height/someTribuns)),//
                        new Point3D(leftDown.get_x(), leftDown.get_y(), - height + (i * height/someTribuns)),//
                        temp);
                benchs[i] = new Square(Color.BLACK, tMaterial, new Point3D(leftDown.get_x() + i * size/someTribuns, leftDown.get_y(), - height + (i * height/someTribuns)),//
                        new Point3D(leftDown.get_x() + i * size/someTribuns, leftDown.get_y(), - height + ((1+i) * height/( someTribuns))),//
                        temp);
             //   scene.addGeometries(tribuns[i],benchs[i]);
            }
            catch (IllegalArgumentException e){
                if(e.getMessage() != "zero vector")
                    throw new IllegalArgumentException();
            }
        }
        scene.addGeometries(benchs);
        scene.addGeometries(tribuns);
    }
}
