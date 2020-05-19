package geometries;
import primitives.*;



public abstract class Geometry implements Intersectable {

    protected Color _emmission;
    protected Material _matirial;


    public Geometry(Color _emmission, Material _matirial) {
        this._emmission = _emmission;
        this._matirial = _matirial;
    }

    public Geometry() {
        this(Color.BLACK, new Material(0,0,0));
    }
    public Geometry(Color emmission)
    {
        this(emmission,new Material(0,0,0));
    }

    /***
     *
     * @return
     */
    public abstract Vector getNormal(Point3D point);


    public Material get_matirial() {
        return _matirial;
    }

    public Color get_emmission() {
        return _emmission;
    }

}
