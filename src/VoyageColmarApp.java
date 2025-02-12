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
    private JLabel titreLabel;
    private JTextArea descriptionArea;
    private JTextArea adresseArea;
    private JList<String> commentairesList;
    private DefaultListModel<String> commentairesListModel;
    private JList<String> lieuxList;
    private DefaultListModel<String> listModel;
    private DefaultListModel<String> filteredListModel;
    private int currentLieuIndex;
    private List<LieuTouristique> lieuxTouristiques;
    private Utilisateur utilisateur;
    private JTextField searchField;
    private JButton searchButton;
    private JButton afficherCommentairesButton;

    public VoyageColmarApp(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Mon voyage à Colmar");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        ajouterLieuButton = new JButton("Ajouter");
        precedentButton = new JButton("Précédent");
        suivantButton = new JButton("Suivant");
        modifierButton = new JButton();
        supprimerButton = new JButton("Supprimer");
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        ajouterLieuButton.setBackground(new Color(0, 204, 102));
        ajouterLieuButton.setForeground(Color.WHITE);
        ajouterLieuButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        ajouterLieuButton.setFocusPainted(false);

        // Réduire la hauteur des JTextArea
        descriptionArea = new JTextArea(2, 20); // 2 lignes visibles
        adresseArea = new JTextArea(1, 20); // 1 ligne visible
        commentairesListModel = new DefaultListModel<>();
        commentairesList = new JList<>(commentairesListModel);

        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        adresseArea.setFont(new Font("Arial", Font.PLAIN, 14));
        commentairesList.setFont(new Font("Arial", Font.PLAIN, 14));

        descriptionArea.setBackground(new Color(242, 242, 242));
        adresseArea.setBackground(new Color(242, 242, 242));
        commentairesList.setBackground(new Color(242, 242, 242));

        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        adresseArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        commentairesList.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        descriptionArea.setEditable(false);
        adresseArea.setEditable(false);

        titreLabel = new JLabel();
        titreLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations"));
        infoPanel.add(titreLabel);
        infoPanel.add(new JScrollPane(descriptionArea));
        infoPanel.add(new JScrollPane(adresseArea));

        JPanel commentairesPanel = new JPanel();
        commentairesPanel.setLayout(new BorderLayout());
        commentairesPanel.setBorder(BorderFactory.createTitledBorder("Commentaires"));
        commentairesPanel.add(new JScrollPane(commentairesList), BorderLayout.CENTER);

        listModel = new DefaultListModel<>();
        filteredListModel = new DefaultListModel<>();
        lieuxList = new JList<>(filteredListModel);
        JScrollPane listScrollPane = new JScrollPane(lieuxList);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(240, 240, 240));

        searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBackground(new Color(255, 255, 255));
        searchField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        searchButton = new JButton("🔍");
        searchButton.setBackground(new Color(66, 133, 244));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        searchButton.setFocusPainted(false); 
        
        
        
    
        
        
        

        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(52, 118, 224));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                searchButton.setBackground(new Color(66, 133, 244));
            }
        });

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        leftPanel.add(searchPanel, BorderLayout.NORTH);
        leftPanel.add(listScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        precedentButton.setBackground(new Color(66, 133, 244));
        suivantButton.setBackground(new Color(66, 133, 244));
        modifierButton.setBackground(new Color(66, 133, 244));
        supprimerButton.setBackground(new Color(244, 67, 54));

        precedentButton.setForeground(Color.WHITE);
        suivantButton.setForeground(Color.WHITE);
        modifierButton.setForeground(Color.WHITE);
        supprimerButton.setForeground(Color.WHITE);

        buttonPanel.add(precedentButton);
        buttonPanel.add(suivantButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton);
        buttonPanel.add(ajouterLieuButton);

        rightPanel.add(imageLabel, BorderLayout.NORTH);
        rightPanel.add(infoPanel, BorderLayout.CENTER);
        rightPanel.add(commentairesPanel, BorderLayout.SOUTH);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);
        
        // Ajouter un bouton pour afficher les commentaires
        afficherCommentairesButton = new JButton("Afficher les Commentaires");
        afficherCommentairesButton.setBackground(new Color(0, 204, 102));
        afficherCommentairesButton.setForeground(Color.WHITE);
        afficherCommentairesButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        afficherCommentairesButton.setFocusPainted(false);

        // Ajouter l'action du bouton
        afficherCommentairesButton.addActionListener(e -> afficherCommentaires());
        
        // Panel des boutons
        buttonPanel.add(afficherCommentairesButton);

        // Ajouter le panel des boutons dans rightPanel
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        chargerDonnees();

        ajouterLieuButton.addActionListener(e -> {
            LieuTouristiqueFrame lieuFrame = new LieuTouristiqueFrame(utilisateur.getIdentifiant());
            lieuFrame.setVisible(true);

            lieuFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    chargerDonnees();
                }
            });
        });

        precedentButton.addActionListener(e -> afficherLieuPrecedent());
        suivantButton.addActionListener(e -> afficherLieuSuivant());

        searchButton.addActionListener(e -> {
            rechercherLieux();
            new Timer(500, ev -> searchButton.setBackground(new Color(66, 133, 244))).start();
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
                ouvrirFormulaireCommentaire();
            }
        });

        if (utilisateur.getRoleId() == 1) {
            modifierButton.setText("Modifier");
            ajouterLieuButton.setEnabled(true);
        } else {
            modifierButton.setText("Commenter");
            ajouterLieuButton.setEnabled(false);
            supprimerButton.setEnabled(false);
        }

        supprimerButton.addActionListener(e -> supprimerLieu());

        lieuxList.addListSelectionListener(e -> afficherLieuSelectionne());
    }

    private void ouvrirFormulaireCommentaire() {
        if (currentLieuIndex >= 0 && currentLieuIndex < lieuxTouristiques.size()) {
            LieuTouristique lieu = lieuxTouristiques.get(currentLieuIndex);
            CommentaireFrame commentaireFrame = new CommentaireFrame(utilisateur.getIdentifiant(), lieu.getIdentifiant());
            commentaireFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un lieu pour commenter.");
        }
    }

    private void chargerDonnees() {
        String selectedValue = lieuxList.getSelectedValue();

        LieuTouristiqueDAO lieuTouristiqueDAO = new LieuTouristiqueDAO();
        lieuxTouristiques = lieuTouristiqueDAO.getListeLieuxTouristiques();

        filteredListModel.clear();
        for (LieuTouristique lieu : lieuxTouristiques) {
            filteredListModel.addElement(lieu.getTitre());
        }

        if (selectedValue != null && filteredListModel.contains(selectedValue)) {
            lieuxList.setSelectedValue(selectedValue, true);
        } else if (!lieuxTouristiques.isEmpty()) {
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
    private void afficherCommentaires() {
        if (currentLieuIndex >= 0 && currentLieuIndex < lieuxTouristiques.size()) {
            LieuTouristique lieu = lieuxTouristiques.get(currentLieuIndex);
            FenetreCommentaires fenetreCommentaires = new FenetreCommentaires(lieu);
            fenetreCommentaires.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un lieu pour afficher les commentaires.");
        }
    }
    private void verifierNavigation() {
        precedentButton.setEnabled(currentLieuIndex > 0);
        suivantButton.setEnabled(currentLieuIndex < lieuxTouristiques.size() - 1);
    }

    private void modifierLieu() {
        descriptionArea.setEditable(true);
        adresseArea.setEditable(true);

        modifierButton.setText("Enregistrer");
    }

    private void enregistrerModifications() {
        LieuTouristique lieu = lieuxTouristiques.get(currentLieuIndex);
        lieu.setDescription(descriptionArea.getText());
        lieu.setAdresse(adresseArea.getText());
        lieu.setTitre(titreLabel.getText());

        LieuTouristiqueDAO lieuTouristiqueDAO = new LieuTouristiqueDAO();
        lieuTouristiqueDAO.modifier(lieu);

        afficherLieu(lieu);

        descriptionArea.setEditable(false);
        adresseArea.setEditable(false);

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
        titreLabel.setText(lieu.getTitre());
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
        commentairesListModel.clear();
        for (Commentaire commentaire : commentaires) {
            System.out.println(commentaire.getTexte()); // Pour vérifier les commentaires récupérés
            commentairesListModel.addElement(commentaire.getTexte());
        }

        if (utilisateur.getRoleId() == 2) {
            ConsulteDAO consulteDAO = new ConsulteDAO();
            consulteDAO.enregistrerConsultation(utilisateur.getIdentifiant(), lieu.getIdentifiant());
        }

        // Rafraîchir la liste des commentaires
        commentairesList.setModel(commentairesListModel);
        commentairesList.revalidate();
        commentairesList.repaint();

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
        filteredListModel.clear();

        for (LieuTouristique lieu : lieuxTouristiques) {
            if (lieu.getTitre().toLowerCase().contains(recherche) || lieu.getAdresse().toLowerCase().contains(recherche)) {
                filteredListModel.addElement(lieu.getTitre());
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new VoyageColmarApp(null).setVisible(true));
    }
}
