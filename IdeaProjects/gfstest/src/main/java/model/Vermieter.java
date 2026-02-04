package model;

/**
 * Модель данных для таблицы Vermieter (Арендодатель).
 * Каждый объект этого класса представляет одну запись в таблице.
 */
public class Vermieter {

    private int vNr;           // Номер арендодателя (Primary Key)
    private String name;       // Фамилия
    private String vorname;    // Имя

    // Конструктор по умолчанию
    public Vermieter() {}

    // Конструктор с параметрами
    public Vermieter(int vNr, String name, String vorname) {
        this.vNr = vNr;
        this.name = name;
        this.vorname = vorname;
    }

    // Конструктор без ID (для создания новых записей)
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

    // Переопределение метода toString для удобного отображения
    @Override
    public String toString() {
        return "Vermieter{" +
                "VNr=" + vNr +
                ", Name='" + name + '\'' +
                ", Vorname='" + vorname + '\'' +
                '}';
    }
}