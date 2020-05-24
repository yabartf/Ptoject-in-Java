package geometries;
import primitives.*;



public abstract class Geometry implements Intersectable {

    protected Color _emmission;
    protected Material _matirial;

    /**
     * constarctor
     * @param _emmission
     * @param _matirial
     */
    public Geometry(Color _emmission, Material _matirial) {
        this._emmission = _emmission;
        this._matirial = _matirial;
    }

    /**
     * constarctor
     */
    public Geometry() {
        this(Color.BLACK, new Material(0,0,0));
    }

    /**
     * constarctor
     * @param emmission
     */
    public Geometry(Color emmission)
    {
        this(emmission,new Material(0,0,0));
    }

    /**
     * getter
     * @param point
     * @return
     */
    public abstract Vector getNormal(Point3D point);

    /**
     * getter
     * @return
     */

    public Material get_matirial() {
        return _matirial;
    }

    /**
     * getter
     * @return
     */
    public Color get_emmission() {
        return _emmission;
    }

}
