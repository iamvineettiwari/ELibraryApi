/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eLibraryRest;

import com.eLibraryRest.Beans.CoursesPojo;
import com.eLibraryRest.Beans.DBConnection;
import com.eLibraryRest.Beans.StreamPojo;
import java.util.ArrayList;
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
@Path("/course")
public class Courses {

    @GET
    @Path("/allCourse")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCourses() {
        Response response = null;
        List<CoursesPojo> courseList = new ArrayList<CoursesPojo>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM course_info");
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    CoursesPojo cp = new CoursesPojo();
                    cp.setCourseId(db.rst.getInt(1));
                    cp.setCourseName(db.rst.getString(2));
                    courseList.add(cp);
                }
                response = Response.status(200).entity(courseList).build();
            } else {
                response = Response.status(201).entity("No data found").build();
            }
        } catch (Exception e) {
                response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/findStream/{courseName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStreamByCourseId(@PathParam("courseName") String searchTerm) {
        Response response = null;
        List<StreamPojo> streamList = new ArrayList<StreamPojo>();
        String courseId = "";
        DBConnection db = new DBConnection();
        try {
            db.pstmt = db.conn.prepareStatement("SELECT course_id FROM course_info WHERE course_name = ?");
            db.pstmt.setString(1, searchTerm);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                courseId = db.rst.getString(1);
                db.rst.close();
                db.pstmt = db.conn.prepareStatement("SELECT stream_info.stream_name, stream_info.stream_id, course_info.course_id FROM stream_info, course_info WHERE course_info.course_id = ? AND stream_info.course_id = ?");
                db.pstmt.setString(1, courseId);
                db.pstmt.setString(2, courseId);
                db.rst = db.pstmt.executeQuery();
                if (db.rst.isBeforeFirst()) {
                    while (db.rst.next()) {
                        StreamPojo strm = new StreamPojo();
                        strm.setCourseId(Integer.parseInt(db.rst.getString(3)));
                        strm.setStreamId(Integer.parseInt(db.rst.getString(2)));
                        strm.setStreamName(db.rst.getString(1));
                        streamList.add(strm);
                    }
                    response = Response.status(200).entity(streamList).build();
                } else {
                    response = Response.status(201).entity("No data found").build();
                }
            } else {
                response = Response.status(201).entity("No course available").build();
            }
        } catch (Exception e) {
                response = Response.status(202).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }
}
