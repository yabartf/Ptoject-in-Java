package elements;

import primitives.Color;

public class AmbientLight {
    Color Ia;
    double  Ka;
    Color _intensity;

    public AmbientLight(Color ia, double ka, Color _intensity) {
        Ia = ia;
        Ka = ka;
        this._intensity = ia.reduce(ka);
    }

    Color GetIntensity(){
        return _intensity;
    }

}
