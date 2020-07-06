package geometries;
import primitives.*;


/**
 * a class that define all of the geometries
 * holds the color the matirial and the box of the geometry
 */
public abstract class Geometry implements Intersectable {

    protected final Color _emmission;
    protected final Material _matirial;
    protected Box _box;

    /**
     * constarctor that generate geometry with color and material
     * @param _emmission the color of the geonetry
     * @param _matirial of the geometry
     */

    public Geometry(Color _emmission, Material _matirial) {
        this._emmission = _emmission;
        this._matirial = _matirial;
    }

    /**
     * constarctor
     */
    public Geometry() {
        this(Color.BLACK);
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
     * @return normal to the geometry
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

    @Override
    public Box getBox() {
        return new Box(_box._min, _box._max);
    }

    @Override
    public void BVH(int deep) {
        return;
    }
}
