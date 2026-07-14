package modele.filtre;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class FiltrePrewitt implements ActionFiltre {
    @Override
    public Image appliquer(Image imageOriginale) {
        int largeur = (int) imageOriginale.getWidth();
        int hauteur = (int) imageOriginale.getHeight();

        WritableImage imageSortie = new WritableImage(largeur, hauteur);
        PixelReader scanneur = imageOriginale.getPixelReader();
        PixelWriter pinceau = imageSortie.getPixelWriter();

        // On commence à 1 et on finit à -1 pour ne pas planter sur les bords
        for (int y = 1; y < hauteur - 1; y++) {
            for (int x = 1; x < largeur - 1; x++) {

                // Récupération des 8 pixels autour du pixel central
                Color hautGauche = scanneur.getColor(x - 1, y - 1);
                Color hautMilieu = scanneur.getColor(x, y - 1);
                Color hautDroite = scanneur.getColor(x + 1, y - 1);
                Color milieuGauche = scanneur.getColor(x - 1, y);
                Color milieuDroite = scanneur.getColor(x + 1, y);
                Color basGauche = scanneur.getColor(x - 1, y + 1);
                Color basMilieu = scanneur.getColor(x, y + 1);
                Color basDroite = scanneur.getColor(x + 1, y + 1);

                // Transformation en gris rapide pour le calcul
                double gHG = (hautGauche.getRed() + hautGauche.getGreen() + hautGauche.getBlue()) / 3.0;
                double gHM = (hautMilieu.getRed() + hautMilieu.getGreen() + hautMilieu.getBlue()) / 3.0;
                double gHD = (hautDroite.getRed() + hautDroite.getGreen() + hautDroite.getBlue()) / 3.0;
                double gMG = (milieuGauche.getRed() + milieuGauche.getGreen() + milieuGauche.getBlue()) / 3.0;
                double gMD = (milieuDroite.getRed() + milieuDroite.getGreen() + milieuDroite.getBlue()) / 3.0;
                double gBG = (basGauche.getRed() + basGauche.getGreen() + basGauche.getBlue()) / 3.0;
                double gBM = (basMilieu.getRed() + basMilieu.getGreen() + basMilieu.getBlue()) / 3.0;
                double gBD = (basDroite.getRed() + basDroite.getGreen() + basDroite.getBlue()) / 3.0;

                // Application des matrices Gx et Gy
                double gx = (-1 * gHG) + (1 * gHD) + (-1 * gMG) + (1 * gMD) + (-1 * gBG) + (1 * gBD);
                double gy = (-1 * gHG) + (-1 * gHM) + (-1 * gHD) + (1 * gBG) + (1 * gBM) + (1 * gBD);

                // Calcul du contour final
                double magnitude = Math.sqrt((gx * gx) + (gy * gy));
                if (magnitude > 1.0) magnitude = 1.0;

                pinceau.setColor(x, y, new Color(magnitude, magnitude, magnitude, 1.0));
            }
        }
        return imageSortie;
    }
}