package com.visiontutor.finalproject;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.visiontutor.finalproject.utils.City;
import com.visiontutor.finalproject.utils.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchTutor extends AppCompatActivity {

    private Spinner citySpinner;
    private Spinner classSpinner;
    private Spinner SubjectSpinner;
    private Button reviewProceed;
    private ArrayList<City> cityList;
    private ArrayList<Pair<Integer, String>> classList;
    private ArrayList<Pair<Integer, String>> subjectList;
    private int classid;
    private int subjectid;
    private City _city;
    private String _class;
    private String _subject;
    ArrayAdapter<City> cityArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtutor);

        initViews();
        AndroidNetworking.initialize(getApplicationContext());

        getCities();

        reviewProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _city = (City) citySpinner.getSelectedItem();
                Log.d("SearchTutor", "onClick: "+ _city.getCityId()+"=>"+_city.getCityName());//cityArrayAdapter.getItem(citySpinner.getSelectedItemPosition()).getCityId().toString());
            }
        });
    }

    private void getCities() {

        AndroidNetworking.get(URLS.CITIES)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(City.class, new ParsedRequestListener<List<City>>() {
                    @Override
                    public void onResponse(List<City> response) {
                        Log.d("SearchTutor.getCities", "onResponse: entered"+response.toString());
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
        Log.d("SearchTutor.....", "getCities: " + citySpinner.getSelectedItemPosition());

    }

    private String getClasses() {
        AndroidNetworking.get(URLS.CLASSES)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SearchTutor.getClasses", "onResponse: entered");
                        try {
                            JSONArray classes = response.getJSONArray("classes");
                            for (int i = 0; i < classes.length(); i++) {
                                JSONObject obj = classes.getJSONObject(i);
                                Pair<Integer, String> pair = new Pair<>(obj.getInt("id"), obj.getString("name"));
                                classList.add(pair);
                            }
                            citySpinner.setAdapter(new ArrayAdapter<>(SearchTutor.this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    cityList));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("SearchTutor.getClasses", "onError: " + anError.getErrorDetail());
                    }
                });

        return citySpinner.getSelectedItem().toString();
    }

    private void initViews() {
        citySpinner = findViewById(R.id.cities);
        classSpinner = findViewById(R.id.classes);
        SubjectSpinner = findViewById(R.id.subjects);
        reviewProceed = findViewById(R.id.reviewproceed);
        cityList = new ArrayList<>();
        classList = new ArrayList<>();
        subjectList = new ArrayList<>();
    }


}
