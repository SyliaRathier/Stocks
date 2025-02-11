import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsulteDAO {

    public void enregistrerConsultation(int utilisateurId, int lieuId) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("INSERT INTO Consulte (utilisateur_id, lieu_id) VALUES (?, ?)");
            ps.setInt(1, utilisateurId);
            ps.setInt(2, lieuId);
            ps.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
    }
}
