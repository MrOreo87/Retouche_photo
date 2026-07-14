package modele.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FiltreMiroir implements ActionFiltre {
    @Override
    public Image appliquer(Image imageOriginale) {
        // Dimensions px de l'image
        int largeur = (int) imageOriginale.getWidth();
        int hauteur = (int) imageOriginale.getHeight();

        // Image de sortie
        WritableImage imageSortie = new WritableImage(largeur, hauteur);
        PixelReader scanneur = imageOriginale.getPixelReader();
        PixelWriter pinceau = imageSortie.getPixelWriter();

        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                // On récupère la couleur du pixel actuel
                Color couleur = scanneur.getColor(x, y);

                // On l'écrit à la position inverse sur l'horizontale
                pinceau.setColor(largeur - 1 - x, y, couleur);
            }
        }
        return imageSortie;
    }
}