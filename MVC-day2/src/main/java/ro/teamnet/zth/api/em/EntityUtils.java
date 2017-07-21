package ro.teamnet.zth.api.em;

import ro.teamnet.zth.api.annotations.Column;
import ro.teamnet.zth.api.annotations.Id;
import ro.teamnet.zth.api.annotations.Table;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
* Clasa contine metode ajutatoare pentru colectarea
* informatiilor despre campuri si clase dinamic, la runtime
*/
public class EntityUtils {

    public EntityUtils() {
        throw new UnsupportedOperationException();
    }

    /*
    * Returneaza numele tabelei asociate clasei entity
    */
    public static String getTableName(Class entity) {
        Table tableAnnotation = (Table) entity.getAnnotation(Table.class);
        if (tableAnnotation.name().equals("")) {
            return entity.getClass().getSimpleName();
        } else {
            return tableAnnotation.name();
        }
    }

    /*
   * Returneaza o lista (List<ColumnInfo>) cu
   * informatii despre campurile clasei entity
   */
    public static List<ColumnInfo> getColumns(Class entity) {
        Field[] fields = entity.getDeclaredFields();
        List<ColumnInfo> columnInfos = new ArrayList<>();

        for (Field f : fields) {
            Column column = f.getAnnotation(Column.class);
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setColumnName(f.getName());
            columnInfo.setColumnType(f.getType());

            if (column != null) {
                columnInfo.setDbName(column.name());
            } else {
                Id id = f.getAnnotation(Id.class);
                columnInfo.setDbName(id.name());
                columnInfo.setIsId(true);
            }
            columnInfos.add(columnInfo);
        }

        return columnInfos;
    }

    /*
    * Metoda primeste un obiect din baza de date (value),
    * si il transforma intr-un obiect de tip wantedType
    */
    public static Object castFromSqlType(Object value, Class<?> wantedType) {
        if (value != null) {
            if (value instanceof BigDecimal) {
                BigDecimal valueBD = (BigDecimal) value;
                return wantedType.equals(Integer.class) ? valueBD.intValue() :
                        wantedType.equals(Double.class) ? valueBD.doubleValue() :
                                wantedType.equals(Long.class) ? valueBD.longValue() : value;
            } else {
                return value;
            }
        }
        return null;
    }

    /*
    * Returneaza o lista (List<Field>) ce contine campurile
    * clasei clazz annotate cu annotarea annotation
    */
    public static List<Field> getFieldsByAnnotations(Class clazz, Class annotation) {
        List<Field> fieldsToReturn = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (f.getAnnotation(annotation) != null) {
                fieldsToReturn.add(f);
            }
        }
        return fieldsToReturn;
    }


    public static Object getSqlValue(Object object) throws IllegalAccessException {
        if (object == null)
            return null;

        if (object.getClass().getAnnotation(Table.class) != null) {
            Field idField = getFieldsByAnnotations(object.getClass(), Id.class).get(0);
            idField.setAccessible(true);
            return idField.get(object);
        } else {
            return object;
        }
    }
}
