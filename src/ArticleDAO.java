import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    final static String URL = "jdbc:mariadb://localhost:3307/app";
    final static String LOGIN = "root";
    final static String PASS = "root";

    public ArticleDAO() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e2) {
            System.err.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
        }
    }

    public int ajouter(Article nouvArticle) {
        Connection con = null;
        PreparedStatement ps = null;
        int retour = 0;

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);
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

    public Article getArticle(int reference) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Article retour = null;

        try {
            con = DriverManager.getConnection(URL, LOGIN, PASS);
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
            con = DriverManager.getConnection(URL, LOGIN, PASS);
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
        ArticleDAO articleDAO = new ArticleDAO();

        Article a = new Article(1, "Set de 2 raquettes de ping-pong", 149.9, 10);
        int retour = articleDAO.ajouter(a);

        System.out.println(retour + " lignes ajoutées");

        Article a2 = articleDAO.getArticle(1);
        System.out.println(a2);

        List<Article> liste = articleDAO.getListeArticles();
        for (Article art : liste) {
            System.out.println(art.toString());
        }
    }
}
