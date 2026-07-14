package modele.sécurité;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class GestionnaireSecurite {


    public static byte[] genererGraine(String motDePasse) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(motDePasse.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image traiterImage(Image img, String motDePasse) {
        int w = (int) img.getWidth();
        int h = (int) img.getHeight();
        int totalPixels = w * h;
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < totalPixels; i++) indices.add(i);


        byte[] graineBytes = genererGraine(motDePasse);

        long graineLong = 0;
        for (byte b : graineBytes) graineLong = (graineLong << 8) + (b & 0xFF);

        Random random = new Random(graineLong);
        Collections.shuffle(indices, random);
        // ----------------------

        WritableImage sortie = new WritableImage(w, h);
        PixelReader lecteur = img.getPixelReader();
        PixelWriter ecrivain = sortie.getPixelWriter();

        for (int i = 0; i < totalPixels; i++) {
            int posMelangee = indices.get(i);
            // On envoie le pixel 'i' vers sa destination mélangée
            ecrivain.setColor(posMelangee % w, posMelangee / w, lecteur.getColor(i % w, i / w));
        }
        return sortie;
    }

    public static Image dechiffrerImage(Image imageChiffree, String motDePasse) {
        int w = (int) imageChiffree.getWidth();
        int h = (int) imageChiffree.getHeight();
        int totalPixels = w * h;
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < totalPixels; i++) positions.add(i);


        byte[] graineBytes = genererGraine(motDePasse);
        long graineLong = 0;
        for (byte b : graineBytes) graineLong = (graineLong << 8) + (b & 0xFF);

        Random random = new Random(graineLong);
        Collections.shuffle(positions, random);


        WritableImage imageRetrouvee = new WritableImage(w, h);
        PixelReader lecteur = imageChiffree.getPixelReader();
        PixelWriter ecrivain = imageRetrouvee.getPixelWriter();

        for (int i = 0; i < totalPixels; i++) {
            int posMelangee = positions.get(i);
            // INVERSE : On prend le pixel qui est à 'posMelangee' pour le remettre en 'i'
            ecrivain.setColor(i % w, i / w, lecteur.getColor(posMelangee % w, posMelangee / w));
        }
        return imageRetrouvee;
    }
}