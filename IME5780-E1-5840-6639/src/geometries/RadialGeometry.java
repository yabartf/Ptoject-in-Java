package geometries;

import primitives.Color;
import primitives.Material;

public abstract class RadialGeometry extends Geometry {
    double _radius;

    /***************constructors***************/
    public RadialGeometry(RadialGeometry other) {
        this._radius = other._radius;
    }

    public RadialGeometry(Color _emmission, Material _matirial, double _radius) {
        super(_emmission, _matirial);
        if(_radius<0)
            throw new IllegalArgumentException("radius cant be less then 0");

        this._radius = _radius;
    }

    public RadialGeometry(double _radius) {
        if(_radius<0)
            throw new IllegalArgumentException("radius cant be less then 0");
        this._radius = _radius;
    }

    public double get_radius() {
        return _radius;
    }
}
