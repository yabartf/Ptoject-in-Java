package elements;

import primitives.*;



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
        this._direction = direction.normalized();
    }

        /****************getters****************/
    @Override
    public Color getIntensity(Point3D p) {
        Vector l=getL(p);
        if(l==null)
            l=_direction;
        double cos=Util.alignZero(l.dotProduct(_direction));
        return super.getIntensity(p).scale(Math.max(0,cos));


    }

}
