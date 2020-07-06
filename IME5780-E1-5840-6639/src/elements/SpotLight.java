package elements;

import primitives.*;



public class SpotLight extends PointLight {
    private Vector _direction;
    private double _narrowBeam;

    /**
     * constractor that generate a spot light with size
     * @param emmision the color of the light
     * @param position  of the light
     * @param direction of the light
     * @param Kc
     * @param Kl
     * @param Kq
     * @param radius of the light
     */
    public SpotLight(Color emmision, Point3D position, Vector direction, double Kc, double Kl, double Kq, double radius){
        super(emmision,position,Kc,Kl,Kq,radius);
        this._direction = direction.normalized();
    }
    /**
     *  constructor
     * @param emmision the color of the light
     * @param position  of the light
     * @param direction of the light
     * @param Kc
     * @param Kl
     * @param Kq
     */
    public SpotLight(Color emmision, Point3D position, Vector direction, double Kc, double Kl, double Kq) {
        this(emmision, position, direction, Kc, Kl, Kq, 0);
    }

    /**
     *
     * @param point
     * @return the distace from the camera to a point
     */
    @Override
    public double getDistance(Point3D point) {
        return this._pL.distance(point);
    }

    /****************getters****************/
    /**
     *
     * @param p point
     * @return the power of the light in a point
     */
    @Override
    public Color getIntensity(Point3D p) {
        Vector l=getL(p);
        if(l==null)
            l=_direction;
        double cos=Util.alignZero(l.dotProduct(_direction));
        return super.getIntensity(p).scale(Math.max(0,cos));

    }

}
