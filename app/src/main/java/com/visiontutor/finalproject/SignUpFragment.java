package com.visiontutor.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.visiontutor.finalproject.utils.City;
import com.visiontutor.finalproject.utils.URLS;
import com.visiontutor.finalproject.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment implements OnClickListener {
    private View view;
    private EditText fullName, fullName2, emailId, mobileNumber,
            password, confirmPassword;
    private TextView login;
    private Button signUpButton;
    private CheckBox terms_conditions;
    private ToggleButton signuptoggle;
    private City _city;
    private Spinner citySpinner;
    private ArrayAdapter<City> cityArrayAdapter;

    public SignUpFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        initViews();
        setListeners();
        AndroidNetworking.initialize(view.getContext());
        getCities();
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
        return view;
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
                        cityArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
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

    // Initialize all views
    private void initViews() {
        fullName = view.findViewById(R.id.fullName);
        fullName2 = view.findViewById(R.id.fullName2);
        emailId = view.findViewById(R.id.userEmailId);
        mobileNumber = view.findViewById(R.id.mobileNumber);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        signUpButton = view.findViewById(R.id.signUpBtn);
        login = view.findViewById(R.id.already_user);
        terms_conditions = view.findViewById(R.id.terms_conditions);
        signuptoggle = view.findViewById(R.id.signuptoggle);
        citySpinner = view.findViewById(R.id.cities);
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();
                break;

//            case R.id.already_user:
//
//                // Replace login fragment
//                new MainActivity().replaceLoginFragment();
//                break;
        }

    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getFullName = fullName.getText().toString();
        String getFullName2 = fullName2.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobileNumber.getText().toString();
        int getCity = _city.getCityId();

        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();
        boolean mode = signuptoggle.isChecked();
        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.isEmpty()
                || getFullName2.isEmpty()
                || getEmailId.isEmpty()
                || getMobileNumber.isEmpty()
                || getCity <= 0
                || getPassword.isEmpty()
                || getConfirmPassword.isEmpty())

            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");

            // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");

            // Else do signup or do your stuff
        else {
            if (mode)
                doStudentRegister(getCity,getFullName, getFullName2, getEmailId, getMobileNumber, getPassword);
            else
                doTutorRegister(getCity,getFullName, getFullName2, getEmailId, getMobileNumber, getPassword);
        }

    }

    private void doStudentRegister(int getCity, String getFullName, String getFullName2, String getEmailId, String getMobileNumber, String getPassword) {
        AndroidNetworking.post(URLS.STUDENT_REGISTER)
                .addBodyParameter("fname", getFullName)
                .addBodyParameter("lname", getFullName2)
                .addBodyParameter("email", getEmailId)
                .addBodyParameter("mobile", getMobileNumber)
                .addBodyParameter("city", String.valueOf(getCity))
                .addBodyParameter("password", getPassword)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SignUpFragment", "onResponse: entered");
                        try {
                            if (response.getString("status").equals("accepted")) {
                                Toast.makeText(getContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), StudentDashboard.class);
                                Log.d("StuSignUpFragment", "onResponse: accepted");
                                startActivity(intent);
                            } else {
                                new CustomToast().Show_Toast(getActivity(), view,
                                        response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("StuSignUpFragment", "onError: " + anError.getErrorDetail());
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void doTutorRegister(int getCity, String getFullName, String getFullName2, String getEmailId, String getMobileNumber, String getPassword) {

    }
}
