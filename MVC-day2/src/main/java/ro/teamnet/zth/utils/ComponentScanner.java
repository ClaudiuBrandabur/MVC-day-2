package ro.teamnet.zth.utils;

import ro.teamnet.zth.fmk.MethodAttributes;
import ro.teamnet.zth.fmk.domain.BeanKey;
import ro.teamnet.zth.fmk.domain.HttpMethod;

/**
 * Created by Alexandru.Bottea on 7/20/2017.
 */
public interface ComponentScanner {

    void scan();

    default BeanKey createUniqueKeyForRequest(HttpMethod methodType, String urlPath) {
        return new BeanKey(urlPath, methodType);
    }

    default MethodAttributes getMetaData(String uri, HttpMethod httpMethod) {
        return null;
    }


}
