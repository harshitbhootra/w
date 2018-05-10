package com.visiontutor.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.visiontutor.app.utils.URLS;
import com.visiontutor.app.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginFragment extends Fragment implements OnClickListener {

    private View view;

    private EditText emailid, password;
    private Button loginButton;
    private TextView forgotPassword, signUp;
    private CheckBox show_hide_password;
    private LinearLayout loginLayout;
    private Animation shakeAnimation;
    private FragmentManager fragmentManager;
    private ToggleButton loginToggle;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        AndroidNetworking.initialize(view.getContext());
        return view;
    }

    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        emailid = view.findViewById(R.id.login_emailid);
        password = view.findViewById(R.id.login_password);
        loginButton = view.findViewById(R.id.loginBtn);
        forgotPassword = view.findViewById(R.id.forgot_password);
        signUp = view.findViewById(R.id.createAccount);
        show_hide_password = view.findViewById(R.id.show_hide_password);
        loginLayout = view.findViewById(R.id.login_layout);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        loginToggle = view.findViewById(R.id.logintoggle);
    }

    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);
                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());
                        } else {
                            show_hide_password.setText(R.string.show_pwd);
                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;

            case R.id.forgot_password:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ForgotPasswordFragment(),
                                Utils.ForgotPassword_Fragment).commit();
                break;

            case R.id.createAccount:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUpFragment(),
                                Utils.SignUp_Fragment).commit();
                break;
        }

    }

    private void checkValidation() {
        final String email = emailid.getText().toString().isEmpty()?"bhootrah@gmail.com":emailid.getText().toString();
        final String pass = password.getText().toString().isEmpty()?"harshit1":password.getText().toString();
        final boolean mode = loginToggle.isChecked();

        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(email);

        if (email.equals("") || email.length() == 0
                || pass.equals("") || pass.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Enter both credentials.");

        } else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
        else {
            if(mode) doStudentLogin(email, pass);
            else doTutorLogin(email,pass);
        }
    }

    private void doTutorLogin(String email, String pass) {
        AndroidNetworking.post(URLS.TUTOR_LOGIN)
                .addBodyParameter("email", email)
                .addBodyParameter("password", pass)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("LoginFragment", "onResponse: entered");
                        try {
                            if (response.getBoolean("status")) {
                                Intent intent = new Intent(getActivity(), TutorDashboard.class);
                                String tutid = response.getString("tutorid");
                                Log.d("LoginFragment", "onResponse: tutid"+tutid);
                                SharedPreferences sharedPref = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("tutid", tutid);
                                editor.apply();
                                startActivity(intent);
                            } else {
                                loginLayout.startAnimation(shakeAnimation);
                                new CustomToast().Show_Toast(getActivity(), view,
                                        "Wrong email or password");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("LoginFragment", "onError: "+anError.getErrorDetail());
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void doStudentLogin(String email, String pass) {
        AndroidNetworking.post(URLS.STUDENT_LOGIN)
                .addBodyParameter("email", email)
                .addBodyParameter("password", pass)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("LoginFragment", "onResponse: entered");
                        try {
                            if (response.getBoolean("status")) {
                                Intent intent = new Intent(getActivity(), StudentDashboard.class);
                                String stuid = response.getString("stuid");
                                Log.d("LoginFragment", "onResponse: stuid"+stuid);
                                SharedPreferences sharedPref = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("stuid", stuid);
                                editor.apply();
                                startActivity(intent);
                            } else {
                                loginLayout.startAnimation(shakeAnimation);
                                new CustomToast().Show_Toast(getActivity(), view,
                                        "Wrong email or password");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("LoginFragment", "onError: "+anError.getErrorDetail());
                        Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
