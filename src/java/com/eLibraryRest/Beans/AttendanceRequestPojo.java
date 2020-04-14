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
public class AttendanceRequestPojo {
    
    private int attendId;
    private String studentLibId;
    private int attendStatus;

    public AttendanceRequestPojo() {
    }

    public AttendanceRequestPojo(int attendId, String studentLibId, int attendStatus) {
        this.attendId = attendId;
        this.studentLibId = studentLibId;
        this.attendStatus = attendStatus;
    }

    public int getAttendId() {
        return attendId;
    }

    public void setAttendId(int attendId) {
        this.attendId = attendId;
    }

    public String getStudentLibId() {
        return studentLibId;
    }

    public void setStudentLibId(String studentLibId) {
        this.studentLibId = studentLibId;
    }

    public int getAttendStatus() {
        return attendStatus;
    }

    public void setAttendStatus(int attendStatus) {
        this.attendStatus = attendStatus;
    }
    
    
}
