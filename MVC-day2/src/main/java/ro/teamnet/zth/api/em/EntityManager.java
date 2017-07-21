package ro.teamnet.zth.api.em;

import java.util.List;
import java.util.Map;

public interface EntityManager {

    <T> T findById(Class<T> entityClass, Long id);

    <T> T findByStringId(Class<T> entityClass, String id);

    <T> List<T> findAll(Class<T> entityClass);

    <T> Object insert(T entity);

    <T> T update(T entity);

    void delete(Object entity);

    <T> List<T> findByParams(Class<T> entityClass, Map<String, Object> params);
}
