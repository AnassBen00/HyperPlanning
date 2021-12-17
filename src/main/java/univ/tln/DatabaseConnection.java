package univ.tln;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    public Connection conn ;
    public Connection connectDB() {
        try {
            conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/HyperPlanning", "sa", "root");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
