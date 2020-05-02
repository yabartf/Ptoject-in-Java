package geometries;

import primitives.Point3D;
import primitives.Ray;
import static java.util.Collection.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Geometries implements Intersectable {
   List<Intersectable> intersectables = new ArrayList<>();

    public Geometries(List<Intersectable> intersectables) {
        this.intersectables = intersectables;
    }

    public Geometries(Intersectable... _geometries){
            add(_geometries);
    }

    public void add(Intersectable... _geometries){
            this.intersectables.addAll(Arrays.asList(_geometries));
    }

    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        List<Point3D> answer = new ArrayList<>();
        for (var shape: intersectables) {
            if(shape.findIntsersections(ray)!=null)
                  answer.addAll(shape.findIntsersections(ray));
        }
        if(answer.size() > 0)
            return answer;
        return null;
    }
}
