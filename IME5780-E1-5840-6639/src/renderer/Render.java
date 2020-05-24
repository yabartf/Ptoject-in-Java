package renderer;
import elements.*;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
import primitives.Vector;
import scene.*;
import primitives.Point3D;

import java.util.List;

public class Render {
    ImageWriter imageWriter;
    Scene scene;

    /**
     * constructor
     * @param _imageWriter
     * @param _scene
     */
    public Render(ImageWriter _imageWriter, Scene _scene){
        this.imageWriter=_imageWriter;
        this.scene=_scene;
    }

    /**
     * renderImage
     */
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

    /**
     * calc color
     * @param intersection
     * @return
     */
    private Color calcColor(GeoPoint intersection) {
        Color color = scene.getAmbientLight().getIntensity();
        color = color.add(intersection.geometry.get_emmission());
        Vector v = intersection.point.subtract(scene.getCamera().getLocation()).normalize();
        Vector n = intersection.geometry.getNormal(intersection.point);
        int nShininess = intersection.geometry.get_matirial().getnShininess();
        double kd = intersection.geometry.get_matirial().getKd();
        double ks = intersection.geometry.get_matirial().getKs();
        for (LightSource lightSource : scene.get_lights()) {
            Vector l = lightSource.getL(intersection.point);
            double n1 = n.dotProduct(l);
            double n2 = n.dotProduct(v);
            if (n1 > 0 && n2 > 0 || n1 < 0 && n2 < 0) {
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }

        }
    }

    /**
     * calc closest point
     * @param geoPointsList
     * @return
     */

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

    /**
     * print grid
     * @param interval
     * @param color
     */
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

    /**
     * write to image
     */
    public void writeToImage(){imageWriter.writeToImage();}
}
