package com.eduardorosillo.mobileapplication_c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eduardorosillo.mobileapplication_c196.Database.Repository;
import com.eduardorosillo.mobileapplication_c196.Entity.Assessment;
import com.eduardorosillo.mobileapplication_c196.Entity.Course;
import com.eduardorosillo.mobileapplication_c196.Entity.Term;
import com.eduardorosillo.mobileapplication_c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetail extends AppCompatActivity {

    Repository repository = new Repository(getApplication());
    private int termID;
    List<Term> allTerms;
    Term termForCourse;
    List<Course> allCourses;
    List<Assessment> allAssessments;
    List<Assessment> filteredAssessments;
    Course currentCourse;
    int numberOfAssessments;
    private int courseID;
    String title;
    String status;
    String start;
    String end;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String note;

    EditText courseTitleField;
    Button startDateField;
    Button endDateField;
    EditText courseStatusField;
    EditText courseInstructorNameField;
    EditText courseInstructorPhoneField;
    EditText courseInstructorEmailField;
    EditText courseNoteField;
    Button saveCourseButton;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;

    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allTerms = repository.getAllTerms();
        allCourses = repository.getAllCourses();
        allAssessments = repository.getAllAssessments();

        courseID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start_date");
        end = getIntent().getStringExtra("end_date");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructor_name");
        instructorPhone = getIntent().getStringExtra("instructor_phone");
        instructorEmail = getIntent().getStringExtra("instructor_email");
        note = getIntent().getStringExtra("note");
        termID = getIntent().getIntExtra("term_ID", -1);

        courseTitleField= findViewById(R.id.courseTitleField);
        courseTitleField.setText(title);
        startDateField = findViewById(R.id.startDateField);
        //startDateField.setText(start);
        if (start == null){
            startDateField.setText("Start Date");
        } else {
            startDateField.setText(start);
        }
        endDateField = findViewById(R.id.endDateField);
        //endDateField.setText(end);
        if (end == null){
            endDateField.setText("End Date");
        } else {
            endDateField.setText(end);
        }
        courseStatusField = findViewById(R.id.courseStatusField);
        courseStatusField.setText(status);
        courseInstructorNameField = findViewById(R.id.courseInstructorNameField);
        courseInstructorNameField.setText(instructorName);
        courseInstructorPhoneField = findViewById(R.id.courseInstructorPhoneField);
        courseInstructorPhoneField.setText(instructorPhone);
        courseInstructorEmailField = findViewById(R.id.courseInstructorEmailField);
        courseInstructorEmailField.setText(instructorEmail);
        courseNoteField = findViewById(R.id.courseNoteField);
        courseNoteField.setText(note);

        saveCourseButton = findViewById(R.id.saveCourseButton);

        // Set currentCourse
        for (Course course : allCourses) {
            if (course.getCourseID() == courseID) {
                currentCourse = course;
            }
        }
        // Find Assessments that belong to the current course
        filteredAssessments = new ArrayList<>();
        for (Assessment assessment : allAssessments) {
            if (assessment.getCourseID() == courseID) {
                filteredAssessments.add(assessment);
            }
        }

        // Set numberOfAssessments
        numberOfAssessments = filteredAssessments.size();

        // Populate the Assessment RecyclerView
        RecyclerView recyclerView = findViewById(R.id.assessmentsRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(filteredAssessments);



        myFormat = "MM/dd/yy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDateField.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Date date;
                String info= startDateField.getText().toString();
                if(info.equals(""))info="02/10/22";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDate=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR,year);
                myCalendarStart.set(Calendar.MONTH,monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        endDateField.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Date date;
                String info= endDateField.getText().toString();
                if(info.equals(""))info="02/10/22";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetail.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR,year);
                myCalendarEnd.set(Calendar.MONTH,monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };


    }



    private  void updateLabelStart(){ startDateField.setText(sdf.format(myCalendarStart.getTime())); }

    private  void updateLabelEnd(){
        endDateField.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.homeFromAssessments:
                Intent goHome = new Intent(CourseDetail.this, TermList.class);
                startActivity(goHome);
                return true;
            case R.id.refreshAssessments:
                repository = new Repository(getApplication());
                filteredAssessments = new ArrayList<>();
                for (Assessment assessment : allAssessments) {
                    if (assessment.getCourseID() == courseID) {
                        filteredAssessments.add(assessment);
                    }
                }
                final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
                RecyclerView recyclerView = findViewById(R.id.assessmentsRecyclerView);
                recyclerView.setAdapter(assessmentAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                assessmentAdapter.setAssessments(filteredAssessments);
                Toast.makeText(CourseDetail.this, "Assessments have been refreshed.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.deleteCourse:
                if (numberOfAssessments == 0) {
                    repository.deleteCourse(currentCourse);
                    Toast.makeText(CourseDetail.this, "Course Deleted.", Toast.LENGTH_LONG).show();
                    for (Term term : allTerms) {
                        termForCourse = term;
                    }
                    Intent intent = new Intent(CourseDetail.this, TermDetail.class);
                    intent.putExtra("id", termForCourse.getTermID());
                    intent.putExtra("title", termForCourse.getTermTitle());
                    intent.putExtra("start", termForCourse.getStartDate());
                    intent.putExtra("end", termForCourse.getEndDate());
                    startActivity(intent);
                } else {
                    Toast.makeText(CourseDetail.this, "A Course with Assessments can not be deleted.", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.shareCourseNotes:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, courseNoteField.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, courseTitleField.getText().toString() + " Notes" );
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifyCourseStart:
                String screenCourseTitle = courseTitleField.getText().toString();
                String screenStartDate = startDateField.getText().toString();
                myFormat = "MM/dd/yy"; //In which you need put here
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date date = null;
                try {
                    date = sdf.parse(screenStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = date.getTime();
                Intent notifyStart = new Intent(CourseDetail.this, MyReceiver.class);
                notifyStart.putExtra("key", "Your " + screenCourseTitle + " course STARTS TODAY! " + screenStartDate);
                PendingIntent pendingStart = PendingIntent.getBroadcast(CourseDetail.this, ++MainActivity.numAlert, notifyStart, 0);
                AlarmManager startAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                startAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingStart);
                return true;
            case R.id.notifyCourseEnd:
                String screenCourseTitle2 = courseTitleField.getText().toString();
                String screenEndDate = endDateField.getText().toString();
                myFormat = "MM/dd/yy";
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                date = null;
                try {
                    date = sdf.parse(screenEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                trigger = date.getTime();
                Intent notifyEnd = new Intent(CourseDetail.this, MyReceiver.class);
                notifyEnd.putExtra("key", "Your " + screenCourseTitle2 + " course ENDS TODAY! " + screenEndDate);
                PendingIntent pendingEnd = PendingIntent.getBroadcast(CourseDetail.this, ++MainActivity.numAlert, notifyEnd, 0);
                AlarmManager endAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                endAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void goToAssessmentDetails(View view) {
        if (courseID != -1) {
            Intent intent = new Intent(CourseDetail.this, AssessmentDetail.class);
            intent.putExtra("course_ID", currentCourse.getCourseID());
            startActivity(intent);
        } else {
            Toast.makeText(CourseDetail.this, "Assessments can only be added to an existing Course.", Toast.LENGTH_LONG).show();
        }
    }

    public void saveCourse(View view) {
        String courseTitle = courseTitleField.getText().toString();
        String startDate = startDateField.getText().toString();
        String endDate = endDateField.getText().toString();
        String courseStatus = courseStatusField.getText().toString();
        String courseInstructorName = courseInstructorNameField.getText().toString();
        String courseInstructorPhone = courseInstructorPhoneField.getText().toString();
        String courseInstructorEmail = courseInstructorEmailField.getText().toString();
        String courseNote = courseNoteField.getText().toString();
        if (termID == -1) {
            Toast.makeText(CourseDetail.this, "A Course must be added to an existing Term.", Toast.LENGTH_LONG).show();
        } else {
            if (courseID == -1) {
                courseID = allCourses.size();
                Course newCourse = new Course(++courseID, courseTitle, startDate, endDate,
                        courseStatus, courseInstructorName, courseInstructorPhone, courseInstructorEmail, courseNote,
                        termID);
                repository.insertCourse(newCourse);
                Toast.makeText(CourseDetail.this, "New Course saved.", Toast.LENGTH_LONG).show();
            } else {
                Course existingCourse = new Course(courseID, courseTitle, startDate, endDate,
                        courseStatus, courseInstructorName, courseInstructorPhone, courseInstructorEmail, courseNote,
                        termID);
                repository.updateCourse(existingCourse);
                Toast.makeText(CourseDetail.this, "Course updated.", Toast.LENGTH_LONG).show();
            }
        }
        for (Term term : allTerms) {
            if (term.getTermID() == termID) {
                termForCourse = term;
            }
        }
        Intent intent = new Intent(CourseDetail.this, TermDetail.class);
        intent.putExtra("id", termForCourse.getTermID());
        intent.putExtra("title", termForCourse.getTermTitle());
        intent.putExtra("start", termForCourse.getStartDate());
        intent.putExtra("end", termForCourse.getEndDate());
        startActivity(intent);
    }
}