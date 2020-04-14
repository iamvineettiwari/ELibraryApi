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
public class BookInfo {
    
    private int bookId;
    private String bookCategory;
    private String bookSubCategory;
    private String bookName;
    private String bookWriter;
    private String bookPublisher;
    private String bookEdition;
    private int bookStock;
    private long bookPrice;
    private String bookDescription;

    public BookInfo() {
    }

    public BookInfo(int bookId, String bookCategory, String bookSubCategory, String bookName, String bookWriter, String bookPublisher, String bookEdition, int bookStock, long bookPrice, String bookDescription) {
        this.bookId = bookId;
        this.bookCategory = bookCategory;
        this.bookSubCategory = bookSubCategory;
        this.bookName = bookName;
        this.bookWriter = bookWriter;
        this.bookPublisher = bookPublisher;
        this.bookEdition = bookEdition;
        this.bookStock = bookStock;
        this.bookPrice = bookPrice;
        this.bookDescription = bookDescription;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getBookSubCategory() {
        return bookSubCategory;
    }

    public void setBookSubCategory(String bookSubCategory) {
        this.bookSubCategory = bookSubCategory;
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

    public int getBookStock() {
        return bookStock;
    }

    public void setBookStock(int bookStock) {
        this.bookStock = bookStock;
    }

    public long getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(long bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

}
