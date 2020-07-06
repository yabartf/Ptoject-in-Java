package geometries;

import primitives.Color;
import primitives.Material;

/**
 * abstract class that defined ol geometries that ar dadial
 */
public abstract class RadialGeometry extends Geometry {
    double _radius;

    /***************constructors***************/
    /**
     * copy constructor
     * @param other
     */
    public RadialGeometry(RadialGeometry other) {
        this._radius = other._radius;
    }

    /**
     *
     * @param _emmission the color of the geometry
     * @param _matirial of the geometry
     * @param _radius of the geometry
     */
    public RadialGeometry(Color _emmission, Material _matirial, double _radius) {
        super(_emmission, _matirial);
        if(_radius<0)
            throw new IllegalArgumentException("radius cant be less then 0");
        this._radius = _radius;
    }

    /**
     * constructor
     * @param _radius of the geometry
     */
    public RadialGeometry(double _radius) {
       this(Color.BLACK, new Material(0,0,0),_radius);
    }

    /**
     * getter
     * @return
     */
    public double get_radius() {
        return _radius;
    }
}
