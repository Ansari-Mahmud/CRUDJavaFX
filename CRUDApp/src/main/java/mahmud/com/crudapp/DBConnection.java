package mahmud.com.crudapp;

import java.sql.*;

public class DBConnection {

    public static Connection connect() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/java", "postgres", "admin");
        return con;
    }
}
