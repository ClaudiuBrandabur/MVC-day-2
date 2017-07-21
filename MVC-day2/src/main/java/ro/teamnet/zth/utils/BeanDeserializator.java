package ro.teamnet.zth.utils;

import org.codehaus.jackson.map.ObjectMapper;
import ro.teamnet.zth.api.annotations.Z2HRequestObject;
import ro.teamnet.zth.api.annotations.Z2HRequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class BeanDeserializator {

    public static List<Object> getMethodParams(Method method, HttpServletRequest req) {
        // Pregatim parametrii necesari pentru invocarea metodei.
        List<Object> methodParamsValues = new ArrayList<>();
        try {
            // Obtinem o descriere a parametrilor asteptati de metoda.
            final Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                Class<?> parameterType = parameter.getType();
                if (parameter.isAnnotationPresent(Z2HRequestParam.class)) {
                    // Extragem parametrii transmisi in url pe baza numelui specificat in adnotarea Z2HRequestParam.
                    Z2HRequestParam annotation = parameter.getAnnotation(Z2HRequestParam.class);
                    String requestParameterStringValue = req.getParameter(annotation.name());
                    // Convertim string-ul citit din url intr-un obiect de tipul asteptat de metoda din controller.
                    Object requestParameterObject = parameterType.equals(String.class) ? requestParameterStringValue
                            : new ObjectMapper().readValue(requestParameterStringValue, parameterType);
                    methodParamsValues.add(requestParameterObject);
                } else if (parameter.isAnnotationPresent(Z2HRequestObject.class)) {
                    // Construim parametrii transmisi in body-ul request-ului HTTP.
                    BufferedReader requestBodyReader = req.getReader();
                    // Convertim body-ul requestului intr-un obiect de tipul asteptat de metoda din controller.
                    Object requestBodyObject = new ObjectMapper().readValue(requestBodyReader, parameterType);
                    methodParamsValues.add(requestBodyObject);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return methodParamsValues;
    }
}
