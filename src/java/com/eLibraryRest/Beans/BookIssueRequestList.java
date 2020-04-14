/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eLibraryRest.Beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Manas
 */
@XmlRootElement
public class BookIssueRequestList {
    
    private int issueId;
    private int bookId;
    private String studentLibId;
    private String bookApplyDate;
    private String bookName;
    private String bookWriter;

    public BookIssueRequestList() {
    }

    public BookIssueRequestList(int issueId, int bookId, String studentLibId, String bookApplyDate, String bookName, String bookWriter) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.studentLibId = studentLibId;
        this.bookApplyDate = bookApplyDate;
        this.bookName = bookName;
        this.bookWriter = bookWriter;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getStudentLibId() {
        return studentLibId;
    }

    public void setStudentLibId(String studentLibId) {
        this.studentLibId = studentLibId;
    }

    public String getBookApplyDate() {
        return bookApplyDate;
    }

    public void setBookApplyDate(String bookApplyDate) {
        this.bookApplyDate = bookApplyDate;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookWriter() {
        return bookWriter;
    }

    public void setBookWriter(String bookWriter) {
        this.bookWriter = bookWriter;
    }
    
     
}
