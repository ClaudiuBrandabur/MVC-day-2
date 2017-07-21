package ro.teamnet.zth.api.em;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
* Clasa ce contine metode ajutatoare crearii de interogari SQL
* de tip select/insert/delete/update in mod dinamic. Pentru aceasta
* sunt declarate si folosite campurile:
 *      tableName - numele tabelei pe care se executa interogarile
 *      queryColumns - coloanele implicate in interogari
 *      queryType - tipul interogarii SQL: select/insert/update/delete
 *      conditions - conditiile interogarii
*/
public class QueryBuilder {

    private Object tableName;
    private List<ColumnInfo> tableColumns;
    private QueryType queryType;
    private List<Condition> conditions;

    public QueryBuilder() {

    }

    /**
     * Adauga o conditie la conditiile deja existente in interogare
     */
    public QueryBuilder addCondition(Condition condition) {
        if (this.conditions == null) {
            conditions = new ArrayList<Condition>();
        }
        this.conditions.add(condition);
        return this;
    }

    public QueryBuilder setTableName(Object tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * Adauga lista de coloane implicate in interogare
     */
    public QueryBuilder addQueryColumns(List<ColumnInfo> queryColumns) {
        if (this.tableColumns == null) {
            tableColumns = new ArrayList<ColumnInfo>();
        }
        this.tableColumns.addAll(queryColumns);
        return this;
    }

    public QueryBuilder setQueryType(QueryType queryType) {
        this.queryType = queryType;
        return this;
    }

    /*
    * Metoda ce creaza o interogare de tip select, folosind
    * coloanele din queryColumns si conditiile din conditions
    */
    private String createSelectQuery() {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        boolean isFirst = true;
        for (ColumnInfo columnInfo : this.tableColumns) {
            if (!isFirst) {
                sql.append(",");
            }
            sql.append(this.tableName + "." + columnInfo.getDbName());
            isFirst = false;
        }
        sql.append(" from " + this.tableName);

        boolean isFirstCondition = true;
        if (this.conditions != null && !this.conditions.isEmpty()) {
            for (Condition c : conditions) {
                if (!isFirstCondition) {
                    sql.append(" and ").append(c.getColumnName()).append(" = ").append(getValueForQuery(c.getValue()));
                }
                if (isFirstCondition) {
                    sql.append(" where ").append(c.getColumnName()).append(" = ").append(getValueForQuery(c.getValue()));
                }
                isFirstCondition = false;
            }
        }
        return sql.toString();
    }

    /*
    * Metoda ce creaza o interogare de tip delete, folosind
    * conditiile din conditions
    */
    private String createDeleteQuery() {
        StringBuilder sql = new StringBuilder();
        sql.append(" delete from " + this.tableName);
        boolean isFirstCondition = true;
        if (this.conditions != null && !this.conditions.isEmpty()) {
            for (Condition c : conditions) {
                if (!isFirstCondition) {
                    sql.append(" and ").append(c.getColumnName()).append(" = ").append(getValueForQuery(c.getValue()));
                }
                if (isFirstCondition) {
                    sql.append(" where ").append(c.getColumnName()).append(" = ").append(getValueForQuery(c.getValue()));
                }
                isFirstCondition = false;
            }
        }
        return sql.toString();
    }

    /*
    * Metoda ce creaza o interogare de tip insert, folosind
    * coloanele si valorile acestora din queryColumns
    */
    private String createInsertQuery() {
        StringBuilder sql = new StringBuilder();
        StringBuilder sqlValues = new StringBuilder();
        sql.append("insert into " + this.tableName).append(" ( ");
        boolean isFirst = true;
        for (ColumnInfo columnInfo : this.tableColumns) {
            if (!isFirst) {
                sql.append(",");
                sqlValues.append(",");
            }

            sql.append(columnInfo.getDbName());
            sqlValues.append(getValueForQuery(columnInfo.getValue()));
            isFirst = false;
        }
        sql.append(" ) values( ").append(sqlValues).append(")");
        return sql.toString();
    }

    /*
    * Metoda ce creaza o interogare de tip update, folosind
    * coloanele din queryColumns si conditiile din conditions
    */
    private String createUpdateQuery() {
        StringBuilder sql = new StringBuilder();
        sql.append("update " + this.tableName + " SET ");

        boolean isFirst = true;
        for (ColumnInfo columnInfo : this.tableColumns) {
            if (!isFirst) {
                sql.append(",");
            }
            sql.append(columnInfo.getDbName()).append("=").append(getValueForQuery(columnInfo.getValue()));
            isFirst = false;
        }

        boolean isFirstCondition = true;
        if (this.conditions != null && !this.conditions.isEmpty()) {
            for (Condition c : conditions) {
                if (!isFirstCondition) {
                    sql.append(" and ").append(c.getColumnName()).append(" = ").append(getValueForQuery(c.getValue()));
                }
                if (isFirstCondition) {
                    sql.append(" where ").append(c.getColumnName()).append(" = ").append(getValueForQuery(c.getValue()));
                }
                isFirstCondition = false;
            }
        }

        return sql.toString();
    }

    /*
    * Metoda ce creaza interogari de tip select/update/delete/insert,
    * in functie de valoarea lui queryType
    */
    public String createQuery() {
        if (QueryType.SELECT.equals(this.queryType)) {
            return createSelectQuery();
        } else if (QueryType.INSERT.equals(this.queryType)) {
            return createInsertQuery();
        } else if (QueryType.UPDATE.equals(this.queryType)) {
            return createUpdateQuery();
        } else if (QueryType.DELETE.equals(this.queryType)) {
            return createDeleteQuery();
        }
        return null;
    }

    /*
    * Metoda transforma obiectul value, daca este de tip Date/String,
    * intr-un format potrivit standardului SQL
    */
    private String getValueForQuery(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return "'" + value + "'";
        }

        if (value instanceof java.sql.Date) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            return "TO_DATE('" + dateFormat.format((Date) value) + "','yyyy/MM/dd')";
        }

        return value.toString();
    }

}
