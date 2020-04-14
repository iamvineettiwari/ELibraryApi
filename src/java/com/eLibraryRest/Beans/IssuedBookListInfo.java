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
public class IssuedBookListInfo {
    
    private int bookId;
    private String bookName;
    private String bookWriter;
    private String bookPublisher;
    private String bookEdition;
    private String bookReturnDate;

    public IssuedBookListInfo() {
    }

    public IssuedBookListInfo(int bookId, String bookName, String bookWriter, String bookPublisher, String bookEdition, String bookReturnDate) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookWriter = bookWriter;
        this.bookPublisher = bookPublisher;
        this.bookEdition = bookEdition;
        this.bookReturnDate = bookReturnDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookEdition() {
        return bookEdition;
    }

    public void setBookEdition(String bookEdition) {
        this.bookEdition = bookEdition;
    }

    public String getBookReturnDate() {
        return bookReturnDate;
    }

    public void setBookReturnDate(String bookReturnDate) {
        this.bookReturnDate = bookReturnDate;
    }
    
    
    
}
