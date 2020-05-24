package primitives;

public class Material {
    double Kd;
    double Ks;
    int nShininess;

    /**
     * constructor
     * @param kd
     * @param ks
     * @param nShininess
     */
    public Material(double kd, double ks, int nShininess) {
        Kd = kd;
        Ks = ks;
        this.nShininess = nShininess;
    }

    /********************getters**********************/

    public double getKd() {
        return Kd;
    }

    public double getKs() {
        return Ks;
    }

    public int getnShininess() {
        return nShininess;
    }
}
