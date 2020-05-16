package geometries;
import primitives.*;



public abstract class Geometry implements Intersectable {
    abstract Vector getNormal(Point3D point);
    protected Color _emmission;

    public Geometry(Color emmission) {
        this._emmission = emmission;
    }

    public Geometry() {
        this._emmission=Color.BLACK;
    }

    /***
     *
     * @return
     */

    public Color get_emmission() {
        return _emmission;
    }

}
