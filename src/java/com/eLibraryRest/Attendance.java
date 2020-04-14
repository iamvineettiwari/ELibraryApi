/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eLibraryRest;

import com.eLibraryRest.Beans.AttendanceList;
import com.eLibraryRest.Beans.AttendanceRequestPojo;
import com.eLibraryRest.Beans.DBConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Manas
 */
@Path("/attendance")
public class Attendance {

    @GET
    @Path("/requestAttendance/{studentLibId}")
    public Response requestAttendance(@PathParam("studentLibId") String studentLibId) {
        Response response = null;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currDate = sdf.format(date);
        DBConnection db = new DBConnection();
        try {
            db.pstmt = db.conn.prepareStatement("INSERT INTO student_attendance (student_lib_id) VALUES (?)");
            db.pstmt.setString(1, studentLibId);
            int done = db.pstmt.executeUpdate();
            if (done > 0) {
                response = Response.status(200).entity(new Integer(200)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(201).build();
        }
        return response;
    }
    
    @GET
    @Path("/cancelAttendance/{studentLibId}")
    public Response cancelAttendance(@PathParam("studentLibId") String studentLibId) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("DELETE FROM student_attendance WHERE student_lib_id = ? AND attend_status = ?");
            db.pstmt.setString(1, studentLibId);
            db.pstmt.setString(2, "0");
            int done = db.pstmt.executeUpdate();
            if (done > 0) {
               response = Response.status(200).entity(new Integer(200)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(201).entity(e.getMessage()).build();
        }
        
        return response;
    }
    
    @GET
    @Path("/attendStatus/{studentLibId}")
    public Response attendanceStatus(@PathParam("studentLibId") String studentLibId){
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT attend_status FROM student_attendance WHERE student_lib_id = ? ORDER BY attend_id DESC");
            db.pstmt.setString(1, studentLibId);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                response = Response.status(200).entity(Integer.parseInt(db.rst.getString(1))).build();
            } else {
                response = Response.status(200).entity(new Integer(4)).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(201).entity(e.getMessage()).build();
        }
        return response;
    }
    
    @GET
    @Path("/getAllRequest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAttendanceRequest() {
        Response response = null;
        List<AttendanceRequestPojo> al = new ArrayList<AttendanceRequestPojo>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT attend_id , student_lib_id, attend_status FROM student_attendance WHERE attend_status = ? OR attend_status = ?");
            db.pstmt.setString(1, "0");
            db.pstmt.setString(2, "1");
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    AttendanceRequestPojo arp = new AttendanceRequestPojo();
                    arp.setAttendId(db.rst.getInt(1));
                    arp.setStudentLibId(db.rst.getString(2));
                    arp.setAttendStatus(Integer.parseInt(db.rst.getString(3)));
                    al.add(arp);
                }   
                response = Response.status(200).entity(al).build();
            } else {
                response = Response.status(201).entity("No Content found !").build();
            }
        } catch (Exception e) {
            response = Response.status(202).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }
    
    @GET
    @Path("/approveAttend/{attendId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response approveAttendanceRequest(@PathParam("attendId") String attendId) {
        Response response = null;
        List<AttendanceRequestPojo> al = new ArrayList<AttendanceRequestPojo>();
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currDate = sdf.format(date);
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("UPDATE student_attendance SET attend_status = ?, entry_time = ? WHERE attend_id = ?");
            db.pstmt.setString(1, "1");
            db.pstmt.setString(2, currDate);
            db.pstmt.setString(3, attendId);
            int done = db.pstmt.executeUpdate();
            if (done > 0) { 
                response = Response.status(200).build();
            } else {
                response.status(201).entity("no content found").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(202).entity(e.getMessage()).build();
        }
        return response;
    }
    
    
    
    @GET
    @Path("/exitApprove/{attendId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response exitAttendanceRequest(@PathParam("attendId") String attendId) {
        Response response = null;
        List<AttendanceRequestPojo> al = new ArrayList<AttendanceRequestPojo>();
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currDate = sdf.format(date);
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("UPDATE student_attendance SET attend_status = ?, exit_time = ? WHERE attend_id = ?");
            db.pstmt.setString(1, "2");
            db.pstmt.setString(2, currDate);
            db.pstmt.setString(3, attendId);
            int done = db.pstmt.executeUpdate();
            if (done > 0) { 
                response = Response.status(200).entity("success").build();
            } else {
                response.status(201).entity("failed").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(202).entity(e.getMessage()).build();
        }
        return response;
    }
    
    @GET
    @Path("/getAttendanceRecord/{fromDate}/{toDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAttendanceRecord(@PathParam("fromDate") String fromDate, @PathParam("toDate") String toDate) {
        Response response = null;
        List<AttendanceList> alList = new ArrayList<AttendanceList>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM student_attendance WHERE entry_time >= ? AND exit_time <= ?");
            db.pstmt.setString(1, fromDate);
            db.pstmt.setString(2, toDate);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    AttendanceList al = new AttendanceList();
                    al.setAttendId(Integer.parseInt(db.rst.getString(1)));
                    al.setAttendStatus(Integer.parseInt(db.rst.getString(5)));
                    al.setStudentLibId(db.rst.getString(2));
                    al.setEntryTime(db.rst.getString(3));
                    al.setExitTime(db.rst.getString(4));
                    alList.add(al);
                }
                response = Response.status(200).entity(alList).build();
            } else {
                response = Response.status(202).entity("No data found").build();
            }
        } catch (Exception e) {
            response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }
    
}
