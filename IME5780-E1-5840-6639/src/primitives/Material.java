package primitives;

public class Material {
    double Kd;
    double Ks;
    double Kt;
    double Kr;
    int nShininess;

    /**
     * constructor
     * @param kd
     * @param ks
     * @param nShininess
     */
    public Material(double kd, double ks, int nShininess) {
       this(kd,ks,nShininess,0,0);
    }

    public Material(double kd, double ks, int nShininess, double kt, double kr) {
        Kd = kd;
        Ks = ks;
        Kt = kt;
        Kr = kr;
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

    public double getKt() {
        return Kt;
    }

    public double getKr() {
        return Kr;
    }
}
