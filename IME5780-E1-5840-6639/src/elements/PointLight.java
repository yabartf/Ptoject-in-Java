package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    protected Point3D _pL;
    protected double _kc,_kl,_kq;
    double _radius;
    /**
     * constructor
     * @param emmision the color of the light
     * @param pos the position of the light
     * @param kc
     * @param kl
     * @param kq
     */
    public PointLight(Color emmision,Point3D pos,double kc,double kl,double kq){
    this(emmision, pos, kc, kl, kq, 0);
    }

    /**
     *constractor that generate a point light with size
     *
     * @param emmision the color of the light
     * @param pos the position of the light
     * @param kc
     * @param kl
     * @param kq
     * @param radius of the light
     */
    public PointLight(Color emmision, Point3D pos, double kc, double kl, double kq, double radius){
        super(emmision);
        this._pL=pos;
        this._kc=kc;
        this._kl=kl;
        this._kq=kq;
        this._radius=radius;
    }

    /**
     * calc Intensity
     * @param p point
     * @return the power of the lighr in a point after redusing it
     */
    public Color getIntensity(Point3D p){
        double distSqure = _pL.distanceSquared(p);
        double dist=Math.sqrt(distSqure);
        return _intensity.reduce(_kc+_kl*dist+_kq*distSqure);
    }

    /**
     *
     * @param p point
     * @return the vector from the point light to the point
     */
    public Vector getL(Point3D p){
        try {
            return p.subtract(_pL).normalized();
        }
        catch (IllegalArgumentException e){
            return null;
        }
    }

    /**
     *
     * @param point
     * @return the distance from the point light to the point
     */
    @Override
    public double getDistance(Point3D point) {
        return this._pL.distance(point);
    }

    /**
     *
     * @return the radius of the point light
     */
    public double getRadius() {
        return _radius;
    }

    /**
     *
     * @return the point light direction
     */
    public Point3D get_pL() {
        return _pL;
    }

    public double get_kc() {
        return _kc;
    }

    public double get_kl() {
        return _kl;
    }

    public double get_kq() {
        return _kq;
    }
}
