package model;

/**
 * Datenmodell für die Tabelle Vermieter (Vermieter).
 * Jedes Objekt dieser Klasse repräsentiert einen Datensatz in der Tabelle.
 */
public class Vermieter {

    private int vNr;           // Vermieternummer (Primary Key)
    private String name;       // Nachname
    private String vorname;    // Vorname

    // Standard-Konstruktor
    public Vermieter() {}

    // Konstruktor mit Parametern
    public Vermieter(int vNr, String name, String vorname) {
        this.vNr = vNr;
        this.name = name;
        this.vorname = vorname;
    }

    // Konstruktor ohne ID (für neue Datensätze)
    public Vermieter(String name, String vorname) {
        this.name = name;
        this.vorname = vorname;
    }

    // Getter und Setter
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

    // Überschreibung der toString-Methode für eine einfache Anzeige
    @Override
    public String toString() {
        return "Vermieter{" +
                "VNr=" + vNr +
                ", Name='" + name + '\'' +
                ", Vorname='" + vorname + '\'' +
                '}';
    }
}