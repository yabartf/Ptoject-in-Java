package unittests;
import primitives.Vector;

import static java.lang.System.out;

public class VectorTest {

    @org.junit.Test
    public void add() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,1,1);
        if(!new Vector(2,3,4).equals(v1.add(v2)))
            out.println("problam in adding vectors");
    }

    @org.junit.Test
    public void substract() {
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,1,1);
        if(!new Vector(0,1,2).equals(v1.substract(v2)))
            out.println("problam in substracting vectors");
    }

    @org.junit.Test
    public void scale() {
        Vector v1 = new Vector(1,2,3);
        if(!new Vector(3,6,9).equals(v1.scale(3)))
            out.println("problam in scale method");
    }

    @org.junit.Test
    public void dotProduct() {
    }

    @org.junit.Test
    public void crossProduct() {
    }

    @org.junit.Test
    public void lengthSquared() {
    }

    @org.junit.Test
    public void length() {
    }

    @org.junit.Test
    public void normalize() {
    }

    @org.junit.Test
    public void normalized() {
    }
}