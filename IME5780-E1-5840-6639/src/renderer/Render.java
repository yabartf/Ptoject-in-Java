package renderer;
import elements.*;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.*;

import java.util.List;
import java.util.logging.Level;

import static primitives.Util.alignZero;

public class Render {
    ImageWriter imageWriter;
    Scene scene;
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


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
                GeoPoint closestPoint = findClosestIntersection(ray);
                if (closestPoint == null)
                    imageWriter.writePixel(j, i, background);
                else {
                    imageWriter.writePixel(j, i, calcColor(closestPoint,ray).getColor());
                }
            }
        }

    }

    /**
     * calc color
     * @param intersection
     * @return
     */
    private Color calcColor(GeoPoint intersection,Ray inRay,int level,double k) {
        if(level==0)
            return Color.BLACK;

        Color color=intersection.geometry.get_emmission();
        Vector v = intersection.point.subtract(scene.getCamera().getLocation()).normalize();
        Vector n = intersection.geometry.getNormal(intersection.point);
        int nShininess = intersection.geometry.get_matirial().getnShininess();
        double kd = intersection.geometry.get_matirial().getKd();
        double ks = intersection.geometry.get_matirial().getKs();
        for (LightSource lightSource : scene.get_lights()) {
            Vector l = lightSource.getL(intersection.point);
            double n1 = n.dotProduct(l);
            double n2 = n.dotProduct(v);
            if ((n1 > 0 && n2 > 0) || (n1 < 0 && n2 < 0)) {
                if(unshaded(lightSource,l, n,intersection)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffusive(kd, alignZero(n.dotProduct(l)), lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        double kr=intersection.geometry.get_matirial().getKr(), kkr=k*kr;
        if(kkr>MIN_CALC_COLOR_K) {
            Ray reflectedRay = constractReflectedRay(n, intersection.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if(reflectedPoint!=null)
                color=color.add(calcColor(reflectedPoint,reflectedRay,level-1,kkr).scale(kr));
        }
        double kt=intersection.geometry.get_matirial().getKt(), kkt=k*kt;
        if(kkr>MIN_CALC_COLOR_K) {
            Ray refrectedRay = constractRefrectedRay(intersection.point, inRay);
            GeoPoint refrectedPoint = findClosestIntersection(refrectedRay);
            if(refrectedPoint!=null)
                color=color.add(calcColor(refrectedPoint,refrectedRay,level-1,kkt).scale(kt));
        }

        return color;
    }
    private Color calcColor(GeoPoint intersection,Ray ray){
        return calcColor(findClosestIntersection(ray),ray,MAX_CALC_COLOR_LEVEL,1.0).add(
                scene.getAmbientLight().getIntensity());
    }

    private Color calcDiffusive(double kd, double nl, Color ip) {
        return ip.scale(Math.abs(nl) * kd);
    }

    private Color calcSpecular(double ks,Vector l,Vector n,Vector v,int nShininess,Color lightIntensity){
        Vector r=l.substract(n.scale(2).scale(l.dotProduct(n)));
        return lightIntensity.scale(ks*Math.pow(Math.max(0,-1*v.dotProduct(r)),nShininess));
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


    private boolean unshaded(LightSource light,Vector l, Vector n, GeoPoint geoPoint){
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector DeltaVector = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point3D point = geoPoint.point.add(DeltaVector);
        Ray lightRay = new Ray(lightDirection,point);
        List<GeoPoint> intersections = scene.getGeometries().findIntersections(lightRay,light.getDistance(geoPoint.point));
        double shdow=1;
        if(intersections==null)
            return true;
        for(var intersection : intersections){
            //shdow*=intersection.geometry.get_matirial().getKt();
            if (intersection.geometry.get_matirial().getKt()==0)
                return false;
        }
        return true;
    }
    private Ray constractReflectedRay(Vector n,Point3D point,Ray inRay){
        return new Ray(inRay.getDirection().substract(n.scale(2*inRay.getDirection().dotProduct(n))),point);
    }
    private Ray constractRefrectedRay(Point3D point,Ray inRay) {
        return new Ray(inRay.getDirection(),point);
    }
    private GeoPoint findClosestIntersection(Ray ray){
        if(ray == null)
            return null;

        GeoPoint closesPoint=null;
        double colsesDistance=Double.MAX_VALUE;
        Point3D ray_p0=ray.getPoint();
        List <GeoPoint> intersections=scene.getGeometries().findIntersections(ray);
        if(intersections == null)
            return null;

        for (var geoPoint:intersections) {
            double distance=ray_p0.distance(geoPoint.point);
            if (distance < colsesDistance){
                colsesDistance = distance;
                closesPoint = geoPoint;
            }
        }
        return closesPoint;
    }
}
