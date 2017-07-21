package ro.teamnet.zth.utils;

import ro.teamnet.zth.api.annotations.Z2HController;
import ro.teamnet.zth.api.annotations.Z2HRequestMethod;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;
import ro.teamnet.zth.fmk.domain.BeanKey;
import ro.teamnet.zth.fmk.domain.HttpMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ControllerScanner implements ComponentScanner {

    private String[] packages;
    private static Map<BeanKey, MethodAttributes> ALLOWED_METHODS = new ConcurrentHashMap<>();

    public Map<BeanKey, MethodAttributes> getAllowedMethods() {
        return ALLOWED_METHODS;
    }

    public ControllerScanner(String... packages) {
        this.packages = packages;
    }

    @Override
    public void scan() {
        for (String aPackage : packages) {
            try {
                loadControllers(AnnotationScanUtils.getClasses(aPackage));
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadControllers(Iterable<Class> classes) {
        for (Class aClass : classes) {
            if (aClass.isAnnotationPresent(Z2HController.class)) {
                Z2HController controllerAnnotation = (Z2HController) aClass.getAnnotation(Z2HController.class);
                String controllerUrlPath = controllerAnnotation.urlPath();
                Method[] controllerMethods = aClass.getMethods();
                for (Method method : controllerMethods) {
                    if (method.isAnnotationPresent(Z2HRequestMethod.class)) {
                        Z2HRequestMethod annotation = method.getAnnotation(Z2HRequestMethod.class);
                        String methodUrlPath = annotation.urlPath();
                        HttpMethod methodType = annotation.methodType();

                        MethodAttributes methodAttributes = new MethodAttributes();
                        methodAttributes.setControllerClass(aClass);
                        methodAttributes.setMethod(method);
                        methodAttributes.setMethodType(methodType);
                        methodAttributes.setParameterTypes(method.getParameterTypes());

                        String urlPath = controllerUrlPath + methodUrlPath;

                        final BeanKey key = createUniqueKeyForRequest(methodType, urlPath);

                        ALLOWED_METHODS.put(key, methodAttributes);

                        System.out.println(key + " : " + methodAttributes);
                    }
                }
            }
        }
    }

    @Override
    public MethodAttributes getMetaData(String uri, HttpMethod httpMethod) {
        return ALLOWED_METHODS.get(new BeanKey(uri, httpMethod));
    }
}
