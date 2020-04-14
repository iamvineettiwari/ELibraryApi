/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eLibraryRest;

import com.eLibraryRest.Beans.DBConnection;
import com.eLibraryRest.Beans.EBookList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/eBook")
public class Ebook {

    @POST
    @Path("/addEBook")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addEbookToDatabase(@FormDataParam("file") InputStream uploadFileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("category") String category,
            @FormDataParam("subCategory") String subCategory, @FormDataParam("bookName") String bookName,
            @FormDataParam("bookWriter") String bookWriter, @FormDataParam("bookPublisher") String bookPublisher,
            @FormDataParam("bookEdition") String bookEdition, @FormDataParam("bookDescription") String bookDescription,
            @FormDataParam("bookLocation") String bookLocation) {
        Response response = null;
        try {
            String fileLocation = this.getClass().getResource("../../../../eBook/").getPath() + fileDetail.getFileName();
            FileOutputStream fos = new FileOutputStream(new File(fileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = uploadFileInputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            fos.flush();
            fos.close();

            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("INSERT INTO ebook_info(ebook_category, ebook_sub_category, ebook_name,"
                    + "ebook_writer, ebook_publisher, ebook_edition, ebook_description, ebook_location) VALUES(?,?,?,?,?,?,?,?)");
            db.pstmt.setString(1, category);
            db.pstmt.setString(2, subCategory);
            db.pstmt.setString(3, bookName);
            db.pstmt.setString(4, bookWriter);
            db.pstmt.setString(5, bookPublisher);
            db.pstmt.setString(6, bookEdition);
            db.pstmt.setString(7, bookDescription);
            db.pstmt.setString(8, fileDetail.getFileName());

            int done = db.pstmt.executeUpdate();
            if (done > 0) {
                response = Response.status(200).entity("success").build();
            } else {
                response = Response.status(202).entity("failed").build();
            }
        } catch (Exception e) {
            response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/allBook")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEBook() {
        Response response = null;
        List<EBookList> ebookList = new ArrayList<EBookList>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM ebook_info");
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    EBookList ebl = new EBookList();
                    ebl.setEbookId(Integer.parseInt(db.rst.getString(1)));
                    ebl.setEbookCategory(db.rst.getString(2));
                    ebl.setEbookSubCategory(db.rst.getString(3));
                    ebl.setEbookName(db.rst.getString(4));
                    ebl.setEbookLocation(db.rst.getString(9));
                    ebookList.add(ebl);
                }
                response = Response.status(200).entity(ebookList).build();
            } else {
                response = Response.status(200).entity("No data found").build();
            }
        } catch (Exception e) {
            response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }
    
    @GET
    @Path("/deleteEbook/{ebookId}")
    public Response deleteEbook(@PathParam("ebookId") String ebookId) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("DELETE FROM ebook_info WHERE ebook_id = ? ");
            db.pstmt.setString(1, ebookId);
            int done = db.pstmt.executeUpdate();
            if (done > 0) {
                response = Response.status(200).entity("Successfully deleted e-book").build();
            } else {
                response = Response.status(202).entity("Something went wrong").build();;
            }
        } catch (Exception e) {
            response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

}
