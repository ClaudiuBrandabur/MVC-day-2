package ro.teamnet.zth.api.annotations;


import ro.teamnet.zth.fmk.domain.HttpMethod;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Z2HRequestMethod {
    HttpMethod methodType() default HttpMethod.GET;

    String urlPath();
}
