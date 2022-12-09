package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;
    private static Connection connect;

    private String dbname;

    private DBConnection() throws SQLException {
        // variables a modifier en fonction de la base
        String userName = "root";
        String password = "";
        String serverName = "localhost";
        //Attention, sous MAMP, le port est 8889
        String portNumber = "3306";
        String tableName = "personne";
        String dbName;

        if (this.dbname == null){
            // iL faut une base nommee testPersonne !
            dbName = "testpersonne";
        } else {
            dbName = this.dbname;
        }

        // creation de la connection
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        connect = DriverManager.getConnection(urlDB, connectionProps);
    }

    public static synchronized DBConnection getInstance() throws SQLException {
        if (instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    public static Connection getConnection(){
        return connect;
    }

    public void setNomDB(String nomDB) throws SQLException {
        this.dbname = nomDB;
        instance = null;
        getInstance();
    }

}
