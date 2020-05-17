package elements;

import primitives.Color;

public class AmbientLight extends Light{
    double  Ka;

    public AmbientLight(Color Ia,double ka) {
        super(Ia.scale(ka));
        Ka = ka;
    }
}
