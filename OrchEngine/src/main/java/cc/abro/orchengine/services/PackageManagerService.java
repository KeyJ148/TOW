package cc.abro.orchengine.services;

import cc.abro.orchengine.context.EngineService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@EngineService
public class PackageManagerService {

    private final Set<String> defaultPackages = new HashSet<>();
    private final Set<String> allPackages = new HashSet<>();

    public void addToDefaultPackages(Collection<String> toDefaultPackages) {
        defaultPackages.addAll(toDefaultPackages);
        allPackages.addAll(toDefaultPackages);
    }

    public void addToAllPackages(Collection<String> toAllPackages) {
        allPackages.addAll(toAllPackages);
    }

    public void addToAllPackages(String toAllPackages) {
        allPackages.add(toAllPackages);
    }

    public Set<String> getDefaultPackages() {
        return Collections.unmodifiableSet(defaultPackages);
    }

    public Set<String> getAllPackages() {
        return Collections.unmodifiableSet(allPackages);
    }
}
