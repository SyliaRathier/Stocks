import java.awt.Image;

public class LieuTouristique {

    private int identifiant;
    private String titre;
    private String description;
    private String adresse;
    private byte[] image;
    private int guideId;

    public LieuTouristique(int identifiant, String titre, String description, String adresse, byte[] image, int guideId) {
        this.identifiant = identifiant;
        this.titre = titre;
        this.description = description;
        this.adresse = adresse;
        this.image = image;
        this.guideId = guideId;
    }

    // Getters and Setters
    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    } 
    

 
}
