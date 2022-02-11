package com.eduardorosillo.mobileapplication_c196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseTitle;
    private Date startDate;
    private Date endDate;
    private String courseStatus;
    private String courseNotes;
    private int termID;

    public Course(int courseID, String courseTitle, Date startDate, Date endDate, String courseStatus, String courseNotes, int termID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
        this.courseNotes = courseNotes;
        this.termID = termID;
    }

    public int getCourseID() { return courseID; }

    public void setCourseID(int courseID) { this.courseID = courseID; }

    public String getCourseTitle() { return courseTitle; }

    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public Date getStartDate() { return startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getCourseStatus() { return courseStatus; }

    public void setCourseStatus(String courseStatus) { this.courseStatus = courseStatus; }

    public String getCourseNotes() { return courseNotes; }

    public void setCourseNotes(String courseNotes) { this.courseNotes = courseNotes; }

    public int getTermID() { return termID; }

    public void setTermID(int termID) { this.termID = termID; }
}
