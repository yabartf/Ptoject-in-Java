package elements;

import primitives.Color;

abstract class Light {
    protected Color _intensity;

    /**
     * constructor
     * @param intensity
     */
    public Light(Color intensity){
        this._intensity=intensity;
    }
    /********************getter********************/
    public Color getIntensity(){
        return _intensity;
    }

}
