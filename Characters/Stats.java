package Characters;

import java.io.Serializable;

public class Stats implements Serializable {
    private static final long serialVersionUID = 1L;

    public int ATK;
    public int HEP;
    public int DEF;
    public int SPD;
    public int CRD;
    public int CRR;

    /**
     * Stats, this will be used throughout the entire game. ENTIRELY based on Honkai
     * starrail/genshin
     * 
     * @param atk
     * @param hep
     * @param def
     * @param spd
     * @param crd
     * @param crr
     */
    public Stats(int atk, int hep, int def, int spd, int crd, int crr) {
        this.ATK = atk;
        this.HEP = hep;
        this.DEF = def;
        this.SPD = spd;
        this.CRD = crd;
        this.CRR = crr;
    }

    // Hopefully this will carry the best operations in this game
    public void add(Stats rhs) {
        // Man fuck, I would've done offsetof() in c++ or std::begin for easier matrix
        // programming
        this.ATK += rhs.ATK;
        this.HEP += rhs.HEP;
        this.DEF += rhs.DEF;
        this.SPD += rhs.SPD;
        this.CRD += rhs.CRD;
        this.CRR += rhs.CRR;
    }

    public void minus(Stats rhs) {
        // Man fuck, I would've done offsetof() in c++ or std::begin for easier matrix
        // programming
        this.ATK -= rhs.ATK;
        this.HEP -= rhs.HEP;
        this.DEF -= rhs.DEF;
        this.SPD -= rhs.SPD;
        this.CRD -= rhs.CRD;
        this.CRR -= rhs.CRR;
    }

    public static final Stats baseStat = new Stats(200, 250, 10, 80, 100, 5);
}
