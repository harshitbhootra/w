package com.visiontutor.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.squareup.picasso.Picasso;
import com.visiontutor.app.utils.Tutor;
import com.visiontutor.app.utils.URLS;

import androidx.appcompat.app.AppCompatActivity;

public class TutorProfile extends AppCompatActivity {

    ImageView userImg;
    TextView fname;
//    RatingBar ratingBar;
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
        AndroidNetworking.initialize(getApplicationContext());
        SharedPreferences sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String tutid = sharedPref.getString("tutid", null);
        if (tutid == null) Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        getTutorDetails(tutid);
    }

    private void getTutorDetails(String tutid) {
        AndroidNetworking.get(URLS.TUTOR_PROFILE + tutid)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(Tutor.class, new ParsedRequestListener<Tutor>() {
                    @Override
                    public void onResponse(Tutor response) {
                        Log.d("TutorProfile", "getTutorDetails.onResponse: entered");
                        Log.d("TutorProfile", "onResponse: image : " + response.getImage());
                        Picasso.get()
                                .load(response.getImage())
                                .into(userImg);

                        fname.setText(response.getName());
                        gender.setText(response.getGender());
                        about.setText(response.getAbout());
                        age.setText(response.getYob());
                        city.setText(response.getCity());
                        vid.setText(response.getTutorid());
                        instruction.setText(response.getMedium());
                        time.setText(response.getTimeteach());
                        area.setText(response.getArea());
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("TutorProfile", "getTutorDetails.onError: " + anError.getErrorDetail());
                        Toast.makeText(TutorProfile.this, anError.getErrorDetail(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initViews() {
        userImg=findViewById(R.id.userimg2);
        fname=findViewById(R.id.fname);
//        ratingBar=findViewById(R.id.rating);
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
