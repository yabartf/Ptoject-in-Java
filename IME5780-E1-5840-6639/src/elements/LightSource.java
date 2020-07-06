package elements;
import primitives.*;

/**
 * an intrface for all light soucrce withe
 * get L: get the direction of the light
 * get intensity: get the power of the light to a point
 * get radius: get the radius of the source light
 * get distance: get the distance of the lighr from a point
 */
public interface LightSource {
    public Color getIntensity(Point3D p);
    public Vector getL(Point3D p);
    public double getRadius();
    double getDistance(Point3D point);

}
