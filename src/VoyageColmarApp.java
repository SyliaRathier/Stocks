import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import com.formdev.flatlaf.FlatLightLaf;
public class VoyageColmarApp extends JFrame {
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton ajouterLieuButton;
    private JButton precedentButton;
    private JButton suivantButton;
    private JButton modifierButton;
    private JButton supprimerButton;
    private JLabel imageLabel;
    private JLabel descriptionLabel;
    private JLabel adresseLabel;
    private JTextArea descriptionArea;
    private JTextArea adresseArea;
    private JTextArea commentairesArea;
    private JList<String> lieuxList;
    private DefaultListModel<String> listModel;
    private DefaultListModel<String> filteredListModel;  // Modèle filtré pour la recherche
    private int currentLieuIndex;
    private List<LieuTouristique> lieuxTouristiques;
    private Utilisateur utilisateur;
    private JTextField titreField;
    
    private JTextField searchField;  // Champ de recherche
    private JButton searchButton;    // Bouton de recherche

    public VoyageColmarApp(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Mon voyage à Colmar");
        setSize(1000, 700); // Taille plus grande
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

     // Panneaux avec marges et bordures modernes
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Ajout de marges

        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));  // Bordure gris clair pour leftPanel

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));  // BorderLayout pour le rightPanel

        // Composants
        ajouterLieuButton = new JButton("Ajouter"); 
        precedentButton = new JButton("Précédent");
        suivantButton = new JButton("Suivant");
        modifierButton = new JButton(); // Le texte sera défini plus tard
        supprimerButton = new JButton("Supprimer");
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER); // Centrer l'image 
        
        
        
        ajouterLieuButton.setBackground(new Color(0, 204, 102)); // Couleur verte pour "Ajouter"
        ajouterLieuButton.setForeground(Color.WHITE); // Texte en blanc
        ajouterLieuButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Espacement intérieur
        ajouterLieuButton.setFocusPainted(false); 
        
        
        
        
        
        

     // Créer des JTextArea avec un style moderne
        descriptionArea = new JTextArea(5, 20);
        adresseArea = new JTextArea(5, 20);
        commentairesArea = new JTextArea(5, 20);

        // Définir une police moderne
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        adresseArea.setFont(new Font("Arial", Font.PLAIN, 14));
        commentairesArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Appliquer des couleurs de fond plus claires et une bordure moderne
        descriptionArea.setBackground(new Color(242, 242, 242)); // Gris clair
        adresseArea.setBackground(new Color(242, 242, 242));
        commentairesArea.setBackground(new Color(242, 242, 242));

        // Ajouter des bordures légères
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        adresseArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        commentairesArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        descriptionArea.setEditable(false);
        adresseArea.setEditable(false);
        commentairesArea.setEditable(false);

     // Créer le JTextField pour le titre
        titreField = new JTextField(20);

        // Définir une police moderne
        titreField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Appliquer une couleur de fond claire et une bordure discrète
        titreField.setBackground(new Color(242, 242, 242));  // Gris clair
        titreField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));  // Bordure gris clair

        // Appliquer une couleur de texte qui contraste bien
        titreField.setForeground(new Color(50, 50, 50));  // Gris foncé pour le texte

        // Ajouter au panel
        rightPanel.add(titreField, BorderLayout.NORTH);
        titreField.setEditable(false);

        // Liste des lieux
        listModel = new DefaultListModel<>();
        filteredListModel = new DefaultListModel<>();
        lieuxList = new JList<>(filteredListModel);
        JScrollPane listScrollPane = new JScrollPane(lieuxList);

     // Panel de recherche (plus compact)
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));  // Espacement plus large
        searchPanel.setBackground(new Color(240, 240, 240)); // Fond clair pour le panel

        searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14)); // Police moderne
        searchField.setBackground(new Color(255, 255, 255)); // Fond blanc
        searchField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Bordure discrète
        
       

        searchButton = new JButton("🔍");
        searchButton.setBackground(new Color(66, 133, 244)); // Fond bleu
        searchButton.setForeground(Color.WHITE); // Texte en blanc
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Espacement intérieur
        searchButton.setFocusPainted(false); // Retirer l'effet de survol du focus 
        
        
        


        // Ajouter un effet de survol sur le bouton
        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(52, 118, 224)); // Bleu plus foncé
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(66, 133, 244)); // Bleu clair
            }
        }); 
        
        
        

        // Ajouter les composants au panel de recherche
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Ajout au leftPanel
        leftPanel.add(searchPanel, BorderLayout.NORTH); // En haut
        leftPanel.add(listScrollPane, BorderLayout.CENTER); // Au centre

        // Panel pour les boutons d'action (en bas du rightPanel)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10)); // Espacement plus large
        buttonPanel.setBackground(new Color(240, 240, 240));  // Fond clair pour le panel

        // Appliquer un style aux boutons
        precedentButton.setBackground(new Color(66, 133, 244));
        suivantButton.setBackground(new Color(66, 133, 244));
        modifierButton.setBackground(new Color(66, 133, 244));
        supprimerButton.setBackground(new Color(244, 67, 54)); // Rouge pour supprimer

        // Couleur du texte des boutons
        precedentButton.setForeground(Color.WHITE);
        suivantButton.setForeground(Color.WHITE);
        modifierButton.setForeground(Color.WHITE);
        supprimerButton.setForeground(Color.WHITE);

        // Ajouter des effets de survol pour les boutons

        // Ajout des boutons au panel
        buttonPanel.add(precedentButton);
        buttonPanel.add(suivantButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton); 
        buttonPanel.add(ajouterLieuButton); 
        
        

        // Ajout au rightPanel
        rightPanel.add(buttonPanel, BorderLayout.SOUTH); // En bas

        // Méthode pour ajouter un effet de survol sur les boutons
      


        // Ajout des composants au rightPanel (avec BorderLayout)
        rightPanel.add(titreField, BorderLayout.NORTH); // En haut
        rightPanel.add(imageLabel, BorderLayout.CENTER); // Au centre
        rightPanel.add(new JScrollPane(descriptionArea), BorderLayout.WEST);
        rightPanel.add(new JScrollPane(adresseArea), BorderLayout.EAST);
        rightPanel.add(new JScrollPane(commentairesArea), BorderLayout.SOUTH);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH); // En bas

        // Style et marges (plus d'espace)
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ajout des panneaux au mainPanel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        chargerDonnees(); 
     // Rafraîchir la liste des lieux toutes les secondes

        ajouterLieuButton.addActionListener(e -> {
            LieuTouristiqueFrame lieuFrame = new LieuTouristiqueFrame(utilisateur.getIdentifiant());
            lieuFrame.setVisible(true);

            // Attendre que la fenêtre se ferme pour recharger les données
            // Une fois la fenêtre fermée, recharger les lieux
            lieuFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    chargerDonnees(); // Recharger les lieux après l'ajout
                }
            });
        });

        
        
        
     
        precedentButton.addActionListener(e -> afficherLieuPrecedent());
        suivantButton.addActionListener(e -> afficherLieuSuivant()); 
        
        
        
     // Ajouter un ActionListener pour le bouton de recherche
        searchButton.addActionListener(e -> {

            // Appeler la fonction de recherche
            rechercherLieux();

            // Remettre la couleur du bouton après un délai (par exemple 500ms)
            new Timer(500, ev -> searchButton.setBackground(new Color(66, 133, 244))).start(); // Bleu clair
        });
        
        

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

        if (utilisateur.getRoleId() == 1) {
            modifierButton.setText("Modifier");
            ajouterLieuButton.setEnabled(true);
        } else {
            modifierButton.setText("Commenter");
            ajouterLieuButton.setEnabled(false);
        }

        supprimerButton.addActionListener(e -> supprimerLieu());

        lieuxList.addListSelectionListener(e -> afficherLieuSelectionne()); 
        
        
        // ... (Reste du code inchangé pour le chargement des données et les listeners)
      
        
  
        
        

    }

    private void chargerDonnees() {
        // Sauvegarder l'élément actuellement sélectionné
        String selectedValue = lieuxList.getSelectedValue();

        // Charger les lieux touristiques depuis la base de données
        LieuTouristiqueDAO lieuTouristiqueDAO = new LieuTouristiqueDAO();
        lieuxTouristiques = lieuTouristiqueDAO.getListeLieuxTouristiques();

        // Mettre à jour la liste des lieux
        filteredListModel.clear();  // Vider la liste filtrée avant d'ajouter
        for (LieuTouristique lieu : lieuxTouristiques) {
            filteredListModel.addElement(lieu.getTitre());  // Ajouter dans le modèle filtré
        }

        // Restaurer la sélection précédente si possible
        if (selectedValue != null && filteredListModel.contains(selectedValue)) {
            lieuxList.setSelectedValue(selectedValue, true);
        } else if (!lieuxTouristiques.isEmpty()) {
            // Sélectionner le premier élément si la sélection précédente n'existe plus
            currentLieuIndex = 0;
            lieuxList.setSelectedIndex(0);
            afficherLieu(lieuxTouristiques.get(0));
        }
    }

    private void afficherLieuPrecedent() {
        if (currentLieuIndex > 0) {
            currentLieuIndex--;
            lieuxList.setSelectedIndex(currentLieuIndex); 
            afficherLieu(lieuxTouristiques.get(currentLieuIndex));
        }
        verifierNavigation();
    }

    private void afficherLieuSuivant() {
        if (currentLieuIndex < lieuxTouristiques.size() - 1) {
            currentLieuIndex++;
            lieuxList.setSelectedIndex(currentLieuIndex); 
            afficherLieu(lieuxTouristiques.get(currentLieuIndex));
        }
        verifierNavigation();
    }

    private void verifierNavigation() {
        precedentButton.setEnabled(currentLieuIndex > 0);
        suivantButton.setEnabled(currentLieuIndex < lieuxTouristiques.size() - 1);
    }

    private void modifierLieu() {
        descriptionArea.setEditable(true);
        adresseArea.setEditable(true);
        titreField.setEditable(true); 

        modifierButton.setText("Enregistrer");
    }

    private void enregistrerModifications() {
        LieuTouristique lieu = lieuxTouristiques.get(currentLieuIndex);
        lieu.setDescription(descriptionArea.getText());
        lieu.setAdresse(adresseArea.getText());
        lieu.setTitre(titreField.getText()); 

        LieuTouristiqueDAO lieuTouristiqueDAO = new LieuTouristiqueDAO();
        lieuTouristiqueDAO.modifier(lieu);

        afficherLieu(lieu);

        descriptionArea.setEditable(false);
        adresseArea.setEditable(false);
        titreField.setEditable(false); 

        modifierButton.setText("Modifier");

        filteredListModel.set(currentLieuIndex, lieu.getTitre());
    }

    private void afficherLieuSelectionne() {
        int selectedIndex = lieuxList.getSelectedIndex();
        if (selectedIndex != -1) {
            currentLieuIndex = selectedIndex; 
            afficherLieu(lieuxTouristiques.get(selectedIndex));
        }
    }

    private void afficherLieu(LieuTouristique lieu) {
        titreField.setText(lieu.getTitre()); 
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

        CommentaireDAO commentaireDAO = new CommentaireDAO();
        List<Commentaire> commentaires = commentaireDAO.getCommentairesPourLieu(lieu.getIdentifiant());
        commentairesArea.setText("");
        for (Commentaire commentaire : commentaires) {
            commentairesArea.append(commentaire.getTexte() + "\n");
        }

        if(utilisateur.getRoleId() == 2) {
            ConsulteDAO consulteDAO = new ConsulteDAO();
            consulteDAO.enregistrerConsultation(utilisateur.getIdentifiant(), lieu.getIdentifiant());
        }

        verifierNavigation();
    }

    private void supprimerLieu() {
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Êtes-vous sûr de vouloir supprimer ce lieu touristique ?", 
            "Confirmation", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            LieuTouristique lieu = lieuxTouristiques.get(currentLieuIndex);

            LieuTouristiqueDAO lieuTouristiqueDAO = new LieuTouristiqueDAO();
            lieuTouristiqueDAO.supprimer(lieu.getIdentifiant());  

            lieuxTouristiques.remove(currentLieuIndex);
            filteredListModel.remove(currentLieuIndex);

            if (currentLieuIndex > 0) {
                currentLieuIndex--;
            }
            if (!lieuxTouristiques.isEmpty()) {
                afficherLieu(lieuxTouristiques.get(currentLieuIndex));
            }
        }
    }

    private void rechercherLieux() {
        String recherche = searchField.getText().toLowerCase().trim();
        filteredListModel.clear();  // Vider la liste filtrée avant d'ajouter

        for (LieuTouristique lieu : lieuxTouristiques) {
            if (lieu.getTitre().toLowerCase().contains(recherche) || lieu.getAdresse().toLowerCase().contains(recherche)) {
                filteredListModel.addElement(lieu.getTitre());
            }
        }
    }

    public static void main(String[] args) {
    	try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // Applique un look moderne
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new VoyageColmarApp(null).setVisible(true));
    }
}
