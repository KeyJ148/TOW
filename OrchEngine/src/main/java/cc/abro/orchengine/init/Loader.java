package cc.abro.orchengine.init;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.ContextAnnotationScanner;
import cc.abro.orchengine.context.ProfilesService;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.init.interfaces.GameInterface;
import cc.abro.orchengine.util.LogUtils;
import lombok.extern.log4j.Log4j2;

import java.util.Set;

@Log4j2
public class Loader {

    public void start(Set<String> activeProfiles, Set<String> packagesForScan) {
        try {
            Thread.currentThread().setName("Engine");
            setupFinalizerService(); //Создание сервиса для корректного освобождения ресурсов при завершении программы
            setupProfilesService(activeProfiles); //Создание сервиса по учету активных профилей
            ContextAnnotationScanner.loadServicesAndBeans(activeProfiles, packagesForScan); //Сканирование пакетов и поиск сервисов и бинов
            initServices(); //Запуск всех сервисов
            initGame(); //Вызов инициализации у класса игры
            Context.getService(Engine.class).run(); //Запуск главного цикла движка
        } catch (Exception e) {
            LogUtils.logFatalException(log, "The game ended with an error: ", e);
            throw new RuntimeException(e);
        } finally {
            try {
                Context.getService(Finalizer.class).stopServicesAndCloseResources();
            } catch (Exception e) {
                LogUtils.logFatalException(log, "The process of completing the game is not successful: ", e);
                System.exit(0);
            }
        }
    }

    private void setupFinalizerService() {
        Finalizer finalizerService = new Finalizer();
        Context.addService(finalizerService);
        finalizerService.registryShutdownCallback();
    }

    private void setupProfilesService(Set<String> activeProfiles) {
        ProfilesService profilesService = new ProfilesService(activeProfiles);
        Context.addService(profilesService);
    }

    private void initServices() {
        log.info("Initialize engine...");
        Context.start();
        log.info("Initialize engine complete");
    }

    private void initGame() {
        log.info("Initialize game...");
        Context.getService(LocationManager.class).setActiveLocation(new Location(1920, 1080));
        Context.getService(GameInterface.class).init();
        log.info("Initialize game complete");
    }
}
