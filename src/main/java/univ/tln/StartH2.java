package univ.tln;

import lombok.extern.java.Log;
import org.h2.tools.RunScript;
import org.h2.tools.Server;
import univ.tln.datasource.DBCPDataSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

@Log
public class StartH2 {
    public static void main(String[] args) {
        try {

            App.loadProperties("app.properties");
            App.configureLogger();

            Server server = Server.createTcpServer("-ifNotExists").start();
            try (Connection connection = DBCPDataSource.getConnection()) {
                RunScript.execute(connection,
                        new InputStreamReader(StartH2.class.getClassLoader().getResourceAsStream("create.H2.sql")));
            }
            log.info(server.getStatus());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
