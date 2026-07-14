package modele.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FiltreSepia implements ActionFiltre {
    @Override
    public Image appliquer(Image imageOriginale) {
        int largeur = (int) imageOriginale.getWidth();
        int hauteur = (int) imageOriginale.getHeight();

        WritableImage imageSortie = new WritableImage(largeur, hauteur);
        PixelReader scanneur = imageOriginale.getPixelReader();
        PixelWriter pinceau = imageSortie.getPixelWriter();

        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                Color couleur = scanneur.getColor(x, y);

                double rouge = couleur.getRed();
                double vert = couleur.getGreen();
                double bleu = couleur.getBlue();

                // Calcul du sepia
                double nouveauRouge = (rouge * 0.393) + (vert * 0.769) + (bleu * 0.189);
                double nouveauVert = (rouge * 0.349) + (vert * 0.686) + (bleu * 0.168);
                double nouveauBleu = (rouge * 0.272) + (vert * 0.534) + (bleu * 0.131);

                // Sécurité pour ne pas dépasser 1.0
                if (nouveauRouge > 1.0) nouveauRouge = 1.0;
                if (nouveauVert > 1.0) nouveauVert = 1.0;
                if (nouveauBleu > 1.0) nouveauBleu = 1.0;

                Color nouvelleCouleur = new Color(nouveauRouge, nouveauVert, nouveauBleu, 1.0);
                pinceau.setColor(x, y, nouvelleCouleur);
            }
        }
        return imageSortie;
    }
}