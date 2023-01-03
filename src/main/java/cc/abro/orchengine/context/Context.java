package cc.abro.orchengine.context;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.injectors.AbstractInjector;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EngineService
public class Context {

    private static final Map<ThreadGroup, ThreadContext> contextByThreadGroup = new ConcurrentHashMap<>();

    public static void addService(Class<?> serviceClass) {
        addService(serviceClass, getThreadGroup());
    }

    public static void addService(Class<?> serviceClass, ThreadGroup threadGroup) {
        getThreadContext(threadGroup).getServices().addComponent(serviceClass);
    }

    public static void addService(Object service) {
        addService(service, getThreadGroup());
    }

    public static void addService(Object service, ThreadGroup threadGroup) {
        getThreadContext(threadGroup).getServices().addComponent(service);
    }

    public static <T> T getService(Class<T> serviceClass) {
        return getService(serviceClass, getThreadGroup());
    }

    public static <T> T getService(Class<T> serviceClass, ThreadGroup threadGroup) {
        return getThreadContext(threadGroup).getServices().getComponent(serviceClass);
    }

    public static boolean hasService(Class<?> serviceClass) {
        return hasService(serviceClass, getThreadGroup());
    }

    public static boolean hasService(Class<?> serviceClass, ThreadGroup threadGroup) {
        try {
            return getThreadContext(threadGroup).getServices().getComponent(serviceClass) != null;
        } catch (AbstractInjector.UnsatisfiableDependenciesException e) {
            //Если пока не хватает сервисов для инициализации этого сервиса, то считаем, что сервис все равно в наличие
            return true;
        }
    }

    public static void addBean(Class<?> beanClass) {
        addBean(beanClass, getThreadGroup());
    }

    public static void addBean(Class<?> beanClass, ThreadGroup threadGroup) {
        getThreadContext(threadGroup).getBeans().addComponent(beanClass);
    }

    public static <T> T createBean(Class<T> beanClass) {
        return createBean(beanClass, getThreadGroup());
    }

    public static <T> T createBean(Class<T> beanClass, ThreadGroup threadGroup) {
        return getThreadContext(threadGroup).getBeans().getComponent(beanClass);
    }

    public static boolean hasBean(Class<?> beanClass) {
        return hasBean(beanClass, getThreadGroup());
    }

    public static boolean hasBean(Class<?> beanClass, ThreadGroup threadGroup) {
        try {
            return getThreadContext(threadGroup).getBeans().getComponent(beanClass) != null;
        } catch (AbstractInjector.UnsatisfiableDependenciesException e) {
            //Если пока не хватает сервисов для инициализации этого сервиса, то считаем, что сервис все равно в наличие
            return true;
        }
    }

    public static void start() {
        if (!getThreadContext().getServices().getLifecycleState().isStarted()) {
            getThreadContext().getServices().start();
        }
    }

    public static void stop() {
        if (getThreadContext().getServices().getLifecycleState().isStarted() &&
                !getThreadContext().getServices().getLifecycleState().isStopped()) {
            getThreadContext().getServices().stop();
        }
    }

    private static ThreadGroup getThreadGroup() {
        return Thread.currentThread().getThreadGroup();
    }

    private static ThreadContext getThreadContext() {
        return getThreadContext(getThreadGroup());
    }

    private static ThreadContext getThreadContext(ThreadGroup key) {
        while (key != null && !contextByThreadGroup.containsKey(key)) {
            key = key.getParent();
        }

        if (key == null) {
            key = Thread.currentThread().getThreadGroup();
        }
        return contextByThreadGroup.computeIfAbsent(key, (tg) -> new ThreadContext());
    }

    private static class ThreadContext {
        private final DefaultPicoContainer picoContainerServices = new DefaultPicoContainer(new Caching());
        private final DefaultPicoContainer picoContainerBeans = new DefaultPicoContainer(picoContainerServices);

        private DefaultPicoContainer getServices() {
            return picoContainerServices;
        }

        private DefaultPicoContainer getBeans() {
            return picoContainerBeans;
        }
    }
}
