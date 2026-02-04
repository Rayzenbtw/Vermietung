package gfs.gfs2;

/**
 * Model-Klasse für Vermieter.
 * Repräsentiert eine Zeile in der Vermieter-Tabelle.
 */
public class Vermieter {

    private int vNr;           // Primary Key
    private String name;       // Nachname
    private String vorname;    // Vorname

    // Конструктор по умолчанию
    public Vermieter() {}

    // Конструктор со всеми параметрами
    public Vermieter(int vNr, String name, String vorname) {
        this.vNr = vNr;
        this.name = name;
        this.vorname = vorname;
    }

    // Конструктор без ID (для CREATE)
    public Vermieter(String name, String vorname) {
        this.name = name;
        this.vorname = vorname;
    }

    // Геттеры и сеттеры
    public int getVNr() {
        return vNr;
    }

    public void setVNr(int vNr) {
        this.vNr = vNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    @Override
    public String toString() {
        return "Vermieter{" +
                "vNr=" + vNr +
                ", name='" + name + '\'' +
                ", vorname='" + vorname + '\'' +
                '}';
    }
}