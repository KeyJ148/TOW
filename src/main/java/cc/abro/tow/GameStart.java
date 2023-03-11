package cc.abro.tow;

import cc.abro.orchengine.OrchEngine;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class GameStart {

    public static void main(String[] args) {
        Set<String> activeProfiles = Arrays.stream(args).collect(Collectors.toSet());
        Set<String> packagesForScan = Set.of(GameStart.class.getPackage().getName());
        OrchEngine.start(activeProfiles, packagesForScan);
        System.exit(0);
    }
}
