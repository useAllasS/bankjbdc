package bank;
import java.sql.*;
// Global connection Class
public class connection {
      static Connection   con ;// Global Connection Object
    public static Connection getConnection(){
       
        try { 
          //String mysqlJDBCDriver= "com.mysql.cj.jdbc.Driver"; //jdbc driver
            String url= "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=CONVERT_TO_NULL"; //mysql url
            String user = "root";  //mysql username
            String pass = "090909";  //mysql passcode
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user,pass);
        }
        catch (Exception e) {
            System.out.println("Connection Failed!");
        }
        return con;
    }
}