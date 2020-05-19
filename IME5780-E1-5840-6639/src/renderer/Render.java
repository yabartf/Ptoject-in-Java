package renderer;
import elements.*;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import scene.*;
import primitives.Point3D;

import java.util.List;

public class Render {
    ImageWriter imageWriter;
    Scene scene;
    public Render(ImageWriter _imageWriter, Scene _scene){
        this.imageWriter=_imageWriter;
        this.scene=_scene;
    }

    public void renderImage(){

        Camera camera=scene.getCamera();
        Intersectable geometries=scene.getGeometries();
        java.awt.Color background=scene.getBackground();
        int nX=imageWriter.getNx();
        int nY=imageWriter.getNy();
        double distance=scene.getDistance();
        double width=imageWriter.getWidth();
        double height=imageWriter.getHeight();
        for (int i = 0; i <nY ; i++) {
            for (int j = 0; j <nX ; j++) {
                Ray ray=camera.constructRayThroughPixel(nX,nY,j,i,distance,width,height);
                List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null)
                imageWriter.writePixel(j, i, background);
                else {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
                }
            }
        }

    }


     private Color calcColor(GeoPoint p){
        return p.geometry.get_emmission();
    }
    public GeoPoint getClosestPoint(List<GeoPoint> geoPointsList){
        GeoPoint closesPoint=null;
        Point3D cameraLocation=scene.getCamera().getLocation();
        double closesDistance=Double.MAX_VALUE;
        for (var geoPoint:geoPointsList) {
            double distance=geoPoint.point.distance(cameraLocation);
            if (distance < closesDistance){
                closesDistance = distance;
                closesPoint = geoPoint;
            }
        }
        return closesPoint;
    }
    public void printGrid(int interval, java.awt.Color color){
        int nX=imageWriter.getNx();
        int nY=imageWriter.getNy();
        for (int i = 0; i <nY ; i++) {
            for (int j = 0; j <nX ; j++) {
                if(j % interval==0||i % interval==0 )
                    imageWriter.writePixel(j,i,color);
            }
        }
    }
    public void writeToImage(){imageWriter.writeToImage();}
}
