package elements;

import primitives.Color;

public class AmbientLight extends Light{
    double  Ka;

    /**
     * constructor
     * @param Ia the original color
     * @param ka the power of the color
     */
    public AmbientLight(Color Ia,double ka) {
        super(Ia.scale(ka));
        Ka = ka;
    }
}
