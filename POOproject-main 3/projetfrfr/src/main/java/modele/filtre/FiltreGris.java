package modele.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FiltreGris implements ActionFiltre {
    @Override
    public Image appliquer(Image imageOriginale) {
        // Dimensions px de l'image
        int largeur = (int) imageOriginale.getWidth();
        int hauteur = (int) imageOriginale.getHeight();

        // Image de sortie
        WritableImage imageSortie = new WritableImage(largeur, hauteur);
        PixelReader scanneur = imageOriginale.getPixelReader();
        PixelWriter pinceau = imageSortie.getPixelWriter();

        //Boucle classique pour parcourir tout les px de l'image
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {

                //Couleur du pixel
                Color couleur = scanneur.getColor(x, y);

                //Extraction des couleurs
                double rouge = couleur.getRed();
                double vert = couleur.getGreen();
                double bleu = couleur.getBlue();

                //Calcul du nouveau gris
                double gris = (rouge + vert + bleu) / 3.0;

                Color nouveauGris = new Color(gris, gris, gris, 1.0);

                //Application du gris sur le pixel
                pinceau.setColor(x, y, nouveauGris);
            }
        }
        return imageSortie;
    }
}