import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class CommentaireFrame extends JFrame {
    private JTextArea texteArea;
    private JButton ajouterButton;
    private int lieuId;
    private int utilisateurId;

    public CommentaireFrame(int utilisateurId, int lieuId) {
        this.utilisateurId = utilisateurId;
        this.lieuId = lieuId;
        setTitle("Ajouter un Commentaire");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel texteLabel = new JLabel("Texte:");
        texteLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        texteArea = new JTextArea(5, 20);
        texteArea.setFont(new Font("Arial", Font.PLAIN, 14));
        texteArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        ajouterButton = new JButton("Ajouter");
        ajouterButton.setBackground(new Color(66, 133, 244));
        ajouterButton.setForeground(Color.WHITE);
        ajouterButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        ajouterButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(texteLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JScrollPane(texteArea), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(ajouterButton, gbc);

        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajouterCommentaire();
            }
        });
    }

    private void ajouterCommentaire() {
        String texte = texteArea.getText();

        if (texte.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        Date datePublication = new java.util.Date(); // Utiliser la date actuelle

        Commentaire nouvCommentaire = new Commentaire(0, texte, datePublication, lieuId);
        CommentaireDAO commentaireDAO = new CommentaireDAO();
        int result = commentaireDAO.ajouter(nouvCommentaire);

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Commentaire ajouté avec succès!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du commentaire.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CommentaireFrame frame = new CommentaireFrame(1, 1); // Exemple avec un ID utilisateur et un ID lieu
            frame.setVisible(true);
        });
    }
}
