import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentaireDAO {

    public CommentaireDAO() {
        // Le chargement du pilote est déjà fait dans DBConnection
    }

    public int ajouter(Commentaire nouvCommentaire) {
        Connection con = null;
        PreparedStatement ps = null;
        int retour = 0;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("INSERT INTO commentaire (texte, date_publication, lieu_id) VALUES (?, ?, ?)");
            ps.setString(1, nouvCommentaire.getTexte());
            ps.setTimestamp(2, new Timestamp(nouvCommentaire.getDatePublication().getTime()));
            ps.setInt(3, nouvCommentaire.getLieuId());

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
            ps = con.prepareStatement("DELETE FROM commentaire WHERE identifiant = ?");
            ps.setInt(1, identifiant);

            ps.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
    }

    public void modifier(Commentaire commentaire) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("UPDATE commentaire SET texte = ?, date_publication = ?, lieu_id = ? WHERE identifiant = ?");
            ps.setString(1, commentaire.getTexte());
            ps.setTimestamp(2, new Timestamp(commentaire.getDatePublication().getTime()));
            ps.setInt(3, commentaire.getLieuId());
            ps.setInt(4, commentaire.getIdentifiant());

            ps.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
    }

    public Commentaire getCommentaire(int identifiant) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Commentaire retour = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("SELECT * FROM commentaire WHERE identifiant = ?");
            ps.setInt(1, identifiant);

            rs = ps.executeQuery();
            if (rs.next())
                retour = new Commentaire(rs.getInt("identifiant"), rs.getString("texte"), rs.getTimestamp("date_publication"), rs.getInt("lieu_id"));
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception t) {}
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }

    public List<Commentaire> getListeCommentaires() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Commentaire> retour = new ArrayList<Commentaire>();

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("SELECT * FROM commentaire");

            rs = ps.executeQuery();
            while (rs.next())
                retour.add(new Commentaire(rs.getInt("identifiant"), rs.getString("texte"), rs.getTimestamp("date_publication"), rs.getInt("lieu_id")));
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception t) {}
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }

    public List<Commentaire> getCommentairesPourLieu(int lieuId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Commentaire> retour = new ArrayList<Commentaire>();

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("SELECT * FROM commentaire WHERE lieu_id = ?");
            ps.setInt(1, lieuId);

            rs = ps.executeQuery();
            while (rs.next()) {
                retour.add(new Commentaire(rs.getInt("identifiant"), rs.getString("texte"), rs.getTimestamp("date_publication"), rs.getInt("lieu_id")));
            }
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
