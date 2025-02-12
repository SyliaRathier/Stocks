### Guide détaillé pour configurer et exécuter du projet

#### 1. Récupérer le code en le clonant depuis GitHub

- **Cloner le dépôt GitHub** :
  - Ouvrez Eclipse et allez dans `File` > `Import`.
  - Sélectionnez `Git` > `Projects from Git` et cliquez sur `Next`.
  - Choisissez `Clone URI` et collez l'URL du dépôt GitHub que vous souhaitez cloner (https://github.com/SyliaRathier/Stocks).
  - Suivez les instructions pour cloner le dépôt dans votre espace de travail Eclipse.

#### 2. Modification de la classe `DBConnection` avec la bonne connexion

- **Configurer la connexion à la base de données** :
  - Ouvrez la classe `DBConnection` dans votre projet.
  - Assurez-vous que les informations de connexion (URL, login, mot de passe) sont correctement configurées pour votre base de données locale.
  - Par exemple :
    ```java
    public class DBConnection {
    final static String URL = "jdbc:mysql://localhost:3306/app"; // Changer le port pour MySQL (3306 par défaut)
    final static String LOGIN = "root";
    final static String PASS = "";
    //OU
    final static String URL = "jdbc:mariadb://localhost:3307/app";
    final static String LOGIN = "root";
    final static String PASS = "root";

    public DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Pilote JDBC pour MySQL
        } catch (ClassNotFoundException e2) {
            System.err.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
        }
    }
     
    ```

#### 3. Ajout des fichiers JAR en faisant `Build Path`

- **Ajouter les bibliothèques nécessaires** :
  - Faites un clic droit sur votre projet dans l'Explorateur de projets Eclipse.
  - Sélectionnez `Build Path` > `Configure Build Path`.
  - Allez dans l'onglet `Libraries` et cliquez sur `Add External JARs`.
  - Sélectionnez les fichiers JAR nécessaires pour votre projet (par exemple, le driver JDBC pour MySQL).

#### 4. Configurer la base de données locale avec Docker ou WAMP

- **Utiliser Docker** :
  - Installez Docker sur votre machine.
  - Utilisez une image Docker pour MarieDB, par exemple (Vous pouvez télécharger le fichier docker-compose du fichier envoyé)
  - Au même niveau que le fichier docker-compose, ouvrez un termila et lancez la commande suivante :
    ```bash
    docker compose up -d
    ```
  - Connectez-vous à la base de données en utilisant les informations de connexion fournies par Docker.

- **Utiliser WAMP** :
  - Installez WAMP sur votre machine.
  - Démarrez les services MySQL via l'interface WAMP.

#### 5. Importer le fichier SQL pour créer la base de données

- **Créer la base de données** :
  - Utilisez un outil comme phpMyAdmin pour importer le fichier SQL qui contient les instructions pour créer la base de données et les tables nécessaires (fournie dans le   
    dossier zip. 

#### 6. Lancer le projet à partir de la classe `AuthentificationFrame`

- **Exécuter le projet** :
  - Assurez-vous que toutes les modifications sont enregistrées et que le projet est compilé sans erreurs.
  - Faites un clic droit sur la classe `AuthentificationFrame` dans l'Explorateur de projets Eclipse.
  - Sélectionnez `Run As` > `Java Application`.
  - L'application devrait se lancer et afficher la fenêtre d'authentification.
