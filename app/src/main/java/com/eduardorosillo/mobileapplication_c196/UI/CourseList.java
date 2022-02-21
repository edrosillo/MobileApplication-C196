package com.eduardorosillo.mobileapplication_c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.eduardorosillo.mobileapplication_c196.Database.Repository;
import com.eduardorosillo.mobileapplication_c196.Entity.Course;
import com.eduardorosillo.mobileapplication_c196.R;

import java.util.List;

public class CourseList extends AppCompatActivity {
    private Repository repository;

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        loadRecycler();
    }

    public void onResume() {
        loadRecycler();
        super.onResume();

    }

    private void loadRecycler() {
        repository = new Repository(getApplication());
        List<Course> allClasses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.allCourses);
        CourseAdapter courseAdapter = new CourseAdapter(this, allClasses);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        repository = new Repository(getApplication());
        List<Course> allClasses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.allCourseList);
        CourseAdapter classAdapter = new CourseAdapter(this, allClasses);
        recyclerView.setAdapter(classAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        return true;
    }



    public void addCourse(View view) {
        startActivity(new Intent(CourseList.this, CourseDetail.class));

    }*/

}