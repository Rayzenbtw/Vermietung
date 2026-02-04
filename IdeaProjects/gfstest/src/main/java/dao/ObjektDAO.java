package dao;

import model.Objekt;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) для работы с таблицей Objekt.
 * Реализует все CRUD операции: Create, Read, Update, Delete.
 */
public class ObjektDAO {

    public boolean createObjekt(Objekt objekt) {
        String sql = "INSERT INTO Objekt (Groesse, Mietpreis, VNr) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Установка параметров запроса
            pstmt.setBigDecimal(1, objekt.getGroesse());
            pstmt.setBigDecimal(2, objekt.getMietpreis());
            pstmt.setInt(3, objekt.getVNr());

            // Выполнение запроса
            int affectedRows = pstmt.executeUpdate();

            // Получение сгенерированного ID
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        objekt.setONr(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Objekt created: " + objekt);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Objekt> getAllObjekte() {
        List<Objekt> objektList = new ArrayList<>();

        // JOIN запрос для получения имени арендодателя
        String sql = "SELECT o.ONr, o.Groesse, o.Mietpreis, o.VNr, " +
                "CONCAT(v.Vorname, ' ', v.Name) AS VermieterName " +
                "FROM Objekt o " +
                "LEFT JOIN Vermieter v ON o.VNr = v.VNr " +
                "ORDER BY o.ONr";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Обработка результатов запроса
            while (rs.next()) {
                Objekt objekt = new Objekt();
                objekt.setONr(rs.getInt("ONr"));
                objekt.setGroesse(rs.getBigDecimal("Groesse"));
                objekt.setMietpreis(rs.getBigDecimal("Mietpreis"));
                objekt.setVNr(rs.getInt("VNr"));
                objekt.setVermieterName(rs.getString("VermieterName"));

                objektList.add(objekt);
            }

            System.out.println("Insgesamt Objekte: " + objektList.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return objektList;
    }

    public Objekt getObjektById(int oNr) {
        String sql = "SELECT * FROM Objekt WHERE ONr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, oNr);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Objekt(
                            rs.getInt("ONr"),
                            rs.getBigDecimal("Groesse"),
                            rs.getBigDecimal("Mietpreis"),
                            rs.getInt("VNr")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateObjekt(Objekt objekt) {
        String sql = "UPDATE Objekt SET Groesse = ?, Mietpreis = ?, VNr = ? WHERE ONr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBigDecimal(1, objekt.getGroesse());
            pstmt.setBigDecimal(2, objekt.getMietpreis());
            pstmt.setInt(3, objekt.getVNr());
            pstmt.setInt(4, objekt.getONr());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Objekt geändert: " + objekt);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteObjekt(int oNr) {
        String sql = "DELETE FROM Objekt WHERE ONr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, oNr);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Objekt gelöscht (ID: " + oNr + ")");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}