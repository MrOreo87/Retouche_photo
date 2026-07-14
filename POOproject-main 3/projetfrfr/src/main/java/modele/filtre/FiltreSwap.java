package modele.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FiltreSwap implements ActionFiltre {
    @Override
    public Image appliquer(Image imageOriginale) {
        //Recupération de la taille de l'image dans deux variables
        int largeur = (int) imageOriginale.getWidth();
        int hauteur = (int) imageOriginale.getHeight();

        //Création image vierge + 2 outils
        WritableImage imageSortie = new WritableImage(largeur, hauteur);
        PixelReader scanneur = imageOriginale.getPixelReader();
        PixelWriter pinceau = imageSortie.getPixelWriter();

        //Boucle pour parcourir l'image px par px
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {

                //Récupération de la couleur du pixel pos x,y
                Color couleurActuelle = scanneur.getColor(x, y);

                // On récupère les valeurs des 3 couleurs R G B
                double r = couleurActuelle.getRed();
                double v = couleurActuelle.getGreen();
                double b = couleurActuelle.getBlue();

                // LA CONSIGNE : (R, G, B) devient (G, B, R)
                Color nouvelleCouleur = new Color(v, b, r, 1.0);

                pinceau.setColor(x, y, nouvelleCouleur);
            }
        }
        return imageSortie;
    }
}