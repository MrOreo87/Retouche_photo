package modele.data;

import java.util.ArrayList;
import java.util.List;

public class ImageMetadata {
    private String nomImage;
    private List<String> tags = new ArrayList<>();
    private List<String> transformations = new ArrayList<>();

    // Constructeur vide (Obligatoire pour que Jackson fonctionne)
    public ImageMetadata() {}

    // Getters et Setters (Indispensables pour Jackson)
    public String getNomImage() { return nomImage; }
    public void setNomImage(String nomImage) { this.nomImage = nomImage; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public List<String> getTransformations() { return transformations; }
    public void setTransformations(List<String> transformations) { this.transformations = transformations; }
}