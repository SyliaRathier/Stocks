import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InscriptionFrame extends JFrame {
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JPasswordField motDePasseField;
    private JPasswordField confirmationMotDePasseField;
    private JCheckBox guideCheckBox;
    private JCheckBox touristeCheckBox;
    private JButton inscriptionButton;
    private int utilisateurId; // Ajouter un attribut pour stocker l'ID de l'utilisateur connecté

    public InscriptionFrame() {
        setTitle("Formulaire d'Inscription");
        setSize(600, 400); // Agrandir la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Ajouter des marges

        nomField = new JTextField(20);
        prenomField = new JTextField(20);
        emailField = new JTextField(20);
        motDePasseField = new JPasswordField(20);
        confirmationMotDePasseField = new JPasswordField(20);
        guideCheckBox = new JCheckBox("Guide");
        touristeCheckBox = new JCheckBox("Touriste");
        inscriptionButton = new JButton("S'inscrire");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nom:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nomField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Prénom:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(prenomField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Mot de passe:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(motDePasseField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Confirmer le mot de passe:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(confirmationMotDePasseField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Rôle:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        add(guideCheckBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        add(touristeCheckBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(inscriptionButton, gbc);

        inscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscrireUtilisateur();
            }
        });
    }

    private void inscrireUtilisateur() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String motDePasse = new String(motDePasseField.getPassword());
        String confirmationMotDePasse = new String(confirmationMotDePasseField.getPassword());
        boolean isGuide = guideCheckBox.isSelected();
        boolean isTouriste = touristeCheckBox.isSelected();

        if (!motDePasse.equals(confirmationMotDePasse)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas.");
            return;
        }

        int roleId = isGuide ? 1 : (isTouriste ? 2 : 0);
        if (roleId == 0) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un rôle.");
            return;
        }

        Utilisateur nouvUtilisateur = new Utilisateur(0, nom, prenom, email, motDePasse, roleId);
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        int result = utilisateurDAO.ajouter(nouvUtilisateur);

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Inscription réussie!");
            // Récupérer l'ID de l'utilisateur nouvellement inscrit
            utilisateurId = utilisateurDAO.getUtilisateurByEmail(email).getIdentifiant();
            // Rediriger vers VoyageColmarApp
            new VoyageColmarApp(utilisateurId).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InscriptionFrame().setVisible(true);
            }
        });
    }
}
