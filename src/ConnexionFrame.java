import javax.swing.*;
import java.awt.*;

public class ConnexionFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public ConnexionFrame() {
        setTitle("Connexion");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Appliquer un style uniquement à cette fenêtre
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(40, 40, 40));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JButton connectButton = new JButton("Se connecter");
        connectButton.setFont(new Font("Arial", Font.BOLD, 14));
        connectButton.setBackground(new Color(30, 144, 255)); // Bleu
        connectButton.setForeground(Color.WHITE);
        connectButton.setFocusPainted(false);
        connectButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        connectButton.addActionListener(e -> {
            String email = emailField.getText();
            char[] password = passwordField.getPassword();

            UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
            Utilisateur utilisateur = utilisateurDAO.getUtilisateurByEmail(email);

            if (utilisateur != null && utilisateurDAO.getPasswordEncoder().matches(new String(password), utilisateur.getMotDePasse())) {
                JOptionPane.showMessageDialog(this, "Connexion réussie!");
                new VoyageColmarApp(utilisateur).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.");
            }
        });

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(connectButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConnexionFrame frame = new ConnexionFrame();
            frame.setVisible(true);
        });
    }
}
