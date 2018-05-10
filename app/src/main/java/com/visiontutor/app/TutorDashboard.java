package com.visiontutor.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class TutorDashboard extends AppCompatActivity implements View.OnClickListener{

    CardView tutProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutordashboard);

        tutProfile = findViewById(R.id.tutorprofile);

        tutProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tutorprofile:
                intent = new Intent(this, TutorProfile.class);break;
        }

        startActivity(intent);
    }
}
