package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geometries implements Intersectable {  
   /**the list of all the shapes**/ 
   List<Intersectable> intersectables = new ArrayList<>();
    /***************constructors***************/
    /**copy C-tor**/
    public Geometries(List<Intersectable> intersectables) {
        this.intersectables = intersectables;
    }

    /***
     * C-tor
     * 
     * @param _geometries list of geometries shapes
     */
    public Geometries(Intersectable... _geometries){
            add(_geometries);
    }

    /***
     * 
     * @param _geometries add to the list all shapes that are given as a list
     */
    public void add(Intersectable... _geometries){
            this.intersectables.addAll(Arrays.asList(_geometries));
    }

    /***
     * 
     * @param ray check if the ray intersect the geomtries
     * @return the list of intersection
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> answer = new ArrayList<>();
        for (var shape: intersectables) {
            if(shape.findIntersections(ray)!=null)
                  answer.addAll(shape.findIntersections(ray));
        }
        //return the list only if there are intersections
        if(answer.size() > 0)
            return answer;
        //if there are no intersection return null
        return null;
    }
}
