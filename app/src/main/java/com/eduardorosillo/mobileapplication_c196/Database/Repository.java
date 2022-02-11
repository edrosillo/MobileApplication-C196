package com.eduardorosillo.mobileapplication_c196.Database;

import android.app.Application;

import com.eduardorosillo.mobileapplication_c196.DAO.AssessmentDAO;
import com.eduardorosillo.mobileapplication_c196.DAO.CourseDAO;
import com.eduardorosillo.mobileapplication_c196.DAO.TermDAO;
import com.eduardorosillo.mobileapplication_c196.Entity.Assessment;
import com.eduardorosillo.mobileapplication_c196.Entity.Course;
import com.eduardorosillo.mobileapplication_c196.Entity.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    List<Term> mAllTerms;
    List<Course> mAllCourses;
    List<Assessment> mAllAssessments;

    private static int NUMBER_OF_THREADS = 6;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        TermAssistantDBBuilder db = TermAssistantDBBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
    }

    //Gets All Terms from the Database
    public List<Term>getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms=mTermDAO.getAllTerms();
        });
        //Add delay to allow for processing time, or it will return null
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    //Inserts Terms into Database
    public void insert(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.insertTerm(term);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Updates Term in Database
    public void update(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.updateTerm(term);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Deletes Term in Database
    public void delete(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.deleteTerm(term);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}