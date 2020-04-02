package geometries;

public abstract class RadialGeometry {
    double _radius;

    /***************constructors***************/
    public RadialGeometry(RadialGeometry other) {
        this._radius = other._radius;
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
