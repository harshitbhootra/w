package com.visiontutor.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.squareup.picasso.Picasso;
import com.visiontutor.app.utils.Student;
import com.visiontutor.app.utils.URLS;

import androidx.appcompat.app.AppCompatActivity;

public class StudentProfile extends AppCompatActivity {

    ImageView userImage;
    TextView name;
    Button edit;
    TextView gender;
    TextView dob;
    TextView phoneNum;
    TextView email;
    TextView address;
    TextView city;
    TextView pincode;
    TextView school;
    TextView board;
    TextView class_;
    TextView guardianName;
    TextView guardianPhoneNum;
    TextView parentEmail;
    Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentprofile);

        initviews();
        AndroidNetworking.initialize(getApplicationContext());
        SharedPreferences sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String stuid = sharedPref.getString("stuid", null);
        if (stuid == null) Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        getStudentDetails(stuid);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentProfile.this, EditProfileStudent.class);
                i.putExtra("student", student);
            }
        });
    }

    private void getStudentDetails(String stuid) {
        AndroidNetworking.get(URLS.STUDENT_PROFILE + stuid)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(Student.class, new ParsedRequestListener<Student>() {
                    @Override
                    public void onResponse(Student response) {
                        Log.d("StudentProfile", "getStudentDetails.onResponse: entered");
                        Log.d("StudentProfile", "onResponse: image : " + response.getImage());
                        Picasso.get()
                                .load(response.getImage())
                                .into(userImage);

                        name.setText(response.getFullName());
                        gender.setText(response.getGender());
                        dob.setText(response.getDob());
                        phoneNum.setText(response.getMobile());
                        email.setText(response.getEmail());
                        address.setText(response.getAddress());
                        city.setText(response.getCity());
                        pincode.setText(response.getPin());
                        school.setText(response.getSchool());
                        board.setText(response.getSchoolboard());
                        class_.setText(response.getReadclass()==0?"N/A":response.getReadclass().toString());
                        guardianName.setText(response.getPname());
                        guardianPhoneNum.setText(response.getPcontact());
                        parentEmail.setText(response.getPemail());
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("StudentProfile", "getStudentDetails.onError: " + anError.getErrorDetail());
                        Toast.makeText(StudentProfile.this, anError.getErrorDetail(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initviews() {
        userImage = findViewById(R.id.userimg);
        name = findViewById(R.id.firstname);
        edit = findViewById(R.id.edit);
        gender = findViewById(R.id.gender);
        dob = findViewById(R.id.dob);
        phoneNum = findViewById(R.id.pno);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        pincode = findViewById(R.id.pincode);
        school = findViewById(R.id.school);
        board = findViewById(R.id.board);
        class_ = findViewById(R.id.clazz);
        guardianName = findViewById(R.id.pname);
        guardianPhoneNum = findViewById(R.id.parentpno);
        parentEmail = findViewById(R.id.pemail);
    }
}
