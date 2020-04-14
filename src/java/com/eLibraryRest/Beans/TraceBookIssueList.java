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
public class TraceBookIssueList {
    
    private int bookId;
    private int bookIssueId;
    private String studentLibId;
    private String bookIssueDate;
    private String bookReturnDate;

    public TraceBookIssueList() {
    }

    public TraceBookIssueList(int bookId, int bookIssueId, String studentLibId, String bookIssueDate, String bookReturnDate) {
        this.bookId = bookId;
        this.bookIssueId = bookIssueId;
        this.studentLibId = studentLibId;
        this.bookIssueDate = bookIssueDate;
        this.bookReturnDate = bookReturnDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBookIssueId() {
        return bookIssueId;
    }

    public void setBookIssueId(int bookIssueId) {
        this.bookIssueId = bookIssueId;
    }

    public String getStudentLibId() {
        return studentLibId;
    }

    public void setStudentLibId(String studentLibId) {
        this.studentLibId = studentLibId;
    }

    public String getBookIssueDate() {
        return bookIssueDate;
    }

    public void setBookIssueDate(String bookIssueDate) {
        this.bookIssueDate = bookIssueDate;
    }

    public String getBookReturnDate() {
        return bookReturnDate;
    }

    public void setBookReturnDate(String bookReturnDate) {
        this.bookReturnDate = bookReturnDate;
    }
    
        
}
