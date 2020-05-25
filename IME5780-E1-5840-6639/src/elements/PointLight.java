package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    protected Point3D _pL;
    protected double _kc,_kl,_kq;

    /**
     * constructor
     * @param emmision
     * @param pos
     * @param kc
     * @param kl
     * @param kq
     */
    public PointLight(Color emmision,Point3D pos,double kc,double kl,double kq){
        super(emmision);
        this._pL=pos;
        this._kc=kc;
        this._kl=kl;
        this._kq=kq;
    }

    /**
     * calc Intensity
     * @param p
     * @return
     */
    public Color getIntensity(Point3D p){
        double distSqure=_pL.distanceSquared(p);
        double dist=Math.sqrt(distSqure);
        return _intensity.reduce(_kc+_kl*dist+_kq*distSqure);
    }
    public Vector getL(Point3D p){
        try {
            return p.subtract(_pL).normalized();
        }
        catch (IllegalArgumentException e){
            return null;
        }
    }
}
