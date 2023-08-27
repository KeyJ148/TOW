package cc.abro.orchengine.context;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import cc.abro.orchengine.util.ReflectionUtils;

@Log4j2
@UtilityClass
public class ContextAnnotationScanner {

    public void loadServicesAndBeans(Set<String> activeProfiles, Set<String> packagesForScan) {
        loadServices(activeProfiles, packagesForScan);
        loadBeans(activeProfiles, packagesForScan);
    }

    private void loadServices(Set<String> activeProfiles, Set<String> packagesForScan) {
        ReflectionUtils.getClassesWithAnnotations(TestService.class, packagesForScan).stream()
                .peek(service -> log.debug("Found TestService: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(TestService.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(TestService.class).value()).anyMatch(activeProfiles::contains))
                .peek(service -> log.debug("Load TestService: " + service.getSimpleName()))
                .forEach(Context::addService);
        ReflectionUtils.getClassesWithAnnotations(GameService.class, packagesForScan).stream()
                .peek(service -> log.debug("Found GameService: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(GameService.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(GameService.class).value()).anyMatch(activeProfiles::contains))
                .filter(service -> !Context.hasService(service))
                .peek(service -> log.debug("Load GameService: " + service.getSimpleName()))
                .forEach(Context::addService);
        ReflectionUtils.getClassesWithAnnotations(EngineService.class, packagesForScan).stream()
                .peek(service -> log.debug("Found EngineService: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(EngineService.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(EngineService.class).value()).anyMatch(activeProfiles::contains))
                .filter(service -> !Context.hasService(service))
                .peek(service -> log.debug("Load EngineService: " + service.getSimpleName()))
                .forEach(Context::addService);
    }

    private void loadBeans(Set<String> activeProfiles, Set<String> packagesForScan) {
        ReflectionUtils.getClassesWithAnnotations(TestBean.class, packagesForScan).stream()
                .peek(service -> log.debug("Found TestBean: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(TestBean.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(TestBean.class).value()).anyMatch(activeProfiles::contains))
                .peek(service -> log.debug("Load TestBean: " + service.getSimpleName()))
                .forEach(Context::addBean);
        ReflectionUtils.getClassesWithAnnotations(GameBean.class, packagesForScan).stream()
                .peek(service -> log.debug("Found GameBean: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(GameBean.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(GameBean.class).value()).anyMatch(activeProfiles::contains))
                .peek(service -> log.debug("Load GameBean: " + service.getSimpleName()))
                .filter(service -> !Context.hasBean(service))
                .forEach(Context::addBean);
        ReflectionUtils.getClassesWithAnnotations(EngineBean.class, packagesForScan).stream()
                .peek(service -> log.debug("Found EngineBean: " + service.getSimpleName()))
                .filter(service -> service.getAnnotation(EngineBean.class).value().length == 0 ||
                        Arrays.stream(service.getAnnotation(EngineBean.class).value()).anyMatch(activeProfiles::contains))
                .peek(service -> log.debug("Load EngineBean: " + service.getSimpleName()))
                .filter(service -> !Context.hasBean(service))
                .forEach(Context::addBean);
    }
}
