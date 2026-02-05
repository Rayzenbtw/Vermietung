package dao;

import model.Vermietung;
import util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO für die Arbeit mit der Tabelle Vermietung.
 */

public class VermietungDAO {
    // CREATE
    public boolean createVermietung(Vermietung vermietung) {
        String sql = "INSERT INTO Vermietung (MNr, ONr, ADatum, EDatum) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, vermietung.getMNr());
            pstmt.setInt(2, vermietung.getONr());
            pstmt.setDate(3, Date.valueOf(vermietung.getADatum()));

            if (vermietung.getEDatum() != null) {
                pstmt.setDate(4, Date.valueOf(vermietung.getEDatum()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        vermietung.setVmNr(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Create Vermietung: " + vermietung);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Fehler");
            e.printStackTrace();
        }
        return false;
    }

    // READ - All with JOIN
    public List<Vermietung> getAllVermietungen() {
        List<Vermietung> list = new ArrayList<>();

        String sql = "SELECT v.VMNr, v.MNr, v.ONr, v.ADatum, v.EDatum, " +
                "CONCAT(m.Vorname, ' ', m.Name) AS MieterName, " +
                "CONCAT('Objekt #', o.ONr, ' (', o.Groesse, 'm^2)') AS ObjektInfo " +
                "FROM Vermietung v " +
                "LEFT JOIN Mieter m ON v.MNr = m.MNr " +
                "LEFT JOIN Objekt o ON v.ONr = o.ONr " +
                "ORDER BY v.VMNr";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vermietung vermietung = new Vermietung();
                vermietung.setVmNr(rs.getInt("VMNr"));
                vermietung.setMNr(rs.getInt("MNr"));
                vermietung.setONr(rs.getInt("ONr"));

                Date aDatum = rs.getDate("ADatum");
                vermietung.setADatum(aDatum != null ? aDatum.toLocalDate() : null);

                Date eDatum = rs.getDate("EDatum");
                vermietung.setEDatum(eDatum != null ? eDatum.toLocalDate() : null);

                vermietung.setMieterName(rs.getString("MieterName"));
                vermietung.setObjektInfo(rs.getString("ObjektInfo"));

                list.add(vermietung);
            }

            System.out.println("Insgesamt vermietung: " + list.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // READ - By ID
    public Vermietung getVermietungById(int vmNr) {
        String sql = "SELECT * FROM Vermietung WHERE VMNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vmNr);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Vermietung vermietung = new Vermietung();
                    vermietung.setVmNr(rs.getInt("VMNr"));
                    vermietung.setMNr(rs.getInt("MNr"));
                    vermietung.setONr(rs.getInt("ONr"));

                    Date aDatum = rs.getDate("ADatum");
                    vermietung.setADatum(aDatum != null ? aDatum.toLocalDate() : null);

                    Date eDatum = rs.getDate("EDatum");
                    vermietung.setEDatum(eDatum != null ? eDatum.toLocalDate() : null);

                    return vermietung;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE
    public boolean updateVermietung(Vermietung vermietung) {
        String sql = "UPDATE Vermietung SET MNr = ?, ONr = ?, ADatum = ?, EDatum = ? WHERE VMNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vermietung.getMNr());
            pstmt.setInt(2, vermietung.getONr());
            pstmt.setDate(3, Date.valueOf(vermietung.getADatum()));

            if (vermietung.getEDatum() != null) {
                pstmt.setDate(4, Date.valueOf(vermietung.getEDatum()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }

            pstmt.setInt(5, vermietung.getVmNr());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Vermietung ist geändert: " + vermietung);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // DELETE
    public boolean deleteVermietung(int vmNr) {
        String sql = "DELETE FROM Vermietung WHERE VMNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vmNr);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Vermietung gelöscht (ID: " + vmNr + ")");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}