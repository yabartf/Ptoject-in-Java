package elements;

import primitives.*;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;

public class Camera {
    Point3D _location;
    Vector _Vto;
    Vector _Vup;
    Vector _Vright;
    double _irisSize;

    /**
     * constructor
     * @param location
     * @param to
     * @param up
     */
    public Camera(Point3D location, Vector to, Vector up) {
        if (Util.alignZero(up.dotProduct(to))!=0)
            throw new IllegalArgumentException("vectors are not vartical");
        _Vup =up.normalized();
        _Vto =to.normalized();
        _Vright =(to.crossProduct(up)).normalized();
        this._location = location;
    }

    public Camera(Point3D location, Vector to, Vector up,double irisSize){
        this(location,to,up);
        this._irisSize=irisSize;
    }
    /***
     *
     * @param nX number of width pixels
     * @param nY number of height pixels
     * @param j the index that the ray go through
     * @param i the index that the ray go through
     * @param screenDistance the distance of the screen from the camera
     * @param screenWidth the width of the screen
     * @param screenHeight the hight of the screen
     * @return the ray prom the camera through the wanted pixel
     */
    public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight){
        Point3D center= _location.add(this._Vto.scale(screenDistance));//the center is the point that towads the camera
        double ratioY=screenHeight/nY;//size of each pixel
        double ratioX=screenWidth/nX;//size of each pixel
        double yi=(i-nY/2d)*ratioY+ratioY/2;//the distance of the center of the pixle from the center of the screen
        double xj=(j-nX/2d)*ratioX+ratioX/2;
        Point3D p_i_j=center;
        if(xj!=0)p_i_j=p_i_j.add(_Vright.scale(xj));//set yhe point in the center of the wanted pixel
        if(yi!=0)p_i_j=p_i_j.add(_Vup.scale(-yi));
        return new Ray(p_i_j.subtract(_location), _location);
    }


    public List<Ray> constractImageFocusRay(Point3D pointInViewPlane, Point3D pointInFocalPlane, int numOfBeamRays){
        List<Ray> beamRays=new LinkedList<>();
        for(int i = 0;i < numOfBeamRays-1;i++){
            Point3D point=pointInViewPlane.add(_Vright.scale(Math.random()*(_irisSize/2)*Math.pow(-1,i)));
            point=point.add(_Vright.scale(Math.random()*(_irisSize/2)*Math.pow(-1,i)));
            beamRays.add(new Ray(pointInFocalPlane.subtract(point),point));
        }
        return beamRays;
    }

            /**************getters******************/
    public Vector get_Vright() {
        return _Vright;
    }

    public Vector get_Vup() {
        return _Vup;
    }

    public Vector get_Vto() {
        return _Vto;
    }

    public Point3D get_location() { return _location; }
}
