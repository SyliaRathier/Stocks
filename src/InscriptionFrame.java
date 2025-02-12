import javax.swing.*;
import java.awt.*;

public class InscriptionFrame extends JFrame {
    private JTextField nomField, prenomField, emailField;
    private JPasswordField motDePasseField, confirmationMotDePasseField;
    private JRadioButton guideRadio, touristeRadio;
    private JButton inscriptionButton;
    private int utilisateurId;

    public InscriptionFrame() {
        setTitle("Inscription");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Ajouter les champs de texte
        nomField = createTextField("Nom");
        prenomField = createTextField("Prénom");
        emailField = createTextField("Email");
        motDePasseField = createPasswordField("Mot de passe");
        confirmationMotDePasseField = createPasswordField("Confirmer le mot de passe");

        // Ajouter les champs au panel
        panel.add(nomField);
        panel.add(prenomField);
        panel.add(emailField);
        panel.add(motDePasseField);
        panel.add(confirmationMotDePasseField);

        // Rôle (Guide / Touriste) avec Radio Buttons
        JPanel rolePanel = new JPanel();
        rolePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        rolePanel.setBorder(BorderFactory.createTitledBorder("Rôle"));

        guideRadio = new JRadioButton("Guide");
        touristeRadio = new JRadioButton("Touriste");

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(guideRadio);
        roleGroup.add(touristeRadio);

        rolePanel.add(guideRadio);
        rolePanel.add(touristeRadio);
        panel.add(rolePanel);

        // Bouton d'inscription stylé
        inscriptionButton = new JButton("S'inscrire");
        inscriptionButton.setFont(new Font("Arial", Font.BOLD, 14));
        inscriptionButton.setBackground(new Color(30, 144, 255));
        inscriptionButton.setForeground(Color.WHITE);
        inscriptionButton.setFocusPainted(false);
        panel.add(inscriptionButton);

        add(panel);

        // Ajouter l'action du bouton
        inscriptionButton.addActionListener(e -> inscrireUtilisateur());
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setPreferredSize(new Dimension(200, 30)); // Réduction de la hauteur
        field.setBorder(BorderFactory.createTitledBorder(placeholder));
        return field;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(20);
        field.setPreferredSize(new Dimension(200, 30)); // Réduction de la hauteur
        field.setBorder(BorderFactory.createTitledBorder(placeholder));
        return field;
    }

    private void inscrireUtilisateur() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String motDePasse = new String(motDePasseField.getPassword());
        String confirmationMotDePasse = new String(confirmationMotDePasseField.getPassword());
        boolean isGuide = guideRadio.isSelected();
        boolean isTouriste = touristeRadio.isSelected();

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
            utilisateurId = utilisateurDAO.getUtilisateurByEmail(email).getIdentifiant();
            new VoyageColmarApp(nouvUtilisateur).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'inscription.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InscriptionFrame().setVisible(true));
    }
}
