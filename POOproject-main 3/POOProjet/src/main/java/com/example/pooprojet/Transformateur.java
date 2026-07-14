package com.example.pooprojet;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Transformateur {

    public static Image filtreBandW(Image imageOriginale) {
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

    public static Image effetMiroir(Image imageOriginale) {

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

    public static Image filtreExchange(Image imageOriginale) {

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