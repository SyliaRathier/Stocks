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
    private JButton supprimerButton; // Nouveau bouton supprimer
    private JLabel imageLabel;
    private JLabel descriptionLabel;
    private JLabel adresseLabel;
    private JTextArea descriptionArea;
    private JTextArea adresseArea;
    private JTextArea commentairesArea;
    private JList<String> lieuxList;
    private DefaultListModel<String> listModel;
    private int currentLieuIndex;
    private List<LieuTouristique> lieuxTouristiques;
    private Utilisateur utilisateur;
    private JTextField titreField; // Nouveau champ pour le titre

    public VoyageColmarApp(Utilisateur utilisateur) {
        // Initialisation de la fenêtre
        this.utilisateur = utilisateur;
        setTitle("Mon voyage à Colmar");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialisation des panneaux
        mainPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // Utilisation de BoxLayout pour empiler les composants

        // Initialisation des composants
        ajouterLieuButton = new JButton("Ajouter un lieu");
        precedentButton = new JButton("Précédent");
        suivantButton = new JButton("Suivant");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer"); // Nouveau bouton
        imageLabel = new JLabel();

        // Initialiser les zones de texte
        descriptionArea = new JTextArea(5, 20);
        adresseArea = new JTextArea(5, 20);
        commentairesArea = new JTextArea(5, 20);

        // Rendre ces zones de texte non modifiables au départ
        descriptionArea.setEditable(false);
        adresseArea.setEditable(false);
        commentairesArea.setEditable(false);

        // Utilisation de JLabel pour afficher les informations non modifiables
        descriptionLabel = new JLabel();
        adresseLabel = new JLabel();

        // Initialiser le champ titre
        titreField = new JTextField(20);
        titreField.setEditable(false); // Par défaut, le champ est non modifiable

        // Initialisation de la liste des lieux
        listModel = new DefaultListModel<>();
        lieuxList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(lieuxList);

        // Ajout des composants aux panneaux
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(ajouterLieuButton, BorderLayout.NORTH);
        leftPanel.add(listScrollPane, BorderLayout.CENTER);

        // Ajouter l'image, le titre, la description, l'adresse et les commentaires au rightPanel
        rightPanel.add(new JLabel("Titre :"));
        rightPanel.add(titreField); // Afficher le champ titre
        rightPanel.add(imageLabel);
        rightPanel.add(new JScrollPane(descriptionArea)); // Afficher la description modifiable
        rightPanel.add(new JScrollPane(adresseArea)); // Afficher l'adresse modifiable
        rightPanel.add(new JScrollPane(commentairesArea));

        // Vérification de l'ID utilisateur pour désactiver les boutons
        if (utilisateur.getRoleId() == 2) { // Rôle de touriste
            ajouterLieuButton.setEnabled(false);
            modifierButton.setText("Commenter");
            supprimerButton.setVisible(false); // Masquer le bouton "Supprimer" pour les touristes
        }

        if (utilisateur.getRoleId() == 1) { // Rôle de guide
            modifierButton.setText("Modifier");
            supprimerButton.setVisible(true); // Afficher le bouton "Supprimer" pour les guides
        }

        // Ajout du bouton de navigation au bas du rightPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(precedentButton);
        buttonPanel.add(suivantButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton); // Ajouter le bouton "Supprimer"
        rightPanel.add(buttonPanel);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // Ajout du panneau principal à la fenêtre
        add(mainPanel);

        // Chargement des données initiales
        chargerDonnees();

        // Ajout des écouteurs d'événements
        ajouterLieuButton.addActionListener(e -> new LieuTouristiqueFrame(utilisateur.getIdentifiant()).setVisible(true)); // Passer l'ID de l'utilisateur connecté

        precedentButton.addActionListener(e -> afficherLieuPrecedent());
        suivantButton.addActionListener(e -> afficherLieuSuivant());

        modifierButton.addActionListener(e -> {
            if (utilisateur.getRoleId() == 1) {
                if ("Modifier".equals(modifierButton.getText())) {
                    modifierLieu();
                } else {
                    enregistrerModifications();
                }
            }
            if (utilisateur.getRoleId() == 2) {
                // action commenter
            }
        });

        supprimerButton.addActionListener(e -> supprimerLieu()); // Action pour le bouton "Supprimer"

        lieuxList.addListSelectionListener(e -> afficherLieuSelectionne());
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
            currentLieuIndex = 0; // Initialiser l'index sur le premier élément
            afficherLieu(lieuxTouristiques.get(0));
        }
    }

    private void afficherLieuPrecedent() {
        if (currentLieuIndex > 0) {
            currentLieuIndex--;
            lieuxList.setSelectedIndex(currentLieuIndex); // Mettre à jour la sélection
            afficherLieu(lieuxTouristiques.get(currentLieuIndex));
        }
        verifierNavigation();
    }

    private void afficherLieuSuivant() {
        if (currentLieuIndex < lieuxTouristiques.size() - 1) {
            currentLieuIndex++;
            lieuxList.setSelectedIndex(currentLieuIndex); // Mettre à jour la sélection
            afficherLieu(lieuxTouristiques.get(currentLieuIndex));
        }
        verifierNavigation();
    }

    private void verifierNavigation() {
        precedentButton.setEnabled(currentLieuIndex > 0);
        suivantButton.setEnabled(currentLieuIndex < lieuxTouristiques.size() - 1);
    }

    private void modifierLieu() {
        // Rendre les champs modifiables
        descriptionArea.setEditable(true);
        adresseArea.setEditable(true);
        titreField.setEditable(true); // Rendre le champ titre modifiable

        // Changer le texte du bouton Modifier en "Enregistrer"
        modifierButton.setText("Enregistrer");
    }

    private void enregistrerModifications() {
        // Récupérer l'objet LieuTouristique que l'on souhaite modifier
        LieuTouristique lieu = lieuxTouristiques.get(currentLieuIndex);

        // Mettre à jour les informations de l'objet avec les nouvelles valeurs
        lieu.setDescription(descriptionArea.getText());
        lieu.setAdresse(adresseArea.getText());
        lieu.setTitre(titreField.getText()); // Mettre à jour le titre

        // Appeler la méthode de DAO pour enregistrer les modifications dans la base de données
        LieuTouristiqueDAO lieuTouristiqueDAO = new LieuTouristiqueDAO();
        lieuTouristiqueDAO.modifier(lieu);  // Met à jour les informations du lieu dans la base de données

        // Mettre à jour l'affichage
        afficherLieu(lieu);

        // Rendre les champs non modifiables après l'enregistrement
        descriptionArea.setEditable(false);
        adresseArea.setEditable(false);
        titreField.setEditable(false); // Rendre le champ titre non modifiable

        // Réinitialiser le bouton Modifier pour qu'il redevienne fonctionnel pour une autre modification
        modifierButton.setText("Modifier");

        // Mettre à jour la liste à gauche avec le nouveau titre
        listModel.set(currentLieuIndex, lieu.getTitre());
    }

    private void afficherLieuSelectionne() {
        int selectedIndex = lieuxList.getSelectedIndex();
        if (selectedIndex != -1) {
            currentLieuIndex = selectedIndex; // Mettre à jour l'index actuel
            afficherLieu(lieuxTouristiques.get(selectedIndex));
        }
    }

    private void afficherLieu(LieuTouristique lieu) {
        // Mettre à jour l'interface avec les informations du lieu
        titreField.setText(lieu.getTitre()); // Afficher le titre du lieu
        byte[] imageData = lieu.getImage();
        if (imageData != null && imageData.length > 0) {
            ImageIcon imageIcon = new ImageIcon(imageData);
            Image img = imageIcon.getImage();
            Image scaledImage = img.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            imageLabel.setIcon(null);
        }

        descriptionArea.setText(lieu.getDescription());
        adresseArea.setText(lieu.getAdresse());

        // Charger les commentaires pour le lieu
        CommentaireDAO commentaireDAO = new CommentaireDAO();
        List<Commentaire> commentaires = commentaireDAO.getCommentairesPourLieu(lieu.getIdentifiant());
        commentairesArea.setText("");
        for (Commentaire commentaire : commentaires) {
            commentairesArea.append(commentaire.getTexte() + "\n");
        }

        // Enregistrer la consultation
        if(utilisateur.getRoleId() == 2) {
            ConsulteDAO consulteDAO = new ConsulteDAO();
            consulteDAO.enregistrerConsultation(utilisateur.getIdentifiant(), lieu.getIdentifiant());
        }

        // Activer/désactiver les boutons de navigation
        verifierNavigation();
    }

    private void supprimerLieu() {
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce lieu touristique ?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            // Récupérer l'objet LieuTouristique actuel
            LieuTouristique lieu = lieuxTouristiques.get(currentLieuIndex);

            // Passer l'identifiant du lieu à la méthode supprimer de LieuTouristiqueDAO
            LieuTouristiqueDAO lieuTouristiqueDAO = new LieuTouristiqueDAO();
            lieuTouristiqueDAO.supprimer(lieu.getIdentifiant());  // Utilisation de l'identifiant du lieu

            // Mettre à jour l'affichage après la suppression
            lieuxTouristiques.remove(currentLieuIndex);
            listModel.remove(currentLieuIndex);

            // Afficher le lieu précédent, si disponible
            if (currentLieuIndex > 0) {
                currentLieuIndex--;
            }
            if (!lieuxTouristiques.isEmpty()) {
                afficherLieu(lieuxTouristiques.get(currentLieuIndex));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VoyageColmarApp(null).setVisible(true); // Exemple avec un ID utilisateur
        });
    }
}
