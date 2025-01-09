package cc.abro.orchengine.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

public class Loader {


    /**
     * Загружает классы в указанном .jar файле в {@link cc.abro.orchengine.util.ClassCache}, если он еще не был загружен.
     * @param jarPath - путь к .jar файлу.
     * @return Список классов загруженнх в ClassCache из этого .jar файла.
     */
    public static List<Class<?>> load(String jarPath) throws IOException {
        return load(jarPath, false);
    }

    /**
     * Загружает классы в указанном .jar файле в {@link cc.abro.orchengine.util.ClassCache}
     * @param jarPath - путь к .jar файлу.
     * @param forceLoad - попытается загрузить .jar файл, даже если он уже был загружен.
     * @return Список классов загруженнх в ClassCache из этого .jar файла.
     */
    public static List<Class<?>> load(String jarPath, boolean forceLoad) throws IOException {
        // Если .jar уже загружен, вернуть закэшированные классы.
        if (ClassCache.isLoadedJar(jarPath) && !forceLoad) {
            return ClassCache.getLoadedJarClasses(jarPath);
        }

        File jarFile = new File(jarPath);
        URL jarUrl = jarFile.toURI().toURL();
        ArrayList<String> classNames = new ArrayList<>();
        ArrayList<Class<?>> classes = new ArrayList<>();

        // Находим все классы в джарнике для использования их в URLClassLoader
        try (JarFile jar = new JarFile(jarFile)) {
            jar.stream().forEach(jarEntry -> {
                if (jarEntry.getName().endsWith(".class")){
                    classNames.add(jarEntry.getName());
                }
            });
        }

        // Пытаемся загрузить классы из джарника
        try (URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jarUrl}, ClassLoader.getSystemClassLoader())) {
            classNames.forEach(classPath -> {
                try {
                    String className = classPath.replaceAll("/",".").replace(".class","");
                    if (ClassCache.isClassKnown(className)) {
                        return; // не нужно грузить уже загруженный класс.
                    }
                    Class<?> cls = urlClassLoader.loadClass(className);
                    ClassCache.addClass(jarPath, cls);
                } catch (Exception e) {
                    System.out.println(e);
                }
            });
            // Регистрируем .jar файл, если удалось его открыть.
            ClassCache.registerLoadedJar(jarPath);
        }

        return ClassCache.getLoadedJarClasses(jarPath);
    }
}
