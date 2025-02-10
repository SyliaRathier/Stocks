import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LieuTouristiqueDAO {

    public LieuTouristiqueDAO() {
        // Le chargement du pilote est déjà fait dans DBConnection
    }

    public int ajouter(LieuTouristique nouvLieu) {
        Connection con = null;
        PreparedStatement ps = null;
        int retour = 0;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("INSERT INTO lieu_touristique (titre, description, adresse, image, idguide) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, nouvLieu.getTitre());
            ps.setString(2, nouvLieu.getDescription());
            ps.setString(3, nouvLieu.getAdresse());
            ps.setBytes(4, nouvLieu.getImage());
            ps.setInt(5, nouvLieu.getGuideId());

            retour = ps.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }

    public void supprimer(int identifiant) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("DELETE FROM lieu_touristique WHERE identifiant = ?");
            ps.setInt(1, identifiant);

            ps.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
    }

    public void modifier(LieuTouristique lieu) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("UPDATE lieu_touristique SET titre = ?, description = ?, adresse = ?, image = ?, idguide = ? WHERE identifiant = ?");
            ps.setString(1, lieu.getTitre());
            ps.setString(2, lieu.getDescription());
            ps.setString(3, lieu.getAdresse());
            ps.setBytes(4, lieu.getImage());
            ps.setInt(5, lieu.getGuideId());
            ps.setInt(6, lieu.getIdentifiant());

            ps.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
    }

    public LieuTouristique getLieuTouristique(int identifiant) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LieuTouristique retour = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("SELECT * FROM lieu_touristique WHERE identifiant = ?");
            ps.setInt(1, identifiant);

            rs = ps.executeQuery();
            if (rs.next())
                retour = new LieuTouristique(rs.getInt("identifiant"), rs.getString("titre"), rs.getString("description"), rs.getString("adresse"), rs.getBytes("image"), rs.getInt("idguide"));
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception t) {}
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }

    public List<LieuTouristique> getListeLieuxTouristiques() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LieuTouristique> retour = new ArrayList<LieuTouristique>();

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("SELECT * FROM lieu_touristique");

            rs = ps.executeQuery();
            while (rs.next())
                retour.add(new LieuTouristique(rs.getInt("identifiant"), rs.getString("titre"), rs.getString("description"), rs.getString("adresse"), rs.getBytes("image"), rs.getInt("idguide")));
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception t) {}
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }
}
