package scene;

import elements.*;
import geometries.*;
import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Scene {
    String _name;
    Color _background;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double viewPlaneDistance;
    double focalPlaneDistance;  //distance from view plane
    List <LightSource> _lights = new LinkedList<LightSource>();



    /**
     * add lights
     * @param lights
     */
    public  void addLights(LightSource... lights){
        _lights.addAll(List.of(lights));
    }

    /**
     * writeToImage
     * @param _name
     */
    public Scene(String _name) {

        this._name = _name;
        _geometries=new Geometries();
    }

    /******************getters******************/

    public List<LightSource> get_lights() {
        return _lights;
    }

    public String getName() {
        return _name;
    }

    public Color getBackground() {
        return _background;
    }

    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public Geometries getGeometries() {
        return _geometries;
    }

    public Camera getCamera() {
        return _camera;
    }

    public double getViewPlaneDistance() {
        return viewPlaneDistance;
    }
    public double getFocalPlaneDistance() { return focalPlaneDistance; }


        /************************setters******************/

    public void setBackground(Color _background) {
        this._background = _background;
    }

    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    public void setViewPlaneDistance(double _distance) {
        this.viewPlaneDistance = _distance;
    }

    public void setFocalPlaneDistance(double focalPlaneDistance) { this.focalPlaneDistance = focalPlaneDistance; }

    /**
     * add geometries
     * @param geometries
     */
    public void addGeometries(Intersectable ...geometries){
        _geometries.add(geometries);
    }
    public void addArreyGeometries(Intersectable[] geometries){
        _geometries.addArrey(geometries);
    }

}
