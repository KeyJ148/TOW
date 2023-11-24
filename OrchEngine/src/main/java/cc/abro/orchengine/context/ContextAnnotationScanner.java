package cc.abro.orchengine.context;

import cc.abro.orchengine.util.ReflectionUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Set;

@Log4j2
@UtilityClass
public class ContextAnnotationScanner {

    public void loadServices(Set<String> activeProfiles, Set<String> packagesForScan) {
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
}
