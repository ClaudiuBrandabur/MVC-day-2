package ro.teamnet.zth.api.annotations;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Z2HController {
    String urlPath();
}
