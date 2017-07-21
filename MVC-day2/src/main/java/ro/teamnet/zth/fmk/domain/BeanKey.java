package ro.teamnet.zth.fmk.domain;

/**
 * Created by Alexandru.Bottea on 7/20/2017.
 */
public class BeanKey {

    private String uri;
    private HttpMethod httpMethod;

    public BeanKey(String uri, HttpMethod httpMethod) {
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeanKey)) return false;

        BeanKey beanKey = (BeanKey) o;

        return getUri().equals(beanKey.getUri()) && getHttpMethod() == beanKey.getHttpMethod();
    }

    @Override
    public int hashCode() {
        int result = getUri().hashCode();
        result = 31 * result + getHttpMethod().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\nBeanKey{" +
                "uri='" + uri + '\'' +
                ", httpMethod=" + httpMethod +
                "}";
    }
}
