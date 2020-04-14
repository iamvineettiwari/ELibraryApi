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
public class AttendanceList {
    
    private int attendId;
    private int attendStatus;
    private String studentLibId;
    private String entryTime;
    private String exitTime;

    public AttendanceList() {
    }

    public AttendanceList(int attendId, int attendStatus, String studentLibId, String entryTime, String exitTime) {
        this.attendId = attendId;
        this.attendStatus = attendStatus;
        this.studentLibId = studentLibId;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public int getAttendId() {
        return attendId;
    }

    public void setAttendId(int attendId) {
        this.attendId = attendId;
    }

    public int getAttendStatus() {
        return attendStatus;
    }

    public void setAttendStatus(int attendStatus) {
        this.attendStatus = attendStatus;
    }

    public String getStudentLibId() {
        return studentLibId;
    }

    public void setStudentLibId(String studentLibId) {
        this.studentLibId = studentLibId;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }
    
    
    
}
