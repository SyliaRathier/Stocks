import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthentificationFrame extends JFrame {
    private JButton seConnecterButton;
    private JButton sInscrireButton;

    public AuthentificationFrame() {
        setTitle("Mon voyage à Colmar");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH); // Mettre la fenêtre en plein écran

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Ajouter des marges

        JLabel titleLabel = new JLabel("Authentification", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Utiliser une police plus grande et en gras
        titleLabel.setForeground(new Color(50, 50, 50)); // Couleur du texte
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10)); // Ajouter des espaces entre les boutons
        buttonPanel.setBackground(new Color(240, 240, 240)); // Couleur de fond pour le panneau des boutons

        seConnecterButton = new JButton("Se connecter");
        sInscrireButton = new JButton("S'inscrire");

        // Styliser les boutons
        seConnecterButton.setBackground(new Color(66, 133, 244));
        seConnecterButton.setForeground(Color.WHITE);
        seConnecterButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        seConnecterButton.setFocusPainted(false);

        sInscrireButton.setBackground(new Color(66, 133, 244));
        sInscrireButton.setForeground(Color.WHITE);
        sInscrireButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sInscrireButton.setFocusPainted(false);

        buttonPanel.add(seConnecterButton);
        buttonPanel.add(sInscrireButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);

        seConnecterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConnexionFrame().setVisible(true);
                dispose();
            }
        });

        sInscrireButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InscriptionFrame().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthentificationFrame frame = new AuthentificationFrame();
            frame.setVisible(true);
        });
    }
}
