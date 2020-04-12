package unittests

import geometries.Sphere
import org.junit.Assert
import org.junit.Test
import primitives.Point3D
import primitives.Ray
import primitives.Vector
import java.util.List

class SphereTest {
    @Test
    fun testConstructor() {
        try {
            Sphere(Point3D(-2, -5, 7), 1)
        } catch (e: IllegalArgumentException) {
            Assert.fail("Failed constructing a correct sphere")
        }
        try {
            Sphere(Point3D(2, -3, 1), (-2).toDouble())
            Assert.fail("Constructed a sphere withe negative radius")
        } catch (e: IllegalArgumentException) {
        }
    }

    @Test
    fun testFindIntersections() {
        val sphere = Sphere(Point3D(1, 0, 0), 1.0)
        var vector: Vector? = Vector(3, 1, 0)
        // ============ Equivalence Partitions Tests ==============
// TC01: Ray's line is outside the sphere (0 points)
        Assert.assertEquals("Ray's line out of sphere", null,
                sphere.findIntsersections(Ray(Vector(1, 1, 0), Point3D(-1, 0, 0))))
        // TC02: Ray starts before and crosses the sphere (2 points)
        var p1: Point3D? = Point3D(0.0651530771650466, 0.355051025721682, 0)
        var p2: Point3D? = Point3D(1.53484692283495, 0.844948974278318, 0)
        var result = sphere.findIntsersections(Ray(vector,
                Point3D(-1, 0, 0)))
        Assert.assertEquals("Wrong number of points", 2, result.size.toLong())
        if (result[0]._x > result[1]._x) result = List.of(result[1], result[0])
        Assert.assertEquals("Ray crosses sphere", List.of(p1, p2), result)
        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntsersections(Ray(vector, Point3D(0.6651530771650466, 0.555051025721682, 0)))
        Assert.assertEquals("Wrong number of points", 1, result.size.toLong())
        Assert.assertEquals("Ray crosses sphere", p2, result[0])
        // TC04: Ray starts after the sphere (0 points)
        result = sphere.findIntsersections(Ray(vector, Point3D(1.83484692283495, 0.944948974278318, 0)))
        Assert.assertEquals("Wrong number of points", null, result)
        // =============== Boundary Values Tests ==================
// **** Group: Ray's line crosses the sphere (but not the center)
// TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntsersections(Ray(vector, p1))
        Assert.assertEquals("Wrong number of points", 1, result.size.toLong())
        Assert.assertEquals("Ray crosses sphere", p2, result[0])
        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntsersections(Ray(vector, p2))
        Assert.assertEquals("Wrong number of points", null, result)
        // **** Group: Ray's line goes through the center
        vector = Vector(0, 1, 0)
        p1 = Point3D(1, -1, 0)
        p2 = Point3D(1, 1, 0)
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntsersections(Ray(vector, Point3D(1, -2, 0)))
        Assert.assertEquals("Wrong number of points", 2, result.size.toLong())
        if (result[0]._y > result[1]._y) result = List.of(result[1], result[0])
        Assert.assertEquals("Ray crosses sphere", List.of(p1, p2), result)
        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntsersections(Ray(vector, p1))
        Assert.assertEquals("Wrong number of points", 1, result.size.toLong())
        Assert.assertEquals("Ray crosses sphere", p2, result[0])
        // TC15: Ray starts inside (1 points)
        result = sphere.findIntsersections(Ray(vector, Point3D(1, 0.5, 0)))
        Assert.assertEquals("Wrong number of points", 1, result.size.toLong())
        Assert.assertEquals("Ray crosses sphere", p2, result[0])
        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntsersections(Ray(Vector(1, 1, 0), sphere.center))
        Assert.assertEquals("Wrong number of points", 1, result.size.toLong())
        Assert.assertEquals("Ray crosses sphere", Point3D(1.7071067811865475, 0.7071067811865475, 0), result[0])
        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntsersections(Ray(vector, p2))
        Assert.assertEquals("Wrong number of points", null, result)
        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntsersections(Ray(vector, Point3D(1, 2, 0)))
        Assert.assertEquals("Wrong number of points", null, result)
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        p1 = Point3D(0.7071067812, 0.7071067812, 0)
        vector = Vector(1, -1, 0)
        // TC19: Ray starts before the tangent point
        result = sphere.findIntsersections(Ray(vector, Point3D(0.7071067811865475, 1.7071067811865475, 0)))
        Assert.assertEquals("Wrong number of points", null, result)
        // TC20: Ray starts at the tangent point
        result = sphere.findIntsersections(Ray(vector, Point3D(1.7071067811865475, 0.7071067811865475, 0)))
        Assert.assertEquals("Wrong number of points", null, result)
        // TC21: Ray starts after the tangent point
        result = sphere.findIntsersections(Ray(vector, Point3D(2.7071067811865475, 0.29289321881345254, 0)))
        Assert.assertEquals("Wrong number of points", null, result)
        // **** Group: Special cases
// TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntsersections(Ray(Vector(0, 1, 0), Point3D(-1, 0, 0)))
        Assert.assertEquals("Wrong number of points", null, result)
    }

    @Test
    fun testGetNormal() { // ============ Equivalence Partitions Tests ==============
// TC01: There is a simple single test here
        val sp = Sphere(Point3D(1, 1, 1), 5)
        Assert.assertEquals("Bad normal to sphere", Vector(1, 0, 0), sp.getNormal(Point3D(6, 1, 1)))
    }
}