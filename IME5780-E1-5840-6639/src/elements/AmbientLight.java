package elements;

import primitives.Color;

public class AmbientLight {
    double  Ka;
    Color _intensity;

    public AmbientLight(Color Ia,double ka) {
        Ka = ka;
        this._intensity = Ia.scale(Ka);
    }

    public Color getIntensity(){
        return _intensity;
    }

}
