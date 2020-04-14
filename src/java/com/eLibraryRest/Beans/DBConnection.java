/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eLibraryRest.Beans;

import java.sql.*;
/**
 *
 * @author Manas
 */
public class DBConnection {
    
    public Connection conn;
    public Statement stmt;
    public PreparedStatement pstmt;
    public ResultSet rst, rst1, rst2;
    
    public DBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payalbea_eLibraryRest?user=payalbea_manas&password=Manas@630");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
