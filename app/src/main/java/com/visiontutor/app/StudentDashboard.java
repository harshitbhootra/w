package com.visiontutor.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class StudentDashboard extends AppCompatActivity implements View.OnClickListener{


    private CardView addreview, searchtutor, stuProfile, tutProfile, req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdashboard);

        addreview = findViewById(R.id.reviewtutor);
        searchtutor = findViewById(R.id.searchtutor);
        stuProfile = findViewById(R.id.att);
        tutProfile = findViewById(R.id.att2);
        req = findViewById(R.id.postreq);

        addreview.setOnClickListener(this);
        searchtutor.setOnClickListener(this);
        stuProfile.setOnClickListener(this);
        tutProfile.setOnClickListener(this);
        req.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.reviewtutor:
                intent = new Intent(this, AddReview.class);break;
            case R.id.searchtutor:
                intent = new Intent(this, SearchTutor.class);break;
            case R.id.att:
                intent = new Intent(this, StudentProfile.class);break;
            case R.id.att2:
                intent = new Intent(this, TutorProfile.class);break;
            case R.id.postreq:
                intent = new Intent(this, Requirment.class);break;
        }
        
        startActivity(intent);
    }
}
