package ro.teamnet.zth.api.em;

import static org.junit.Assert.*;

import org.junit.Test;
import ro.teamnet.zth.api.annotations.Column;
import ro.teamnet.zth.appl.domain.Department;
import ro.teamnet.zth.appl.domain.Location;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

public class EntityUtilsTest {

    @Test
    public void testGetTableNameMethod() {
        String tableName = EntityUtils.getTableName(Department.class);
        assertEquals("Table name should be departments!", "departments", tableName);
    }

    @Test
    public void testGetColumnsMethod() {
        List<ColumnInfo> columns = EntityUtils.getColumns(Department.class);
        assertEquals(columns.size(), 3);
    }

    @Test
    public void testCastFromSqlType() {
        Object wantedObject = EntityUtils.castFromSqlType(new BigDecimal("5"), Long.class);
        assertEquals(wantedObject.getClass(), Long.class);
    }

    @Test
    public void testGetFielsyAnnotations() {
        List<Field> fields = EntityUtils.getFieldsByAnnotations(Location.class, Column.class);
        assertEquals(fields.size(), 4);
    }

    @Test
    public void testGetSqlValue() throws IllegalAccessException {
        Department d = new Department();
        d.setId(5l);
        Object value = EntityUtils.getSqlValue(d);
        assertTrue(value instanceof Long);
        assertEquals(value, 5l);
    }
}
