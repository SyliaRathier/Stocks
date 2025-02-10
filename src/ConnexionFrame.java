import javax.swing.*;
import java.awt.*;

public class ConnexionFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public ConnexionFrame() {
        setTitle("Connexion");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Mot de passe:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton connectButton = new JButton("Se connecter");
        panel.add(connectButton);

        add(panel);

        connectButton.addActionListener(e -> {
            String email = emailField.getText();
            char[] password = passwordField.getPassword();

            UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            Utilisateur utilisateur = utilisateurDAO.getUtilisateurByEmail(email);

            if (utilisateur != null && utilisateurDAO.getPasswordEncoder().matches(new String(password), utilisateur.getMotDePasse())) {
                JOptionPane.showMessageDialog(this, "Connexion réussie!");
                // Rediriger vers VoyageColmarApp
                new VoyageColmarApp().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConnexionFrame frame = new ConnexionFrame();
            frame.setVisible(true);
        });
    }
}
