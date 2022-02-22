package com.eduardorosillo.mobileapplication_c196.UI;

import androidx.appcompat.app.AppCompatActivity;

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
import com.eduardorosillo.mobileapplication_c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {

    Repository repository = new Repository(getApplication());
    private int assessmentID;
    String title;
    String start;
    String end;
    String type;
    private int courseID;

    EditText assessmentTitleField;
    Button assessmentStartField;
    Button assessmentEndField;
    EditText assessmentTypeField;
    List<Course> allCourses;
    Course courseForAssessment;
    List<Assessment> allAssessments;
    Assessment currentAssessment;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;

    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        allCourses = repository.getAllCourses();
        allAssessments = repository.getAllAssessments();

        // Get current Assessment items to populate EditTexts
        assessmentID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start_date");
        end = getIntent().getStringExtra("end_date");
        type = getIntent().getStringExtra("type");
        courseID = getIntent().getIntExtra("course_ID", -1);

        assessmentTitleField = findViewById(R.id.assessmentTitleField);
        assessmentTitleField.setText(title);
        assessmentStartField = findViewById(R.id.assessmentStartField);
        //assessmentStartField.setText(start);
        if (start == null){
            assessmentStartField.setText("Start Date");
        } else {
            assessmentStartField.setText(start);
        }
        assessmentEndField = findViewById(R.id.assessmentEndField);
        //assessmentEndField.setText(end);
        if (end == null){
            assessmentEndField.setText("End Date");
        } else {
            assessmentEndField.setText(end);
        }
        assessmentTypeField = findViewById(R.id.assessmentTypeField);
        assessmentTypeField.setText(type);

        // Set currentAssessment
        for (Assessment assessment : allAssessments) {
            if (assessment.getAssessmentID() == assessmentID) {
                currentAssessment = assessment;
            }
        }


        myFormat = "MM/dd/yy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        assessmentStartField.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Date date;
                String info= assessmentStartField.getText().toString();
                if(info.equals(""))info="02/10/22";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, startDate, myCalendarStart
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

        assessmentEndField.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Date date;
                String info= assessmentEndField.getText().toString();
                if(info.equals(""))info="02/10/22";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetail.this, endDate, myCalendarEnd
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

    private  void updateLabelStart(){ assessmentStartField.setText(sdf.format(myCalendarStart.getTime())); }

    private  void updateLabelEnd(){ assessmentEndField.setText(sdf.format(myCalendarEnd.getTime())); }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.deleteAssessment:
                repository.deleteAssessment(currentAssessment);
                Toast.makeText(AssessmentDetail.this, "Assessment deleted.", Toast.LENGTH_LONG).show();
                for (Course course : allCourses) {
                    if (course.getCourseID() == courseID) {
                        courseForAssessment = course;
                    }
                }
                Intent intent = new Intent(AssessmentDetail.this, CourseDetail.class);
                intent.putExtra("id", courseForAssessment.getCourseID());
                intent.putExtra("title", courseForAssessment.getCourseTitle());
                intent.putExtra("start_date", courseForAssessment.getStartDate());
                intent.putExtra("end_date", courseForAssessment.getEndDate());
                intent.putExtra("status", courseForAssessment.getCourseStatus());
                intent.putExtra("instructor_name", courseForAssessment.getCourseInstructorName());
                intent.putExtra("instructor_phone", courseForAssessment.getCourseInstructorPhone());
                intent.putExtra("instructor_email", courseForAssessment.getCourseInstructorEmail());
                intent.putExtra("note", courseForAssessment.getCourseNotes());
                intent.putExtra("term_ID", courseForAssessment.getTermID());
                startActivity(intent);
            case R.id.notifyAssessmentStart:
                String screenAssessmentTitle = assessmentTitleField.getText().toString();
                String screenAssessmentStart = assessmentStartField.getText().toString();
                myFormat = "MM/dd/yy"; //In which you need put here
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                Date date = null;
                try {
                    date = sdf.parse(screenAssessmentStart);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = date.getTime();
                Intent notifyAssessmentStart = new Intent(AssessmentDetail.this, MyReceiver.class);
                notifyAssessmentStart.putExtra("key", "Your " + screenAssessmentTitle + " assessment STARTS TODAY! " + screenAssessmentStart);
                PendingIntent pendingAssessmentStart = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert, notifyAssessmentStart, 0);
                AlarmManager startAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingAssessmentStart);
                return true;
            case R.id.notifyAssessmentEnd:
                String screenAssessmentTitle2 = assessmentTitleField.getText().toString();
                String screenAssessmentEnd = assessmentEndField.getText().toString();
                myFormat = "MM/dd/yy"; //In which you need put here
                sdf = new SimpleDateFormat(myFormat, Locale.US);
                date = null;
                try {
                    date = sdf.parse(screenAssessmentEnd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                trigger = date.getTime();
                Intent notifyAssessmentEnd = new Intent(AssessmentDetail.this, MyReceiver.class);
                notifyAssessmentEnd.putExtra("key", "Your " + screenAssessmentTitle2 + " assessment end TODAY! " + screenAssessmentEnd);
                PendingIntent pendingAssessmentEnd = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert, notifyAssessmentEnd, 0);
                AlarmManager endAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarm.set(AlarmManager.RTC_WAKEUP, trigger, pendingAssessmentEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


        public void saveAssessment (View view){
            String assessmentTitle = assessmentTitleField.getText().toString();
            String assessmentStartDate = assessmentStartField.getText().toString();
            String assessmentEndDate = assessmentEndField.getText().toString();
            String assessmentType = assessmentTypeField.getText().toString();
            if (courseID == -1) {
                Toast.makeText(AssessmentDetail.this, "An Assessment must be added to an existing Course.", Toast.LENGTH_LONG).show();
            } else {
                if (assessmentID == -1) {
                    assessmentID = allAssessments.size();
                    Assessment newAssessment = new Assessment(++assessmentID, assessmentTitle, assessmentStartDate, assessmentEndDate,
                            assessmentType, courseID);
                    repository.insertAssessment(newAssessment);
                    Toast.makeText(AssessmentDetail.this, "New Assessment saved.", Toast.LENGTH_LONG).show();
                } else {
                    Assessment existingAssessment = new Assessment(assessmentID, assessmentTitle, assessmentStartDate, assessmentEndDate,
                            assessmentType, courseID);
                    repository.updateAssessment(existingAssessment);
                    Toast.makeText(AssessmentDetail.this, "Assessment updated.", Toast.LENGTH_LONG).show();
                }
            }
            for (Course course : allCourses) {
                if (course.getCourseID() == courseID) {
                    courseForAssessment = course;
                }
            }
            Intent intent = new Intent(AssessmentDetail.this, CourseDetail.class);
            intent.putExtra("id", courseForAssessment.getCourseID());
            intent.putExtra("title", courseForAssessment.getCourseTitle());
            intent.putExtra("start_date", courseForAssessment.getStartDate());
            intent.putExtra("end_date", courseForAssessment.getEndDate());
            intent.putExtra("status", courseForAssessment.getCourseStatus());
            intent.putExtra("instructor_name", courseForAssessment.getCourseInstructorName());
            intent.putExtra("instructor_phone", courseForAssessment.getCourseInstructorPhone());
            intent.putExtra("instructor_email", courseForAssessment.getCourseInstructorEmail());
            intent.putExtra("note", courseForAssessment.getCourseNotes());
            intent.putExtra("term_ID", courseForAssessment.getTermID());
            startActivity(intent);
        }

}