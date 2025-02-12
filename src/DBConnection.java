public class DBConnection {
    final static String URL = "jdbc:mysql://localhost:3306/app"; // Changer le port pour MySQL (3306 par défaut)
    final static String LOGIN = "root";
    final static String PASS = "";

    public DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Pilote JDBC pour MySQL
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
