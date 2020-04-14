/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eLibraryRest;

import com.eLibraryRest.Beans.DBConnection;
import com.eLibraryRest.Beans.SMTP_Authenticator;
import com.eLibraryRest.Beans.StudentInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author Manas
 */
@Path("/student")
public class Student {

    @POST
    @Path("/addStudent")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addStudent(@FormDataParam("profilePhoto") InputStream fileInputStream,
            @FormDataParam("profilePhoto") FormDataContentDisposition fileDetail,
            @FormDataParam("studentCourse") String studentCourse,
            @FormDataParam("studentStream") String studentStream,
            @FormDataParam("studentAadhar") String studentAadhar,
            @FormDataParam("studentSemester") String studentSemester,
            @FormDataParam("studentName") String studentName,
            @FormDataParam("studentEmail") String studentEmail,
            @FormDataParam("studentAddress") String studentAddress,
            @FormDataParam("studentEnroll") String studentEnroll,
            @FormDataParam("studentContact") String studentContact) {
        Response response = null;
        try {
            String originalFileExtension = fileDetail.getFileName().substring(fileDetail.getFileName().indexOf("."));
            Random rand = new Random();
            int randNumber = rand.nextInt(9999999) + 1000000;
            int randPass = rand.nextInt(888888) + 111111;
            String newFileName = studentName.substring(0, 3) + randNumber + originalFileExtension;

            String fileLocation = this.getClass().getResource("../../../../images/student/").getPath() + newFileName;
            FileOutputStream fos = new FileOutputStream(new File(fileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = fileInputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            fos.flush();
            fos.close();

            String lib_id = "MUIT-" + studentAadhar.substring(0, 6);
            String pass = String.valueOf(randPass);

            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("INSERT INTO student_info (student_lib_id, student_name, student_email, student_enroll_no, student_aadhar,"
                    + "student_course, student_semester, student_address, student_contact, student_photo, student_stream) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            db.pstmt.setString(1, lib_id);
            db.pstmt.setString(2, studentName);
            db.pstmt.setString(3, studentEmail);
            db.pstmt.setString(4, studentEnroll);
            db.pstmt.setLong(5, Long.parseLong(studentAadhar));
            db.pstmt.setString(6, studentCourse);
            db.pstmt.setInt(7, Integer.parseInt(studentSemester));
            db.pstmt.setString(8, studentAddress);
            db.pstmt.setLong(9, Long.parseLong(studentContact));
            db.pstmt.setString(10, newFileName);
            db.pstmt.setString(11, studentStream);

            int done = db.pstmt.executeUpdate();

            if (done > 0) {
                db.pstmt = db.conn.prepareStatement("INSERT INTO student_login_info(student_lib_id, student_password) VALUES (?,?)");
                db.pstmt.setString(1, lib_id);
                db.pstmt.setString(2, pass);

                int secondDone = db.pstmt.executeUpdate();
                if (secondDone > 0) {
                    try {
                        String from = "manasjaiswal07@gmail.com";
                        String to = studentEmail;
                        String sub = "Credentials for MUIT E-library Mobile Application";
                        String message = "Dear Candidate, \nYour registration for MUIT Library has been successfully done. Your Library ID and "
                                + "Password is given below. Use these credentials for further access. \nUsername / Library ID : " + lib_id + ""
                                + "\nPassword : " + pass + " \nRegards,\nE-Library Cell,\nMUIT Lucknow";
                        Properties prop = new Properties();
                        prop.setProperty("mail.host", "smtp.gmail.com");
                        prop.setProperty("mail.smtp.starttls.enable", "true");
                        prop.setProperty("mail.smtp.port", "587");
                        prop.setProperty("mail.smtp.auth", "true");

                        SMTP_Authenticator auth = new SMTP_Authenticator("manasjaiswal07@gmail.com", "douknow_630");
                        Session session = Session.getInstance(prop, auth);
                        MimeMessage msg = new MimeMessage(session);
                        msg.setText(message);
                        msg.setSubject(sub);
                        msg.setFrom(new InternetAddress(from));
                        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                        Transport.send(msg);
                        response = Response.status(200).entity("success").build();
                    } catch (Exception e) {
                        response = Response.status(202).entity(e.getMessage()).build();
                        e.printStackTrace();
                    }
                } else {
                    db.pstmt = db.conn.prepareStatement("DELETE FROM student_info WHERE student_lib_id = ?");
                    db.pstmt.setString(1, lib_id);
                    int del = db.pstmt.executeUpdate();
                    response = Response.status(202).entity("Something went wrong while registration 2 !").build();
                }
            } else {
                response = Response.status(202).entity("Something went wrong while student registration 1 !").build();
            }
        } catch (Exception e) {
            response = Response.status(202).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/studentDetail/{searchTerm}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentDetailsByNameAadharLibId(@PathParam("searchTerm") String searchTerm) {
        Response response = null;
        List<StudentInfo> studentObject = new ArrayList<StudentInfo>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM student_info WHERE student_aadhar = ? OR student_lib_id = ? OR student_name = ?");
            db.pstmt.setString(1, searchTerm);
            db.pstmt.setString(2, searchTerm);
            db.pstmt.setString(3, searchTerm);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                StudentInfo stuInfo = new StudentInfo();
                stuInfo.setStudentLibId(db.rst.getString(1));
                stuInfo.setStudentName(db.rst.getString(2));
                stuInfo.setStudentEmail(db.rst.getString(3));
                stuInfo.setStudentEnrollNo(db.rst.getString(4));
                stuInfo.setStudentAadhar(Long.parseLong(db.rst.getString(5)));
                stuInfo.setStudentCourse(db.rst.getString(6));
                stuInfo.setStudentStream(db.rst.getString(7));
                stuInfo.setStudentSemester(Integer.parseInt(db.rst.getString(8)));
                stuInfo.setStudentAddress(db.rst.getString(9));
                stuInfo.setStudentContact(Long.parseLong(db.rst.getString(10)));
                stuInfo.setStudentPhoto(db.rst.getString(11));
                studentObject.add(stuInfo);
                response = Response.status(200).entity(studentObject).build();
            } else {
                response = Response.status(201).entity("No data found").build();
            }
        } catch (Exception e) {
                response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @POST
    @Path("/updateStudentProfile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateStudentProfile(@FormDataParam("file") InputStream uploadInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("studentName") String studentName,
            @FormDataParam("studentEmail") String studentEmail, @FormDataParam("studentContact") String studentContact,
            @FormDataParam("studentAddress") String studentAddress, @FormDataParam("studentAadhar") String studentAadhar,
            @FormDataParam("studentCourse") String studentCourse, @FormDataParam("studentStream") String studentStream,
            @FormDataParam("studentSemester") String studentSemester, @FormDataParam("studentEnrollNo") String studentEnrollNo,
            @FormDataParam("oldPhoto") String oldPhoto) {
        Response response = null;
        String oldPhotoFile = this.getClass().getResource("../../../../images/student/").getPath() + oldPhoto;
        File oldFile = new File(oldPhotoFile);
        if (oldFile.exists()) {
            oldFile.delete();
        }
        String newFileName = "";

        //saving file  
        try {
            String originalFileExtension = fileDetail.getFileName().substring(fileDetail.getFileName().indexOf("."));
            Random rand = new Random();
            int randNumber = rand.nextInt(9999999) + 1000000;
            newFileName = studentName.substring(0, 3) + randNumber + originalFileExtension;

            String fileLocation = this.getClass().getResource("../../../../images/student/").getPath() + newFileName;
            FileOutputStream fos = new FileOutputStream(new File(fileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            fos = new FileOutputStream(new File(fileLocation));
            while ((read = uploadInputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("UPDATE student_info SET student_name = ?, student_email = ?, student_course = ?,"
                    + "student_stream = ?, student_semester = ?, student_address = ?, student_contact = ?, student_photo = ?"
                    + " WHERE student_enroll_no = ? AND student_aadhar = ?");
            db.pstmt.setString(1, studentName);
            db.pstmt.setString(2, studentEmail);
            db.pstmt.setString(3, studentCourse);
            db.pstmt.setString(4, studentStream);
            db.pstmt.setInt(5, Integer.parseInt(studentSemester));
            db.pstmt.setString(6, studentAddress);
            db.pstmt.setLong(7, Long.parseLong(studentContact));
            db.pstmt.setString(8, newFileName);
            db.pstmt.setString(9, studentEnrollNo);
            db.pstmt.setLong(10, Long.parseLong(studentAadhar));

            int done = db.pstmt.executeUpdate();

            if (done > 0) {
                try {
                    String from = "manasjaiswal07@gmail.com";
                    String to = studentEmail;
                    String sub = "Credentials for MUIT E-library Mobile Application";
                    String message = "Dear Candidate, \nYour profile was successfully updated. Kindly login with your Library ID and Password to view your profile.\nRegards,\nMUIT, LUCKNOW.";
                    Properties prop = new Properties();
                    prop.setProperty("mail.host", "smtp.gmail.com");
                    prop.setProperty("mail.smtp.starttls.enable", "true");
                    prop.setProperty("mail.smtp.port", "587");
                    prop.setProperty("mail.smtp.auth", "true");

                    SMTP_Authenticator auth = new SMTP_Authenticator("manasjaiswal07@gmail.com", "douknow_630");
                    Session session = Session.getInstance(prop, auth);
                    MimeMessage msg = new MimeMessage(session);
                    msg.setText(message);
                    msg.setSubject(sub);
                    msg.setFrom(new InternetAddress(from));
                    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    Transport.send(msg);
                    response = Response.status(200).entity("success").build();
                } catch (Exception e) {
                    response = Response.status(201).entity(e.getMessage()).build();
                    e.printStackTrace();
                }
            } else {
                response = Response.status(202).entity("Something went wrong while student registration 1 !").build();
            }
        } catch (Exception e) {
            response = Response.status(203).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @POST
    @Path("/updateStudentProfileWithoutPhoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateStudentProfileWithoutPhoto(@FormDataParam("studentName") String studentName,
            @FormDataParam("studentEmail") String studentEmail, @FormDataParam("studentContact") String studentContact,
            @FormDataParam("studentAddress") String studentAddress, @FormDataParam("studentAadhar") String studentAadhar,
            @FormDataParam("studentCourse") String studentCourse, @FormDataParam("studentStream") String studentStream,
            @FormDataParam("studentSemester") String studentSemester, @FormDataParam("studentEnrollNo") String studentEnrollNo,
            @FormDataParam("oldPhoto") String oldPhoto) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("UPDATE student_info SET student_name = ?, student_email = ?, student_course = ?,"
                    + "student_stream = ?, student_semester = ?, student_address = ?, student_contact = ? "
                    + " WHERE student_enroll_no = ? AND student_aadhar = ?");
            db.pstmt.setString(1, studentName);
            db.pstmt.setString(2, studentEmail);
            db.pstmt.setString(3, studentCourse);
            db.pstmt.setString(4, studentStream);
            db.pstmt.setInt(5, Integer.parseInt(studentSemester));
            db.pstmt.setString(6, studentAddress);
            db.pstmt.setLong(7, Long.parseLong(studentContact));
            db.pstmt.setString(8, studentEnrollNo);
            db.pstmt.setLong(9, Long.parseLong(studentAadhar));

            int done = db.pstmt.executeUpdate();

            if (done > 0) {
                try {
                    String from = "manasjaiswal07@gmail.com";
                    String to = studentEmail;
                    String sub = "Credentials for MUIT E-library Mobile Application";
                    String message = "Dear Candidate, \nYour profile was successfully updated. Kindly login with your Library ID and Password to view your profile.\nRegards,\nMUIT, LUCKNOW.";
                    Properties prop = new Properties();
                    prop.setProperty("mail.host", "smtp.gmail.com");
                    prop.setProperty("mail.smtp.starttls.enable", "true");
                    prop.setProperty("mail.smtp.port", "587");
                    prop.setProperty("mail.smtp.auth", "true");

                    SMTP_Authenticator auth = new SMTP_Authenticator("manasjaiswal07@gmail.com", "douknow_630");
                    Session session = Session.getInstance(prop, auth);
                    MimeMessage msg = new MimeMessage(session);
                    msg.setText(message);
                    msg.setSubject(sub);
                    msg.setFrom(new InternetAddress(from));
                    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    Transport.send(msg);
                    response = Response.status(200).entity("success").build();
                } catch (Exception e) {
                    response = Response.status(201).entity(e.getMessage()).build();
                    e.printStackTrace();
                }
            } else {
                response = Response.status(202).entity("Something went wrong while student registration 1 !").build();
            }
        } catch (Exception e) {
            response = Response.status(203).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/studentLogin/{username}/{password}")
    public Response doStudentLogin(@PathParam("username") String studentUsername, @PathParam("password") String studentPassword) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT student_password FROM student_login_info WHERE student_lib_id = ?");
            db.pstmt.setString(1, studentUsername);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                if (db.rst.getString(1).equals(studentPassword)) {
                    response = Response.status(200).entity("success").build();
                } else {
                    response = Response.status(202).entity("failed").build();
                }
            } else {
                response = Response.status(201).entity("failed").build();
            }
        } catch (Exception e) {
                response = Response.status(203).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }
    
    @GET
    @Path("/insertToken/{id}/{token}")
    public Response insertToken(@PathParam("token") String token, @PathParam("id") String id) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("INSERT INTO notification_table (student_lib_id, student_key) VALUES (?,?)");
            db.pstmt.setString(1, id);
            db.pstmt.setString(2, token);
            int i = db.pstmt.executeUpdate();
            if (i > 0) {
                response = Response.status(200).entity("success").build();
            }
        } catch (Exception e) {
                response = Response.status(200).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/allStudent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allStudentList() {
        Response response = null;
        List<StudentInfo> stuInfo = new ArrayList<StudentInfo>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM student_info");
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    StudentInfo student = new StudentInfo();
                    student.setStudentLibId(db.rst.getString(1));
                    student.setStudentName(db.rst.getString(2));
                    student.setStudentEmail(db.rst.getString(3));
                    student.setStudentEnrollNo(db.rst.getString(4));
                    student.setStudentAadhar(Long.parseLong(db.rst.getString(5)));
                    student.setStudentCourse(db.rst.getString(6));
                    student.setStudentStream(db.rst.getString(7));
                    student.setStudentSemester(Integer.parseInt(db.rst.getString(8)));
                    student.setStudentAddress(db.rst.getString(9));
                    student.setStudentContact(Long.parseLong(db.rst.getString(10)));
                    student.setStudentPhoto(db.rst.getString(11));
                    stuInfo.add(student);
                }
                response = Response.status(200).entity(stuInfo).build();
            } else {
                response = Response.status(201).entity("failed").build();
            }
        } catch (Exception e) {
                response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }
    
    

    @GET
    @Path("/allStudent/{searchTerm}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allStudentListBySortedWay(@PathParam("searchTerm") String searchTerm) {
        Response response = null;
        List<StudentInfo> stuInfo = new ArrayList<StudentInfo>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM student_info WHERE student_lib_id = ? OR student_course = ? OR student_stream = ?"
                    + "OR student_semester = ? OR student_contact = ?");
            db.pstmt.setString(1, searchTerm);
            db.pstmt.setString(2, searchTerm);
            db.pstmt.setString(3, searchTerm);
            db.pstmt.setString(4, searchTerm);
            db.pstmt.setString(5, searchTerm);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    StudentInfo student = new StudentInfo();
                    student.setStudentLibId(db.rst.getString(1));
                    student.setStudentName(db.rst.getString(2));
                    student.setStudentEmail(db.rst.getString(3));
                    student.setStudentEnrollNo(db.rst.getString(4));
                    student.setStudentAadhar(Long.parseLong(db.rst.getString(5)));
                    student.setStudentCourse(db.rst.getString(6));
                    student.setStudentStream(db.rst.getString(7));
                    student.setStudentSemester(Integer.parseInt(db.rst.getString(8)));
                    student.setStudentAddress(db.rst.getString(9));
                    student.setStudentContact(Long.parseLong(db.rst.getString(10)));
                    student.setStudentPhoto(db.rst.getString(11));
                    stuInfo.add(student);
                }
                response = Response.status(200).entity(stuInfo).build();
            } else {
                response = Response.status(201).entity("failed").build();
            }
        } catch (Exception e) {
                response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/deleteStudent/{libID}")
    public Response deleteStudent(@PathParam("libID") String libId) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("DELETE FROM student_login_info WHERE student_lib_id = ?");
            db.pstmt.setString(1, libId);
            int done = db.pstmt.executeUpdate();
            if (done > 0) {
                db.pstmt = db.conn.prepareStatement("DELETE FROM student_info WHERE student_lib_id = ?");
                db.pstmt.setString(1, libId);
                int done2 = db.pstmt.executeUpdate();
                if (done2 > 0) {
                    response = Response.status(200).entity("success").build();
                } else {
                    response = Response.status(203).entity("failed").build();
                }
            } else {
                response = Response.status(201).entity("failed").build();
            }
        } catch (Exception e) {
            response = Response.status(202).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

}
