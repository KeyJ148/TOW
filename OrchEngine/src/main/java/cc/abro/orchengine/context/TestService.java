package cc.abro.orchengine.context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TestService {

    /**
     * At least one of these profiles must be active for the service to be loaded into the context
     *
     * @return profile list
     */
    String[] value() default {};
}
