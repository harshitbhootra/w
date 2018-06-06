package com.visiontutor.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.visiontutor.app.utils.Utils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.fragment.app.Fragment;

public class ForgotPasswordFragment extends Fragment implements
		OnClickListener {
	private View view;

	private EditText emailId;
	private TextView submit, back;

	public ForgotPasswordFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.forgotpassword_layout, container,
				false);
		initViews();
		setListeners();
		return view;
	}

	// Initialize the views
	private void initViews() {
		emailId = view.findViewById(R.id.registered_emailid);
		submit = view.findViewById(R.id.forgot_button);
		back = view.findViewById(R.id.backToLoginBtn);



	}

	// Set Listeners over buttons
	private void setListeners() {
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backToLoginBtn:

			// Replace Login Fragment on Back Presses
			new MainActivity().replaceLoginFragment();
			break;

		case R.id.forgot_button:

			// Call Submit button task
			submitButtonTask();
			break;

		}

	}

	private void submitButtonTask() {
		String getEmailId = emailId.getText().toString();

		// Pattern for email id validation
		Pattern p = Pattern.compile(Utils.REGEX);

		// Match the pattern
		Matcher m = p.matcher(getEmailId);

		// First check if email id is not null else show error toast
		if (getEmailId.equals("") || getEmailId.length() == 0)

            CustomToast.INSTANCE.showToast(Objects.requireNonNull(getActivity()),
                    "Please enter your Email Id.");

		// Check if email id is valid or not
		else if (!m.find())
            CustomToast.INSTANCE.showToast(Objects.requireNonNull(getActivity()),
                    "Your Email Id is Invalid.");

		// Else submit email id and fetch passwod or do your stuff
		else
			Toast.makeText(getActivity(), "Get Forgot Password.",
					Toast.LENGTH_SHORT).show();
	}
}