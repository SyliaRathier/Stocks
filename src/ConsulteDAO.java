import java.sql.*;

public class ConsulteDAO {

    public void enregistrerConsultation(int utilisateurId, int lieuId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());

            // Vérifier si l'entrée existe déjà
            ps = con.prepareStatement("SELECT 1 FROM Consulte WHERE utilisateur_id = ? AND lieu_id = ?");
            ps.setInt(1, utilisateurId);
            ps.setInt(2, lieuId);
            rs = ps.executeQuery();

            if (!rs.next()) {
                // L'entrée n'existe pas, procéder à l'insertion
                ps = con.prepareStatement("INSERT INTO Consulte (utilisateur_id, lieu_id) VALUES (?, ?)");
                ps.setInt(1, utilisateurId);
                ps.setInt(2, lieuId);
                ps.executeUpdate();
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception t) {}
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
    }
}
