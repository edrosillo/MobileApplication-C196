package com.eduardorosillo.mobileapplication_c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eduardorosillo.mobileapplication_c196.Database.Repository;
import com.eduardorosillo.mobileapplication_c196.Entity.Course;
import com.eduardorosillo.mobileapplication_c196.Entity.Term;
import com.eduardorosillo.mobileapplication_c196.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startApp(View view) {
        Intent intent = new Intent(MainActivity.this, TermList.class);
        startActivity(intent);
        Repository repo=new Repository(getApplication());
    }
}