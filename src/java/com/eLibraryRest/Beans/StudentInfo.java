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
public class StudentInfo {
    
    private String studentLibId;
    private String studentName;
    private String studentEmail;
    private String studentEnrollNo;
    private long studentAadhar;
    private String studentCourse;
    private int studentSemester;
    private String studentAddress;
    private long studentContact;
    private String studentPhoto;
    private String studentStream;

    public StudentInfo() {
    }

    public StudentInfo(String studentLibId, String studentName, String studentEmail, String studentEnrollNo, long studentAadhar, 
            String studentCourse, int studentSemester, String studentAddress, long studentContact, String studentPhoto, String studentStream) {
        this.studentLibId = studentLibId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentEnrollNo = studentEnrollNo;
        this.studentAadhar = studentAadhar;
        this.studentCourse = studentCourse;
        this.studentSemester = studentSemester;
        this.studentAddress = studentAddress;
        this.studentContact = studentContact;
        this.studentPhoto = studentPhoto;
        this.studentStream = studentStream;
    }

    public String getStudentLibId() {
        return studentLibId;
    }

    public void setStudentLibId(String studentLibId) {
        this.studentLibId = studentLibId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentEnrollNo() {
        return studentEnrollNo;
    }

    public void setStudentEnrollNo(String studentEnrollNo) {
        this.studentEnrollNo = studentEnrollNo;
    }

    public long getStudentAadhar() {
        return studentAadhar;
    }

    public void setStudentAadhar(long studentAadhar) {
        this.studentAadhar = studentAadhar;
    }

    public String getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(String studentCourse) {
        this.studentCourse = studentCourse;
    }

    public String getStudentStream() {
        return studentStream;
    }

    public void setStudentStream(String studentStream) {
        this.studentStream = studentStream;
    }

    
    
    public int getStudentSemester() {
        return studentSemester;
    }

    public void setStudentSemester(int studentSemester) {
        this.studentSemester = studentSemester;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public long getStudentContact() {
        return studentContact;
    }

    public void setStudentContact(long studentContact) {
        this.studentContact = studentContact;
    }

    public String getStudentPhoto() {
        return studentPhoto;
    }

    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }
    
}
