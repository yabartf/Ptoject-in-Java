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
            createBox();
    }

    /***
     * 
     * @param _geometries add to the list all shapes that are given as a list
     *                    and updating the box
     */
    public void add(Intersectable... _geometries){
        if (box == null)
            createBox();
        double Xmax = box._max.get_x();
        double Xmin = box._min.get_x();
        double Ymax = box._max.get_y();
        double Ymin = box._min.get_y();
        double Zmax = box._max.get_z();
        double Zmin = box._min.get_z();
        for (var inter : _geometries){
            Xmax = Math.max(Xmax,inter.getBox()._max.get_x());
            Xmin = Math.min(Xmin,inter.getBox()._min.get_x());
            Ymax = Math.max(Ymax,inter.getBox()._max.get_y());
            Ymin = Math.min(Ymin,inter.getBox()._min.get_y());
            Zmax = Math.max(Zmax,inter.getBox()._max.get_z());
            Zmin = Math.min(Zmin,inter.getBox()._min.get_z());
        }
        box._max = new Point3D(Xmax,Ymax,Zmax);
        box._min = new Point3D(Xmin,Ymin,Zmin);
        this.intersectables.addAll(Arrays.asList(_geometries));
    }


    /***
     * 
     * @param ray check if the ray intersect the geomtries
     * @return the list of intersection
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) {
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
        return new Box(box._min,box._max);
    }

    /**
     * create a box around all the intersectabels
     */
    public void createBox(){
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

    /**
     * BVH recursive algoritem. create binary tree.
     * @param deep
     */
    @Override
    public void BVH(int deep) {
        if(intersectables.size() < 2 || deep--  <= 0)
            return;
        int range = 2;

        Geometries[] geometriesArr = new Geometries[range];
        for (int i = 0; i < range; i++) {
            geometriesArr[i] = new Geometries();
        }
        for (var geo : this.intersectables){
            // check to where the geo is closer.
            double toMax = geo.getBox().mid.distance(box._max);
            double toMin = geo.getBox().mid.distance(box._min);
            if (Math.min(toMax,toMin) == toMax)
                geometriesArr[0].add(geo);
            else
                geometriesArr[1].add(geo);
        }
        intersectables.clear();

        for (int i = 0; i < range; i++) {
            this.add(geometriesArr[i]);
            if (deep > 0)
            geometriesArr[i].BVH(deep);
        }
    }

    /**
     * find minimum for some numbers.
     * @param  numbers
     * @return minimum of all numbers.
     */
    double minimum(double... numbers){
        double min = numbers[0];
        for (var mm : numbers){
            min = Math.min(min, mm);
        }
        return min;
    }

}
