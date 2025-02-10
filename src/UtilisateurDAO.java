import java.sql.*;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    public UtilisateurDAO() {
        // Le chargement du pilote est déjà fait dans DBConnection
    }

    public int ajouter(Utilisateur nouvUtilisateur) {
        Connection con = null;
        PreparedStatement ps = null;
        int retour = 0;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("INSERT INTO utilisateur (nom, prenom, email, motdepasse, role_id) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, nouvUtilisateur.getNom());
            ps.setString(2, nouvUtilisateur.getPrenom());
            ps.setString(3, nouvUtilisateur.getEmail());
            ps.setString(4, nouvUtilisateur.getMotDePasse());
            ps.setInt(5, nouvUtilisateur.getRoleId());

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
            ps = con.prepareStatement("DELETE FROM utilisateur WHERE identifiant = ?");
            ps.setInt(1, identifiant);

            ps.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
    }

    public void modifier(Utilisateur utilisateur) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, motdepasse = ?, role_id = ? WHERE identifiant = ?");
            ps.setString(1, utilisateur.getNom());
            ps.setString(2, utilisateur.getPrenom());
            ps.setString(3, utilisateur.getEmail());
            ps.setString(4, utilisateur.getMotDePasse());
            ps.setInt(5, utilisateur.getRoleId());
            ps.setInt(6, utilisateur.getIdentifiant());

            ps.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
    }

    public Utilisateur getUtilisateur(int identifiant) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Utilisateur retour = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("SELECT * FROM utilisateur WHERE identifiant = ?");
            ps.setInt(1, identifiant);

            rs = ps.executeQuery();
            if (rs.next())
                retour = new Utilisateur(rs.getInt("identifiant"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("motdepasse"), rs.getInt("role_id"));
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception t) {}
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }

    public List<Utilisateur> getListeUtilisateurs() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Utilisateur> retour = new ArrayList<Utilisateur>();

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("SELECT * FROM utilisateur");

            rs = ps.executeQuery();
            while (rs.next())
                retour.add(new Utilisateur(rs.getInt("identifiant"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("motdepasse"), rs.getInt("role_id")));
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
