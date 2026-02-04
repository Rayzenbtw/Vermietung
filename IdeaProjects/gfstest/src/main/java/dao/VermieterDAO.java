package dao;

import model.Vermieter;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VermieterDAO {

    // CREATE
    public boolean createVermieter(Vermieter vermieter) {
        String sql = "INSERT INTO Vermieter (Name, Vorname) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, vermieter.getName());
            pstmt.setString(2, vermieter.getVorname());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        vermieter.setVNr(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Vermieter created " + vermieter);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // READ - All
    public List<Vermieter> getAllVermieter() {
        List<Vermieter> list = new ArrayList<>();
        String sql = "SELECT * FROM Vermieter ORDER BY VNr";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vermieter vermieter = new Vermieter(
                        rs.getInt("VNr"),
                        rs.getString("Name"),
                        rs.getString("Vorname")
                );
                list.add(vermieter);
            }

            System.out.println("Insgesamt vermieterÖ " + list.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // READ - By ID
    public Vermieter getVermieterById(int vNr) {
        String sql = "SELECT * FROM Vermieter WHERE VNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vNr);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Vermieter(
                            rs.getInt("VNr"),
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
    public boolean updateVermieter(Vermieter vermieter) {
        String sql = "UPDATE Vermieter SET Name = ?, Vorname = ? WHERE VNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vermieter.getName());
            pstmt.setString(2, vermieter.getVorname());
            pstmt.setInt(3, vermieter.getVNr());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Vermieter updated: " + vermieter);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // DELETE
    public boolean deleteVermieter(int vNr) {
        String sql = "DELETE FROM Vermieter WHERE VNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vNr);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Vermieter gelöscht(ID: " + vNr + ")");
                return true;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }
}