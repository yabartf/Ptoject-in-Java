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

import java.util.LinkedList;
import java.util.List;

public class miniProject2 {
    @Test
    public void football_hall() {
        double height = 850;
        double width = 1500;
        double length = 1200;
        int tribunsSize = (int) width / 10;
        Point3D rightUp = new Point3D(width / 2, -length / 2, 0);
        Point3D rightDown = new Point3D(width / 2, length / 2, 0);
        Point3D leftUp = new Point3D(-width / 2, -length / 2, 0);
        Point3D leftDown = new Point3D(-width / 2, length / 2, 0);
        Material wallsMaterial = new Material(0.5, 0.5, 60, 0, 0.3);
        Material forwardMaterial = new Material(0.1, 0.5, 60, 0.8, 0);
        Scene scene = new Scene("mini project2");
        scene.setCamera(new Camera(new Point3D(0, length / 2, -height * 0.8), new Vector(0, -1, 0.4), new Vector(0, -1, -2.5), 100));
        scene.setViewPlaneDistance(50);
        scene.setFocalPlaneDistance(length / 2 + 100);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
        Point3D p3 = new Point3D(rightUp.get_x(), rightUp.get_y(), -height);
        Square forwardWall = new Square(new Color(java.awt.Color.WHITE), forwardMaterial, rightUp, leftUp, getForthPoint(rightUp, leftUp, p3), p3);
        Triangle david1 = new Triangle(new Color(java.awt.Color.BLUE),forwardMaterial,
                new Point3D(rightUp.get_x()/2,rightUp.get_y()+1,-height/3),
                new Point3D(leftUp.get_x()/2,leftUp.get_y()+1,-height/3),
                new Point3D(0,rightUp.get_y()+1,-5*height/6));
        Triangle david2 = new Triangle(new Color(java.awt.Color.BLUE),forwardMaterial,
                new Point3D(rightUp.get_x()/2,rightUp.get_y()+1,-4*height/6),
                new Point3D(leftUp.get_x()/2,leftUp.get_y()+1,-4*height/6),
                new Point3D(0,rightUp.get_y()+1,-height/4));
        p3 = new Point3D(rightDown.get_x(), rightDown.get_y(), -height);
        Square rightWall = new Square(new Color(java.awt.Color.BLUE), wallsMaterial, rightDown, rightUp, getForthPoint(rightDown, rightUp, p3), p3);
        Point3D p1 = new Point3D(leftDown.get_x(), leftDown.get_y(), -height);
        Point3D p2 = new Point3D(leftUp.get_x(), leftUp.get_y(), -height);
        p3 = new Point3D(rightDown.get_x(), rightDown.get_y(), -height);
        Square roof = new Square(new Color(java.awt.Color.DARK_GRAY), wallsMaterial, p1, p2, getForthPoint(p1, p2, p3), p3);
        p3 = new Point3D(leftDown.get_x(), leftDown.get_y(), -height);
        Square leftWall = new Square(new Color(java.awt.Color.RED), wallsMaterial, leftDown, leftUp, getForthPoint(leftDown, leftUp, p3), p3);
        Square floor = new Square(new Color(java.awt.Color.DARK_GRAY), new Material(0.5, 0.5, 60),
                leftDown, leftUp, getForthPoint(leftDown, leftUp, rightDown), rightDown);
        Square[] borders = new Square[4];
        Point3D bordUpRight = new Point3D(rightUp.get_x() - tribunsSize - width/30, rightUp.get_y() + 250, -0.1);
        Point3D bordDownRight = new Point3D(rightDown.get_x() - tribunsSize - width/30, rightDown.get_y() - 180, -0.1);
        Point3D bordUpLeft = new Point3D(leftUp.get_x() + tribunsSize + 80, leftUp.get_y() + 250, -0.1);
        Point3D bordDownLeft = new Point3D(leftDown.get_x() + tribunsSize + 80, leftDown.get_y() - 180, -0.1);
        p3 = new Point3D(bordUpRight.get_x() - width/30, bordUpRight.get_y(), -0.1);
        borders[0] = new Square(Color.BLACK, wallsMaterial, bordUpRight, bordDownRight, getForthPoint(bordUpRight, bordDownRight, p3), p3);
        p3 = new Point3D(bordUpLeft.get_x() + width/30, bordUpLeft.get_y(), -0.1);
        borders[1] = new Square(Color.BLACK, wallsMaterial, bordUpLeft, bordDownLeft, getForthPoint(bordUpLeft, bordDownLeft, p3), p3);
        p3 = new Point3D(bordUpLeft.get_x(), bordUpLeft.get_y() - width/30, -0.1);
        borders[2] = new Square(Color.BLACK, wallsMaterial, bordUpLeft, bordUpRight, getForthPoint(bordUpLeft, bordUpRight, p3), p3);
        p3 = new Point3D(bordDownLeft.get_x(), bordDownLeft.get_y() + width/30, -0.1);
        borders[3] = new Square(Color.BLACK, wallsMaterial, bordDownLeft, bordDownRight, getForthPoint(bordDownLeft, bordDownRight, p3), p3);
        projectors(height, length, width, scene, new Point3D(0, 0, -height/10));
        Geometries geoBorders = new Geometries(borders[0],borders[1],borders[2],borders[3]);
        gate(bordUpLeft.get_y(), width, scene, height);
        rightTribune(rightDown, rightUp, tribunsSize, height, scene);
        leftTribune(leftDown, leftUp, tribunsSize, height, scene);
        Sphere ball = new Sphere(new Color(76, 153, 0), new Material(0.2, 0.2, 30, 0.6, 0),
                height/10, new Point3D(0, 0, -height/10));
        Geometries forward = new Geometries(forwardWall,david1,david2);
        Geometries allFloor = new Geometries(floor,ball,geoBorders);
        scene.addGeometries(leftWall, rightWall, roof,allFloor);
        scene.addGeometries(forward);
        scene.addLights(new SpotLight(new Color(80000, 80000, 80000), new Point3D(0, -length / 2 + 1, -2 * height / 3),
                        new Vector(0, 1, 1), 1, 4E-1, 2E-3, 10),
                //new DirectionalLight(new Color(150,150,0),new Vector(0,1,0.3)),
                new SpotLight(new Color(80000, 80000, 80000), new Point3D(0, -2*height/10, -3*height/10), new Vector(0, 1, 1.5)
                        , 1, 4E-1, 2E-3, 10));

        ImageWriter imageWriter = new ImageWriter("football hall", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint();
        render.renderImage(100, 100);
        render.writeToImage();
    }

    private void projectors(double height, double length, double width, Scene scene, Point3D ballPoint) {
        int some = 16;
        SpotLight[] projectors = new SpotLight[some];
        Geometries upRightLamps = new Geometries();
        Geometries downRightLamps = new Geometries();
        Geometries downLeftLamps = new Geometries();
        Geometries upLeftLamps = new Geometries();
        Color lampColor = new Color(32, 32, 32);
        Color projecColor = new Color(80000, 80000, 0);
        Material lampMaterial = new Material(0.02, 0.2, 30, 0.9, 0);


        for (int i = 0, j = 1; i < some / 2; i++, j *= -1) {
            Point3D rightPoint = new Point3D(width / 2, j * i * length / some, height / 20 - height);
            Point3D leftPoint = new Point3D(-width / 2, j * i * length / some, height / 20 - height);
            if ( j > 0) {
                downRightLamps.add(new Sphere(lampColor, lampMaterial, height / 20, rightPoint));
                downLeftLamps.add(new Sphere(lampColor, lampMaterial, height / 20, leftPoint));
            }
            else {
                upRightLamps.add(new Sphere(lampColor, lampMaterial, height / 20, rightPoint));
                upLeftLamps.add(new Sphere(lampColor, lampMaterial, height / 20, leftPoint));
            }
            Point3D lightRight = new Point3D(rightPoint.get_x() - 1, rightPoint.get_y(), rightPoint.get_z());
            Point3D lightLeft = new Point3D(leftPoint.get_x() + 1, leftPoint.get_y(), leftPoint.get_z());
            Vector rightToBall = ballPoint.subtract(lightRight);
            Vector leftToBall = ballPoint.subtract(lightLeft);

            projectors[i] = new SpotLight(projecColor, lightRight,
                    rightToBall, 1, 2E-1, 1E-1);
            projectors[some / 2 + i] = new SpotLight(projecColor, lightLeft,
                    leftToBall, 1, 2E-1, 1E-1);
           // scene.addGeometries(rightLamps,leftLamps);
        }
        Geometries allRightLamps = new Geometries(upRightLamps,downRightLamps);
        Geometries allLeftLamps = new Geometries(upLeftLamps,downLeftLamps);
        scene.addLights(projectors);
        scene.addGeometries(allRightLamps, allLeftLamps);
    }

    private void gate(double y, double w, Scene scene, double hei) {
        int density = 10;
        double height = hei / 2;
        double XRight = w / 8;
        double XLeft = -w / 8;
        double width = w / 100;
        Material tMaterial = new Material(0.9, 0.5, 60, 0, 0);
        Square[] sheet = new Square[density * 2 + 1];
        Square[] gate = new Square[7];
        for (int i = 0; i < density; i++) {
            double x = XRight - w * i * 2 / 80;
            Point3D p1 = new Point3D(x, y - hei/10, -0.1);
            Point3D p2 = new Point3D(x - width, y - hei/10, -0.1);
            Point3D p3 = new Point3D(x, y - 100, -height);
            sheet[i] = new Square(Color.BLACK, tMaterial, p1, p2, getForthPoint(p1, p2, p3), p3);
           // scene.addGeometries(sheet[i]);
        }
        for (int i = 0; i < density + 1; i++) {
            Point3D p1 = new Point3D(XRight, y - 100, -i * height / (density));
            Point3D p2 = new Point3D(XLeft, y - 100, -i * height / (density));
            Point3D p3 = new Point3D(XRight, y - 100, -(i * height / (density)) - width);
            sheet[i + density] = new Square(Color.BLACK, tMaterial, p1, p2, getForthPoint(p1, p2, p3), p3);
            //scene.addGeometries(sheet[i]);
        }

        Point3D p1 = new Point3D(XRight, y, 0);
        Point3D p2 = new Point3D(XRight - width, y, 0);
        Point3D p3 = new Point3D(XRight, y, -height);
        gate[0] = new Square(Color.BLACK, tMaterial, p1, p2, getForthPoint(p1, p2, p3), p3);
        p1 = new Point3D(XRight, y, -height);
        p2 = new Point3D(XRight, y, -height - width);
        p3 = new Point3D(XLeft, y, -height);
        gate[1] = new Square(Color.BLACK, tMaterial, p1, p2, getForthPoint(p1, p2, p3), p3);
        p1 = new Point3D(XLeft, y, 0);
        p2 = new Point3D(XLeft + width, y, 0);
        p3 = new Point3D(XLeft, y, -height);
        gate[2] = new Square(Color.BLACK, tMaterial, p1, p2, getForthPoint(p1, p2, p3), p3);
        p1 = new Point3D(XRight, y, -height);
        p2 = new Point3D(XRight, y, -height + width);
        p3 = new Point3D(XRight, y - hei/10, -height);
        gate[3] = new Square(Color.BLACK, tMaterial, p1, p2, getForthPoint(p1, p2, p3), p3);
        p1 = new Point3D(XLeft, y, -height);
        p2 = new Point3D(XLeft, y, -height + width);
        p3 = new Point3D(XLeft, y - hei/10, -height);
        gate[4] = new Square(Color.BLACK, tMaterial, p1, p2, getForthPoint(p1, p2, p3), p3);
        p1 = new Point3D(XRight, y - hei/10, -height);
        p2 = new Point3D(XRight, y - hei/10, 0);
        p3 = new Point3D(XRight - width, y - hei/10, -height);
        gate[5] = new Square(Color.BLACK, tMaterial, p1, p2, getForthPoint(p1, p2, p3), p3);
        p1 = new Point3D(XLeft, y - hei/10, -height);
        p2 = new Point3D(XLeft, y - hei/10, 0);
        p3 = new Point3D(XLeft + width, y - hei/10, -height);
        gate[6] = new Square(Color.BLACK, tMaterial, p1, p2, getForthPoint(p1, p2, p3), p3);
        //scene.addGeometries(gate[0],gate[1],gate[2],gate[3],gate[4],gate[5],gate[6]);
        Geometries partsGate = new Geometries(gate);
        Geometries allGate = new Geometries();
        allGate.add(sheet);
        allGate.add(partsGate);
        scene.addGeometries(allGate);
    }

    private void rightTribune(Point3D rightDown, Point3D rightUp, int size, double height, Scene scene) {

        Color tColor = new Color(java.awt.Color.GREEN);
        Material tMaterial = new Material(0, 0.5, 60, 0, 0);
        int someTribuns = (size / 20) - 1;
        Square[] tribuns = new Square[someTribuns];
        Square[] benchs = new Square[someTribuns];
        Color benchColor = new Color(0, 51, 25);
        for (int i = 1; i <= someTribuns; i++) {
            try {
                Point3D p1 = new Point3D(rightUp.get_x(), rightUp.get_y(), -height + (i * height / someTribuns));
                Point3D p2 = new Point3D(rightDown.get_x(), rightDown.get_y(), -height + (i * height / someTribuns));
                Point3D p3 = new Point3D(rightUp.get_x() - i * size / someTribuns, rightUp.get_y(), -height + (i * height / someTribuns));
                Point3D p4 = getForthPoint(p1, p2, p3);
                Point3D p = new Point3D(rightUp.get_x() - i * size / someTribuns, rightUp.get_y(), -height + ((1 + i) * height / someTribuns));
                tribuns[i - 1] = new Square(tColor, tMaterial, p1, p2, p4, p3);
                benchs[i - 1] = new Square(benchColor, tMaterial, p3, p4, getForthPoint(p3, p4, p), p);
            } catch (IllegalArgumentException e) {
                if (e.getMessage() != "zero vector")
                    throw new IllegalArgumentException();
            }
            //scene.addGeometries(tribuns[i-1]);
            //scene.addGeometries(benchs[i-1]);
        }
        Geometries tribunsAndBenchs = new Geometries(tribuns);
        tribunsAndBenchs.add(benchs);
        scene.addGeometries(tribunsAndBenchs);
    }

    private void leftTribune(Point3D leftDown, Point3D leftUp, int size, double height, Scene scene) {

        Color tColor = new Color(java.awt.Color.GREEN);
        Material tMaterial = new Material(0, 0.5, 60, 0, 0);
        int someTribuns = (size / 20) - 1;
        Square[] tribuns = new Square[someTribuns];
        Square[] benchs = new Square[someTribuns];
        Color benchColor = new Color(0, 51, 25);
        for (int i = 1; i <= someTribuns; i++) {
            try {
                Point3D p1 = new Point3D(leftUp.get_x(), leftUp.get_y(), -height + (i * height / someTribuns));
                Point3D p2 = new Point3D(leftDown.get_x(), leftDown.get_y(), -height + (i * height / someTribuns));
                Point3D p3 = new Point3D(leftUp.get_x() + i * size / someTribuns, leftUp.get_y(), -height + (i * height / someTribuns));
                Point3D p4 = getForthPoint(p1, p2, p3);

                tribuns[i - 1] = new Square(tColor, tMaterial, p1, p2, p4, p3);
                p1 = new Point3D(leftDown.get_x() + i * size / someTribuns, leftDown.get_y(), -height + (i * height / someTribuns));
                p2 = new Point3D(leftDown.get_x() + i * size / someTribuns, leftDown.get_y(), -height + ((1 + i) * height / (someTribuns)));
                benchs[i - 1] = new Square(benchColor, tMaterial, p1, p2, getForthPoint(p1, p2, p3), p3);
            } catch (IllegalArgumentException e) {
                if (e.getMessage() != "zero vector")
                    throw new IllegalArgumentException();
            }
           // scene.addGeometries(tribuns[i-1]);
           // scene.addGeometries(benchs[i-1]);
        }
        Geometries tribunsAndBenchs = new Geometries(tribuns);
        tribunsAndBenchs.add(benchs);
        scene.addGeometries(tribunsAndBenchs);
    }

    private Point3D getForthPoint(Point3D p1, Point3D p2, Point3D p3) {
        Point3D mid = new Point3D((p2.get_x() + p3.get_x()) / 2, (p2.get_y() + p3.get_y()) / 2, (p2.get_z() + p3.get_z()) / 2);
        Vector toP4 = mid.subtract(p1);
        return p1.add(toP4.scale(2));
    }
}