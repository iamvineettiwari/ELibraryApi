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
public class AdminInfo {

    private int adminId;
    private long adminAadhar;
    private String adminName;
    private String adminEmail;
    private long adminContact;
    private String adminAddress;
    private String adminUsername;
    private String adminPassword;
    private String adminPhoto;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public long getAdminAadhar() {
        return adminAadhar;
    }

    public void setAdminAadhar(long adminAadhar) {
        this.adminAadhar = adminAadhar;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public long getAdminContact() {
        return adminContact;
    }

    public void setAdminContact(long adminContact) {
        this.adminContact = adminContact;
    }

    public String getAdminAddress() {
        return adminAddress;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminPhoto() {
        return adminPhoto;
    }

    public void setAdminPhoto(String adminPhoto) {
        this.adminPhoto = adminPhoto;
    }

    public AdminInfo(int adminId, long adminAadhar, String adminName, String adminEmail, long adminContact, String adminAddress, String adminUsername, String adminPassword, String adminPhoto) {

        this.adminId = adminId;
        this.adminAadhar = adminAadhar;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminContact = adminContact;
        this.adminAddress = adminAddress;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.adminPhoto = adminPhoto;
    }

    public AdminInfo() {
    }
    
}
