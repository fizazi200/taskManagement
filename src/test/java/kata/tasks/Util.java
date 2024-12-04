package kata.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import kata.tasks.model.Task;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.aspectj.bridge.MessageUtil.fail;

public class Util {

    public static Task getTask() {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Utiliser le ClassLoader pour obtenir le fichier depuis le classpath
            URL resource = Util.class.getClassLoader().getResource("task.json");
            if (resource == null) {
                fail("Le fichier reservation.json est introuvable dans les ressources");
            }
            File file = new File(resource.getFile());
            // Lire le fichier JSON et convertir en objet
           Task task = objectMapper.readValue(file, Task.class);

            return task;

    }catch (IOException e) {
            e.printStackTrace();
            fail("IOException lors de la lecture du fichier JSON");

        }
        return null;
    }
}





