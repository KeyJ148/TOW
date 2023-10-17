package cc.abro.orchengine.services;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.util.ReflectionUtils;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.Set;

@EngineService
@RequiredArgsConstructor
public class AnnotationScanService {

    private final PackageManagerService packageManagerService;

    public <T extends Annotation> Set<Class<?>> getClassesWithAnnotations(Class<T> annotation) {
        return ReflectionUtils.getClassesWithAnnotations(annotation, packageManagerService.getAllPackages());
    }
}
