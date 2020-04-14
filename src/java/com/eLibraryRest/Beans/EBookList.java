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
public class EBookList {
    
    private int ebookId;
    private String ebookCategory;
    private String ebookSubCategory;
    private String ebookName;
    private String ebookLocation;

    public EBookList() {
    }

    public EBookList(int ebookId, String ebookCategory, String ebookSubCategory, String ebookName, String ebookLocation) {
        this.ebookId = ebookId;
        this.ebookCategory = ebookCategory;
        this.ebookSubCategory = ebookSubCategory;
        this.ebookName = ebookName;
        this.ebookLocation = ebookLocation;
    }

    public int getEbookId() {
        return ebookId;
    }

    public void setEbookId(int ebookId) {
        this.ebookId = ebookId;
    }

    public String getEbookCategory() {
        return ebookCategory;
    }

    public void setEbookCategory(String ebookCategory) {
        this.ebookCategory = ebookCategory;
    }

    public String getEbookSubCategory() {
        return ebookSubCategory;
    }

    public void setEbookSubCategory(String ebookSubCategory) {
        this.ebookSubCategory = ebookSubCategory;
    }

    public String getEbookName() {
        return ebookName;
    }

    public void setEbookName(String ebookName) {
        this.ebookName = ebookName;
    }

    public String getEbookLocation() {
        return ebookLocation;
    }

    public void setEbookLocation(String ebookLocation) {
        this.ebookLocation = ebookLocation;
    }
    
}
