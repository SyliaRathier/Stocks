import java.util.Date;

public class Commentaire {
    private int identifiant;
    private String texte;
    private Date datePublication;
    private int lieuId;

    public Commentaire(int identifiant, String texte, Date datePublication, int lieuId) {
        this.identifiant = identifiant;
        this.texte = texte;
        this.datePublication = datePublication;
        this.lieuId = lieuId;
    }

    // Getters and Setters
    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public int getLieuId() {
        return lieuId;
    }

    public void setLieuId(int lieuId) {
        this.lieuId = lieuId;
    }
}
