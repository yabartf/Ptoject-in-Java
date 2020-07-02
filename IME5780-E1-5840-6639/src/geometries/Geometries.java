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
        if(intersectables.size() < 2 || deep--  <= 0)
            return;
        int range = 2;

        Geometries[] geometriesArr = new Geometries[range];
        for (int i = 0; i < range; i++) {
            geometriesArr[i] = new Geometries();
        }
        for (var geo : this.intersectables){
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
    double minimum(double... numbers){
        double min = numbers[0];
        for (var mm : numbers){
            min = Math.min(min, mm);
        }
        return min;
    }
  /*  public void createTreeRec(int depthOfTree) {

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
    }*/
}
