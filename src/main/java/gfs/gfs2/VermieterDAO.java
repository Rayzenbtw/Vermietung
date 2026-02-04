package gfs.gfs2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) für die Vermieter-Tabelle.
 * Implementiert alle CRUD-Operationen.
 */
public class VermieterDAO {

    /**
     * CREATE - Neuen Vermieter in die Datenbank einfügen
     */
    public boolean createVermieter(Vermieter vermieter) {
        String sql = "INSERT INTO Vermieter (Name, Vorname) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Параметры в правильном порядке
            pstmt.setString(1, vermieter.getName());      // Параметр 1 = Name (Nachname)
            pstmt.setString(2, vermieter.getVorname());   // Параметр 2 = Vorname

            System.out.println("📝 SQL: " + sql);
            System.out.println("   Параметр 1 (Name): " + vermieter.getName());
            System.out.println("   Параметр 2 (Vorname): " + vermieter.getVorname());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                // Получаем сгенерированный ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        vermieter.setVNr(generatedKeys.getInt(1));
                    }
                }
                System.out.println("✅ CREATE erfolgreich: " + vermieter);
                return true;
            } else {
                System.err.println("❌ Keine Zeilen wurden eingefügt!");
            }

        } catch (SQLException e) {
            System.err.println("❌ CREATE Fehler!");
            System.err.println("   Vermieter: Name='" + vermieter.getName() + "', Vorname='" + vermieter.getVorname() + "'");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * READ - Alle Vermieter aus der Datenbank holen
     */
    public List<Vermieter> getAllVermieter() {
        List<Vermieter> list = new ArrayList<>();
        String sql = "SELECT VNr, Name, Vorname FROM Vermieter ORDER BY VNr";

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

            System.out.println("📋 READ erfolgreich: " + list.size() + " Einträge geladen");

        } catch (SQLException e) {
            System.err.println("❌ READ Fehler!");
            e.printStackTrace();
        }

        return list;
    }

    /**
     * READ - Einen Vermieter nach ID holen
     */
    public Vermieter getVermieterById(int vNr) {
        String sql = "SELECT VNr, Name, Vorname FROM Vermieter WHERE VNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vNr);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Vermieter vermieter = new Vermieter(
                            rs.getInt("VNr"),
                            rs.getString("Name"),
                            rs.getString("Vorname")
                    );
                    System.out.println("✅ READ BY ID erfolgreich: " + vermieter);
                    return vermieter;
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ READ BY ID Fehler für VNr=" + vNr);
            e.printStackTrace();
        }

        return null;
    }

    /**
     * UPDATE - Bestehenden Vermieter aktualisieren
     */
    public boolean updateVermieter(Vermieter vermieter) {
        String sql = "UPDATE Vermieter SET Name = ?, Vorname = ? WHERE VNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vermieter.getName());      // Параметр 1 = Name
            pstmt.setString(2, vermieter.getVorname());   // Параметр 2 = Vorname
            pstmt.setInt(3, vermieter.getVNr());          // Параметр 3 = VNr

            System.out.println("📝 SQL: " + sql);
            System.out.println("   Параметр 1 (Name): " + vermieter.getName());
            System.out.println("   Параметр 2 (Vorname): " + vermieter.getVorname());
            System.out.println("   Параметр 3 (VNr): " + vermieter.getVNr());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("✅ UPDATE erfolgreich: " + vermieter);
                return true;
            } else {
                System.err.println("❌ Keine Zeilen wurden aktualisiert! VNr=" + vermieter.getVNr() + " existiert nicht?");
            }

        } catch (SQLException e) {
            System.err.println("❌ UPDATE Fehler!");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * DELETE - Vermieter aus der Datenbank löschen
     */
    public boolean deleteVermieter(int vNr) {
        String sql = "DELETE FROM Vermieter WHERE VNr = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vNr);

            System.out.println("📝 SQL: " + sql);
            System.out.println("   Параметр 1 (VNr): " + vNr);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("✅ DELETE erfolgreich: VNr=" + vNr);
                return true;
            } else {
                System.err.println("❌ Keine Zeilen wurden gelöscht! VNr=" + vNr + " existiert nicht?");
            }

        } catch (SQLException e) {
            System.err.println("❌ DELETE Fehler!");
            System.err.println("   Möglicherweise existieren abhängige Datensätze (Foreign Key Constraint)");
            e.printStackTrace();
        }

        return false;
    }
}