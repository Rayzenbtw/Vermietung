package dao;

import model.Mieter;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO für die Arbeit mit der Tabelle Mieter.
 */
public class MieterDAO {

    // CREATE
    public boolean createMieter(Mieter mieter) {
        String sql = "INSERT INTO Mieter (Name, Vorname) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, mieter.getName());
            pstmt.setString(2, mieter.getVorname());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        mieter.setMNr(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Mieter created: " + mieter);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ - All
    public List<Mieter> getAllMieter() {
        List<Mieter> list = new ArrayList<>();
        String sql = "SELECT * FROM Mieter ORDER BY MNr";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mieter mieter = new Mieter(
                        rs.getInt("MNr"),
                        rs.getString("Name"),
                        rs.getString("Vorname")
                );
                list.add(mieter);
            }

            System.out.println("Insgesamt Mieter: " + list.size());

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return list;
    }

    // READ - By ID
    public Mieter getMieterById(int mNr) {
        String sql = "SELECT * FROM Mieter WHERE MNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mNr);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Mieter(
                            rs.getInt("MNr"),
                            rs.getString("Name"),
                            rs.getString("Vorname")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE
    public boolean updateMieter(Mieter mieter) {
        String sql = "UPDATE Mieter SET Name = ?, Vorname = ? WHERE MNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, mieter.getName());
            pstmt.setString(2, mieter.getVorname());
            pstmt.setInt(3, mieter.getMNr());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Mieter geändert " + mieter);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // DELETE
    public boolean deleteMieter(int mNr) {
        String sql = "DELETE FROM Mieter WHERE MNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mNr);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Mieter gelöscht (ID: " + mNr + ")");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}