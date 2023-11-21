package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DBConnexion {
    private static Connection connect;
    private String userName;
    private String password;
    private String serverName;
    private String portNumber;
    private static String tableName;
    private static String dbName;
    private DBConnexion() throws SQLException {
        // variables a modifier en fonction de la base
        this.userName = "root";
        this.password = "";
        this.serverName = "localhost";
        //Attention, sous MAMP, le port est 8889
        this.portNumber = "3306";
        tableName = "personne";

        // iL faut une base nommee testPersonne !
        this.dbName = "testpersonne";

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
            new DBConnexion();
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
