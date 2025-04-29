/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author e.talmitte
 */
public class MySQLConnexion {

//    private static String url = "jdbc:mysql://172.28.37.20:3306/ap4";
//    private static String user = "e_talmitte";
//    private static String pass = "Btssio82300";
    private static String url = "jdbc:mysql://localhost:3306/ap4";
    private static String user = "root";
    private static String pass = "";

    private static Connection con = null;

    public static Connection getConnexion() {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(url, user, pass);
            } catch (Exception ex) {
                Logger.getLogger(MySQLConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return con;
    }
}
