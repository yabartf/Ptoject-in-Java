package scene;

import elements.*;
import geometries.*;
import primitives.*;

import java.util.ArrayList;

public class Scene {
    String _name;
    Color _background;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double _distance;

    public Scene(String _name) {

        this._name = _name;
        _geometries=new Geometries();
    }

    public String getName() {
        return _name;
    }

    public java.awt.Color getBackground() {
        return _background.getColor();
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

    public double getDistance() {
        return _distance;
    }

    public void setBackground(Color _background) {
        this._background = _background;
    }

    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    public void addGeometries(Intersectable ...geometries){
        _geometries.add(geometries);
    }
}
