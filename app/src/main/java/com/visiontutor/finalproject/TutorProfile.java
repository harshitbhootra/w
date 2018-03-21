package com.visiontutor.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class TutorProfile extends AppCompatActivity {

    ImageView userImg;
    TextView fname;
    RatingBar ratingBar;
    TextView about;
    TextView gender;
    TextView age;
    TextView city;
    TextView vid;
    TextView instruction;
    TextView time;
    TextView area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorprofile);

        initViews();
    }

    private void initViews() {
        userImg=findViewById(R.id.userimg2);
        fname=findViewById(R.id.fname);
        ratingBar=findViewById(R.id.rating);
        about=findViewById(R.id.about);
        gender=findViewById(R.id.gender);
        age=findViewById(R.id.age);
        city=findViewById(R.id.city);
        vid=findViewById(R.id.vid);
        instruction=findViewById(R.id.instruction);
        time=findViewById(R.id.time);
        area=findViewById(R.id.area);
    }
}
