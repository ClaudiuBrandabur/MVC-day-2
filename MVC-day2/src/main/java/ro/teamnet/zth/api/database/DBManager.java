package ro.teamnet.zth.api.database;

import java.sql.*;

public class DBManager {

    public static final String CONNECTION_STRING = "jdbc:oracle:thin:@"
            + DBProperties.IP + ":" + DBProperties.PORT + ":" + DBProperties.SID;

    private static boolean isDriverRegistered = false;

    private DBManager() {
        throw new UnsupportedOperationException();
    }

    private static void registerDriver() throws ClassNotFoundException {

        if (!isDriverRegistered) {
            Class.forName(DBProperties.DRIVER_CLASS);
            isDriverRegistered = true;
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        registerDriver();
        Connection connection = DriverManager.getConnection(CONNECTION_STRING, DBProperties.USER, DBProperties.PASS);
        connection.createStatement().execute("alter session set current_schema="+DBProperties.SCHEMA);
        return connection;
    }

    public static boolean checkConnection(Connection connection) {
        boolean result = false;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("select 1 from dual");
            if (rs.next()) {
                result = rs.getLong(1) == 1l;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
