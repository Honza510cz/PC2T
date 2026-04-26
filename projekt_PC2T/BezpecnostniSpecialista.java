package projekt_PC2T;

public class BezpecnostniSpecialista extends Zamestnanec {

    public BezpecnostniSpecialista(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public void vykonejDovednost() {
        System.out.println("Vyhodnocuji riziko spoluprace...");
    }
}