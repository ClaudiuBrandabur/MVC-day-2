package ro.teamnet.zth.fmk;

import ro.teamnet.zth.fmk.domain.HttpMethod;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * MethodAttributes.java
 */
public class MethodAttributes {

    private Class controllerClass;
    private Method method;
    private HttpMethod methodType;
    private Class<?>[] parameterTypes;

    public Class getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public HttpMethod getMethodType() {
        return methodType;
    }

    public void setMethodType(HttpMethod methodType) {
        this.methodType = methodType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodAttributes)) return false;

        MethodAttributes that = (MethodAttributes) o;

        if (!getControllerClass().equals(that.getControllerClass())) return false;
        if (!getMethod().equals(that.getMethod())) return false;
        if (getMethodType() != that.getMethodType()) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(getParameterTypes(), that.getParameterTypes());
    }

    @Override
    public int hashCode() {
        int result = getControllerClass().hashCode();
        result = 31 * result + getMethod().hashCode();
        result = 31 * result + getMethodType().hashCode();
        result = 31 * result + Arrays.hashCode(getParameterTypes());
        return result;
    }

    @Override
    public String toString() {
        return "MethodAttributes{\n" +
                "controllerClass=" + controllerClass +
                ", method=" + method +
                ", methodType=" + methodType +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                "}\n\n";
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }
}
