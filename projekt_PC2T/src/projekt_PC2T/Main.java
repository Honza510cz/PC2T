package projekt_PC2T;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Zamestnanec> seznam = new ArrayList<>();

        int volba = 0;
        int id = 1;

        
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:firma.db");
            Statement stmt = conn.createStatement();

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS zamestnanci (" +
                "id INTEGER, jmeno TEXT, prijmeni TEXT, rok INTEGER, typ TEXT)"
            );

            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS spoluprace (" +
                "idA INTEGER, idB INTEGER, kvalita TEXT)"
            );


            ResultSet rs = stmt.executeQuery("SELECT * FROM zamestnanci");

            while (rs.next()) {
                int iid = rs.getInt("id");
                String j = rs.getString("jmeno");
                String p = rs.getString("prijmeni");
                int r = rs.getInt("rok");
                String t = rs.getString("typ");

                if (t.equals("DatovyAnalytik"))
                    seznam.add(new DatovyAnalytik(iid, j, p, r));
                else
                    seznam.add(new BezpecnostniSpecialista(iid, j, p, r));

                if (iid >= id)
                    id = iid + 1;
            }

            ResultSet rs2 = stmt.executeQuery("SELECT * FROM spoluprace");

            while (rs2.next()) {
                int A = rs2.getInt("idA");
                int B = rs2.getInt("idB");
                String kvalita = rs2.getString("kvalita");

                Zamestnanec zA = null;
                Zamestnanec zB = null;

                for (Zamestnanec z : seznam) {
                    if (z.getId() == A) zA = z;
                    if (z.getId() == B) zB = z;
                }

                if (zA != null && zB != null) {
                    zA.pridejSpolupraci(zB, kvalita);
                }
            }

            conn.close();

        } catch (Exception e) {
            System.out.println("DB prazdna nebo chyba.");
        }

        while (volba != 14) {

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
            System.out.println("12 - Vypsat spolupracovniky");
            System.out.println("13 - Statistika kvality");
            System.out.println("14 - Konec");
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
                        if (z.getId() == id1) a = z;
                        if (z.getId() == id2) b = z;
                    }

                    if (a != null && b != null) {
                        a.pridejSpolupraci(b, kvalita);
                        b.pridejSpolupraci(a, kvalita); 
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

                    boolean nalezen = false;

                    for (Zamestnanec z : seznam) {
                        if (z.getId() == idd) {
                            z.vykonejDovednost();
                            nalezen = true;
                        }
                    }

                    if (!nalezen) {
                        System.out.println("Zamestnanec nenalezen.");
                    }

                    break;

                case 6:
                    System.out.print("ID ke smazani: ");
                    int smazat = sc.nextInt();

                    for (Zamestnanec z : seznam)
                        z.odeberSpolupraci(smazat);

                    seznam.removeIf(z -> z.getId() == smazat);

                    System.out.println("Smazano vcetne vazeb.");
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

                    System.out.println("Analytici: " + analytici);
                    System.out.println("Specialisti: " + specialisti);
                    break;

                case 8:
                    ArrayList<Zamestnanec> analyticiList = new ArrayList<>();
                    ArrayList<Zamestnanec> specialistiList = new ArrayList<>();

                    for (Zamestnanec z : seznam) {
                        if (z instanceof DatovyAnalytik)
                            analyticiList.add(z);
                        else
                            specialistiList.add(z);
                    }

                    Collections.sort(analyticiList,
                            Comparator.comparing(Zamestnanec::getPrijmeni));

                    Collections.sort(specialistiList,
                            Comparator.comparing(Zamestnanec::getPrijmeni));

                    System.out.println("=== DATOVI ANALYTICI ===");
                    for (Zamestnanec z : analyticiList)
                        System.out.println(z);

                    System.out.println("=== BEZPECNOSTNI SPECIALISTI ===");
                    for (Zamestnanec z : specialistiList)
                        System.out.println(z);
                    break;

                case 9:

                    if (seznam.size() == 0) {
                        System.out.println("Zadni zamestnanci.");
                        break;
                    }

                    int max = 0;

                    for (Zamestnanec z : seznam) {
                        if (z.pocetSpolupracovniku() > max) {
                            max = z.pocetSpolupracovniku();
                        }
                    }

                    System.out.println("Nejvice vazeb (" + max + "):");

                    for (Zamestnanec z : seznam) {
                        if (z.pocetSpolupracovniku() == max) {
                            System.out.println(
                                z.getJmeno() + " " +
                                z.getPrijmeni() +
                                " (" + max + " vazby)"
                            );
                        }
                    }

                    break;

                case 10:
                    try {
                        PrintWriter zapisZ = new PrintWriter("zamestnanci.txt");
                        PrintWriter zapisS = new PrintWriter("spoluprace.txt");

                        for (Zamestnanec z : seznam) {
                            zapisZ.println(
                                    z.getId() + ";" +
                                    z.getJmeno() + ";" +
                                    z.getPrijmeni() + ";" +
                                    z.getRokNarozeni() + ";" +
                                    z.getClass().getSimpleName()
                            );
                        }

                        for (Zamestnanec z : seznam) {
                            for (Spoluprace s : z.getSpolupracovnici()) {
                                zapisS.println(
                                        z.getId() + ";" +
                                        s.getKolega().getId() + ";" +
                                        s.getKvalita()
                                );
                            }
                        }

                        zapisZ.close();
                        zapisS.close();

                        System.out.println("Ulozeno.");

                    } catch (Exception e) {
                        System.out.println("Chyba pri ukladani.");
                    }
                    break;

                case 11:
                    try {
                        seznam.clear();

                        Scanner souborZ = new Scanner(new File("zamestnanci.txt"));

                        while (souborZ.hasNextLine()) {
                            String[] data = souborZ.nextLine().split(";");

                            int iid = Integer.parseInt(data[0]);
                            String j = data[1];
                            String p = data[2];
                            int r = Integer.parseInt(data[3]);
                            String t = data[4];

                            if (t.equals("DatovyAnalytik"))
                                seznam.add(new DatovyAnalytik(iid, j, p, r));
                            else
                                seznam.add(new BezpecnostniSpecialista(iid, j, p, r));

                            if (iid >= id)
                                id = iid + 1;
                        }

                        souborZ.close();

                        Scanner souborS = new Scanner(new File("spoluprace.txt"));

                        while (souborS.hasNextLine()) {
                            String[] data = souborS.nextLine().split(";");

                            int A = Integer.parseInt(data[0]);
                            int B = Integer.parseInt(data[1]);
                            String kvalita2 = data[2];

                            Zamestnanec zA = null;
                            Zamestnanec zB = null;

                            for (Zamestnanec z : seznam) {
                                if (z.getId() == A) zA = z;
                                if (z.getId() == B) zB = z;
                            }

                            if (zA != null && zB != null)
                                zA.pridejSpolupraci(zB, kvalita2);
                        }

                        souborS.close();

                        System.out.println("Nacteno.");

                    } catch (Exception e) {
                        System.out.println("Chyba pri nacitani.");
                    }
                    break;

                case 12:
                    System.out.print("ID: ");
                    int idx = sc.nextInt();

                    for (Zamestnanec z : seznam)
                        if (z.getId() == idx)
                            z.vypisSpolupracovniky();
                    break;

                case 13:

                    int spatna = 0, prumerna = 0, dobra = 0;

                    for (Zamestnanec z : seznam) {
                        for (Spoluprace s : z.getSpolupracovnici()) {

                            String k = s.getKvalita().toLowerCase().trim();

                            if (k.equals("spatna")) spatna++;
                            else if (k.equals("prumerna")) prumerna++;
                            else if (k.equals("dobra")) dobra++;
                        }
                    }

                    System.out.println("Spatna: " + spatna);
                    System.out.println("Prumerna: " + prumerna);
                    System.out.println("Dobra: " + dobra);

                    if (spatna == 0 && prumerna == 0 && dobra == 0) {
                        System.out.println("Zadne spoluprace.");
                    }
                    else if (spatna == prumerna && spatna == dobra) {
                        System.out.println("Vsechny kvality jsou vyrovnane.");
                    }
                    else if (spatna == prumerna && spatna > dobra) {
                        System.out.println("Prevlada: spatna a prumerna");
                    }
                    else if (spatna == dobra && spatna > prumerna) {
                        System.out.println("Prevlada: spatna a dobra");
                    }
                    else if (prumerna == dobra && prumerna > spatna) {
                        System.out.println("Prevlada: prumerna a dobra");
                    }
                    else if (spatna > prumerna && spatna > dobra) {
                        System.out.println("Prevlada: spatna");
                    }
                    else if (prumerna > spatna && prumerna > dobra) {
                        System.out.println("Prevlada: prumerna");
                    }
                    else {
                        System.out.println("Prevlada: dobra");
                    }

                    break;
                case 14:
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:sqlite:firma.db");
                        Statement stmt = conn.createStatement();

                        stmt.executeUpdate("DELETE FROM zamestnanci");
                        stmt.executeUpdate("DELETE FROM spoluprace");

                        for (Zamestnanec z : seznam) {
                            stmt.executeUpdate(
                                "INSERT INTO zamestnanci VALUES (" +
                                z.getId() + ", '" +
                                z.getJmeno() + "', '" +
                                z.getPrijmeni() + "', " +
                                z.getRokNarozeni() + ", '" +
                                z.getClass().getSimpleName() + "')"
                            );
                        }
                        
                        for (Zamestnanec z : seznam) {
                            for (Spoluprace s : z.getSpolupracovnici()) {

                                stmt.executeUpdate(
                                    "INSERT INTO spoluprace VALUES (" +
                                    z.getId() + ", " +
                                    s.getKolega().getId() + ", '" +
                                    s.getKvalita() + "')"
                                );
                            }
                        }

                        conn.close();

                        System.out.println("Ulozeno do SQL.");

                    } catch (Exception e) {
                        System.out.println("Chyba DB.");
                    }

                    System.out.println("Konec programu.");
                    break;

                default:
                    System.out.println("Spatna volba.");
            }
        }

        sc.close();
    }
}