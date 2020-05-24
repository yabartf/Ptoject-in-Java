package elements;

import primitives.Color;

public class AmbientLight extends Light{
    double  Ka;

    /**
     * constructor
     * @param Ia
     * @param ka
     */
    public AmbientLight(Color Ia,double ka) {
        super(Ia.scale(ka));
        Ka = ka;
    }
}
