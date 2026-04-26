package projekt_PC2T;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Zamestnanec> seznam = new ArrayList<>();

        int volba = 0;
        int id = 1;

        while (volba != 6) {

            System.out.println("\n--- MENU ---");
            System.out.println("1 - Pridat zamestnance");
            System.out.println("2 - Vypsat zamestnance");
            System.out.println("3 - Pridat spolupraci");
            System.out.println("4 - Najit podle ID");
            System.out.println("5 - Spustit dovednost");
            System.out.println("6 - Konec");
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

                    if (typ == 1) {
                        seznam.add(new DatovyAnalytik(id, jmeno, prijmeni, rok));
                    } else {
                        seznam.add(new BezpecnostniSpecialista(id, jmeno, prijmeni, rok));
                    }

                    id++;
                    System.out.println("Pridano.");
                    break;

                case 2:

                    for (Zamestnanec z : seznam) {
                        System.out.println(z);
                    }

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
                        if (z.getId() == id1) a = z;
                        if (z.getId() == id2) b = z;
                    }

                    if (a != null && b != null) {
                        a.pridejSpolupraci(b, kvalita);
                        System.out.println("Spoluprace pridana.");
                    } else {
                        System.out.println("ID nenalezeno.");
                    }

                    break;

                case 4:

                    System.out.print("Zadej ID: ");
                    int hledane = sc.nextInt();

                    for (Zamestnanec z : seznam) {
                        if (z.getId() == hledane) {
                            System.out.println(z);
                        }
                    }

                    break;

                case 5:

                    System.out.print("Zadej ID: ");
                    int idd = sc.nextInt();

                    for (Zamestnanec z : seznam) {
                        if (z.getId() == idd) {
                            z.vykonejDovednost();
                        }
                    }

                    break;

                case 6:
                    System.out.println("Konec programu.");
                    break;

                default:
                    System.out.println("Spatna volba.");
            }
        }

        sc.close();
    }
}