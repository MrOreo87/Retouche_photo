package modele.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class MetadataDAO {
    private final ObjectMapper mapper = new ObjectMapper();

    public void sauvegarder(ImageMetadata data) throws IOException {
        // Cette ligne crée le fichier "metadata.json" sur ton ordi
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("metadata.json"), data);
    }
}