package projekt_PC2T;

public class DatovyAnalytik extends Zamestnanec {

    public DatovyAnalytik(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public void vykonejDovednost() {
        System.out.println("Analyzuji spolupracovniky...");
    }
}