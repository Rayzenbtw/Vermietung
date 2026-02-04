package model;

/**
 * Модель данных для таблицы Mieter (Арендатор).
 */
public class Mieter {

    private int mNr;           // Номер арендатора (Primary Key)
    private String name;       // Фамилия
    private String vorname;    // Имя

    // Конструкторы
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

    // Геттеры и сеттеры
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