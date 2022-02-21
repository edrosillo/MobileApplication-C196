package com.eduardorosillo.mobileapplication_c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.eduardorosillo.mobileapplication_c196.Database.Repository;
import com.eduardorosillo.mobileapplication_c196.Entity.Term;
import com.eduardorosillo.mobileapplication_c196.R;

import java.util.List;

public class TermList extends AppCompatActivity {
    private Repository repository;
    List<Term> allTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create repository instance
        // Populate the RecyclerView
        repository = new Repository(getApplication());
        allTerms = repository.getAllTerms();

        RecyclerView recyclerView = findViewById(R.id.termsRecyclerView);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

/*
        Repository repo=new Repository(getApplication());
        List<Term> terms=repo.getAllTerms();
        final TermAdapter adapter=new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);*/
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu; adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.refreshTerms:
                repository = new Repository(getApplication());
                List<Term> allTerms = repository.getAllTerms();
                final TermAdapter termAdapter = new TermAdapter(this);
                RecyclerView recyclerView = findViewById(R.id.termsRecyclerView);
                recyclerView.setAdapter(termAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                termAdapter.setTerms(allTerms);
                Toast.makeText(TermList.this, "Terms Table refreshed.", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToTermDetail(View view) {
        Intent intent = new Intent(TermList.this, TermDetail.class);
        startActivity(intent);
    }

}