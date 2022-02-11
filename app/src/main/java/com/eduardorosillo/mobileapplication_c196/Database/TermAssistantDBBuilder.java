package com.eduardorosillo.mobileapplication_c196.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.eduardorosillo.mobileapplication_c196.DAO.AssessmentDAO;
import com.eduardorosillo.mobileapplication_c196.DAO.CourseDAO;
import com.eduardorosillo.mobileapplication_c196.DAO.TermDAO;
import com.eduardorosillo.mobileapplication_c196.Entity.Assessment;
import com.eduardorosillo.mobileapplication_c196.Entity.Course;
import com.eduardorosillo.mobileapplication_c196.Entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 2, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class TermAssistantDBBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile TermAssistantDBBuilder INSTANCE;

    static TermAssistantDBBuilder getDatabase(final Context context){
        if(INSTANCE==null) {
            synchronized (TermAssistantDBBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermAssistantDBBuilder.class, "TermAssistantDB.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

