/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eLibraryRest;

import com.eLibraryRest.Beans.BookInfo;
import com.eLibraryRest.Beans.BookIssueRequestList;
import com.eLibraryRest.Beans.DBConnection;
import com.eLibraryRest.Beans.IssuedBookListInfo;
import com.eLibraryRest.Beans.TraceBookIssueList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author Manas
 */
@Path("/book")
public class Book {

    @POST
    @Path("/addBook")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addBookToDatabase(@FormDataParam("bookCategory") String bookCategory, @FormDataParam("bookSubCategory") String bookSubCategory,
            @FormDataParam("bookName") String bookName, @FormDataParam("bookWriter") String bookWriter,
            @FormDataParam("bookPublisher") String bookPublisher, @FormDataParam("bookEdition") String bookEdition,
            @FormDataParam("bookStock") String bookStock, @FormDataParam("bookPrice") String bookPrice,
            @FormDataParam("bookDescription") String bookDiscription) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM book_info WHERE book_name = ?  AND book_writer = ? AND book_edition = ?");
            db.pstmt.setString(1, bookName);
            db.pstmt.setString(2, bookWriter);
            db.pstmt.setString(3, bookEdition);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                response = Response.status(203).entity("Book is already in the database").build();
            } else {
                db.pstmt = db.conn.prepareStatement("INSERT INTO book_info (book_category, book_sub_category, book_name, book_writer,"
                        + "book_publisher, book_edition, book_stock, book_price, book_description) VALUES (?,?,?,?,?,?,?,?,?)");
                db.pstmt.setString(1, bookCategory);
                db.pstmt.setString(2, bookSubCategory);
                db.pstmt.setString(3, bookName);
                db.pstmt.setString(4, bookWriter);
                db.pstmt.setString(5, bookPublisher);
                db.pstmt.setString(6, bookEdition);
                db.pstmt.setString(7, bookStock);
                db.pstmt.setString(8, bookPrice);
                db.pstmt.setString(9, bookDiscription);

                int done = db.pstmt.executeUpdate();
                if (done > 0) {
                    response = Response.status(200).entity("sucesss").build();
                } else {
                    response = Response.status(202).entity("failed").build();
                }
            }
        } catch (Exception e) {
            response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/deleteBook/{bookId}")
    public Response deleteBook(@PathParam("bookId") String bookId) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("DELETE FROM book_info WHERE book_id = ?");
            db.pstmt.setString(1, bookId);
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
    public Response getAllBookList() {
        Response response = null;
        List<BookInfo> bookObject = new ArrayList<BookInfo>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM book_info");
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    BookInfo bi = new BookInfo();
                    bi.setBookId(Integer.parseInt(db.rst.getString(1)));
                    bi.setBookCategory(db.rst.getString(2));
                    bi.setBookSubCategory(db.rst.getString(3));
                    bi.setBookName(db.rst.getString(4));
                    bi.setBookWriter(db.rst.getString(5));
                    bi.setBookPublisher(db.rst.getString(6));
                    bi.setBookEdition(db.rst.getString(7));
                    bi.setBookStock(Integer.parseInt(db.rst.getString(8)));
                    bi.setBookPrice(Long.parseLong(db.rst.getString(9)));
                    bi.setBookDescription(db.rst.getString(10));
                    bookObject.add(bi);
                }
                response = Response.status(200).entity(bookObject).build();
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
    @Path("/findBook/{searchTerm}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBookBySearchTerm(@PathParam("searchTerm") String searchTerm) {
        Response response = null;
        List<BookInfo> bookInfo = new ArrayList<BookInfo>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM book_info WHERE book_id = ? OR book_category = ? OR book_sub_category = ? OR "
                    + "book_name = ? OR book_writer = ? OR book_publisher = ? OR book_edition = ?");
            db.pstmt.setString(1, searchTerm);
            db.pstmt.setString(2, searchTerm);
            db.pstmt.setString(3, searchTerm);
            db.pstmt.setString(4, searchTerm);
            db.pstmt.setString(5, searchTerm);
            db.pstmt.setString(6, searchTerm);
            db.pstmt.setString(7, searchTerm);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    BookInfo bi = new BookInfo();
                    bi.setBookId(Integer.parseInt(db.rst.getString(1)));
                    bi.setBookCategory(db.rst.getString(2));
                    bi.setBookSubCategory(db.rst.getString(3));
                    bi.setBookName(db.rst.getString(4));
                    bi.setBookWriter(db.rst.getString(5));
                    bi.setBookPublisher(db.rst.getString(6));
                    bi.setBookEdition(db.rst.getString(7));
                    bi.setBookStock(Integer.parseInt(db.rst.getString(8)));
                    bi.setBookPrice(Integer.parseInt(db.rst.getString(9)));
                    bi.setBookDescription(db.rst.getString(10));
                    bookInfo.add(bi);
                }
                response = Response.status(200).entity(bookInfo).build();
            } else {
                response = Response.status(200).entity("no data").build();
            }
        } catch (Exception e) {
            response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @POST
    @Path("/requestIssue")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addRequestForBookIssue(@FormParam("bookId") String bookId, @FormParam("studentId") String studentId) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT COUNT(issue_id) FROM book_issue_info WHERE student_lib_id = ? AND book_return_status = ?");
            db.pstmt.setString(1, studentId);
            db.pstmt.setString(2, "0");
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                if (Integer.parseInt(db.rst.getString(1)) > 2) {
                    response = Response.status(201).build();
                } else {
                    db.pstmt = db.conn.prepareStatement("SELECT book_stock FROM book_info WHERE book_id = ?");
                    db.pstmt.setString(1, bookId);
                    db.rst = db.pstmt.executeQuery();
                    if (db.rst.next()) {
                        int stock = Integer.parseInt(db.rst.getString(1));
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String applyDate = sdf.format(date);
                        if (stock > 0) {
                            db.pstmt = db.conn.prepareStatement("INSERT INTO book_issue_info (book_id, student_lib_id, book_apply_date) VALUES (?,?,?)");
                            db.pstmt.setString(1, bookId);
                            db.pstmt.setString(2, studentId);
                            db.pstmt.setString(3, applyDate);

                            int done = db.pstmt.executeUpdate();

                            if (done > 0) {
                                int currStock = stock - 1;
                                if (currStock < 0) {
                                    currStock = 0;
                                }
                                db.pstmt = db.conn.prepareStatement("UPDATE book_info SET book_stock = ? WHERE book_id = ?");
                                db.pstmt.setInt(1, currStock);
                                db.pstmt.setString(2, bookId);
                                int done2 = db.pstmt.executeUpdate();
                                if (done2 > 0) {
                                    response = Response.status(200).entity("success").build();
                                } else {
                                    response = Response.status(203).entity("failed").build();
                                }
                            } else {
                                response = Response.status(203).entity("failed").build();
                            }

                        } else {
                            response = Response.status(202).entity("failed").build();
                        }
                    }
                }
            } else {
                db.pstmt = db.conn.prepareStatement("SELECT book_stock FROM book_info WHERE book_id = ?");
                db.pstmt.setString(1, bookId);
                db.rst = db.pstmt.executeQuery();
                if (db.rst.next()) {
                    int stock = Integer.parseInt(db.rst.getString(1));
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String applyDate = sdf.format(date);
                    if (stock > 0) {
                        db.pstmt = db.conn.prepareStatement("INSERT INTO book_issue_info (book_id, student_lib_id, book_apply_date) VALUES (?,?,?)");
                        db.pstmt.setString(1, bookId);
                        db.pstmt.setString(2, studentId);
                        db.pstmt.setString(3, applyDate);

                        int done = db.pstmt.executeUpdate();

                        if (done > 0) {
                            int currStock = stock - 1;
                            if (currStock < 0) {
                                currStock = 0;
                            }
                            db.pstmt = db.conn.prepareStatement("UPDATE book_info SET book_stock = ? WHERE book_id = ?");
                            db.pstmt.setInt(1, currStock);
                            db.pstmt.setString(2, bookId);
                            int done2 = db.pstmt.executeUpdate();
                            if (done2 > 0) {
                                response = Response.status(200).entity("success").build();
                            } else {
                                response = Response.status(203).entity("failed").build();
                            }
                        } else {
                            response = Response.status(203).entity("failed").build();
                        }

                    } else {
                        response = Response.status(202).entity("failed").build();
                    }
                }
            }
        } catch (Exception e) {
                        response = Response.status(202).entity(e.getMessage()).build();            
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/checkIssue/{bookId}/{studentId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response checkIssuedOrNot(@PathParam("bookId") String bookId,
            @PathParam("studentId") String studentId
    ) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT book_issue_status FROM book_issue_info WHERE book_id = ? AND student_lib_id = ? AND book_return_status = ?");
            db.pstmt.setString(1, bookId);
            db.pstmt.setString(2, studentId);
            db.pstmt.setString(3, "0");
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                response = Response.status(200).entity(db.rst.getString(1)).build();
            } else {
                response = Response.status(200).entity(new Integer(201)).build();
            }
        } catch (Exception e) {
                response = Response.status(200).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/getStock/{bookId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getBookStock(@PathParam("bookId") String bookId
    ) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT book_stock FROM book_info WHERE book_id = ?");
            db.pstmt.setString(1, bookId);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                int stock = Integer.parseInt(db.rst.getString(1));
                response = Response.status(200).entity(new Integer(stock)).build();
            } else {
                response = Response.status(201).entity(new Integer(8)).build();
            }
        } catch (Exception e) {
                response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/requestedIssueList")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRequestedBookIssueList() {
        Response response = null;
        List<BookIssueRequestList> bilist = new ArrayList<BookIssueRequestList>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT issue_id,book_id,student_lib_id,book_apply_date FROM book_issue_info WHERE book_issue_status = ? ORDER BY book_apply_date ASC");
            db.pstmt.setString(1, "0");
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    String book_id = db.rst.getString(2);
                    db.pstmt = db.conn.prepareStatement("SELECT book_name, book_writer FROM book_info WHERE book_id = ?");
                    db.pstmt.setString(1, book_id);
                    db.rst1 = db.pstmt.executeQuery();
                    if (db.rst1.next()) {
                        BookIssueRequestList birl = new BookIssueRequestList();
                        birl.setBookId(Integer.parseInt(book_id));
                        birl.setBookName(db.rst1.getString(1));
                        birl.setBookWriter(db.rst1.getString(2));
                        birl.setIssueId(Integer.parseInt(db.rst.getString(1)));
                        birl.setStudentLibId(db.rst.getString(3));
                        birl.setBookApplyDate(db.rst.getString(4));
                        bilist.add(birl);
                    }
                }
                response = Response.status(200).entity(bilist).build();
            } else {
                response = Response.status(201).entity("No data found").build();
            }
        } catch (Exception e) {
            response = Response.status(203).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @POST
    @Path("/issueBook")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response issueBook(@FormDataParam("issueId") String issueId, @FormDataParam("returnDate") String returnDate) {
        Response response = null;
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currDate = sdf.format(date);
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("UPDATE book_issue_info SET book_return_date = ?, book_issue_date = ?, book_issue_status = ?"
                    + " WHERE issue_id = ?");
            db.pstmt.setString(1, returnDate);
            db.pstmt.setString(2, currDate);
            db.pstmt.setString(3, "1");
            db.pstmt.setString(4, issueId);
            int done = db.pstmt.executeUpdate();
            if (done > 0) {
                response = Response.status(200).entity("sucess").build();
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
    @Path("/cancelRequestIssue/{bookIssueId}/{bookId}")
    public Response cancelBookIssueRequest(@PathParam("bookIssueId") String bookIssueId, @PathParam("bookId") String bookId) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT book_stock FROM book_info WHERE book_id = ?");
            db.pstmt.setString(1, bookId);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                int oldStock = Integer.parseInt(db.rst.getString(1));
                int currentStock = oldStock + 1;
                db.pstmt = db.conn.prepareStatement("UPDATE book_info SET book_stock = ? WHERE book_id = ?");
                db.pstmt.setString(1, String.valueOf(currentStock));
                db.pstmt.setString(2, bookId);
                int done1 = db.pstmt.executeUpdate();
                if (done1 > 0) {
                    db.pstmt = db.conn.prepareStatement("DELETE FROM book_issue_info WHERE book_id = ? AND issue_id = ?");
                    db.pstmt.setString(1, bookId);
                    db.pstmt.setString(2, bookIssueId);
                    int done2 = db.pstmt.executeUpdate();
                    if (done2 > 0) {
                        response = response.status(200).build();
                    }
                }
            }
        } catch (Exception e) {
            response = response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/getIssuedBook/{studentLibId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIssuedBookList(@PathParam("studentLibId") String studentLibId) {
        Response response = null;
        List<IssuedBookListInfo> ibli = new ArrayList<IssuedBookListInfo>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT book_id, book_return_date FROM book_issue_info WHERE book_issue_status = ? AND student_lib_id = ? AND book_return_status = ?");
            db.pstmt.setString(1, "1");
            db.pstmt.setString(2, studentLibId);
            db.pstmt.setString(3, "0");
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    String bookId = db.rst.getString(1);
                    db.pstmt = db.conn.prepareStatement("SELECT book_name, book_writer, book_edition, book_publisher FROM book_info WHERE book_id = ?");
                    db.pstmt.setString(1, bookId);
                    db.rst1 = db.pstmt.executeQuery();
                    if (db.rst1.next()) {
                        IssuedBookListInfo ibl = new IssuedBookListInfo();
                        ibl.setBookId(Integer.parseInt(bookId));
                        ibl.setBookName(db.rst1.getString(1));
                        ibl.setBookWriter(db.rst1.getString(2));
                        ibl.setBookEdition(db.rst1.getString(3));
                        ibl.setBookPublisher(db.rst1.getString(4));
                        ibl.setBookReturnDate(db.rst.getString(2));
                        ibli.add(ibl);
                    }
                }
                response = Response.status(200).entity(ibli).build();
            } else {
                response = Response.status(200).entity(ibli).build();
            }
        } catch (Exception e) {
            response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/getAllIssuedBook")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllIssuedBook() {
        Response response = null;
        List<TraceBookIssueList> tbil = new ArrayList<TraceBookIssueList>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM book_issue_info WHERE book_issue_status = ? AND book_return_status = ?");
            db.pstmt.setString(1, "1");
            db.pstmt.setString(2, "0");
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    TraceBookIssueList tb = new TraceBookIssueList();
                    tb.setBookId(db.rst.getInt(2));
                    tb.setBookIssueId(db.rst.getInt(1));
                    tb.setStudentLibId(db.rst.getString(3));
                    tb.setBookReturnDate(db.rst.getString(4));
                    tb.setBookIssueDate(db.rst.getString(5));
                    tbil.add(tb);
                }
                response = Response.status(200).entity(tbil).build();
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
    @Path("/returnBook/{issueId}/{bookId}")
    public Response returnBook(@PathParam("issueId") String issueId, @PathParam("bookId") String bookId) {
        Response response = null;
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT book_stock FROM book_info WHERE book_id = ?");
            db.pstmt.setString(1, bookId);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.next()) {
                int previousStock = Integer.parseInt(db.rst.getString(1));
                int currentStock = previousStock + 1;
                db.pstmt = db.conn.prepareStatement("UPDATE book_info SET book_stock = ? WHERE book_id = ?");
                db.pstmt.setString(1, String.valueOf(currentStock));
                db.pstmt.setString(2, bookId);
                int done = db.pstmt.executeUpdate();
                if (done > 0) {
                    db.pstmt = db.conn.prepareStatement("UPDATE book_issue_info SET book_return_status = ? WHERE book_id = ? AND issue_id = ?");
                    db.pstmt.setString(1, "1");
                    db.pstmt.setString(2, bookId);
                    db.pstmt.setString(3, issueId);
                    int done2 = db.pstmt.executeUpdate();
                    if (done2 > 0) {
                        response = Response.status(200).entity("success").build();
                    } else {
                        response = Response.status(201).entity("failed").build();
                    }
                }
            }
        } catch (Exception e) {
            response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

    @GET
    @Path("/searchIssuedBook/{searchTerm}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchIssuedBook(@PathParam("searchTerm") String searchTerm) {
        Response response = null;
        List<TraceBookIssueList> tbil = new ArrayList<TraceBookIssueList>();
        try {
            DBConnection db = new DBConnection();
            db.pstmt = db.conn.prepareStatement("SELECT * FROM book_issue_info WHERE book_issue_status = ? AND book_return_status = ? AND student_lib_id = ?");
            db.pstmt.setString(1, "1");
            db.pstmt.setString(2, "0");
            db.pstmt.setString(3, searchTerm);
            db.rst = db.pstmt.executeQuery();
            if (db.rst.isBeforeFirst()) {
                while (db.rst.next()) {
                    TraceBookIssueList tb = new TraceBookIssueList();
                    tb.setBookId(db.rst.getInt(2));
                    tb.setBookIssueId(db.rst.getInt(1));
                    tb.setStudentLibId(db.rst.getString(3));
                    tb.setBookReturnDate(db.rst.getString(4));
                    tb.setBookIssueDate(db.rst.getString(5));
                    tbil.add(tb);
                }
                response = Response.status(200).entity(tbil).build();
            } else {
                response = Response.status(202).entity("failed").build();
            }
        } catch (Exception e) {
            response = Response.status(201).entity(e.getMessage()).build();
            e.printStackTrace();
        }
        return response;
    }

}
