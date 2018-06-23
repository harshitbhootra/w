package com.visiontutor.app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.visiontutor.app.utils.City;
import com.visiontutor.app.utils.Class;
import com.visiontutor.app.utils.Subject;
import com.visiontutor.app.utils.URLS;

import java.util.List;

public class SearchTutor extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner citySpinner;
    private Spinner classSpinner;
    private Spinner subjectSpinner;
    private Button reviewProceed;
    private City _city;
    private Class _class;
    private Subject _subject;
    ArrayAdapter<City> cityArrayAdapter;
    ArrayAdapter<Class> classArrayAdapter;
    ArrayAdapter<Subject> subjectArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtutor);

        initViews();
        AndroidNetworking.initialize(getApplicationContext());

        citySpinner.setOnItemSelectedListener(this);
        classSpinner.setOnItemSelectedListener(this);
        subjectSpinner.setOnItemSelectedListener(this);

        getCities();

        reviewProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _city = (City) citySpinner.getSelectedItem();
                _subject = (Subject) subjectSpinner.getSelectedItem();

                Log.d("SearchTutor", "reviewProceed.onClick: "+_city+" "+_class+" "+_subject);

                Intent intent = new Intent(getBaseContext(),SearchTutorResult.class);
                intent.putExtra("city",_city.getCityId());
                intent.putExtra("class",_class.getId());
                intent.putExtra("subject",_subject.getId());

                startActivity(intent);
            }
        });
    }

    private void getCities() {

        citySpinner.setEnabled(false);
        classSpinner.setEnabled(false);
        subjectSpinner.setEnabled(false);
        reviewProceed.setEnabled(false);

        AndroidNetworking.get(URLS.CITIES)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(City.class, new ParsedRequestListener<List<City>>() {
                    @Override
                    public void onResponse(List<City> response) {
                        Log.d("SearchTutor.getCities", "onResponse: entered");
                         cityArrayAdapter = new ArrayAdapter<>(SearchTutor.this,
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

    private void getClasses() {
        subjectSpinner.setAdapter(new ArrayAdapter<>(SearchTutor.this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.Subject)));
        AndroidNetworking.get(URLS.CLASSES)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(Class.class,new ParsedRequestListener<List<Class>>() {
                    @Override
                    public void onResponse(List<Class> response) {
                        Log.d("SearchTutor.getClasses", "onResponse: entered");
                        classArrayAdapter = new ArrayAdapter<>(SearchTutor.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                response);
                        classSpinner.setAdapter(classArrayAdapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("SearchTutor.getClasses", "onError: " + anError.getErrorDetail());
                    }
                });

        if(citySpinner.getSelectedItemPosition() > 0){
            classSpinner.setEnabled(true);
        }

    }

    private void getSubjects() {
        AndroidNetworking.get(URLS.SUBJECTS + _class.getId())
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(Subject.class, new ParsedRequestListener<List<Subject>>() {
                    @Override
                    public void onResponse(List<Subject> response) {
                        Log.d("SearchTutor.getSubjects", "onResponse: entered");
                        subjectArrayAdapter = new ArrayAdapter<Subject>(SearchTutor.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                response);
                        subjectSpinner.setAdapter(subjectArrayAdapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("SearchTutor", "onError: " + anError.getErrorDetail());
                    }
                });
        subjectSpinner.setEnabled(true);
    }

    private void initViews() {
        citySpinner = findViewById(R.id.cities);
        classSpinner = findViewById(R.id.classes);
        subjectSpinner = findViewById(R.id.subjects);
        reviewProceed = findViewById(R.id.reviewproceed);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int spinnerId = parent.getId();
        switch (spinnerId) {
            case R.id.cities:
                if (position > 0) {
                    getClasses();
                }
                break;
            case R.id.classes:
                if(position > 0){
                    _class = (Class) classSpinner.getSelectedItem();
                    getSubjects();
                }
                break;
            case R.id.subjects:
                if(position > 0){
                    reviewProceed.setEnabled(true);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
