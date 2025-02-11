public class DBConnection {
    final static String URL = "jdbc:mysql://localhost:3306/app"; // Changement de port pour MySQL
    final static String LOGIN = "root";
    final static String PASS = "";

    public DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Changement du driver pour MySQL
        } catch (ClassNotFoundException e2) {
            System.err.println("Impossible de charger le pilote MySQL, ne pas oublier d'importer le fichier .jar dans le projet");
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
