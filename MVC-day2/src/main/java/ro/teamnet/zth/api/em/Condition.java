package ro.teamnet.zth.api.em;

/**
 * Clasa ce contine informatii despre coloanele tabelei asociata unei entitati/clase.
 * Aceste informatii se refera la :
 * columnName - numele coloanei din tabela asociata entitatii/clasei
 * value - valoarea coloanei din tabela asociata entitatii/clasei
 * Aceasta clasa va fi folosita pentru a seta CONDITIILE interogarilor select, update, insert si delete
 * din clasa QueryBuilder
 */
public class Condition {

    private String columnName;
    private Object value;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
