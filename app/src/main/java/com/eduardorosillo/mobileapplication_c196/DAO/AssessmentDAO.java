package com.eduardorosillo.mobileapplication_c196.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.eduardorosillo.mobileapplication_c196.Entity.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAssessment(Assessment assessment);

    @Update
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentType ASC")
    List<Assessment> getAllAssessments();

/*    @Query("SELECT * FROM assessments WHERE assessmentType = :assessmentType")
    Assessment getAssessmentByID(int ID);*/

}
