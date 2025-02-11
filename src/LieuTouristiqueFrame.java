import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LieuTouristiqueFrame extends JFrame {
    private JTextField titreField;
    private JTextArea descriptionArea;
    private JTextField adresseFiels;
    private JFileChooser fileChooser;
    private JButton imageButton;
    private JButton creerButton;
    private byte[] selectedImage;
    private int guideId;
    private LieuTouristique lieuAModifier;

    public LieuTouristiqueFrame(int guideId) {
        this(guideId, null);
    }

    public LieuTouristiqueFrame(int guideId, LieuTouristique lieu) {
        this.guideId = guideId;
        this.lieuAModifier = lieu;
        setTitle(lieu == null ? "Créer un Lieu Touristique" : "Modifier un Lieu Touristique");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        titreField = new JTextField(20);
        descriptionArea = new JTextArea(5, 20);
        adresseFiels = new JTextField(70);
        fileChooser = new JFileChooser();
        imageButton = new JButton("Choisir une image");
        creerButton = new JButton(lieu == null ? "Créer" : "Modifier");

        if (lieu != null) {
            titreField.setText(lieu.getTitre());
            descriptionArea.setText(lieu.getDescription());
            adresseFiels.setText(lieu.getAdresse());
            selectedImage = lieu.getImage();
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Titre:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(titreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(new JScrollPane(descriptionArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Adresse:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(adresseFiels, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Image:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(imageButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(creerButton, gbc);

        imageButton.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileInputStream fis = new FileInputStream(file)) {
                    selectedImage = new byte[(int) file.length()];
                    fis.read(selectedImage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        creerButton.addActionListener(e -> {
            if (lieuAModifier == null) {
                creerLieuTouristique();
            } else {
                modifierLieuTouristique();
            }
        });
    }

    private void creerLieuTouristique() {
        String titre = titreField.getText();
        String description = descriptionArea.getText();
        String adresse = adresseFiels.getText();

        if (titre.isEmpty() || description.isEmpty() || adresse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        LieuTouristique nouvLieu = new LieuTouristique(0, titre, description, adresse, selectedImage, guideId);
        LieuTouristiqueDAO lieuTouristiqueDAO = new LieuTouristiqueDAO();
        int result = lieuTouristiqueDAO.ajouter(nouvLieu);

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Lieu touristique créé avec succès!");
            new VoyageColmarApp(guideId).setVisible(true); // Passer l'ID de l'utilisateur connecté
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la création du lieu touristique.");
        }
    }

    private void modifierLieuTouristique() {
        String titre = titreField.getText();
        String description = descriptionArea.getText();
        String adresse = adresseFiels.getText();

        if (titre.isEmpty() || description.isEmpty() || adresse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        LieuTouristique lieuModifie = new LieuTouristique(lieuAModifier.getIdentifiant(), titre, description, adresse, selectedImage, guideId);
        LieuTouristiqueDAO lieuTouristiqueDAO = new LieuTouristiqueDAO();
        try {
            lieuTouristiqueDAO.modifier(lieuModifie);
            JOptionPane.showMessageDialog(this, "Lieu touristique modifié avec succès!");
            new VoyageColmarApp(guideId).setVisible(true); // Passer l'ID de l'utilisateur connecté
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la modification du lieu touristique: " + e.getMessage());
        }
    }

}
