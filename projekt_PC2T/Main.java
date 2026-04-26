package projekt_PC2T;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Zamestnanec> seznam = new ArrayList<>();

        int volba = 0;
        int id = 1;

        while (volba != 13) {

            System.out.println("\n--- MENU ---");
            System.out.println("1 - Pridat zamestnance");
            System.out.println("2 - Vypsat zamestnance");
            System.out.println("3 - Pridat spolupraci");
            System.out.println("4 - Najit podle ID");
            System.out.println("5 - Spustit dovednost");
            System.out.println("6 - Smazat zamestnance");
            System.out.println("7 - Pocet ve skupinach");
            System.out.println("8 - Abecedni vypis");
            System.out.println("9 - Nejvice vazeb");
            System.out.println("10 - Ulozit do TXT");
            System.out.println("11 - Nacist z TXT");
            System.out.println("12 - Vypsat spolupracovniky zamestnance");
            System.out.println("13 - Konec");
            System.out.print("Volba: ");

            volba = sc.nextInt();
            sc.nextLine();

            switch (volba) {

                case 1:

                    System.out.print("Jmeno: ");
                    String jmeno = sc.nextLine();

                    System.out.print("Prijmeni: ");
                    String prijmeni = sc.nextLine();

                    System.out.print("Rok narozeni: ");
                    int rok = sc.nextInt();
                    sc.nextLine();

                    System.out.println("1 - Datovy analytik");
                    System.out.println("2 - Bezpecnostni specialista");
                    int typ = sc.nextInt();
                    sc.nextLine();

                    if (typ == 1)
                        seznam.add(new DatovyAnalytik(id, jmeno, prijmeni, rok));
                    else
                        seznam.add(new BezpecnostniSpecialista(id, jmeno, prijmeni, rok));

                    id++;
                    System.out.println("Pridano.");
                    break;

                case 2:

                    for (Zamestnanec z : seznam)
                        System.out.println(z);

                    break;

                case 3:

                    System.out.print("ID zamestnance: ");
                    int id1 = sc.nextInt();

                    System.out.print("ID kolegy: ");
                    int id2 = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Kvalita (spatna/prumerna/dobra): ");
                    String kvalita = sc.nextLine();

                    Zamestnanec a = null;
                    Zamestnanec b = null;

                    for (Zamestnanec z : seznam) {
                        if (z.getId() == id1)
                            a = z;

                        if (z.getId() == id2)
                            b = z;
                    }

                    if (a != null && b != null) {
                        a.pridejSpolupraci(b, kvalita);
                        System.out.println("Spoluprace pridana.");
                    } else {
                        System.out.println("ID nenalezeno.");
                    }

                    break;

                case 4:

                    System.out.print("ID: ");
                    int hledane = sc.nextInt();

                    for (Zamestnanec z : seznam)
                        if (z.getId() == hledane)
                            System.out.println(z);

                    break;

                case 5:

                    System.out.print("ID: ");
                    int idd = sc.nextInt();

                    for (Zamestnanec z : seznam)
                        if (z.getId() == idd)
                            z.vykonejDovednost();

                    break;

                case 6:

                    System.out.print("ID ke smazani: ");
                    int smazat = sc.nextInt();

                    seznam.removeIf(z -> z.getId() == smazat);

                    System.out.println("Smazano.");
                    break;

                case 7:

                    int analytici = 0;
                    int specialisti = 0;

                    for (Zamestnanec z : seznam) {
                        if (z instanceof DatovyAnalytik)
                            analytici++;
                        else
                            specialisti++;
                    }

                    System.out.println("Datovi analytici: " + analytici);
                    System.out.println("Bezpecnostni specialisti: " + specialisti);

                    break;

                case 8:

                    Collections.sort(seznam,
                            Comparator.comparing(Zamestnanec::getPrijmeni));

                    for (Zamestnanec z : seznam)
                        System.out.println(z);

                    break;

                case 9:

                    if (seznam.size() > 0) {

                        Zamestnanec max = seznam.get(0);

                        for (Zamestnanec z : seznam) {
                            if (z.pocetSpolupracovniku() >
                                    max.pocetSpolupracovniku()) {
                                max = z;
                            }
                        }

                        System.out.println("Nejvice vazeb:");
                        System.out.println(max);
                    }

                    break;

                case 10:

                    try {

                        PrintWriter zapis = new PrintWriter("zamestnanci.txt");

                        for (Zamestnanec z : seznam) {

                            zapis.println(
                                    z.getId() + ";" +
                                    z.getJmeno() + ";" +
                                    z.getPrijmeni() + ";" +
                                    z.getRokNarozeni() + ";" +
                                    z.getClass().getSimpleName()
                            );
                        }

                        zapis.close();

                        System.out.println("Ulozeno do TXT.");

                    } catch (Exception e) {
                        System.out.println("Chyba pri ukladani.");
                    }

                    break;

                case 11:

                    try {

                        Scanner soubor = new Scanner(new File("zamestnanci.txt"));

                        seznam.clear();

                        while (soubor.hasNextLine()) {

                            String radek = soubor.nextLine();
                            String[] data = radek.split(";");

                            int iid = Integer.parseInt(data[0]);
                            String jj = data[1];
                            String pp = data[2];
                            int rr = Integer.parseInt(data[3]);
                            String tt = data[4];

                            if (tt.equals("DatovyAnalytik"))
                                seznam.add(new DatovyAnalytik(iid, jj, pp, rr));
                            else
                                seznam.add(new BezpecnostniSpecialista(iid, jj, pp, rr));

                            if (iid >= id)
                                id = iid + 1;
                        }

                        soubor.close();

                        System.out.println("Nacteno z TXT.");

                    } catch (Exception e) {
                        System.out.println("Chyba pri nacitani.");
                    }

                    break;

                case 12:

                    System.out.print("Zadej ID zamestnance: ");
                    int idx = sc.nextInt();

                    for (Zamestnanec z : seznam) {
                        if (z.getId() == idx) {
                            z.vypisSpolupracovniky();
                        }
                    }

                    break;

                case 13:
                    System.out.println("Konec.");
                    break;

                default:
                    System.out.println("Spatna volba.");
            }
        }

        sc.close();
    }
}