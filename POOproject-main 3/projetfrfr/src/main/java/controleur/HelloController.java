package controleur;

import modele.data.ImageMetadata;
import modele.data.MetadataDAO;
import modele.filtre.*;
import modele.sécurité.GestionnaireSecurite;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.control.ComboBox;


public class HelloController {

    @FXML
    private ImageView vueImage;

    private Image imageInitiale;

    @FXML
    private ComboBox<String> filtreComboBox;

    private java.util.List<String> historiqueFiltres = new java.util.ArrayList<>();

    @FXML
    public void initialize() {
        // Remplit la liste des filtres
        if (filtreComboBox != null) {
            filtreComboBox.getItems().addAll("Noir et Blanc", "Sépia", "Contours (Prewitt)", "Miroir", "Échange Couleurs (Swap)");
        }
    }

    @FXML
    protected void chargerImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");


        File fichier = fileChooser.showOpenDialog(null);


        if (fichier != null) {
            Image image = new Image(fichier.toURI().toString());
            vueImage.setImage(image);

            imageInitiale = image;
        }
    }


    @FXML
    protected void clicReset() {
        if (imageInitiale != null) {


            vueImage.setImage(imageInitiale);
        }
    }

    @FXML
    protected void clicChiffrer() {

        if (vueImage.getImage() != null) {


            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Sécurité - Chiffrement");
            dialog.setHeaderText("Protection de l'image");
            dialog.setContentText("Choisissez un mot de passe  :");


            Optional<String> resultat = dialog.showAndWait();


            if (resultat.isPresent() && !resultat.get().isEmpty()) {
                String mdp = resultat.get();


                Image imageMelangee = GestionnaireSecurite.traiterImage(vueImage.getImage(), mdp);


                vueImage.setImage(imageMelangee);
            }
        }
    }

    @FXML
    protected void clicDechiffrer() {
        if (vueImage.getImage() != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Sécurité - Déchiffrement");
            dialog.setHeaderText("Accès à l'image protégée");
            dialog.setContentText("Entrez le mot de passe de déverrouillage :");

            Optional<String> resultat = dialog.showAndWait();

            if (resultat.isPresent() && !resultat.get().isEmpty()) {
                String mdp = resultat.get();


                Image imageRetrouvee = GestionnaireSecurite.dechiffrerImage(vueImage.getImage(), mdp);


                vueImage.setImage(imageRetrouvee);
            }
        }
    }

    private void appliquerNouveauFiltre(ActionFiltre leFiltre, String leTag) {
        if (vueImage.getImage() != null) {
            Image resultat = leFiltre.appliquer(vueImage.getImage());
            vueImage.setImage(resultat);

            // NOUVEAU : On enregistre le nom du filtre
            historiqueFiltres.add(leTag);
        }
    }

    @FXML
    protected void clicAppliquerFiltre() {
        String selection = filtreComboBox.getValue();
        if (selection == null) return;

        switch (selection) {
            case "Noir et Blanc":
                appliquerNouveauFiltre(new FiltreGris(), "GRIS");
                break;
            case "Sépia":
                appliquerNouveauFiltre(new FiltreSepia(), "SEPIA");
                break;
            case "Contours (Prewitt)":
                appliquerNouveauFiltre(new FiltrePrewitt(), "PREWITT");
                break;
            case "Miroir":
                appliquerNouveauFiltre(new FiltreMiroir(), "MIROIR");
                break;
            case "Échange Couleurs (Swap)":
                appliquerNouveauFiltre(new FiltreSwap(), "SWAP");
                break;
        }
    }

    @FXML
    protected void clicSauvegarderMetadata() {
        try {
            ImageMetadata meta = new ImageMetadata();
            meta.setNomImage("mon_image_modifiee");
            meta.setTransformations(new java.util.ArrayList<>(historiqueFiltres));
            meta.getTags().add("Projet_L2_Info"); // Exemple de tag

            MetadataDAO dao = new MetadataDAO();
            dao.sauvegarder(meta);
            System.out.println("JSON créé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}