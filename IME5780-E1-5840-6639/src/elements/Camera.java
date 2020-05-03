package elements;

import primitives.*;

public class Camera {
    Point3D location;
    Vector Vto;
    Vector Vup;
    Vector Vright;

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
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @param screenDistance
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance,
                                         double screenWidth, double screenHeight){
        Point3D center = location.add(this.Vto.scale(screenDistance));
        double ratioY = screenHeight / nY;
        double ratioX = screenWidth / nX;
        double yi = (i-nY/2d) * ratioY + ratioY / 2;
        double xj = (j-nX/2d) * ratioX + ratioX / 2;
        Point3D p_i_j = center;
        if(xj!=0)p_i_j = p_i_j.add(Vright.scale(xj));
        if(yi!=0)p_i_j = p_i_j.add(Vup.scale(-yi));
        Ray ray= new Ray(p_i_j.subtract(location), location);
        return ray;
    }


    public Vector getVright() {
        return Vright;
    }

    public Vector getVup() {
        return Vup;
    }

    public Vector getVto() {
        return Vto;
    }
}
