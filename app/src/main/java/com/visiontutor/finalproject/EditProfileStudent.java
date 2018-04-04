package com.visiontutor.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.visiontutor.finalproject.utils.City;
import com.visiontutor.finalproject.utils.Student;
import com.visiontutor.finalproject.utils.URLS;

import java.util.List;
import java.util.Objects;

public class EditProfileStudent extends AppCompatActivity {

    TextView fname,lname,email,phone,dob,address,pincode,school, board, classes,pname,pemail,pno;
    RadioButton male, female;
    Spinner citySpinner;
    ArrayAdapter<City> cityArrayAdapter;
    City _city;
    Button submit;
    Student student;

    private void initviews() {
        citySpinner = findViewById(R.id.city);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        fname = findViewById(R.id.sfname);
        lname = findViewById(R.id.slname);
        email = findViewById(R.id.semail);
        phone = findViewById(R.id.sphone);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        pincode = findViewById(R.id.pincode);
        school = findViewById(R.id.school);
        board = findViewById(R.id.board);
        classes = findViewById(R.id.classes);
        pname = findViewById(R.id.pname);
        pno = findViewById(R.id.pno);
        pemail = findViewById(R.id.pemail);
        submit = findViewById(R.id.savebutton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofilestudent);
        initviews();
        SharedPreferences sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String stuid = sharedPref.getString("stuid", null);
        if (stuid == null) Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        student = (Student) Objects.requireNonNull(getIntent().getExtras()).get("student");
        AndroidNetworking.initialize(getBaseContext());
        getCities();
        setStudentValues();
        setUpdatedValues();
        
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    _city = (City) citySpinner.getSelectedItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



    }

    private void setUpdatedValues() {

        if(fname.getText().toString().isEmpty()) student.setName(fname.getText().toString());
        if(lname.getText().toString().isEmpty()) student.setLastname(lname.getText().toString());
        if(email.getText().toString().isEmpty()) student.setEmail(email.getText().toString());
        if(phone.getText().toString().isEmpty()) student.setMobile(phone.getText().toString());
        if(dob.getText().toString().isEmpty()) student.setDob(dob.getText().toString());
        if(address.getText().toString().isEmpty()) student.setAddress(address.getText().toString());
        if(pincode.getText().toString().isEmpty()) student.setPin(pincode.getText().toString());
        if(school.getText().toString().isEmpty()) student.setReadclass(Integer.valueOf(school.getText().toString()));
        if(board.getText().toString().isEmpty()) student.setName(fname.getText().toString());
        if(classes.getText().toString().isEmpty()) student.setName(fname.getText().toString());
        if(pname.getText().toString().isEmpty()) student.setName(fname.getText().toString());
        if(pno.getText().toString().isEmpty()) student.setName(fname.getText().toString());
        if(pemail.getText().toString().isEmpty()) student.setName(fname.getText().toString());
        if(male.isSelected()) student.setGender("Male");
        if(female.isSelected()) student.setGender("Female");
        student.setCity(String.valueOf(_city.getCityId()));
    }

    private void setStudentValues() {
        male.setSelected(false);
        female.setSelected(false);
        if (student.getName()!=null || !student.getName().equals("N/A")) fname.setText(student.getName());
        if (student.getLastname()!=null || !student.getLastname().equals("N/A")) lname.setText(student.getLastname());
        if (student.getEmail()!=null || !student.getEmail().equals("N/A")) email.setText(student.getEmail());
        if (student.getMobile()!=null || !student.getMobile().equals("N/A")) phone.setText(student.getPcontact());
        if (student.getDob()!=null || !student.getDob().equals("N/A")) dob.setText(student.getDob());
        if (student.getGender()!=null || !student.getGender().equals("N/A") || student.getGender().equals("Male")) male.setSelected(true);
        if (student.getGender()!=null || !student.getGender().equals("N/A") || student.getGender().equals("Female")) female.setSelected(true);
        if (student.getAddress()!=null || !student.getAddress().equals("N/A")) address.setText(student.getAddress());
        if (student.getPin()!=null || !student.getPin().equals("N/A")) pincode.setText(student.getPin());
        if (student.getSchool()!=null || !student.getSchool().equals("N/A")) school.setText(student.getSchool());
        if (student.getSchoolboard()!=null || !student.getSchoolboard().equals("N/A")) board.setText(student.getSchoolboard());
        if (student.getReadclass()!=0 || !student.getReadclass().equals("N/A")) classes.setText(String.valueOf(student.getReadclass()));
        if (student.getPname()!=null || !student.getPname().equals("N/A")) pname.setText(student.getPname());
        if (student.getPcontact()!=null || !student.getPcontact().equals("N/A")) pno.setText(student.getPcontact());
        if (student.getPemail()!=null || !student.getPemail().equals("N/A")) pemail.setText(student.getPemail());
        int pos = cityArrayAdapter.getPosition(new City(student.getCity()));
        citySpinner.setSelection(pos);
    }

    private void getCities() {

        citySpinner.setEnabled(false);

        AndroidNetworking.get(URLS.CITIES)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(City.class, new ParsedRequestListener<List<City>>() {
                    @Override
                    public void onResponse(List<City> response) {
                        Log.d("SearchTutor.getCities", "onResponse: entered");
                        cityArrayAdapter = new ArrayAdapter<>(EditProfileStudent.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                response);
                        citySpinner.setAdapter(cityArrayAdapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("SearchTutor.getCities", "onError: " + anError.getErrorDetail());
                    }
                });

        citySpinner.setEnabled(true);

    }
}
