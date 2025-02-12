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
    private JTextField adresseField;
    private JFileChooser fileChooser;
    private JButton imageButton;
    private JButton creerButton;
    private byte[] selectedImage;
    private int guideId;

    public LieuTouristiqueFrame(int guideId) {
        this.guideId = guideId;
        setTitle("Créer un Lieu Touristique");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        titreField = new JTextField(20);
        descriptionArea = new JTextArea(5, 20);
        adresseField = new JTextField(20);
        fileChooser = new JFileChooser();
        imageButton = new JButton("Choisir une image");
        creerButton = new JButton("Créer");

        titreField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        adresseField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        imageButton.setBackground(new Color(66, 133, 244));
        imageButton.setForeground(Color.WHITE);
        imageButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        imageButton.setFocusPainted(false);

        creerButton.setBackground(new Color(66, 133, 244));
        creerButton.setForeground(Color.WHITE);
        creerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        creerButton.setFocusPainted(false);

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
        add(adresseField, gbc);

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

        creerButton.addActionListener(e -> creerLieuTouristique());
    }

    private void creerLieuTouristique() {
        String titre = titreField.getText();
        String description = descriptionArea.getText();
        String adresse = adresseField.getText();

        if (titre.isEmpty() || description.isEmpty() || adresse.isEmpty() || selectedImage == null) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        LieuTouristique nouvLieu = new LieuTouristique(0, titre, description, adresse, selectedImage, guideId);
        LieuTouristiqueDAO lieuTouristiqueDAO = new LieuTouristiqueDAO();
        int result = lieuTouristiqueDAO.ajouter(nouvLieu);

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Lieu touristique créé avec succès!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la création du lieu touristique.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LieuTouristiqueFrame frame = new LieuTouristiqueFrame(1); // Exemple avec un ID utilisateur
            frame.setVisible(true);
        });
    }
}
