package model;

import java.time.LocalDate;

/**
 * Модель данных для таблицы Vermietung (Аренда).
 */
public class Vermietung {

    private int vmNr;              // Номер аренды (Primary Key)
    private int mNr;               // Номер арендатора (Foreign Key)
    private int oNr;               // Номер объекта (Foreign Key)
    private LocalDate aDatum;      // Дата начала аренды
    private LocalDate eDatum;      // Дата окончания аренды (может быть null)

    // Дополнительные поля для UI
    private String mieterName;     // Имя арендатора
    private String objektInfo;     // Информация об объекте

    // Конструкторы
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

    // Геттеры и сеттеры
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