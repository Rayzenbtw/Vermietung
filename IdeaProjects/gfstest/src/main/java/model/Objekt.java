package model;

import java.math.BigDecimal;

/**
 * Модель данных для таблицы Objekt (Объект недвижимости).
 */
public class Objekt {

    private int oNr;                // Номер объекта (Primary Key)
    private BigDecimal groesse;     // Площадь (в кв.м)
    private BigDecimal mietpreis;   // Цена аренды
    private int vNr;                // Номер арендодателя (Foreign Key)

    // Дополнительное поле для отображения (не хранится в БД)
    private String vermieterName;   // Имя арендодателя для UI

    // Конструкторы
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

    // Геттеры и сеттеры
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