package geometries;

import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * 
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same point
     *                                  <li>The vertices are not in the same plane</li>
     *                                  <li>The order of vertices is not according to edge path</li>
     *                                  <li>Three consequent vertices lay in the same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex></li>
     *                                  </ul>
     */
    public Polygon(Point3D... vertices) {

        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = List.of(vertices);
        createBox();
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3)
        {
            //createBox();
            return; // no need for more tests for a Triangle
        }

        Vector n = _plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[0].subtract(vertices[vertices.length - 1]);
        Vector edge2 = vertices[1].subtract(vertices[0]);
        Vector edge3=vertices[vertices.length-1].subtract(vertices[vertices.length-2]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        if (positive != (edge3.crossProduct(edge1).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        for (int i = 2; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
        //createBox();
    }
    public Polygon(Color objColor,Point3D... vertices){
        this(vertices);
        _emmission=objColor;
    }

    public Polygon(Color emmission, Material matirial, Point3D... _vertices) {
        this(_vertices);
        this._emmission = emmission;
        this._matirial = matirial;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal();
    }

    @Override
    public Box getBox() {
        return _box;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        return null;
    }


    /**
     * crating a box around the geometry
     */
    private void createBox(){
        double xmin =Double.POSITIVE_INFINITY, ymin = Double.POSITIVE_INFINITY, zmin = Double.POSITIVE_INFINITY,
                xmax = Double.NEGATIVE_INFINITY, ymax = Double.NEGATIVE_INFINITY, zmax = Double.NEGATIVE_INFINITY;
        for(var vertic :_vertices){
            xmin = Math.min(xmin,vertic.get_x());
            ymin = Math.min(ymin,vertic.get_y());
            zmin = Math.min(zmin,vertic.get_z());
            xmax = Math.max(xmax, vertic.get_x());
            ymax = Math.max(ymax, vertic.get_y());
            zmax = Math.max(zmax, vertic.get_z());
        }
        _box = new Box(new Point3D(xmin, ymin, zmin), new Point3D(xmax, ymax, zmax));
    }


}
