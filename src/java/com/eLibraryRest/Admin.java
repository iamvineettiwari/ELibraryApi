/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eLibraryRest;

import com.eLibraryRest.Beans.AdminInfo;
import com.eLibraryRest.Beans.DBConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
@Path("/admin")
public class Admin {

    @POST
    @Path("/addAdmin")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("adminName") String adminName,
            @FormDataParam("adminEmail") String adminEmail,
            @FormDataParam("adminContact") String adminContact,
            @FormDataParam("adminAddress") String adminAddress,
            @FormDataParam("adminAadhar") String adminAadhar,
            @FormDataParam("username") String username,
            @FormDataParam("password") String password) {
        String originalFileExtension = fileDetail.getFileName().substring(fileDetail.getFileName().indexOf("."));
        Random rand = new Random();
        int randNumber = rand.nextInt(9999999) + 1000000;
        String newFileName = adminName.substring(0, 3) + randNumber + originalFileExtension;
        String fileLocation = this.getClass().getResource("../../../../images/").getPath() + newFileName;
        Response res = null;
        //saving file  
        try {
            FileOutputStream out = new FileOutputStream(new File(fileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            out = new FileOutputStream(new File(fileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("INSERT INTO admin_info (admin_aadhar, admin_name, admin_email, admin_contact, admin_address, admin_username, admin_password, admin_photo) VALUES (?,?,?,?,?,?,?,?)");
            db.pstmt.setLong(1, Long.parseLong(adminAadhar));
            db.pstmt.setString(2, adminName);
            db.pstmt.setString(3, adminEmail);
            db.pstmt.setLong(4, Long.parseLong(adminContact));
            db.pstmt.setString(5, adminAddress);
            db.pstmt.setString(6, username);
            db.pstmt.setString(7, password);
            db.pstmt.setString(8, newFileName);
            int done = db.pstmt.executeUpdate();
            if (done > 0) {
                res = Response.status(200).entity("Successfully Registered Admin").build();
            } else {
                res = Response.status(101).entity("Something went wrong while registration !").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String output = adminName + "File successfully uploaded to : " + fileLocation;
        return res;
    }

    @GET
    @Path("/searchAdmin/{adminIdAadharName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchAdminByAadhar(@PathParam("adminIdAadharName") String aadhar) {
        Response response = null;
        AdminInfo adm = new AdminInfo();
        DBConnection db = new DBConnection();
        try {
            db.pstmt = db.conn.prepareStatement("SELECT * FROM admin_info WHERE admin_id = ? OR admin_aadhar = ? OR admin_name = ?");
            db.pstmt.setString(1, aadhar);
            db.pstmt.setString(2, aadhar);
            db.pstmt.setString(3, aadhar);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                adm.setAdminId(db.rst.getInt(1));
                adm.setAdminAadhar(db.rst.getLong(2));
                adm.setAdminName(db.rst.getString(3));
                adm.setAdminEmail(db.rst.getString(4));
                adm.setAdminContact(db.rst.getLong(5));
                adm.setAdminAddress(db.rst.getString(6));
                adm.setAdminUsername(db.rst.getString(7));
                adm.setAdminPassword(db.rst.getString(8));
                adm.setAdminPhoto(db.rst.getString(9));
                response = Response.status(200).entity(adm).build();
            } else {
                response = Response.status(202).entity("No data found").build();
            }
        } catch (Exception e) {
            response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @POST
    @Path("/updateAdminWithoutPhoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateAdminProfileWithoutPhoto(@FormDataParam("adminName") String adminName,
            @FormDataParam("adminEmail") String adminEmail,
            @FormDataParam("adminContact") String adminContact,
            @FormDataParam("adminAddress") String adminAddress,
            @FormDataParam("adminAadhar") String adminAadhar,
            @FormDataParam("adminUsername") String username,
            @FormDataParam("adminPassword") String password) {
        Response res = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("UPDATE admin_info SET admin_name = ?, admin_email = ?, admin_contact = ?, admin_address = ?, admin_password = ? WHERE admin_aadhar = ? AND admin_username = ?");
            db.pstmt.setString(1, adminName);
            db.pstmt.setString(2, adminEmail);
            db.pstmt.setLong(3, Long.parseLong(adminContact));
            db.pstmt.setString(4, adminAddress);
            db.pstmt.setString(5, password);
            db.pstmt.setLong(6, Long.parseLong(adminAadhar));
            db.pstmt.setString(7, username);
            int done = db.pstmt.executeUpdate();
            if (done > 0) {
                res = Response.status(200).entity("Suceess").build();
            } else {
                res = Response.status(201).entity("Failed").build();
            }
        } catch (Exception e) {
            res = Response.status(202).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return res;
    }

    @POST
    @Path("/updateAdminWithPhoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateAdminProfileWithPhoto(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("adminName") String adminName,
            @FormDataParam("adminEmail") String adminEmail,
            @FormDataParam("adminContact") String adminContact,
            @FormDataParam("adminAddress") String adminAddress,
            @FormDataParam("adminAadhar") String adminAadhar,
            @FormDataParam("username") String username,
            @FormDataParam("password") String password, @FormDataParam("oldPhoto") String oldPhoto) {
        String oldPhotoLocation = this.getClass().getResource("../../../../images/").getPath() + oldPhoto;
        File oldPhotoFile = new File(oldPhotoLocation);
        if (oldPhotoFile.exists()) {
            oldPhotoFile.delete();
        }
                
        String originalFileExtension = fileDetail.getFileName().substring(fileDetail.getFileName().indexOf("."));
        Random rand = new Random();
        int randNumber = rand.nextInt(9999999) + 1000000;
        String newFileName = adminName.substring(0, 3) + randNumber + originalFileExtension;
        String fileLocation = this.getClass().getResource("../../../../images/").getPath() + newFileName;
        Response res = null;
        //saving file  
        try {
            FileOutputStream out = new FileOutputStream(new File(fileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            out = new FileOutputStream(new File(fileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("UPDATE admin_info SET admin_name = ?, admin_email = ?, admin_contact = ?, admin_address = ?, admin_password = ?, admin_photo = ? WHERE admin_aadhar = ? AND admin_username = ?");
            db.pstmt.setString(1, adminName);
            db.pstmt.setString(2, adminEmail);
            db.pstmt.setLong(3, Long.parseLong(adminContact));
            db.pstmt.setString(4, adminAddress);
            db.pstmt.setString(5, password);
            db.pstmt.setString(6, newFileName);
            db.pstmt.setLong(7, Long.parseLong(adminAadhar));
            db.pstmt.setString(8, username);
            int done = db.pstmt.executeUpdate();
            if (done > 0) {
                res = Response.status(200).entity("Successfully Registered Admin").build();
            } else {
                res = Response.status(202).entity("Something went wrong while registration !").build();
            }
        } catch (Exception e) {
            res = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        String output = adminName + "File successfully uploaded to : " + fileLocation;
        return res;
    }

    @GET
    @Path("/allAdminList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAdminList() {
        Response response = null;
        List<AdminInfo> adminList = new ArrayList<AdminInfo>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM admin_info");
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    AdminInfo adm = new AdminInfo();
                    adm.setAdminId(Integer.parseInt(db.rst.getString(1)));
                    adm.setAdminAadhar(Long.parseLong(db.rst.getString(2)));
                    adm.setAdminName(db.rst.getString(3));
                    adm.setAdminEmail(db.rst.getString(4));
                    adm.setAdminContact(Long.parseLong(db.rst.getString(5)));
                    adm.setAdminAddress(db.rst.getString(6));
                    adm.setAdminUsername(db.rst.getString(7));
                    adm.setAdminPassword(db.rst.getString(8));
                    adm.setAdminPhoto(db.rst.getString(9));
                    adminList.add(adm);
                }
                response = Response.status(200).entity(adminList).build();
            } else {
                response = Response.status(201).entity("No data found").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.status(202).entity(e.getMessage()).build();
        }
        return response;
    }

    @GET
    @Path("/deleteAdmin/{aadharNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAdminByAadhar(@PathParam("aadharNumber") String aadharNumber) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("DELETE FROM admin_info WHERE admin_aadhar = ?");
            db.pstmt.setLong(1, Long.parseLong(aadharNumber));
            int done = db.pstmt.executeUpdate();
            if (done > 0) {
                response = Response.status(200).entity("success").build();
            } else {
                response = Response.status(201).entity("failed").build();
            }
        } catch (Exception e) {
            response = Response.status(202).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/doLogin/{username}/{password}")
    public Response doLogin(@PathParam("username") String user, @PathParam("password") String pass) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT admin_password, admin_name FROM admin_info WHERE admin_username = ?");
            db.pstmt.setString(1, user);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                if (db.rst.getString(1).equals(pass)) {
                    response = Response.status(200).entity(db.rst.getString(2)).build();
                } else {
                    response = Response.status(202).entity("password incorrect").build();
                }
            } else {
                response = Response.status(201).entity("user incorrect").build();
            }
        } catch (Exception e) {
            response = Response.status(203).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

}
