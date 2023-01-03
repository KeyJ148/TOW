package cc.abro.orchengine.context;

import cc.abro.orchengine.OrchEngine;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Несмотря на аннотацию {@link EngineService} данный сервис нельзя переопределить другим сервисом.
 * Т.к. этот сервис инициализируется до сканирования пакетов на сервисы.
 * При этом сервис всё ещё находится в {@link Context} в ThreadContext конкретной {@link ThreadGroup}.
 * При параллельном запуске нескольких экземпляров {@link OrchEngine} у каждого из них будет свой {@link ProfilesService}
 */
@Log4j2
@EngineService
public class ProfilesService {

    private static final String ENV_NAME = "ORCHENGINE_PROFILE";

    private final Set<String> activeProfiles = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public ProfilesService(Set<String> activeProfiles) {
        log.debug("Set profiles: " + activeProfiles);
        this.activeProfiles.addAll(activeProfiles);

        Set<String> activeProfilesFromEnv = getProfilesFromEnv();
        log.debug("Set profiles from environment: " + activeProfilesFromEnv);
        this.activeProfiles.addAll(activeProfilesFromEnv);
    }

    public Set<String> getActiveProfiles() {
        return new HashSet<>(activeProfiles);
    }

    private Set<String> getProfilesFromEnv() {
        try {
            return Arrays.stream(System.getenv(ENV_NAME).split(",")).collect(Collectors.toSet());
        } catch (IllegalArgumentException | NullPointerException ignored) {
            return Collections.emptySet();
        }
    }
}
