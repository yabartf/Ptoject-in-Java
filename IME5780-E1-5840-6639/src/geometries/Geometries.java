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
       // createBox();
    }

    /***
     * constructor
     * 
     * @param _geometries list of geometries shapes
     */
    public Geometries(Intersectable... _geometries){
            add(_geometries); createBox();
    }

    /***
     * 
     * @param _geometries add to the list all shapes that are given as a list
     */
    public void add(Intersectable... _geometries){

        this.intersectables.addAll(Arrays.asList(_geometries));
        //createBox();
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
           // geometriesArr[i].createBox();
            geometriesArr[i].BVH(deep);
        }

       /* for (var geo : this.intersectables){
            double curDistance = geo.getBox().mid.distance(box.get_max());
            for (int i = 0; i < range; i++){
                if(curDistance < (i+1) * distance / range){
                    geometriesArr[i].add(geo);
                }
            }
        }
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
        intersectables.add(geometriesArr[0]);
        intersectables.add(geometriesArr[1]);
        createBox();

    }
    double minimum(double... numbers){
        double min = numbers[0];
        for (var mm : numbers){
            min = Math.min(min, mm);
        }
        return min;
    }
    public void createTreeRec(int depthOfTree) {

        Intersectable topRightCloseVoxel = null;
        Intersectable topLeftCloseVoxel = null;
        Intersectable downRightCloseVoxel = null;
        Intersectable downLeftCloseVoxel = null;
        Intersectable topRightFarVoxel = null;
        Intersectable topLeftFarVoxel = null;
        Intersectable downRightFarVoxel = null;
        Intersectable downLeftFarVoxel = null;

        //Insert any Geometries in the Geometries t the right voxel
        for (int i = 0; i < intersectables.size(); i++) {
            if (intersectables.get(i).getBox().mid.get_z() < this.box.mid.get_z()){
                if (intersectables.get(i).getBox().mid.get_y() < this.box.mid.get_y()){
                    if (intersectables.get(i).getBox().mid.get_x() > this.box.mid.get_x()){
                        if (topRightCloseVoxel == null)
                            topRightCloseVoxel = new Geometries();
                        ((Geometries) topRightCloseVoxel).add(intersectables.get(i));
                    }
                    else {
                        if (topLeftCloseVoxel == null)
                            topLeftCloseVoxel = new Geometries();
                        ((Geometries) topLeftCloseVoxel).add(intersectables.get(i));
                    }
                }
                else {
                    if (intersectables.get(i).getBox().mid.get_x() > this.box.mid.get_x()) {
                        if (downRightCloseVoxel == null)
                            downRightCloseVoxel = new Geometries();
                        ((Geometries) downRightCloseVoxel).add(intersectables.get(i));
                    }
                    else {
                        if (downLeftCloseVoxel == null)
                            downLeftCloseVoxel = new Geometries();
                        ((Geometries) downLeftCloseVoxel).add(intersectables.get(i));
                    }
                }
            }
            else {
                if (intersectables.get(i).getBox().mid.get_y() < this.box.mid.get_y()){
                    if (intersectables.get(i).getBox().mid.get_x() > this.box.mid.get_x()) {
                        if (topRightFarVoxel == null)
                            topRightFarVoxel = new Geometries();
                        ((Geometries) topRightFarVoxel).add(intersectables.get(i));
                    }
                    else {
                        if (topLeftFarVoxel == null)
                            topLeftFarVoxel = new Geometries();
                        ((Geometries) topLeftFarVoxel).add(intersectables.get(i));
                    }
                }
                else {
                    if (intersectables.get(i).getBox().mid.get_x() > this.box.mid.get_x()) {
                        if (downRightFarVoxel == null)
                            downRightFarVoxel = new Geometries();
                        ((Geometries) downRightFarVoxel).add(intersectables.get(i));
                    }
                    else {
                        if (downLeftFarVoxel == null)
                            downLeftFarVoxel = new Geometries();
                        ((Geometries) downLeftFarVoxel).add(intersectables.get(i));
                    }
                }
            }
        }

        intersectables.clear();

        //check for each voxel if it contain more than one geometries
        if(topRightCloseVoxel != null) {
            if (((Geometries)topRightCloseVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries)topRightCloseVoxel).intersectables.get(0));
            else{
                if(depthOfTree > 1)
                    ((Geometries)topRightCloseVoxel).createTreeRec(depthOfTree -1);
                intersectables.add(topRightCloseVoxel);
            }
        }

        if(topLeftCloseVoxel != null) {
            if (((Geometries)topLeftCloseVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries)topLeftCloseVoxel).intersectables.get(0));
            else{
                if(depthOfTree > 1)
                    ((Geometries)topLeftCloseVoxel).createTreeRec(depthOfTree -1);
                intersectables.add(topLeftCloseVoxel);
            }
        }

        if(downRightCloseVoxel != null) {
            if (((Geometries)downRightCloseVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries)downRightCloseVoxel).intersectables.get(0));
            else{
                if(depthOfTree > 1)
                    ((Geometries)downRightCloseVoxel).createTreeRec(depthOfTree -1);
                intersectables.add(downRightCloseVoxel);
            }
        }

        if(downLeftCloseVoxel != null) {
            if (((Geometries)downLeftCloseVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries)downLeftCloseVoxel).intersectables.get(0));
            else{
                if(depthOfTree > 1)
                    ((Geometries)downLeftCloseVoxel).createTreeRec(depthOfTree -1);
                intersectables.add(downLeftCloseVoxel);
            }
        }

        if(topRightFarVoxel != null) {
            if (((Geometries)topRightFarVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries)topRightFarVoxel).intersectables.get(0));
            else{
                if(depthOfTree > 1)
                    ((Geometries)topRightFarVoxel).createTreeRec(depthOfTree -1);
                intersectables.add(topRightFarVoxel);
            }
        }

        if(topLeftFarVoxel != null) {
            if (((Geometries)topLeftFarVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries)topLeftFarVoxel).intersectables.get(0));
            else{
                if(depthOfTree > 1)
                    ((Geometries)topLeftFarVoxel).createTreeRec(depthOfTree -1);
                intersectables.add(topLeftFarVoxel);
            }
        }

        if(downRightFarVoxel != null) {
            if (((Geometries)downRightFarVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries)downRightFarVoxel).intersectables.get(0));
            else{
                if(depthOfTree > 1)
                    ((Geometries)downRightFarVoxel).createTreeRec(depthOfTree -1);
                intersectables.add(downRightFarVoxel);
            }
        }

        if(downLeftFarVoxel != null) {
            if (((Geometries)downLeftFarVoxel).intersectables.size() == 1)
                intersectables.add(((Geometries)downLeftFarVoxel).intersectables.get(0));
            else{
                if(depthOfTree > 1)
                    ((Geometries)downLeftFarVoxel).createTreeRec(depthOfTree -1);
                intersectables.add(downLeftFarVoxel);
            }
        }
    }
}
