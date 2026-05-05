package projekt_PC2T;

public class DatovyAnalytik extends Zamestnanec {

    public DatovyAnalytik(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public void vykonejDovednost() {

        if (getSpolupracovnici().size() == 0) {
            System.out.println("Zadni spolupracovnici.");
            return;
        }

        Zamestnanec nej = null;
        int max = 0;

        for (Spoluprace s1 : getSpolupracovnici()) {

            Zamestnanec kolega = s1.getKolega();
            int spolecni = 0;

            for (Spoluprace s2 : getSpolupracovnici()) {
                for (Spoluprace s3 : kolega.getSpolupracovnici()) {

                    if (s2.getKolega().getId() == s3.getKolega().getId()) {
                        spolecni++;
                    }
                }
            }

            if (spolecni > max) {
                max = spolecni;
                nej = kolega;
            }
        }

        if (nej != null) {
            System.out.println("Nejvice spolecnych spolupracovniku ma s: "
                    + nej.getJmeno() + " " + nej.getPrijmeni());
            System.out.println("Pocet: " + max);
        } else {
            System.out.println("Nenalezeno.");
        }
    }
}