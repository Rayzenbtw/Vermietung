package model;

import java.time.LocalDate;

/**
 * Datenmodell für die Tabelle Vermietung (Vermietung).
 */
public class Vermietung {

    private int vmNr;              // Vermietungsnummer (Primary Key)
    private int mNr;               // Mieternummer (Foreign Key)
    private int oNr;               // Objektnummer (Foreign Key)
    private LocalDate aDatum;      // Anfangsdatum der Vermietung
    private LocalDate eDatum;      // Enddatum der Vermietung (kann null sein)

    // Zusätzliche Felder für UI
    private String mieterName;     // Mieter-Name
    private String objektInfo;     // Objekt-Information

    // Konstruktoren
    public Vermietung() {}

    public Vermietung(int vmNr, int mNr, int oNr, LocalDate aDatum, LocalDate eDatum) {
        this.vmNr = vmNr;
        this.mNr = mNr;
        this.oNr = oNr;
        this.aDatum = aDatum;
        this.eDatum = eDatum;
    }

    public Vermietung(int mNr, int oNr, LocalDate aDatum, LocalDate eDatum) {
        this.mNr = mNr;
        this.oNr = oNr;
        this.aDatum = aDatum;
        this.eDatum = eDatum;
    }

    // Getter und Setter
    public int getVmNr() {
        return vmNr;
    }

    public void setVmNr(int vmNr) {
        this.vmNr = vmNr;
    }

    public int getMNr() {
        return mNr;
    }

    public void setMNr(int mNr) {
        this.mNr = mNr;
    }

    public int getONr() {
        return oNr;
    }

    public void setONr(int oNr) {
        this.oNr = oNr;
    }

    public LocalDate getADatum() {
        return aDatum;
    }

    public void setADatum(LocalDate aDatum) {
        this.aDatum = aDatum;
    }

    public LocalDate getEDatum() {
        return eDatum;
    }

    public void setEDatum(LocalDate eDatum) {
        this.eDatum = eDatum;
    }

    public String getMieterName() {
        return mieterName;
    }

    public void setMieterName(String mieterName) {
        this.mieterName = mieterName;
    }

    public String getObjektInfo() {
        return objektInfo;
    }

    public void setObjektInfo(String objektInfo) {
        this.objektInfo = objektInfo;
    }

    @Override
    public String toString() {
        return "Vermietung{" +
                "VMNr=" + vmNr +
                ", MNr=" + mNr +
                ", ONr=" + oNr +
                ", ADatum=" + aDatum +
                ", EDatum=" + eDatum +
                '}';
    }
}