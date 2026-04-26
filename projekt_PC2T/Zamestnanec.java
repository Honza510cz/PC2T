package projekt_PC2T;

import java.util.ArrayList;

public abstract class Zamestnanec {

    private int id;
    private String jmeno;
    private String prijmeni;
    private int rokNarozeni;
    private ArrayList<Spoluprace> spolupracovnici;

    public Zamestnanec(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
        this.spolupracovnici = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public int getRokNarozeni() {
        return rokNarozeni;
    }
    
    public void pridejSpolupraci(Zamestnanec kolega, String kvalita) {
        Spoluprace s = new Spoluprace(kolega, kvalita);
        spolupracovnici.add(s);
    }
    
    public void vypisSpolupracovniky() {
        for (Spoluprace s : spolupracovnici) {
            System.out.println(s);
        }
    }
    
    public int pocetSpolupracovniku() {
        return spolupracovnici.size();
    }
    
    @Override
    public String toString() {
        return "ID: " + id +
                ", Jméno: " + jmeno +
                ", Příjmení: " + prijmeni +
                ", Rok narození: " + rokNarozeni;
    }
    
    public abstract void vykonejDovednost();
}