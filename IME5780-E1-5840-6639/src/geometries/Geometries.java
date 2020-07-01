package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {  
   /**the list of all the shapes**/ 
    List<Intersectable> intersectables = new LinkedList<>();
    Box box;

    /***************constructors***************/
    /**
     * constructor
     * @param intersectables
     */
    public Geometries(List<Intersectable> intersectables) {
        this.intersectables = intersectables;
        createBox();
    }

    /***
     * constructor
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
        createBox();
    }
    public void addArrey(Intersectable[] _geometries){

        this.intersectables.addAll(Arrays.asList(_geometries));
        createBox();
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
            if(shape != null) {
                if(shape.getBox().inBox(ray)) {
                    List<GeoPoint> intersection = shape.findIntersections(ray, max);
                    if (intersection != null)
                        answer.addAll(intersection);
                }
            }
        }
        //return the list only if there are intersections
        if(answer.size() > 0)
            return answer;
        //if there are no intersection return null
        return null;
    }

    @Override
    public Box getBox() {
        return box;
    }

    private void createBox(){
        double xmin =Double.POSITIVE_INFINITY, ymin = Double.POSITIVE_INFINITY, zmin = Double.POSITIVE_INFINITY,
                xmax = Double.NEGATIVE_INFINITY, ymax = Double.NEGATIVE_INFINITY, zmax = Double.NEGATIVE_INFINITY;
        for(var geometry:this.intersectables){
            xmin = Math.min(xmin,geometry.getBox()._min.get_x());
            ymin = Math.min(ymin,geometry.getBox()._min.get_y());
            zmin = Math.min(zmin,geometry.getBox()._min.get_z());
            xmax = Math.max(xmax, geometry.getBox()._max.get_x());
            ymax = Math.max(ymax, geometry.getBox()._max.get_y());
            zmax = Math.max(zmax, geometry.getBox()._max.get_z());
        }
        box = new Box(new Point3D(xmin, ymin, zmin), new Point3D(xmax, ymax, zmax));
    }

    @Override
    public void BVH(int deep) {
        if(intersectables.size() < 5 || deep <= 0)
            return;
        double distance = box.get_max().distance(box.get_min());
        Geometries[] geometriesArr = new Geometries[4];
        for (int i = 0; i < 4; i++) {
            geometriesArr[i] = new Geometries();
        }
        for (var geo : this.intersectables){
            if(geo.getBox().mid.distance(box.get_max()) < distance / 4){
                geometriesArr[0].add(geo);
            }
            if(geo.getBox().mid.distance(box.get_max()) < 2 * distance / 4){
                geometriesArr[1].add(geo);
            }
            if(geo.getBox().mid.distance(box.get_max()) < 3 * distance / 4){
                geometriesArr[2].add(geo);
            }
            if(geo.getBox().mid.distance(box.get_max()) < 4 * distance / 4){
                geometriesArr[3].add(geo);
            }
        /*    if(geo.getBox().mid.distance(box.get_max()) < 5 * distance / 8){
                geometriesArr[4].add(geo);
            }
            if(geo.getBox().mid.distance(box.get_max()) < 6 * distance / 8){
                geometriesArr[5].add(geo);
            }
            if(geo.getBox().mid.distance(box.get_max()) < 7 * distance / 8){
                geometriesArr[6].add(geo);
            }
            if(geo.getBox().mid.distance(box.get_max()) < 8 * distance / 8){
                geometriesArr[7].add(geo);
            }*/
        }
        intersectables.clear();
        intersectables.addAll(List.of(geometriesArr));
        deep--;
            for (var geo : geometriesArr){
                geo.BVH(deep);
            }
    }
}
