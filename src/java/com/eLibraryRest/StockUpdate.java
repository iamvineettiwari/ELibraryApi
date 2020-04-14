package com.eLibraryRest;

import com.eLibraryRest.Beans.BookInfo;
import com.eLibraryRest.Beans.DBConnection;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONArray;

@Path("/stock")
public class StockUpdate {

    @GET
    @Path("/stockUpdate/{searchTerm}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response stockDetailsFromBookInfo(@PathParam("searchTerm") String searchTerm) {
        Response response = null;
        List<BookInfo> bookInfo = new ArrayList<BookInfo>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM book_info WHERE book_id = ?");
            db.pstmt.setString(1, searchTerm);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                BookInfo bi = new BookInfo();
                bi.setBookId(db.rst.getInt(1));
                bi.setBookName(db.rst.getString(4));
                bi.setBookCategory(db.rst.getString(2));
                bi.setBookSubCategory(db.rst.getString(3));
                bi.setBookWriter(db.rst.getString(5));
                bi.setBookPublisher(db.rst.getString(6));
                bi.setBookEdition(db.rst.getString(7));
                bi.setBookStock(Integer.parseInt(db.rst.getString(8)));
                bi.setBookPrice(db.rst.getLong(9));
                bi.setBookDescription(db.rst.getString(10));
                bookInfo.add(bi);
                
                response = Response.status(200).entity(bookInfo).build();
            } else {
                response = Response.status(202).entity("No Detail found").build();
            }
        } catch (Exception e) {
            response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }
    
    @POST
    @Path("/stockUpdateNow")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateBookStock(@FormDataParam("bookId") String bookId, @FormDataParam("stockValue") String stockValue) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("UPDATE book_info SET book_stock = ? WHERE book_id = ?");
            db.pstmt.setString(1, stockValue);
            db.pstmt.setString(2, bookId);
            int done = db.pstmt.executeUpdate();
            if (done > 0) {
                response = Response.status(200).entity("success").build();
            } else {
                response = Response.status(202).entity("Something went wrong").build();
            }
        } catch (Exception e) {
            response = response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }
}
