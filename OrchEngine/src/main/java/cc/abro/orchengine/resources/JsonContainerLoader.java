package cc.abro.orchengine.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Log4j2
@UtilityClass
public class JsonContainerLoader {

    public <T> T loadExternalFile(Class<T> containerClass, String path) throws IOException {
        try {
            return new ObjectMapper().readValue(new File(path), containerClass);
        } catch (IOException e) {
            log.warn("Unable to load external JSON file \"" + path + "\" to class " + containerClass.getName());
            throw e;
        }
    }

    public <T> void saveExternalFile(T containerObject, String path) throws IOException {
        try {
            new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File(path), containerObject);
        } catch (IOException e) {
            log.warn("Unable to save external JSON file \"" + path + "\" from class " + containerObject.getClass().getName());
            throw e;
        }
    }

    public <T> T loadInternalFile(Class<T> containerClass, String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceLoader.getResourceAsStream(path)))) {
            String json = reader.lines().collect(Collectors.joining("\n"));
            return new ObjectMapper().readValue(json, containerClass);
        } catch (IOException e) {
            log.warn("Unable to load internal JSON file \"" + path + "\" to class " + containerClass.getName());
            throw e;
        }
    }

    public JsonNode loadExternalFile(String path) throws IOException {
        try {
            return new ObjectMapper().readTree(new File(path));
        } catch (IOException e) {
            log.warn("Unable to load internal JSON file \"" + path + "\"");
            throw e;
        }
    }

    public JsonNode loadInternalFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceLoader.getResourceAsStream(path)))) {
            String json = reader.lines().collect(Collectors.joining("\n"));
            return new ObjectMapper().readTree(json);
        } catch (IOException e) {
            log.warn("Unable to load internal JSON file \"" + path + "\"");
            throw e;
        }
    }
}
