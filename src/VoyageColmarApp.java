import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VoyageColmarApp extends JFrame {
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton ajouterLieuButton;
    private JButton precedentButton;
    private JButton suivantButton;
    private JButton modifierButton;
    //private JButton commenterButton;    
    private JLabel imageLabel;
    private JTextArea descriptionArea;
    private JTextArea adresseArea;
    private JTextArea commentairesArea;
    private JList<String> lieuxList;
    private DefaultListModel<String> listModel;
    private int currentLieuIndex;
    private List<LieuTouristique> lieuxTouristiques;
    private int utilisateurId;


    public VoyageColmarApp(int utilisateurId) {
        // Initialisation de la fenêtre
        this.utilisateurId = utilisateurId;
        setTitle("Mon voyage à Colmar");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialisation des panneaux
        mainPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel();
        rightPanel = new JPanel(new BorderLayout());

        // Initialisation des composants
        ajouterLieuButton = new JButton("Ajouter un lieu");
        precedentButton = new JButton("Précédent");
        suivantButton = new JButton("Suivant");
        modifierButton = new JButton("Modifier");
        //commenterButton = new JButton("Commenter");
        imageLabel = new JLabel();
        descriptionArea = new JTextArea();
        adresseArea = new JTextArea();
        commentairesArea = new JTextArea();

        // Vérification de l'ID utilisateur pour désactiver le bouton
        if (utilisateurId == 1) {
            ajouterLieuButton.setEnabled(false);
            modifierButton.setEnabled(false);
            modifierButton.setText("Commenter");

//            modifierButton.setVisible(false);
//            commenterButton.setVisible(true);
        }
        if (utilisateurId == 2) {
            modifierButton.setEnabled(true);
            modifierButton.setText("Modifier");

        }

        // Initialisation de la liste des lieux
        listModel = new DefaultListModel<>();
        lieuxList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(lieuxList);

        // Ajout des composants aux panneaux
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(ajouterLieuButton, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(lieuxList), BorderLayout.CENTER);

        rightPanel.add(imageLabel, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        rightPanel.add(new JScrollPane(adresseArea), BorderLayout.SOUTH);
        rightPanel.add(new JScrollPane(commentairesArea), BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(precedentButton);
        buttonPanel.add(suivantButton);
        buttonPanel.add(modifierButton);
        //buttonPanel.add(commenterButton);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Ajout du panneau principal à la fenêtre
        add(mainPanel);

        // Chargement des données initiales
        chargerDonnees();

        // Ajout des écouteurs d'événements
        ajouterLieuButton.addActionListener(e -> {
            new LieuTouristiqueFrame(utilisateurId).setVisible(true); // Passer l'ID de l'utilisateur connecté
            dispose();
        });

        precedentButton.addActionListener(e -> afficherLieuPrecedent());

        suivantButton.addActionListener(e -> afficherLieuSuivant());

        modifierButton.addActionListener(e -> {
            if (utilisateurId == 2) {
                modifierLieu();
            }
            if(utilisateurId == 1) {
            	// action commenter
            }
        });
        


        lieuxList.addListSelectionListener(e -> {
            afficherLieuSelectionne();
            currentLieuIndex = lieuxList.getSelectedIndex();
        });
    }

    private void chargerDonnees() {
        // Charger les lieux touristiques depuis la base de données
        LieuTouristiqueDAO lieuTouristiqueDAO = new LieuTouristiqueDAO();
        lieuxTouristiques = lieuTouristiqueDAO.getListeLieuxTouristiques();

        // Mettre à jour la liste des lieux
        listModel.clear();
        for (LieuTouristique lieu : lieuxTouristiques) {
            listModel.addElement(lieu.getTitre());
        }

        // Afficher le premier lieu
        if (!lieuxTouristiques.isEmpty()) {
            afficherLieu(lieuxTouristiques.get(0));
        }
    }

    private void ajouterLieu() {
        // Logique pour ajouter un nouveau lieu
    }

    private void afficherLieuPrecedent() {
        if (currentLieuIndex > 0) {
            currentLieuIndex--;
            lieuxList.setSelectedIndex(currentLieuIndex);
            afficherLieu(lieuxTouristiques.get(currentLieuIndex));
        }
    }

    private void afficherLieuSuivant() {
        if (currentLieuIndex < lieuxTouristiques.size() - 1) {
            currentLieuIndex++;
            lieuxList.setSelectedIndex(currentLieuIndex);
            afficherLieu(lieuxTouristiques.get(currentLieuIndex));
        }
    }

    private void modifierLieu() {
        int selectedIndex = lieuxList.getSelectedIndex();
        if (selectedIndex != -1) {
            LieuTouristique lieu = lieuxTouristiques.get(selectedIndex);
            new LieuTouristiqueFrame(utilisateurId, lieu).setVisible(true); // Passer le lieu à modifier
            dispose();
        }
    }

    private void afficherLieuSelectionne() {
        int selectedIndex = lieuxList.getSelectedIndex();
        if (selectedIndex != -1) {
            afficherLieu(lieuxTouristiques.get(selectedIndex));
        }
    }

    private void afficherLieu(LieuTouristique lieu) {
        // Mettre à jour l'interface avec les informations du lieu
//        ImageIcon imageIcon = new ImageIcon(lieu.getImage());
//        imageLabel.setIcon(imageIcon);
        descriptionArea.setText(lieu.getDescription());
        adresseArea.setText(lieu.getAdresse());

        // Charger les commentaires pour le lieu
        CommentaireDAO commentaireDAO = new CommentaireDAO();
        List<Commentaire> commentaires = commentaireDAO.getCommentairesPourLieu(lieu.getIdentifiant());
        commentairesArea.setText("");
        for (Commentaire commentaire : commentaires) {
            commentairesArea.append(commentaire.getTexte() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VoyageColmarApp(1).setVisible(true); // Exemple avec un ID utilisateur
        });
    }
}
