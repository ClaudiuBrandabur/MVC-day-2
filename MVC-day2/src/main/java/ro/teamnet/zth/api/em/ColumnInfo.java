package ro.teamnet.zth.api.em;

/**
 * Clasa ce contine informatii despre campurile unei entitati/clase.
 * Aceste informatii se refera la :
 * columnName - numele campului
 * columnType - tipul campului
 * dbName - numele coloanei din tabela, asociata campului din entitate
 * idId - flag true/false (daca campul este id-ul entitatii sau nu)
 * value - valoarea campului
 * Aceasta clasa va fi folosita pentru a crea interogarile select, update, insert si delete
 * din clasa QueryBuilder
 */
public class ColumnInfo {

    private String columnName;
    private Class columnType;
    private String dbName;
    private boolean isId;
    private Object value;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Class getColumnType() {
        return columnType;
    }

    public void setColumnType(Class columnType) {
        this.columnType = columnType;
    }

    public boolean isId() {
        return isId;
    }

    public void setIsId(boolean isId) {
        this.isId = isId;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
