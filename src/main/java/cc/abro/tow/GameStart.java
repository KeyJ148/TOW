package cc.abro.tow;

import cc.abro.orchengine.OrchEngine;
import cc.abro.orchengine.util.Loader;
import cc.abro.orchengine.util.Mod;
import cc.abro.orchengine.util.ReflectionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GameStart {

    public static void main(String[] args) {
        // Нах не надо, загружать моды можно и без самой игры
        //Set<String> activeProfiles = Arrays.stream(args).collect(Collectors.toSet());
        //Set<String> packagesForScan = Set.of(GameStart.class.getPackage().getName());
        //OrchEngine.start(activeProfiles, packagesForScan);

        // Сюда нужно передать путь к джарнику с модом. Хз как собирать его, помогите!
        List<Class<?>> classes = null;
        try {
            classes = Loader.load("./mods/DemoMod.jar");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Попытка запустить все моды
        ReflectionUtils.getAssignableTo(classes, Mod.class).forEach((c) -> {
            var instance = ReflectionUtils.createInstance(c);
            ((Mod)instance).run();
        });

        System.exit(0);
    }
}
