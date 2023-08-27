package cc.abro.orchengine.services;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.gui.StoredGuiPanel;
import cc.abro.orchengine.util.ReflectionUtils;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

@EngineService
public class AnnotationScanService {

    private final Set<String> packagesForScan = new HashSet<>();


    public void addPackage(String pkg){
        packagesForScan.add(pkg);
    }

    public void addPackages(Set<String> pkgs){
        packagesForScan.addAll(pkgs);
    }

    public <T extends Annotation> Set<Class<?>> getClassesWithAnnotations(Class<T> annotation){
        return ReflectionUtils.getClassesWithAnnotations(annotation,
                packagesForScan);
    }
}
