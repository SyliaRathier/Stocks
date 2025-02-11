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

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Authentification", SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));

        seConnecterButton = new JButton("Se connecter");
        sInscrireButton = new JButton("S'inscrire");

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
