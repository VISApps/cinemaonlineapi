package com.visapps.cinemaonlineapi.utils;

import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

@Component
public final class DatabaseConnection {

    private static final String hostName = "%databaselink%";
    private static final String dbName = "Cinema";
    private static final String user = "%username%";
    private static final String password = "%password%";
    private static final String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);

    public Connection getConnection() throws Exception{
        Driver d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
        return d.connect(url, new Properties());
    }
}
