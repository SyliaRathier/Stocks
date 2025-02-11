import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    public ArticleDAO() {
        // Le chargement du pilote est déjà fait dans DBConnection
    }

    public int ajouter(Article nouvArticle) {
        Connection con = null;
        PreparedStatement ps = null;
        int retour = 0;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("INSERT INTO article (reference, designation, pu_ht, qtestock) VALUES (?, ?, ?, ?)");
            ps.setInt(1, nouvArticle.getReference());
            ps.setString(2, nouvArticle.getDesignation());
            ps.setDouble(3, nouvArticle.getPuHt());
            ps.setInt(4, nouvArticle.getQteStock());

            retour = ps.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }
    
    public void supprimer(int reference) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("DELETE FROM article WHERE reference = ?");
            ps.setInt(1, reference);

            ps.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
    }

    public void modifier(Article article) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("UPDATE article SET designation = ?, pu_ht = ?, qtestock = ? WHERE reference = ?");
            ps.setString(1, article.getDesignation());
            ps.setDouble(2, article.getPuHt());
            ps.setInt(3, article.getQteStock());
            ps.setInt(4, article.getReference());

            ps.executeUpdate();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
    }


    public Article getArticle(int reference) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Article retour = null;

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("SELECT * FROM article WHERE reference = ?");
            ps.setInt(1, reference);

            rs = ps.executeQuery();
            if (rs.next())
                retour = new Article(rs.getInt("reference"), rs.getString("designation"), rs.getDouble("pu_ht"), rs.getInt("qtestock"));
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception t) {}
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }

    public List<Article> getListeArticles() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Article> retour = new ArrayList<Article>();

        try {
            con = DriverManager.getConnection(DBConnection.getUrl(), DBConnection.getLogin(), DBConnection.getPass());
            ps = con.prepareStatement("SELECT * FROM article");

            rs = ps.executeQuery();
            while (rs.next())
                retour.add(new Article(rs.getInt("reference"), rs.getString("designation"), rs.getDouble("pu_ht"), rs.getInt("qtestock")));
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception t) {}
            try { if (ps != null) ps.close(); } catch (Exception t) {}
            try { if (con != null) con.close(); } catch (Exception t) {}
        }
        return retour;
    }

    public static void main(String[] args) throws SQLException {
//        ArticleDAO articleDAO = new ArticleDAO();
//
//        Article a = new Article(1, "Set de 2 raquettes de ping-pong", 149.9, 10);
//        int retour = articleDAO.ajouter(a);
//
//        System.out.println(retour + " lignes ajoutées");
//
//        Article a2 = articleDAO.getArticle(1);
//        System.out.println(a2);
//
//        List<Article> liste = articleDAO.getListeArticles();
//        for (Article art : liste) {
//            System.out.println(art.toString());
//        }
    	
    	 //ArticleDAO articleDAO = new ArticleDAO();

    	 // Ajouter un article
    	 //Article a = new Article(1, "Set de 2 raquettes de ping-pong", 149.9, 10);
    	 //articleDAO.ajouter(a);

    	    // Modifier un article
    	 //Article aModifie = new Article(1, "Set de raquettes de ping-pong", 159.9, 15);
    	 //articleDAO.modifier(aModifie);
    	 
    	 // Supprimer un article
    	 //articleDAO.supprimer(1);
    }
}
