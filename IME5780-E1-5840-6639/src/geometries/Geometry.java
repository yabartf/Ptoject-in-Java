package geometries;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public abstract class Geometry implements Intersectable {
    public abstract Vector getNormal(Point3D point);

    public Geometry() {
        _emmission = Color.BLACK;
    }

    public Geometry(Color _emmission) {
        this._emmission = _emmission;
    }

    public Color get_emmission() {
        return _emmission;
    }

    protected Color _emmission;
}
