package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DBConnexion {
    private static Connection connect;
    private String userName = "root";
    private String password = "";
    private String serverName = "localhost";
    private String portNumber = "3306";
    private String tableName = "personne";
    private static String dbName = "testpersonne";
    private DBConnexion() throws SQLException {
        // creation de la connection
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        connect = DriverManager.getConnection(urlDB, connectionProps);
    }
    public static synchronized Connection getInstance() throws SQLException {
        if (connect == null) {
            try {
                new DBConnexion();
            } catch (SQLException e) {
                throw new SQLException("Erreur de connexion à la base de données");
            }
        }
        return connect;
    }

    //refaire une connexion après
    public static void setNomDB(String nomDB) {
        if (nomDB != null || !Objects.equals(dbName, nomDB))
            dbName = nomDB;
    }
    public static String getNomDB() {
        return dbName;
    }
}
