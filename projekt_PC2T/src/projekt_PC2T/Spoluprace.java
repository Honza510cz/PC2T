package projekt_PC2T;

public class Spoluprace {

    private Zamestnanec kolega;
    private String kvalita; // "spatna", "prumerna", "dobra"

    public Spoluprace(Zamestnanec kolega, String kvalita) {
        this.kolega = kolega;
        this.kvalita = kvalita;
    }

    public Zamestnanec getKolega() {
        return kolega;
    }

    public String getKvalita() {
        return kvalita;
    }

    @Override
    public String toString() {
        return kolega.getJmeno() + " " + kolega.getPrijmeni() +
                " (" + kvalita + ")";
    }
}