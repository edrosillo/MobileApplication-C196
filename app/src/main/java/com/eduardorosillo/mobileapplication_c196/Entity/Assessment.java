package com.eduardorosillo.mobileapplication_c196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String assessmentTitle;
    private String assessmentStartDate;
    private String assessmentEndDate;
    private String assessmentType;
    private int courseID;

    public Assessment(int assessmentID, String assessmentTitle, String assessmentStartDate, String assessmentEndDate, String assessmentType, int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentType = assessmentType;
        this.courseID = courseID;
    }

    public int getAssessmentID() { return assessmentID; }

    public void setAssessmentID(int assessmentID) { this.assessmentID = assessmentID; }

    public String getAssessmentTitle() { return assessmentTitle; }

    public void setAssessmentTitle(String assessmentTitle) { this.assessmentTitle = assessmentTitle; }

    public String getAssessmentStartDate() { return assessmentStartDate; }

    public void setAssessmentStartDate(String assessmentStartDate) { this.assessmentStartDate = assessmentStartDate; }

    public String getAssessmentEndDate() { return assessmentEndDate; }

    public void setAssessmentEndDate(String assessmentEndtDate) { this.assessmentEndDate = assessmentEndtDate; }

    public String getAssessmentType() { return assessmentType; }

    public void setAssessmentType(String assessmentType) { this.assessmentType = assessmentType; }

    public int getCourseID() { return courseID; }

    public void setCourseId(int courseId) { this.courseID = courseId; }
}
