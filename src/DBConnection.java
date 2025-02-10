
/**
 * 
 */
public class DBConnection {
	final static String URL = "jdbc:mariadb://localhost:3307/app";
    final static String LOGIN = "root";
    final static String PASS = "root";

    public DBConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e2) {
            System.err.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
        }
    }

	public static String getUrl() {
		return URL;
	}

	public static String getLogin() {
		return LOGIN;
	}

	public static String getPass() {
		return PASS;
	}
    
    

}
