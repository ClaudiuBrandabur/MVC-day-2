package ro.teamnet.zth.api.database;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DBManagerTest {

    @Test
    public void testNewConnectionMethod() {
        try (Connection connection = DBManager.getConnection()) {
            assertNotNull("Connection was not created", connection);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckConnectionMethod() {
        boolean result = false;
        try (Connection connection = DBManager.getConnection()) {
            result = DBManager.checkConnection(connection);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        assertTrue("Connection is not OK!", result);
    }
}
