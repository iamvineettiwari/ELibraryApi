
package com.eLibraryRest.Beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement        
public class Ebook_Info {
    
    private int ebookId;
    private String ebookCategory;
    private String ebookSubCategory;
    private String ebookName;
    private String ebookWriter;
    private String ebookPublisher;
    private String ebookEdition;
    private String ebookDescription;
    private String ebookLocation;
    
    public Ebook_Info(){
        
    }

    public Ebook_Info(int ebookId, String ebookCategory, String ebookSubCategory, String ebookName, String ebookWriter, String ebookPublisher, String ebookEdition, String ebookDescription, String ebookLocation) {
        this.ebookId = ebookId;
        this.ebookCategory = ebookCategory;
        this.ebookSubCategory = ebookSubCategory;
        this.ebookName = ebookName;
        this.ebookWriter = ebookWriter;
        this.ebookPublisher = ebookPublisher;
        this.ebookEdition = ebookEdition;
        this.ebookDescription = ebookDescription;
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

    public String getEbookWriter() {
        return ebookWriter;
    }

    public void setEbookWriter(String ebookWriter) {
        this.ebookWriter = ebookWriter;
    }

    public String getEbookPublisher() {
        return ebookPublisher;
    }

    public void setEbookPublisher(String ebookPublisher) {
        this.ebookPublisher = ebookPublisher;
    }

    public String getEbookEdition() {
        return ebookEdition;
    }

    public void setEbookEdition(String ebookEdition) {
        this.ebookEdition = ebookEdition;
    }

    public String getEbookDescription() {
        return ebookDescription;
    }

    public void setEbookDescription(String ebookDescription) {
        this.ebookDescription = ebookDescription;
    }

    public String getEbookLocation() {
        return ebookLocation;
    }

    public void setEbookLocation(String ebookLocation) {
        this.ebookLocation = ebookLocation;
    }
   
    
    
}
