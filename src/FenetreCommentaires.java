import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FenetreCommentaires extends JFrame {
    private JTextArea commentairesArea;
    private LieuTouristique lieu;

    public FenetreCommentaires(LieuTouristique lieu) {
        this.lieu = lieu;
        setTitle("Commentaires pour : " + lieu.getTitre());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        commentairesArea = new JTextArea();
        commentairesArea.setFont(new Font("Arial", Font.PLAIN, 14));
        commentairesArea.setEditable(false);
        commentairesArea.setBackground(new Color(242, 242, 242));
        commentairesArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Ajouter les commentaires à la zone de texte
        CommentaireDAO commentaireDAO = new CommentaireDAO();
        List<Commentaire> commentaires = commentaireDAO.getCommentairesPourLieu(lieu.getIdentifiant());

        StringBuilder commentairesText = new StringBuilder();
        for (Commentaire commentaire : commentaires) {
            commentairesText.append(commentaire.getTexte()).append("\n");
        }

        commentairesArea.setText(commentairesText.toString());

        JScrollPane scrollPane = new JScrollPane(commentairesArea);
        add(scrollPane, BorderLayout.CENTER);
    }
}
