package model;

import java.math.BigDecimal;

/**
 * Datenmodell für die Tabelle Objekt (Immobilienobjekt).
 */
public class Objekt {

    private int oNr;                // Objektnummer (Primary Key)
    private BigDecimal groesse;     // Fläche (in qm)
    private BigDecimal mietpreis;   // Mietpreis
    private int vNr;                // Vermieternummer (Foreign Key)

    // Zusätzliches Feld für die Anzeige (nicht in der Datenbank gespeichert)
    private String vermieterName;   // Vermieter-Name für UI

    // Konstruktoren
    public Objekt() {}

    public Objekt(int oNr, BigDecimal groesse, BigDecimal mietpreis, int vNr) {
        this.oNr = oNr;
        this.groesse = groesse;
        this.mietpreis = mietpreis;
        this.vNr = vNr;
    }

    public Objekt(BigDecimal groesse, BigDecimal mietpreis, int vNr) {
        this.groesse = groesse;
        this.mietpreis = mietpreis;
        this.vNr = vNr;
    }

    // Getter und Setter
    public int getONr() {
        return oNr;
    }

    public void setONr(int oNr) {
        this.oNr = oNr;
    }

    public BigDecimal getGroesse() {
        return groesse;
    }

    public void setGroesse(BigDecimal groesse) {
        this.groesse = groesse;
    }

    public BigDecimal getMietpreis() {
        return mietpreis;
    }

    public void setMietpreis(BigDecimal mietpreis) {
        this.mietpreis = mietpreis;
    }

    public int getVNr() {
        return vNr;
    }

    public void setVNr(int vNr) {
        this.vNr = vNr;
    }

    public String getVermieterName() {
        return vermieterName;
    }

    public void setVermieterName(String vermieterName) {
        this.vermieterName = vermieterName;
    }

    @Override
    public String toString() {
        return "Objekt{" +
                "ONr=" + oNr +
                ", Groesse=" + groesse +
                ", Mietpreis=" + mietpreis +
                ", VNr=" + vNr +
                '}';
    }
}