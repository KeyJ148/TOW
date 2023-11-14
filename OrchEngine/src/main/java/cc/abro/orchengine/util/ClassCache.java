package cc.abro.orchengine.util;

import java.util.*;
import java.util.stream.Collectors;

public class ClassCache {
    private static final Set<String> loadedJarPaths = new HashSet<>();
    private static final HashMap<String, List<Class<?>>> loadedJars = new HashMap<>();
    private static final Set<String> knownClasses = new HashSet<>();


    /**
     * @return список зарегистрированных .jar файлов.
     */
    public static Set<String> getLoadedJarPaths(){
        return Collections.unmodifiableSet(loadedJarPaths);
    }

    /**
     *
     * @param path - путь к .jar файлу.
     * @return список классов, загруженных из .jar файла по указанному пути.
     */
    public static List<Class<?>> getLoadedJarClasses(String path){
        return Collections.unmodifiableList(loadedJars.get(path));
    }

    /**
     * @return список всех загруженных Loader'ом классов.
     */
    public static List<Class<?>> getAllLoadedClasses(){
        return loadedJars.values().stream()
                .flatMap(List::stream).toList();
    }

    /**
     * @param clazz - базовый класс.
     * @return список всех закэшированных классов, унаследованных от указанного класса.
     */
    public static List<Class<?>> getAssignableTo(Class<?> clazz){
        return getAllLoadedClasses().stream().filter(clazz::isAssignableFrom).toList();
    }

    /**
     * @param path - путь к .jar файлу.
     * @return true, если .jar файл был загружен в кэш.
     */
    public static boolean isLoadedJar(String path){
        return loadedJarPaths.contains(path);
    }

    /**
     * Добавляет класс в кэш.
     * @param jar - .jar файл, в котором находится класс.
     * @param clazz - сам класс.
     */
    public static void addClass(String jar, Class<?> clazz){
        var classname = clazz.getCanonicalName();
        var loadedClasses = getModifiableLoadedJarClasses(jar);
        loadedClasses.add(clazz);
        knownClasses.add(classname);
    }

    /**
     * @param name - полное имя класса.
     * @return true, если такой класс имеется в кэше.
     */
    public static boolean isClassKnown(String name){
        return knownClasses.contains(name);
    }

    /**
     * Добавляет .jar файл в список зарегистированных. Зарегистрированные .jar файлы будут игрнорироваться при попытке загрузки в {@link Loader#Load(String)}.
     * @param path - путь к .jar файлу.
     */
    public static void registerLoadedJar(String path){
        loadedJarPaths.add(path);
    }

    private static List<Class<?>> getModifiableLoadedJarClasses(String path){
        return loadedJars.computeIfAbsent(path, k -> new ArrayList<>());
    }
}
