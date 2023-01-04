package cc.abro.orchengine.context;

import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public class ContextAnnotationScanner {

    public static void loadServicesAndBeans(Set<String> activeProfiles, Set<String> packagesForScan) {
        loadServices(activeProfiles, packagesForScan);
        loadBeans(activeProfiles, packagesForScan);
    }

    private static void loadServices(Set<String> activeProfiles, Set<String> packagesForScan) {
        getClassesWithAnnotations(TestService.class, packagesForScan).stream()
                .peek(service -> log.debug("Found TestService: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(TestService.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(TestService.class).value()).anyMatch(activeProfiles::contains))
                .peek(service -> log.debug("Load TestService: " + service.getSimpleName()))
                .forEach(Context::addService);
        getClassesWithAnnotations(GameService.class, packagesForScan).stream()
                .peek(service -> log.debug("Found GameService: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(GameService.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(GameService.class).value()).anyMatch(activeProfiles::contains))
                .filter(service -> !Context.hasService(service))
                .peek(service -> log.debug("Load GameService: " + service.getSimpleName()))
                .forEach(Context::addService);
        getClassesWithAnnotations(EngineService.class, packagesForScan).stream()
                .peek(service -> log.debug("Found EngineService: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(EngineService.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(EngineService.class).value()).anyMatch(activeProfiles::contains))
                .filter(service -> !Context.hasService(service))
                .peek(service -> log.debug("Load EngineService: " + service.getSimpleName()))
                .forEach(Context::addService);
    }

    private static void loadBeans(Set<String> activeProfiles, Set<String> packagesForScan) {
        getClassesWithAnnotations(TestBean.class, packagesForScan).stream()
                .peek(service -> log.debug("Found TestBean: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(TestBean.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(TestBean.class).value()).anyMatch(activeProfiles::contains))
                .peek(service -> log.debug("Load TestBean: " + service.getSimpleName()))
                .forEach(Context::addBean);
        getClassesWithAnnotations(GameBean.class, packagesForScan).stream()
                .peek(service -> log.debug("Found GameBean: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(GameBean.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(GameBean.class).value()).anyMatch(activeProfiles::contains))
                .peek(service -> log.debug("Load GameBean: " + service.getSimpleName()))
                .filter(service -> !Context.hasBean(service))
                .forEach(Context::addBean);
        getClassesWithAnnotations(EngineBean.class, packagesForScan).stream()
                .peek(service -> log.debug("Found EngineBean: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(EngineBean.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(EngineBean.class).value()).anyMatch(activeProfiles::contains))
                .peek(service -> log.debug("Load EngineBean: " + service.getSimpleName()))
                .filter(service -> !Context.hasBean(service))
                .forEach(Context::addBean);
    }

    /**
     * @param annotation      классы с этой аннотацией необходимо найти
     * @param packagesForScan список пакетов для рекурсивного сканирования классов
     * @param <T>             тип аннотации
     * @return классы с указанной аннотацией
     */
    private static <T extends Annotation> Set<Class<?>> getClassesWithAnnotations(Class<T> annotation,
                                                                                  Set<String> packagesForScan) {
        return packagesForScan.stream()
                .map(Reflections::new)
                .map(reflections -> reflections.getTypesAnnotatedWith(annotation))
                .flatMap(Collection::stream)
                .filter(aClass -> aClass.getAnnotation(annotation) != null)
                .collect(Collectors.toSet());
    }
}
