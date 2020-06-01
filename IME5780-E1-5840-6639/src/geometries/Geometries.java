package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        List<GeoPoint> answer = new LinkedList<>();
        for (var shape: intersectables) {
            if(shape.findIntersections(ray,max)!=null)
                  answer.addAll(shape.findIntersections(ray,max));
        }
        //return the list only if there are intersections
        if(answer.size() > 0)
            return answer;
        //if there are no intersection return null
        return null;
    }


}
