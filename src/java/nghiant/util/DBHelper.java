/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nghiant.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author localboss
 */
public class DBHelper implements Serializable {

    public static Connection makeConnection()
            throws /*ClassNotFoundException,*/ SQLException, NamingException {
            
            //1. get current Context
            Context context = new InitialContext();
            //2. get tomcat context
            Context tomcat = (Context)context.lookup("java:comp/env");
            //3. Look up Data Source
            DataSource ds = (DataSource)tomcat.lookup("nghiantDS");
            //4.open connection
            Connection con = ds.getConnection();
            
            return con;
//        //1.Load driver
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        //2.Create url Connection String --> DB Adress
//        String url = "jdbc:sqlserver://localhost:1433;"
//                + "databaseName=SinhVien2023;instanceName=AN";
//        //3.Open Connection
//        Connection con = DriverManager.getConnection(url, "sa", "12345");
//        return con;
    }
}
