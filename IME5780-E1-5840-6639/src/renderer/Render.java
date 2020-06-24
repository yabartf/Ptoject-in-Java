package renderer;
import elements.Camera;
import elements.LightSource;
import elements.PointLight;
import elements.SpotLight;
import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

public class Render {
    ImageWriter _imageWriter;
    Scene _scene;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;



    /**
     * constructor
     * @param _imageWriter
     * @param _scene
     */
    public Render(ImageWriter _imageWriter, Scene _scene){
        this._imageWriter=_imageWriter;
        this._scene=_scene;
    }

    /**
     * renderImage
     */
    private int _threads = 1;
    private final int SPARE_THREADS = 2;
    private boolean _print = false;

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     * @author Dan
     *
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         *  Default constructor for secondary Pixel objects
         */
        public Pixel() {}

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print)
                    System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage(int numOfFocusRays, int numOfShadowRays) {
        final int nX = _imageWriter.getNx();
        final int nY = _imageWriter.getNy();
        final double dist = _scene.getViewPlaneDistance();
        final double width = _imageWriter.getWidth();
        final double height = _imageWriter.getHeight();
        final Camera camera = _scene.getCamera();

        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    List<Ray> focusBeam=new LinkedList<>();
                    focusBeam.add(camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, //
                            dist, width, height));
                    focusBeam.addAll(focusRays(camera,focusBeam.get(0),numOfFocusRays));
                    Color color=calcColor(focusBeam,_scene.getBackground(),numOfShadowRays);
                    List<GeoPoint> closestPoints = findClosestIntersection(focusBeam);
                    if (closestPoints != null) {
                        _imageWriter.writePixel(pixel.col, pixel.row, color.getColor());
                    } else _imageWriter.writePixel(pixel.col, pixel.row, _scene.getBackground().getColor());

                }
            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
        if (_print) System.out.printf("\r100%%\n");
    }
     public void renderImage(){
     renderImage(1,1);
     }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }


    /**
     * calc color
     * @param intersection
     * @return
     */
    private Color calcColor(GeoPoint intersection,Ray inRay,int level,double k, int numOfShadowRays) {
        if(level==0)
            return Color.BLACK;

        Color color=intersection.geometry.get_emmission();
        Vector v = intersection.point.subtract(_scene.getCamera().get_location()).normalize();
        Vector n = intersection.geometry.getNormal(intersection.point);
        int nShininess = intersection.geometry.get_matirial().getnShininess();
        double kd = intersection.geometry.get_matirial().getKd();
        double ks = intersection.geometry.get_matirial().getKs();
        for (LightSource lightSource : _scene.get_lights()) {
            Vector l = lightSource.getL(intersection.point);
            double n1 = n.dotProduct(l);
            double n2 = n.dotProduct(v);
            if ((n1 > 0 && n2 > 0) || (n1 < 0 && n2 < 0)) {
                double ktr=transparency(lightSource,l, n,intersection,numOfShadowRays);
                if(ktr*k>MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
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
                color=color.add(calcColor(reflectedPoint,reflectedRay,level-1,kkr, numOfShadowRays).scale(kr));
        }
        double kt=intersection.geometry.get_matirial().getKt(), kkt=k*kt;
        if(kkt>MIN_CALC_COLOR_K) {
            Ray refractedRay = constractRefractedRay(intersection.point, inRay);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if(refractedPoint!=null)
                color=color.add(calcColor(refractedPoint,refractedRay,level-1,kkt, numOfShadowRays).scale(kt));
        }

        return color;
    }
    private Color calcColor(GeoPoint intersection,Ray ray, int numOfShadowRays){
        return calcColor(intersection,ray,MAX_CALC_COLOR_LEVEL,1.0, numOfShadowRays).add(
                _scene.getAmbientLight().getIntensity());
    }

    private Color calcColor(List<Ray> rays,Color background, int numOfShadowRays){
        Color color = new Color(0,0,0);
        for(var ray:rays){
            GeoPoint geoPoint=findClosestIntersection(ray);
            if(geoPoint!=null) {
                color= color.add((calcColor(geoPoint, ray,numOfShadowRays)));
            }
            else
                color = color.add(background);
        }
        return (color.reduce(rays.size()));
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
        int nX=_imageWriter.getNx();
        int nY=_imageWriter.getNy();
        for (int i = 0; i <nY ; i++) {
            for (int j = 0; j <nX ; j++) {
                if(j % interval==0||i % interval==0 )
                    _imageWriter.writePixel(j,i,color);
            }
        }
    }

    /**
     * write to image
     */
    public void writeToImage(){_imageWriter.writeToImage();}


    private double transparency(LightSource light,Vector l, Vector n, GeoPoint geoPoint){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(lightDirection,n,geoPoint.point);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay,light.getDistance(geoPoint.point));
        double shdow=1;
        if(intersections==null)
           return shdow;
        for(var intersection : intersections){
            shdow*=intersection.geometry.get_matirial().getKt();
        }
        return shdow;
    }
    private Ray constractReflectedRay(Vector n,Point3D point,Ray inRay){
        return new Ray(inRay.getDirection().substract(n.scale(2*inRay.getDirection().dotProduct(n))),n,point);
    }
    private Ray constractRefractedRay(Point3D point, Ray inRay) {
        return new Ray(inRay.getDirection(),inRay.getDirection(),point);
    }
    private GeoPoint findClosestIntersection(Ray ray){
        if(ray == null)
            return null;

        GeoPoint closesPoint=null;
        double colsesDistance=Double.MAX_VALUE;
        Point3D ray_p0=ray.getPoint();
        List <GeoPoint> intersections=_scene.getGeometries().findIntersections(ray);
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
    private List<GeoPoint> findClosestIntersection(List<Ray> rays){
        List<GeoPoint> geoPoints = new LinkedList<>();
        for(var ray:rays){
            GeoPoint geoPoint = (findClosestIntersection(ray));
            if(geoPoint != null)
                geoPoints.add(geoPoint);
        }
        if(geoPoints.size()==0)
            return null;
        return geoPoints;
    }

    private List<Ray> focusRays(Camera camera,Ray ray, int numOfBeamRays){
        Plane viewPlane = new Plane(camera.get_Vto(),camera.get_location().add(camera.get_Vto().scale(_scene.getViewPlaneDistance())));
        Point3D pointInViewPlane = viewPlane.findIntersections(ray).get(0).point;
        Plane focalPlane = new Plane(camera.get_Vto(),camera.get_location().add(camera.get_Vto().scale(_scene.getFocalPlaneDistance()+_scene.getViewPlaneDistance())));
        Point3D pointInFocalPlane = focalPlane.findIntersections(ray).get(0).point;
        List<Ray> focusRays= getRandomRays(pointInViewPlane,pointInFocalPlane,camera.get_irisSize(),numOfBeamRays,camera.get_Vright(),camera.get_Vup());//getRandomRays(pointInViewPlane, pointInFocalPlane, r, numOfBeamRays);
        return focusRays;
    }
    private Vector normalToRay(Vector vec){
        double x = vec.getPoint().get_x(), y = vec.getPoint().get_y(), z = vec.getPoint().get_z();
        if(x < y && x < z)
            return new Vector( new Point3D(0, vec.getPoint().get_z()* -1 , vec.getPoint().get_y())).normalize();
        if(y < x && y < z)
            return new Vector( new Point3D(vec.getPoint().get_z()* -1, 0, vec.getPoint().get_x())).normalize();
        return new Vector( new Point3D(vec.getPoint().get_y()* -1, vec.getPoint().get_x(), 0)).normalize();
    }
//    private Ray getRandomRay(Vector sourceRay, Vector vecX, int r, PointLight light ) {
//
//        Random rand = new Random();
//        Vector vecY = vecX.crossProduct(sourceRay.getDirection()).normalize();
//        double cos = Math.pow(Math.random(),rand.nextInt(10));
//        double sin = Math.sqrt(1 - Math.pow(cos,2));
//        double d = Math.pow(Math.random()*r,rand.nextInt(10));
//        Point3D pc = light.get_pL().add(light.get_pL().subtract(sourceRay.getPoint()));
//        Point3D point = new Point3D(pc.add(vecX.scale(cos*d).add(vecY.scale(sin*d))));
//        return new Ray(point.subtract(light.get_pL()).normalize() ,sourceRay.getPoint());
//    }getPoint

    private List<Ray> getRandomRays(Point3D sourcePoint, Point3D point, double r, int numOfRays,Vector vx, Vector vy) {
        List <Ray> rayBeam = new LinkedList<>();
        for(int i = 0;i < numOfRays - 1;i++) {
            Point3D p = sourcePoint;
            double cosTeta = Math.random() * Math.pow(-1,i);
            double sinTeta = Math.sqrt(1 - cosTeta * cosTeta);
            double d = Math.random() * r * Math.pow(-1,i);
            double x = d * cosTeta;
            double y = d * sinTeta;
            if (!Util.isZero(x)) p = p.add(vx.scale(x));
            if (!Util.isZero(y)) p = p.add(vy.scale(y));
            rayBeam.add(new Ray(point.subtract(p), p));
        }
        return rayBeam;
    }

    private List<Ray> getRandomShadowRays(Point3D sourcePoint, Point3D point, double r, int numOfRays){
        Vector v = point.subtract(sourcePoint).normalize();
        Vector vx = normalToRay(v);
        Vector vy = v.crossProduct(vx);
        return getRandomRays(sourcePoint, point, r, numOfRays, vx, vy);
    }

    private double transparency(LightSource light,Vector l,Vector n, GeoPoint intersectionPoint, int numOfRays) {
        double shadow = transparency(light, l, n, intersectionPoint);
        Point3D p;
        if (light.getClass() == PointLight.class || light.getClass() == SpotLight.class) {
            p = ((PointLight) light).get_pL();
            List<Ray> shadowRays = getRandomShadowRays(intersectionPoint.point, p, light.getRadius(), numOfRays);
            for (var ray : shadowRays) {
                shadow += transparency(light, ray.getDirection().scale(-1), n, intersectionPoint);
            }
        }
        return shadow / numOfRays;
    }
}
