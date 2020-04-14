/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eLibraryRest.Beans;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Manas
 */
public class hello extends HttpServlet {
   public void service(HttpServletRequest request, HttpServletResponse response) {
       try {
           DBConnection db = new DBConnection();
           db.pstmt = db.conn.prepareStatement("SELECT * FROM course_info");
           db.rst = db.pstmt.executeQuery();
           PrintWriter out = response.getWriter();
           while (db.rst.next()) {
               out.println(db.rst.getString(1));
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
