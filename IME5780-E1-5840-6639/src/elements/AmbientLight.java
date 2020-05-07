package elements;

import primitives.Color;

public class AmbientLight {
    double  Ka;
    Color _intensity;

    public AmbientLight(double ka, Color Ia) {
        Ka = ka;
        this._intensity = Ia.scale(Ka);
    }

    public Color getIntensity(){
        return _intensity;
    }

}
