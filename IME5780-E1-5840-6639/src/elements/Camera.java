package elements;

import primitives.*;

public class Camera {
    Point3D location;
    Vector Vto;
    Vector Vup;
    Vector Vright;

    /**
     * constructor
     * @param location
     * @param up
     * @param to
     */
    public Camera(Point3D location,Vector up,Vector to) {
        if (up.dotProduct(to)!=0)
            throw new IllegalArgumentException("vectors are not vartical");
        Vup=up.normalized();
        Vto=to.normalized();
        Vright=(to.crossProduct(up)).normalized();
        this.location = location;
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
        Point3D center=location.add(this.Vto.scale(screenDistance));//the center is the point that towads the camera
        double ratioY=screenHeight/nY;//size of each pixel
        double ratioX=screenWidth/nX;//size of each pixel
        double yi=(i-nY/2d)*ratioY+ratioY/2;//the distance of the center of the pixle from the center of the screen
        double xj=(j-nX/2d)*ratioX+ratioX/2;
        Point3D p_i_j=center;
        if(xj!=0)p_i_j=p_i_j.add(Vright.scale(xj));//set yhe point in the center of the wanted pixel
        if(yi!=0)p_i_j=p_i_j.add(Vup.scale(-yi));
        return new Ray(p_i_j.subtract(location),location);
    }

            /**************getters******************/
    public Vector getVright() {
        return Vright;
    }

    public Vector getVup() {
        return Vup;
    }

    public Vector getVto() {
        return Vto;
    }

    public Point3D getLocation() { return location; }
}
