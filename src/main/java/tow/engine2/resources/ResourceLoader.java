package tow.engine2.resources;

import java.io.InputStream;

/*
    Класс используется для получения ресурсов
    В дереве проекта ресурсы находятся в src/main/resources/ и src/test/resources/
    При создание JAR-файла (gradle jar), в том числе и при создание дистрибутива,
    ресурсы упаковываются в основной jar-файл с исходным кодом, поэтому к ним нельзя обращаться
    как к обычным файлам в файловой системе
    При запуске проекта (gradle run) ресурсы копируются в build/resources, откуда их можно получить
    при помощи команды getResource, но нельзя получить обычными средствами файловой системы
    не используя префиксы, характерные для конкретной системы сборки (тем более даже с префиксами,
    обращение к ресурсам как к файлам не будет работать при упаковке ресурсов в JAR)
 */

public class ResourceLoader {

    public static InputStream getResourceAsStream(String path){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

    public static boolean existResource(String path){
        return Thread.currentThread().getContextClassLoader().getResource(path) != null;
    }

}
