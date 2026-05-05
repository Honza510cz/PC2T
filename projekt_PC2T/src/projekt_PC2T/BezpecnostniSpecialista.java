package projekt_PC2T;

public class BezpecnostniSpecialista extends Zamestnanec {

    public BezpecnostniSpecialista(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public void vykonejDovednost() {

        int pocet = getSpolupracovnici().size();

        if (pocet == 0) {
            System.out.println("Zadni spolupracovnici.");
            return;
        }

        int suma = 0;

        for (Spoluprace s : getSpolupracovnici()) {

            String k = s.getKvalita().toLowerCase().trim();

            if (k.equals("spatna")) {
                suma += 3;
            }
            else if (k.equals("prumerna")) {
                suma += 2;
            }
            else if (k.equals("dobra")) {
                suma += 1;
            }
        }

        double prumer = (double) suma / pocet;
        double riziko = prumer * pocet;

        System.out.println("Pocet spolupracovniku: " + pocet);
        System.out.println("Prumerna kvalita: " + prumer);
        System.out.println("Rizikove skore: " + riziko);
    }
}