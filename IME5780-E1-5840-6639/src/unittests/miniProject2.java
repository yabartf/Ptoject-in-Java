package unittests;

import elements.*;
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
        double width = 1500;
        double length = 1200;
        int tribunsSize = (int)width/10;
        Point3D rightUp = new Point3D(width/2,-length/2,0);
        Point3D rightDown = new Point3D(width/2,length/2,0);
        Point3D leftUp = new Point3D(-width/2,-length/2,0);
        Point3D leftDown = new Point3D(-width/2,length/2,0);
        Material wallsMaterial = new Material(0.5, 0.5, 60,0,0.3);
        Scene scene = new Scene("mini project2");
        scene.setCamera(new Camera(new Point3D(0, length/2, -height*0.8), new Vector(0, -1, 0.4), new Vector(0, -1, -2.5),100));
        scene.setViewPlaneDistance(50);
        scene.setFocalPlaneDistance(length/2+100);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Point3D p3 = new Point3D(rightUp.get_x(),rightUp.get_y(),-height);
        Square forwardWall = new Square(new Color(java.awt.Color.WHITE),new Material(0.1, 0.5, 60,0.8,0),rightUp,leftUp,getForthPoint(rightUp,leftUp,p3),p3);
        p3 = new Point3D(rightDown.get_x(),rightDown.get_y(),-height);
        Square rightWall = new Square(new Color(java.awt.Color.BLUE),wallsMaterial,rightDown,rightUp,getForthPoint(rightDown, rightUp, p3),p3);
        Point3D p1 = new Point3D(leftDown.get_x(),leftDown.get_y(),-height);
        Point3D p2 = new Point3D(leftUp.get_x(),leftUp.get_y(),-height);
        p3 = new Point3D( rightDown.get_x(), rightDown.get_y(),-height);
        Square roof = new Square(new Color(java.awt.Color.DARK_GRAY),wallsMaterial,p1,p2,getForthPoint(p1,p2,p3),p3);
        p3 = new Point3D(leftDown.get_x(),leftDown.get_y(),-height);
        Square leftWall = new Square(new Color(java.awt.Color.RED),wallsMaterial,leftDown,leftUp, getForthPoint(leftDown, leftUp, p3), p3);
        Square floor = new Square(new Color(java.awt.Color.DARK_GRAY),new Material(0.5, 0.5, 60,0,0.0),
                leftDown,leftUp, getForthPoint(leftDown, leftUp, rightDown),rightDown);
        Square[] borders = new Square[4];
        Point3D bordUpRight = new Point3D(rightUp.get_x()-tribunsSize-80,rightUp.get_y()+250,-0.1);
        Point3D bordDownRight = new Point3D(rightDown.get_x()-tribunsSize-80,rightDown.get_y()-180,-0.1);
        Point3D bordUpLeft = new Point3D(leftUp.get_x()+tribunsSize+80,leftUp.get_y()+250,-0.1);
        Point3D bordDownLeft = new Point3D(leftDown.get_x()+tribunsSize+80,leftDown.get_y()-180,-0.1);
        p3 = new Point3D(bordUpRight.get_x()-50,bordUpRight.get_y(),-0.1);
        borders[0] = new Square(Color.BLACK,wallsMaterial,bordUpRight, bordDownRight,getForthPoint(bordUpRight, bordDownRight ,p3), p3);
        p3 = new Point3D(bordUpLeft.get_x()+50,bordUpLeft.get_y(),-0.1);
        borders[1] = new Square(Color.BLACK,wallsMaterial,bordUpLeft, bordDownLeft,getForthPoint(bordUpLeft, bordDownLeft, p3), p3);
        p3 = new Point3D(bordUpLeft.get_x(),bordUpLeft.get_y()-50,-0.1);
        borders[2] = new Square(Color.BLACK,wallsMaterial,bordUpLeft,bordUpRight,getForthPoint(bordUpLeft, bordUpRight, p3),p3);
        p3 =   new Point3D(bordDownLeft.get_x(),bordDownLeft.get_y()+50,-0.1);
        borders[3] = new Square(Color.BLACK,wallsMaterial,bordDownLeft,bordDownRight,getForthPoint(bordDownLeft, bordDownRight ,p3), p3);
        projectors(height,length,width,scene,new Point3D(0,0,-100));
        Geometries geoBorders = new Geometries(borders);
        gate(bordUpLeft.get_y(),width,scene,height);
        rightTribune(rightDown,rightUp,tribunsSize,height, scene);
        leftTribune(leftDown,leftUp,tribunsSize,height, scene);
        Sphere ball = new Sphere(new Color(76,153,0),new Material(0.2, 0.2, 30, 0.6, 0),
                100,new Point3D(0,0,-100));

        scene.addGeometries(forwardWall,leftWall, rightWall, floor, roof, ball);
        scene.addGeometries(geoBorders);
        scene.addLights(new SpotLight(new Color(80000,80000,80000),new Point3D(0,-length/2+1,-2*height/3),
                        new Vector(0,1,1),1, 4E-1, 2E-3,10),
                 //new DirectionalLight(new Color(150,150,0),new Vector(0,1,0.3)),
                new SpotLight(new Color(80000,80000,80000),new Point3D(0,-230,-280),new Vector(0,1,1)
                        ,1, 4E-1, 2E-3,10));

        ImageWriter imageWriter = new ImageWriter("room", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();

        render.renderImage(0,20);
        render.writeToImage();
    }

    private void projectors(double height, double length, double width, Scene scene,Point3D ballPoint) {
        int some = 16;
        SpotLight[] projectors = new SpotLight[some];
        Sphere[] lamps = new Sphere[some];
        Material lampMaterial = new Material(0.02, 0.2, 30, 0.9, 0);
        for (int i = 0, j = 1; i < some/2; i++, j *= -1)
        {
            Point3D rightPoint = new Point3D(width/2,j*i*length/some,height/20-height);
            Point3D leftPoint = new Point3D(-width/2,j*i*length/some,height/20-height);

            lamps[i] = new Sphere(new Color(32,32,32),lampMaterial,height/20,rightPoint);
            lamps[some/2+i] = new Sphere(new Color(32,32,32),lampMaterial,height/20,leftPoint);

            Point3D lightRight = new Point3D(rightPoint.get_x()-1,rightPoint.get_y(),rightPoint.get_z());
            Point3D lightLeft = new Point3D(leftPoint.get_x()+1,leftPoint.get_y(),leftPoint.get_z());
            Vector rightToBall = ballPoint.subtract(lightRight);
            Vector leftToBall = ballPoint.subtract(lightLeft);

            projectors[i] =  new SpotLight(new Color(80000,80000,0),lightRight,
                    rightToBall,1, 2E-1, 1E-1);
            projectors[some/2+i] =  new SpotLight(new Color(80000,80000,0),lightLeft,
                    leftToBall,1, 2E-1, 1E-1);
        }
        scene.addLights(projectors);
        scene.addGeometries(lamps);
    }

    private void gate(double y, double w, Scene scene, double hei) {
        int density = 10;
        double height = hei/2;
        double XRight = w/8;
        double XLeft = -w/8;
        double width = w/100;
        Material tMaterial = new Material(0.9, 0.5, 60,0,0);
        Square[] sheet = new Square[density*2+1];
        Square[] gate = new Square[7];
        for (int i = 0; i < density; i++)
        {
            double x = XRight - w*i*2/80;
            Point3D p1 = new Point3D(x,y-100,-0.1);
            Point3D p2 = new Point3D(x - width,y-100,-0.1);
            Point3D p3 = new Point3D(x,y-100,-height);
            sheet[i] = new Square(Color.BLACK,tMaterial,p1,p2,getForthPoint(p1,p2,p3),p3);
        }
        for (int i = 0; i < density+1; i++)
        {
            Point3D p1 = new Point3D(XRight,y-100,-i*height/(density));
            Point3D p2 = new Point3D(XLeft,y-100,-i*height/(density));
            Point3D p3 = new Point3D(XRight,y-100,-(i*height/(density))-width);
            sheet[i+density] = new Square(Color.BLACK,tMaterial,p1, p2, getForthPoint(p1,p2,p3),p3);
        }

        Point3D p1 =new Point3D(XRight,y,0);
        Point3D p2 = new Point3D(XRight-width,y,0);
        Point3D p3 = new Point3D(XRight,y,-height);
        gate[0] = new Square(Color.BLACK,tMaterial,p1,p2,getForthPoint(p1,p2,p3),p3);
        p1 = new Point3D(XRight,y,-height);
        p2 = new Point3D(XRight,y,-height-width);
        p3 = new Point3D(XLeft,y,-height);
        gate[1] = new Square(Color.BLACK,tMaterial,p1, p2, getForthPoint(p1,p2,p3), p3);
        p1 = new Point3D(XLeft,y,0);
        p2 = new Point3D(XLeft+width,y,0);
        p3 = new Point3D(XLeft,y,-height);
        gate[2] = new Square(Color.BLACK,tMaterial,p1, p2, getForthPoint(p1,p2,p3),p3);
        p1 = new Point3D(XRight,y,-height);
        p2 = new Point3D(XRight,y,-height+width);
        p3 = new Point3D(XRight,y-100,-height);
        gate[3] = new Square(Color.BLACK,tMaterial, p1, p2, getForthPoint(p1,p2,p3),p3);
        p1 = new Point3D(XLeft,y,-height);
        p2 = new Point3D(XLeft,y,-height+width);
        p3 = new Point3D(XLeft,y-100,-height);
        gate[4] = new Square(Color.BLACK,tMaterial, p1, p2, getForthPoint(p1,p2,p3),p3);
        p1 = new Point3D(XRight,y-100,-height);
        p2 = new Point3D(XRight,y-100,0);
        p3 = new Point3D(XRight-width,y-100,-height);
        gate[5] = new Square(Color.BLACK,tMaterial, p1, p2, getForthPoint(p1,p2,p3),p3);
        p1 = new Point3D(XLeft,y-100,-height);
        p2 = new Point3D(XLeft,y-100,0);
        p3 = new Point3D(XLeft+width,y-100,-height);
        gate[6] = new Square(Color.BLACK,tMaterial, p1, p2, getForthPoint(p1,p2,p3),p3);
        Geometries allGate = new Geometries(gate);
        allGate.add(sheet);
        scene.addGeometries(allGate);

    }

    private void rightTribune(Point3D rightDown, Point3D rightUp, int size, double height, Scene scene) {

        Color tColor = new Color(java.awt.Color.GREEN);
        Material tMaterial = new Material(0, 0.5, 60,0,0);
        int someTribuns = size/20;
        Square[] tribuns = new Square[someTribuns];
        Square[] benchs = new Square[someTribuns];
        for(int i = 0; i < someTribuns; i++) {
            try {
                Point3D p1 = new Point3D(rightUp.get_x(), rightUp.get_y(), - height + (i * height/someTribuns));
                Point3D p2 = new Point3D(rightDown.get_x(), rightDown.get_y(), - height + (i * height/someTribuns));
                Point3D p3 = new Point3D(rightUp.get_x() - i * size/someTribuns, rightUp.get_y(), - height + (i * height/someTribuns));
                Point3D p4 = getForthPoint(p1,p2,p3);
                Point3D p = new Point3D(rightUp.get_x()- i * size/someTribuns,rightUp.get_y(),- height + ((1+i) * height/someTribuns));
                tribuns[i] = new Square(tColor, tMaterial, p1, p2, p4, p3);
                benchs[i] = new Square(new Color(0,51,25),tMaterial,p3, p4,getForthPoint(p3, p4, p),p);
            }
            catch (IllegalArgumentException e){
                if(e.getMessage() != "zero vector")
                    throw new IllegalArgumentException();
            }
        }
        Geometries tribunsAndBenchs = new Geometries(tribuns);
        tribunsAndBenchs.add(benchs);
        scene.addGeometries(tribunsAndBenchs);
    }
    private void leftTribune(Point3D leftDown, Point3D leftUp, int size, double height, Scene scene) {

        Color tColor = new Color(java.awt.Color.GREEN);
        Material tMaterial = new Material(0, 0.5, 60,0,0);
        int someTribuns = size/20;
        Square[] tribuns = new Square[someTribuns];
        Square[] benchs = new Square[someTribuns];

        for(int i = 0; i < someTribuns; i++) {
            try {
                Point3D p1 = new Point3D(leftUp.get_x(), leftUp.get_y(), - height + (i * height/someTribuns));
                Point3D p2 =new Point3D(leftDown.get_x(), leftDown.get_y(), - height + (i * height/someTribuns));
                Point3D p3 = new Point3D(leftUp.get_x() + i * size/someTribuns, leftUp.get_y(), - height + (i * height/someTribuns));
                Point3D p4 = getForthPoint(p1,p2,p3);

                tribuns[i] = new Square(tColor, tMaterial,p1 ,p2, p4, p3);
                p1 = new Point3D(leftDown.get_x() + i * size/someTribuns, leftDown.get_y(), - height + (i * height/someTribuns));
                p2 = new Point3D(leftDown.get_x() + i * size/someTribuns, leftDown.get_y(), - height + ((1+i) * height/( someTribuns)));
                benchs[i] = new Square(new Color(0,51,25), tMaterial, p1, p2, getForthPoint(p1,p2,p3),p3);
            }
            catch (IllegalArgumentException e){
                if(e.getMessage() != "zero vector")
                    throw new IllegalArgumentException();
            }
        }
        Geometries tribunsAndBenchs = new Geometries(tribuns);
        tribunsAndBenchs.add(benchs);
        scene.addGeometries(tribunsAndBenchs);
    }
    private Point3D getForthPoint(Point3D p1, Point3D p2, Point3D p3){
        Point3D mid = new Point3D((p2.get_x()+p3.get_x())/2,(p2.get_y()+p3.get_y())/2,(p2.get_z()+p3.get_z())/2);
        Vector toP4 = mid.subtract(p1);
        return p1.add(toP4.scale(2));
    }
//    @Test
//    public void room(){
//        double height = 850;
//        double width = 1500;
//        double length = 1200;
//        int tribunsSize = (int)width/10;
//        Point3D rightUp = new Point3D(width/2,-length/2,0);
//        Point3D rightDown = new Point3D(width/2,length/2,0);
//        Point3D leftUp = new Point3D(-width/2,-length/2,0);
//        Point3D leftDown = new Point3D(-width/2,length/2,0);
//        Material wallsMaterial = new Material(0.5, 0.5, 60,0,0.3);
//        Scene scene = new Scene("mini project2");
//        scene.setCamera(new Camera(new Point3D(0, length/2, -height*0.8), new Vector(0, -1, 0.4), new Vector(0, -1, -2.5),2));
//        scene.setViewPlaneDistance(50);
//        scene.setFocalPlaneDistance(180);
//        scene.setBackground(Color.BLACK);
//        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
//        Square forwardWall = new Square(new Color(java.awt.Color.WHITE),new Material(0.1, 0.5, 60,0.8,0),rightUp,leftUp,new Point3D(rightUp.get_x(),rightUp.get_y(),-height));
//        Square rightWall = new Square(new Color(java.awt.Color.BLUE),wallsMaterial,rightDown,rightUp,new Point3D(rightDown.get_x(),rightDown.get_y(),-height));
//        Square leftWall = new Square(new Color(java.awt.Color.RED),wallsMaterial,leftDown,leftUp,new Point3D(leftDown.get_x(),leftDown.get_y(),-height));
//        Square roof = new Square(new Color(java.awt.Color.DARK_GRAY),wallsMaterial,
//                new Point3D(leftDown.get_x(),leftDown.get_y(),-height),
//                new Point3D(leftUp.get_x(),leftUp.get_y(),-height),
//                new Point3D( rightDown.get_x(), rightDown.get_y(),-height));
//        Square floor = new Square(new Color(java.awt.Color.DARK_GRAY),new Material(0.5, 0.5, 60,0,0.0),
//                leftDown,leftUp,rightDown);
//        Square[] borders = new Square[4];
//        Point3D bordUpRight = new Point3D(rightUp.get_x()-tribunsSize-80,rightUp.get_y()+250,-0.1);
//        Point3D bordDownRight = new Point3D(rightDown.get_x()-tribunsSize-80,rightDown.get_y()-180,-0.1);
//        Point3D bordUpLeft = new Point3D(leftUp.get_x()+tribunsSize+80,leftUp.get_y()+250,-0.1);
//        Point3D bordDownLeft = new Point3D(leftDown.get_x()+tribunsSize+80,leftDown.get_y()-180,-0.1);
//        borders[0] = new Square(Color.BLACK,wallsMaterial,bordUpRight,
//                bordDownRight, new Point3D(bordUpRight.get_x()-50,bordUpRight.get_y(),-0.1));
//        borders[1] = new Square(Color.BLACK,wallsMaterial,bordUpLeft,
//                bordDownLeft, new Point3D(bordUpLeft.get_x()+50,bordUpLeft.get_y(),-0.1));
//        borders[2] = new Square(Color.BLACK,wallsMaterial,bordUpLeft,bordUpRight,
//                new Point3D(bordUpLeft.get_x(),bordUpLeft.get_y()-50,-0.1));
//        borders[3] = new Square(Color.BLACK,wallsMaterial,bordDownLeft,bordDownRight,
//                new Point3D(bordDownLeft.get_x(),bordDownLeft.get_y()+50,-0.1));
//        projectors(height,length,width,scene,new Point3D(0,0,-100));
//        Geometries geoBorders = new Geometries(borders);
//        gate(bordUpLeft.get_y(),width,scene,height);
//        rightTribune(rightDown,rightUp,tribunsSize,height, scene);
//        leftTribune(leftDown,leftUp,tribunsSize,height, scene);
//        Sphere ball = new Sphere(new Color(76,153,0),new Material(0.2, 0.2, 30, 0.6, 0),
//                100,new Point3D(0,0,-100));
//        scene.addGeometries(forwardWall,rightWall,leftWall,floor,ball,roof);
//        scene.addGeometries(geoBorders);
//        scene.addLights(new SpotLight(new Color(java.awt.Color.BLUE),new Point3D(0,-length/2+20,-2*height/3),
//                        new Vector(0,1,1),1, 4E-5, 2E-7),
//                // new DirectionalLight(new Color(150,150,0),new Vector(0,1,0.3)),
//                new SpotLight(new Color(1000,1000,1000),new Point3D(0,-230,-230),new Vector(0,1,1)
//                        ,1, 4E-5, 2E-7,5)//,
//                //  new PointLight(new Color(500,500,0),new Point3D(0,0,-100),1, 4E-5, 2E-7));
//        );
//        ImageWriter imageWriter = new ImageWriter("room", 200, 200, 600, 600);
//        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();
//
//        render.renderImage(0,1);
//        render.writeToImage();
//    }
//
//    private void projectors(double height, double length, double width, Scene scene,Point3D ballPoint) {
//        int some = 16;
//        SpotLight[] projectors = new SpotLight[some];
//        Sphere[] lamps = new Sphere[some];
//        Material lampMaterial = new Material(0.02, 0.2, 30, 0.9, 0);
//        for (int i = 0, j = 1; i < some/2; i++, j *= -1)
//        {
//            Point3D rightPoint = new Point3D(width/2,j*i*length/some,height/20-height);
//            Point3D leftPoint = new Point3D(-width/2,j*i*length/some,height/20-height);
//
//            lamps[i] = new Sphere(new Color(32,32,32),lampMaterial,height/20,rightPoint);
//            lamps[some/2+i] = new Sphere(new Color(32,32,32),lampMaterial,height/20,leftPoint);
//
//            Point3D lightRight = new Point3D(rightPoint.get_x()-height/20,rightPoint.get_y(),rightPoint.get_z());
//            Point3D lightLeft = new Point3D(leftPoint.get_x()+height/20,leftPoint.get_y(),leftPoint.get_z());
//            Vector rightToBall = ballPoint.subtract(lightRight);
//            Vector leftToBall = ballPoint.subtract(lightLeft);
//
//            projectors[i] =  new SpotLight(new Color(5000,5000,0),lightRight,
//                    rightToBall,1, 4E-5, 2E-7);
//            projectors[some/2+i] =  new SpotLight(new Color(5000,5000,0),lightLeft,
//                    leftToBall,1, 4E-5, 2E-7);
//        }
//        scene.addLights(projectors);
//        scene.addGeometries(lamps);
//    }
//
//    private void gate(double y, double w, Scene scene, double hei) {
//        int density = 10;
//        double height = hei/2;
//        double XRight = w/8;
//        double XLeft = -w/8;
//        double width = w/100;
//        Material tMaterial = new Material(0.9, 0.5, 60,0,0);
//        Square[] sheet = new Square[density*2];
//        Square[] gate = new Square[7];
//        for (int i = 0; i < density; i++)
//        {
//            double x = XRight - w*i*2/80;
//            sheet[i] = new Square(Color.BLACK,tMaterial,new Point3D(x,y-100,-0.1),
//                    new Point3D(x - width,y-100,-0.1),new Point3D(x,y-100,-height));
//        }
//        for (int i = 0; i < density; i++)
//        {
//            sheet[i+10] = new Square(Color.BLACK,tMaterial,
//                    new Point3D(XRight,y-100,-i*height/(density)),
//                    new Point3D(XLeft,y-100,-i*height/(density)),
//                    new Point3D(XRight,y-100,-(i*height/(density))-width));
//        }
//        gate[0] = new Square(Color.BLACK,tMaterial,new Point3D(XRight,y,0),
//                new Point3D(XRight-width,y,0),new Point3D(XRight,y,-height));
//        gate[1] = new Square(Color.BLACK,tMaterial,new Point3D(XRight,y,-height),
//                new Point3D(XRight,y,-height-width),
//                new Point3D(XLeft,y,-height));
//        gate[2] = new Square(Color.BLACK,tMaterial,new Point3D(XLeft,y,0),
//                new Point3D(XLeft+width,y,0),
//                new Point3D(XLeft,y,-height));
//        gate[3] = new Square(Color.BLACK,tMaterial,new Point3D(XRight,y,-height),
//                new Point3D(XRight,y,-height+width),
//                new Point3D(XRight,y-100,-height));
//        gate[4] = new Square(Color.BLACK,tMaterial,new Point3D(XLeft,y,-height),
//                new Point3D(XLeft,y,-height+width),
//                new Point3D(XLeft,y-100,-height));
//        gate[5] = new Square(Color.BLACK,tMaterial,new Point3D(XRight,y-100,-height),
//                new Point3D(XRight,y-100,0),
//                new Point3D(XRight-width,y-100,-height));
//        gate[6] = new Square(Color.BLACK,tMaterial,new Point3D(XLeft,y-100,-height),
//                new Point3D(XLeft,y-100,0),
//                new Point3D(XLeft+width,y-100,-height));
//        Geometries allGate = new Geometries(gate);
//        allGate.add(sheet);
//        scene.addGeometries(allGate);
//
//    }
//
//    private void rightTribune(Point3D rightDown, Point3D rightUp, int size, double height, Scene scene) {
//
//        Color tColor = new Color(java.awt.Color.GREEN);
//        Material tMaterial = new Material(0, 0.5, 60,0,0);
//        int someTribuns = size/20;
//        Square[] tribuns = new Square[someTribuns];
//        Square[] benchs = new Square[someTribuns];
//        for(int i = 0; i < someTribuns; i++) {
//            try {
//                Point3D temp = new Point3D(rightUp.get_x() - i * size/someTribuns, rightUp.get_y(), - height + (i * height/someTribuns));
//
//                tribuns[i] = new Square(tColor, tMaterial, new Point3D(rightUp.get_x(), rightUp.get_y(), - height + (i * height/someTribuns)),//
//                        new Point3D(rightDown.get_x(), rightDown.get_y(), - height + (i * height/someTribuns)),//
//                        temp);
//                benchs[i] = new Square(new Color(0,51,25),tMaterial,temp,
//                        tribuns[i].fourthPoint,
//                        new Point3D(rightUp.get_x()- i * size/someTribuns,rightUp.get_y(),- height + ((1+i) * height/someTribuns)));
//            }
//            catch (IllegalArgumentException e){
//                if(e.getMessage() != "zero vector")
//                    throw new IllegalArgumentException();
//            }
//        }
//        Geometries tribunsAndBenchs = new Geometries(tribuns);
//        tribunsAndBenchs.add(benchs);
//        scene.addGeometries(tribunsAndBenchs);
//    }
//    private void leftTribune(Point3D leftDown, Point3D leftUp, int size, double height, Scene scene) {
//
//        Color tColor = new Color(java.awt.Color.GREEN);
//        Material tMaterial = new Material(0, 0.5, 60,0,0);
//        int someTribuns = size/20;
//        Square[] tribuns = new Square[someTribuns];
//        Square[] benchs = new Square[someTribuns];
//
//        for(int i = 0; i < someTribuns; i++) {
//            try {
//                Point3D temp = new Point3D(leftUp.get_x() + i * size/someTribuns, leftUp.get_y(), - height + (i * height/someTribuns));
//
//                tribuns[i] = new Square(tColor, tMaterial, new Point3D(leftUp.get_x(), leftUp.get_y(), - height + (i * height/someTribuns)),//
//                        new Point3D(leftDown.get_x(), leftDown.get_y(), - height + (i * height/someTribuns)),//
//                        temp);
//                benchs[i] = new Square(new Color(0,51,25), tMaterial, new Point3D(leftDown.get_x() + i * size/someTribuns, leftDown.get_y(), - height + (i * height/someTribuns)),//
//                        new Point3D(leftDown.get_x() + i * size/someTribuns, leftDown.get_y(), - height + ((1+i) * height/( someTribuns))),//
//                        temp);
//            }
//            catch (IllegalArgumentException e){
//                if(e.getMessage() != "zero vector")
//                    throw new IllegalArgumentException();
//            }
//        }
//        Geometries tribunsAndBenchs = new Geometries(tribuns);
//        tribunsAndBenchs.add(benchs);
//        scene.addGeometries(tribunsAndBenchs);
//    }
}
