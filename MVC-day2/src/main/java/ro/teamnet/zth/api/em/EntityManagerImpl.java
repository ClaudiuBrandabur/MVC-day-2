package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.api.database.DBManager;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityManagerImpl implements EntityManager {

    @Override
    public <T> T findById(Class<T> entityClass, Long id) {
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement()) {

            String tableName = EntityUtils.getTableName(entityClass);
            List<ColumnInfo> tableColumn = EntityUtils.getColumns(entityClass);
            List<Field> fields = EntityUtils.getFieldsByAnnotations(entityClass, Id.class);
            Condition condition = new Condition();
            condition.setColumnName(fields.get(0).getAnnotation(Id.class).name());
            condition.setValue(id);
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setQueryType(QueryType.SELECT).setTableName(tableName).addQueryColumns(tableColumn).addCondition(condition);

            T instance = null;
            ResultSet rs = statement.executeQuery(queryBuilder.createQuery());
            if (rs.next()) {
                instance = entityClass.newInstance();
                for (ColumnInfo columnInfo : tableColumn) {
                    columnInfo.setValue(rs.getObject(columnInfo.getDbName()));
                    Field field = instance.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);
                    if (columnInfo.getValue() instanceof Timestamp)
                        columnInfo.setValue(new Date(((Timestamp) columnInfo.getValue()).getTime()));
                    field.set(instance, EntityUtils.castFromSqlType(columnInfo.getValue(), columnInfo.getColumnType()));
                }
            }
            return instance;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T findByStringId(Class<T> entityClass, String id) {
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement()) {

            String tableName = EntityUtils.getTableName(entityClass);
            List<ColumnInfo> tableColumn = EntityUtils.getColumns(entityClass);
            List<Field> fields = EntityUtils.getFieldsByAnnotations(entityClass, Id.class);
            Condition condition = new Condition();
            condition.setColumnName(fields.get(0).getAnnotation(Id.class).name());
            condition.setValue(id);
            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setQueryType(QueryType.SELECT).setTableName(tableName).addQueryColumns(tableColumn).addCondition(condition);

            T instance = null;
            ResultSet rs = statement.executeQuery(queryBuilder.createQuery());
            if (rs.next()) {
                instance = entityClass.newInstance();
                for (ColumnInfo columnInfo : tableColumn) {
                    columnInfo.setValue(rs.getObject(columnInfo.getDbName()));
                    Field field = instance.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);
                    if (columnInfo.getValue() instanceof Timestamp)
                        columnInfo.setValue(new Date(((Timestamp) columnInfo.getValue()).getTime()));
                    field.set(instance, EntityUtils.castFromSqlType(columnInfo.getValue(), columnInfo.getColumnType()));
                }
            }
            return instance;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement()) {
            String tableName = EntityUtils.getTableName(entityClass);
            List<ColumnInfo> tableColumns = EntityUtils.getColumns(entityClass);

            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setQueryType(QueryType.SELECT).setTableName(tableName).addQueryColumns(tableColumns);

            String sql = queryBuilder.createQuery();
            ResultSet rs = statement.executeQuery(sql);
            List<T> instances = new ArrayList<>();

            while (rs.next()) {
                T instance = entityClass.newInstance();
                for (ColumnInfo columnInfo : tableColumns) {
                    columnInfo.setValue(rs.getObject(columnInfo.getDbName()));
                    if (columnInfo.getValue() instanceof Timestamp)
                        columnInfo.setValue(new Date(((Timestamp) columnInfo.getValue()).getTime()));
                    Field field = instance.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);
                    field.set(instance, EntityUtils.castFromSqlType(columnInfo.getValue(), columnInfo.getColumnType()));
                }
                instances.add(instance);
            }
            return instances;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public <T> Object insert(T entity) {
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement()) {
            String tableName = EntityUtils.getTableName(entity.getClass());
            List<ColumnInfo> tableColumns = EntityUtils.getColumns(entity.getClass());
            Long lastId = null;
            String stringId = null;
            for (ColumnInfo columnInfo : tableColumns) {
                if (columnInfo.isId()) {
                    Field field = entity.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);
                    if (field.getType().equals(String.class)) {
                        columnInfo.setValue(EntityUtils.getSqlValue(field.get(entity)));
                        stringId = (String) EntityUtils.getSqlValue(field.get(entity));
                    } else {
                        lastId = getSeqNextVal(tableName, columnInfo.getDbName());
                        columnInfo.setValue(lastId);
                    }
                } else {
                    Field field = entity.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);
                    columnInfo.setValue(EntityUtils.getSqlValue(field.get(entity)));
                }
            }

            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setQueryType(QueryType.INSERT).setTableName(tableName).addQueryColumns(tableColumns);

            String sql = queryBuilder.createQuery();
            statement.executeUpdate(sql);
            if (lastId == null) {
                return (T) findByStringId(entity.getClass(), stringId);
            } else {
                return (T) findById(entity.getClass(), lastId);
            }


        } catch (SQLException | ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T update(T entity) {
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement()) {

            String tableName = EntityUtils.getTableName(entity.getClass());
            List<ColumnInfo> columns = EntityUtils.getColumns(entity.getClass());

            for (ColumnInfo columnInfo : columns) {
                Field field = entity.getClass().getDeclaredField(columnInfo.getColumnName());
                field.setAccessible(true);
                columnInfo.setValue(field.get(entity));
            }

            QueryBuilder queryBuilder = new QueryBuilder();
            Condition condition = new Condition();
            condition.setColumnName(columns.get(0).getDbName());
            condition.setValue(columns.get(0).getValue());
            queryBuilder.setQueryType(QueryType.UPDATE).setTableName(tableName).addQueryColumns(columns).addCondition(condition);

            String sql = queryBuilder.createQuery();
            statement.executeUpdate(sql);
            Object idValue = columns.get(0).getValue();
            if (idValue instanceof String) {
                return (T) findByStringId(entity.getClass(), (String) idValue);
            } else {
                return (T) findById(entity.getClass(), (Long) idValue);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Object entity) {
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement()) {
            String tableName = EntityUtils.getTableName(entity.getClass());
            List<ColumnInfo> columnInfos = EntityUtils.getColumns(entity.getClass());

            for (ColumnInfo columnInfo : columnInfos) {
                Field field = entity.getClass().getDeclaredField(columnInfo.getColumnName());
                field.setAccessible(true);
                columnInfo.setValue(field.get(entity));
            }

            Condition condition = new Condition();
            condition.setColumnName(columnInfos.get(0).getDbName());
            condition.setValue(columnInfos.get(0).getValue());

            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setQueryType(QueryType.DELETE).setTableName(tableName).addQueryColumns(columnInfos).addCondition(condition);

            String sql = queryBuilder.createQuery();
            statement.executeUpdate(sql);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Long getSeqNextVal(String tableName, String columnIdName) {
        ResultSet rs;
        try (Connection con = DBManager.getConnection();
             Statement statement = con.createStatement()) {
            rs = statement.executeQuery("select max(" + columnIdName + ")+1 from " + tableName);
            rs.next();
            return rs.getLong(1);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> findByParams(Class<T> entityClass, Map<String, Object> params) {
        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement()) {

            String tableName = EntityUtils.getTableName(entityClass);
            List<ColumnInfo> columnInfos = EntityUtils.getColumns(entityClass);

            QueryBuilder queryBuilder = new QueryBuilder();
            queryBuilder.setQueryType(QueryType.SELECT).setTableName(tableName).addQueryColumns(columnInfos);

            for (String key : params.keySet()) {
                Condition condition = new Condition();
                condition.setColumnName(key);
                condition.setValue(params.get(key));
                queryBuilder.addCondition(condition);
            }

            String sql = queryBuilder.createQuery();

            ResultSet rs = statement.executeQuery(sql);

            List<T> instances = new ArrayList<T>();
            while (rs.next()) {
                T instance = entityClass.newInstance();
                for (ColumnInfo columnInfo : columnInfos) {
                    Field field = instance.getClass().getDeclaredField(columnInfo.getColumnName());
                    field.setAccessible(true);
                    field.set(instance, EntityUtils.castFromSqlType(rs.getObject(columnInfo.getDbName()), columnInfo.getColumnType()));
                }
                instances.add(instance);
            }
            return instances;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // TODO inainte de a se rula sa se verifice existenta procedurii in baza de date
    /*public void applyProcedure(Long employeeId, Long employeeSalary){
        try(Connection connection = DBManager.getConnection()){
            String sql = "{call UPDATE_EMP_SAL (?, ?)}";
            CallableStatement statement = connection.prepareCall(sql);
            statement.setLong(1,employeeId);
            statement.setLong(2,employeeSalary);
            statement.execute();
            statement.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }*/
}
