package com.example.pooprojet;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;


public class HelloController {

    @FXML
    private ImageView vueImage;

    private Image imageInitiale;


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
    public void clicBandW() {

        if (vueImage.getImage() != null) {

            Image imageGrise = Transformateur.filtreBandW(vueImage.getImage());

            vueImage.setImage(imageGrise);
        }
    }

    @FXML
    protected void clicBoutonMiroir() {
        if (vueImage.getImage() != null) {


            Image imageMiroir = Transformateur.effetMiroir(vueImage.getImage());


            vueImage.setImage(imageMiroir);
        }
    }

    @FXML
    protected void clicBoutonSwap() {
        if (vueImage.getImage() != null) {
            vueImage.setImage(Transformateur.filtreExchange(vueImage.getImage()));
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
}