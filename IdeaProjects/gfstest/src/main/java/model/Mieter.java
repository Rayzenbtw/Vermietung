package model;

/**
 * Datenmodell für die Tabelle Mieter (Mieter).
 */
public class Mieter {

    private int mNr;           // Mieternummer (Primary Key)
    private String name;       // Nachname
    private String vorname;    // Vorname

    // Konstruktoren
    public Mieter() {}

    public Mieter(int mNr, String name, String vorname) {
        this.mNr = mNr;
        this.name = name;
        this.vorname = vorname;
    }

    public Mieter(String name, String vorname) {
        this.name = name;
        this.vorname = vorname;
    }

    // Getter und Setter
    public int getMNr() {
        return mNr;
    }

    public void setMNr(int mNr) {
        this.mNr = mNr;
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
        return "Mieter{" +
                "MNr=" + mNr +
                ", Name='" + name + '\'' +
                ", Vorname='" + vorname + '\'' +
                '}';
    }
}