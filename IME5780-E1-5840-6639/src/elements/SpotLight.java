package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;


public class SpotLight extends PointLight {
    private Vector _direction;
    private double _narrowBeam;

    /**
     *  constructor
     * @param emmision
     * @param position
     * @param direction
     * @param Kc
     * @param Kl
     * @param Kq
     */
    public SpotLight(Color emmision, Point3D position, Vector direction, double Kc, double Kl, double Kq){
        super(emmision,position,Kc,Kl,Kq);
        this._direction = direction;
    }

        /****************getters****************/
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity(p).scale(Math.max(0,_direction.dotProduct(p.subtract(this._pL))));
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
