package cc.abro.orchengine.resources;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/*
    Класс используется для получения ресурсов
    В дереве проекта ресурсы находятся в src/main/resources/ и src/test/resources/
    При создании JAR-файла (gradle jar), в том числе и при создании дистрибутива,
    ресурсы упаковываются в основной jar-файл с исходным кодом, поэтому к ним нельзя обращаться
    как к обычным файлам в файловой системе
    При запуске проекта (gradle run) ресурсы копируются в build/resources, откуда их можно получить
    при помощи команды getResource, но нельзя получить обычными средствами файловой системы
    не используя префиксы, характерные для конкретной системы сборки (тем более даже с префиксами,
    обращение к ресурсам как к файлам не будет работать при упаковке ресурсов в JAR)
 */

@UtilityClass
public class ResourceLoader {


    /**
     * Пытается просканировать указанную директорию внутри JAR файла.
     * @param path путь до папки внутри JAR
     * @return Содержимое папки. Первой в списке будет сама папка
     * @throws URISyntaxException, IOException
     */
    public List<String> scanResources(String path) throws URISyntaxException, IOException {
        List<String> filesList = new ArrayList<>();

        URI uri = ResourceLoader.class.getResource(path).toURI();
        Path myPath;
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            myPath = fileSystem.getPath(path);
        } else {
            myPath = Paths.get(uri);
        }
        Stream<Path> walk = Files.walk(myPath, 1);
        for (Iterator<Path> it = walk.iterator(); it.hasNext();){
            filesList.add(it.next().getFileName().toString());
        }

        return filesList;
    }
    public InputStream getResourceAsStream(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }


    public BufferedReader getResourceAsBufferedReader(String path) {
        return new BufferedReader(new InputStreamReader(getResourceAsStream(path)));
    }

    public boolean existResource(String path) {
        return Thread.currentThread().getContextClassLoader().getResource(path) != null;
    }

}
