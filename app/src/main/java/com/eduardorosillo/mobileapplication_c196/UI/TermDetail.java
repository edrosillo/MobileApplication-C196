package com.eduardorosillo.mobileapplication_c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eduardorosillo.mobileapplication_c196.DAO.TermDAO;
import com.eduardorosillo.mobileapplication_c196.Database.Repository;
import com.eduardorosillo.mobileapplication_c196.Database.TermAssistantDBBuilder;
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

public class TermDetail extends AppCompatActivity {
    Repository repository = new Repository(getApplication());

    int numCourses;
    Term currentTerm;
    private int termID;
    String title;
    String start;
    String end;

    List<Term> allTerms;
    List<Course> allCourses;
    List<Course> filteredCourses;

    EditText termTitleField;
    Button startDateField;
    Button endDateField;
    Button saveTermButton;
    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allTerms = repository.getAllTerms();
        allCourses = repository.getAllCourses();

        termID = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        termTitleField=findViewById(R.id.termTitleField);
        termTitleField.setText(title);
        startDateField =findViewById(R.id.startDateField);
        startDateField.setText(start);
        endDateField =findViewById(R.id.endDateField);
        endDateField.setText(end);

        saveTermButton =findViewById(R.id.saveTermButton);

        myFormat = "MM/dd/yy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        // Set currentTerm
        for (Term term : allTerms) {
            if (term.getTermID() == termID) {
                currentTerm = term;
            }
        }
        // Find Courses that belong to the current term
        filteredCourses = new ArrayList<>();
        for (Course course : allCourses) {
            if (course.getTermID() == termID) {
                filteredCourses.add(course);
            }
        }

        // Set numberOfCourses
        numCourses = filteredCourses.size();

        // Populate the Course RecyclerView
        RecyclerView recyclerView = findViewById(R.id.coursesRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(filteredCourses);

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
                new DatePickerDialog(TermDetail.this, startDate, myCalendarStart
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
                new DatePickerDialog(TermDetail.this, endDate, myCalendarEnd
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

        /*saveTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String termTitle = termTitleField.getText().toString();
                String termStart = startDateField.getText().toString();
                String termEnd = endDateField.getText().toString();
                if (termTitle.isEmpty() || termStart.isEmpty() || termEnd.isEmpty()) {
                    Toast.makeText(TermDetail.this, "Please enter the valid course details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our course.
                saveTerm(termTitle, termStart, termEnd);
            }
        });*/


    }
    private  void updateLabelStart(){ startDateField.setText(sdf.format(myCalendarStart.getTime())); }

    private  void updateLabelEnd(){
        endDateField.setText(sdf.format(myCalendarEnd.getTime()));
    }


    public void saveTerm(View view) {
        String termTitle = termTitleField.getText().toString();
        String termStart = startDateField.getText().toString();
        String termEnd = endDateField.getText().toString();
        if (termID == -1) {
            termID = allTerms.size();
            Term newTerm = new Term(++termID, termTitle, termStart, termEnd);
            repository.insertTerm(newTerm);
            Toast.makeText(TermDetail.this, "New Term saved.", Toast.LENGTH_LONG).show();
        } else {
            Term updateTerm = new Term(termID, termTitle, termStart, termEnd);
            repository.updateTerm(updateTerm);
            Toast.makeText(TermDetail.this, "Term updated.", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(TermDetail.this, TermList.class);
        startActivity(intent);

 /*     Term term = new Term(termTitle, termStart, termEnd);
        repository.insertTerm(term);

        // displaying a toast message after adding the data
        Toast.makeText(this, "Term has been saved to Room Database. ", Toast.LENGTH_SHORT).show();

        //this.finish();
        Intent intent = new Intent(TermDetail.this, TermList.class);
        startActivity(intent);*/
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu; adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    // Determines what action to take when an option is selected on the navigation bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.homeFromCourses:
                Intent goHome = new Intent(TermDetail.this, TermList.class);
                startActivity(goHome);
                return true;
            case R.id.refreshCourses:
                repository = new Repository(getApplication());
                filteredCourses = new ArrayList<>();
                for (Course course : allCourses) {
                    if (course.getTermID() == termID) {
                        filteredCourses.add(course);
                    }
                }
                final CourseAdapter courseAdapter = new CourseAdapter(this);
                RecyclerView recyclerView = findViewById(R.id.coursesRecyclerView);
                recyclerView.setAdapter(courseAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                courseAdapter.setCourses(filteredCourses);
                Toast.makeText(TermDetail.this, "Courses Table refreshed.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.deleteTerm:
                if (numCourses == 0) {
                    repository.deleteTerm(currentTerm);
                    Toast.makeText(TermDetail.this, "Term deleted.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TermDetail.this, TermList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(TermDetail.this, "A Term with Courses can not be deleted.", Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToCourseDetails(View view) {
        if (termID != -1) {
            Intent intent = new Intent(TermDetail.this, CourseDetail.class);
            intent.putExtra("term_ID", currentTerm.getTermID());
            startActivity(intent);
        } else {
            Toast.makeText(TermDetail.this, "Courses can only be added to an existing Term.", Toast.LENGTH_LONG).show();
        }
    }
}