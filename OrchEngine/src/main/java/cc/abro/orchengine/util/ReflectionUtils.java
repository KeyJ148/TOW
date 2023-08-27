package cc.abro.orchengine.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@UtilityClass
public class ReflectionUtils {
    /**
     * @param annotation      классы с этой аннотацией необходимо найти
     * @param packagesForScan список пакетов для рекурсивного сканирования классов
     * @param <T>             тип аннотации
     * @return классы с указанной аннотацией
     */
    public <T extends Annotation> Set<Class<?>> getClassesWithAnnotations(Class<T> annotation,
                                                                           Set<String> packagesForScan) {
        return packagesForScan.stream()
                .map(Reflections::new)
                .map(reflections -> reflections.getTypesAnnotatedWith(annotation))
                .flatMap(Collection::stream)
                .filter(aClass -> aClass.getAnnotation(annotation) != null)
                .collect(Collectors.toSet());
    }

    public Object CreateInstance(Class<?> type) {
        try{
            return type.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            log.error("Could not create instance of "+type.getName()+": \n",e.getCause());
            return null;
        }
    }

    public Set<Object> CreateInstances(Set<Class<?>> types){
        Set<Object> instaces = new HashSet<>();
        for(var type : types){
            var instance = CreateInstance(type);
            if(instance != null){
                instaces.add(instance);
            }
        }

        return instaces;
    }
}
