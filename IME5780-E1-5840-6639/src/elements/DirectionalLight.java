package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    private Vector _direction;

    /**
     * constructor
     * @param intensity of the light
     * @param direction of the light
     */
    public DirectionalLight(Color intensity,Vector direction){
        super(intensity);
        this._direction=direction.normalized();
    }
        /****************getters***************/
    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    @Override
    public Vector getL(Point3D p) {
        return this._direction;
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }

    public double getRadius() {
        return 0;
    }
}
