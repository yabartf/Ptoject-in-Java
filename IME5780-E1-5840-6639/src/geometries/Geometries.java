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
        //createBox();
    }


    /***
     * 
     * @param ray check if the ray intersect the geomtries
     * @return the list of intersection
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
        createBox();
        if(!box.inBox(ray))
            return null;
        List<GeoPoint> answer = new LinkedList<>();
        for (var shape : intersectables) {
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
        //createBox();
        if(intersectables.size() < 2 || deep--  <= 0)
            return;
        int range = 2;
        double distance = box.get_max().distance(box.get_min());
        Geometries[] geometriesArr = new Geometries[range];
        for (int i = 0; i < range; i++) {
            geometriesArr[i] = new Geometries();
        }
        for (var geo : this.intersectables){
            double toMax = geo.getBox()._max.distance(box._max);
            double toMin = geo.getBox()._min.distance(box._min);
            if (Math.min(toMax,toMin) == toMax)
                geometriesArr[0].add(geo);
            else
                geometriesArr[1].add(geo);
        }
        if (deep > 0)
        for (int i = 0; i < range; i++) {
            geometriesArr[i].BVH(deep);
        }

       /* for (var geo : this.intersectables){
            double curDistance = geo.getBox().mid.distance(box.get_max());
            for (int i = 0; i < range; i++){
                if(curDistance < (i+1) * distance / range){
                    geometriesArr[i].add(geo);
                }
            }
        }*/
       /* for (var geo : this.intersectables){


            double bigX = Math.abs(box._max.get_x() - geo.getBox()._max.get_x());
            double smallX = Math.abs(box._min.get_x() - geo.getBox()._min.get_x());
            double bigY = Math.abs(box._max.get_y() - geo.getBox().mid.get_y());
            double smallY = Math.abs(box._min.get_y() - geo.getBox().mid.get_y());
            double bigZ = Math.abs(box._max.get_y() - geo.getBox().mid.get_z());
            double smallZ = Math.abs(box._min.get_z() - geo.getBox().mid.get_z());

            double min = minimum(bigX,bigY,bigZ,smallX,smallY,smallZ);
            if (bigX == min){
                geometriesArr[0].add(geo);
                if (deep > 0)
                    geometriesArr[0].BVH(deep);
            }
            else if (smallX == min){
                geometriesArr[1].add(geo);
                if (deep > 0)
                    geometriesArr[1].BVH(deep);
            }
            else if (bigY == min){
                geometriesArr[2].add(geo);
                if (deep > 0)
                    geometriesArr[2].BVH(deep);
            }
            else if (smallY == min){
                geometriesArr[3].add(geo);
                if (deep > 0)
                    geometriesArr[3].BVH(deep);
            }
            else if (bigZ == min){
                geometriesArr[4].add(geo);
                if (deep > 0)
                    geometriesArr[4].BVH(deep);
            }
            else if (smallZ == min){
                geometriesArr[5].add(geo);
                if (deep > 0)
                    geometriesArr[5].BVH(deep);
            }
        }*/
        intersectables.clear();
        intersectables.addAll(List.of(geometriesArr));

    }
    double minimum(double... numbers){
        double min = numbers[0];
        for (var mm : numbers){
            min = Math.min(min, mm);
        }
        return min;
    }
}
