package modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionMySQL {
    private static String database_name = "ferreteria";
    private static String user = "root";
    private static String password = "password";
    private static String url = "jdbc:mysql://localhost:3306/" + database_name;
    private static Connection conn = null;
    
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn=DriverManager.getConnection(url,user,password);
        }catch(ClassNotFoundException |SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
    public static void closeConnection(Connection conn){
        try{
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
}
