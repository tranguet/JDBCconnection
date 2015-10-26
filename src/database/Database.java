/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

//import com.mysql.jdbc.Connection;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author TrangUet-PC
 */
public class Database {
    String url;
    String user;
    String password;
    Connection connection;
    Statement statement;
    
    public boolean innitialize(byte dbmsType){
        Properties p= new Properties();
        
        try {
            p.load(new FileInputStream("database.properties"));
            url = p.getProperty("url");
            user = p.getProperty("user");
            password =  p.getProperty("password");
            switch(dbmsType){
                case 0:
                    Class.forName("com.mysql.jdbc.Driver");
                    connection =  DriverManager.getConnection(url +"?user="+user+"&password="+password);
                    break;
                case 1:
                    break;
                default:
                    throw new IllegalArgumentException("invalid");
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(connection == null){
            throw new NullPointerException("Connection is null");
        }
     return true;   
    }
    
    public void createStatement(){
        if(statement == null){
            try {
                statement = connection.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ResultSet retrieveData(String sqlCommand){
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(sqlCommand);
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
        
    public static void main(String[] args){
        Database db = new Database();
        if(db.innitialize((byte) 0)){
            System.out.println("Connection is innitialized");
        }
    }
    
        
    
    
    
    
}
