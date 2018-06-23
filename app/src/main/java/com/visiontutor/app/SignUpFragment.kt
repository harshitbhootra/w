package com.visiontutor.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.visiontutor.app.utils.City
import com.visiontutor.app.utils.URLS
import com.visiontutor.app.utils.Utils
import kotlinx.android.synthetic.main.signup_layout.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.regex.Pattern

class SignUpFragment : Fragment(), OnClickListener {

    private lateinit var baseContext:Context
    private lateinit var _city: City
    private lateinit var citySpinner: Spinner
    private lateinit var cityArrayAdapter: ArrayAdapter<City>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        baseContext = requireContext()
        getCities()
        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position > 0) {
                    _city = citySpinner.selectedItem as City
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }

        }
        return inflater.inflate(R.layout.signup_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signup_btn.setOnClickListener(this)
    }

    private fun getCities() {

        citySpinner.isEnabled = false

        AndroidNetworking.get(URLS.CITIES)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(City::class.java, object : ParsedRequestListener<List<City>> {
                    override fun onResponse(response: List<City>) {
                        Log.d("SearchTutor.getCities", "onResponse: entered")
                        cityArrayAdapter = ArrayAdapter(Objects.requireNonNull<Context>(context),
                                android.R.layout.simple_spinner_dropdown_item,
                                response)
                        citySpinner.adapter = cityArrayAdapter
                    }

                    override fun onError(anError: ANError) {
                        Log.d("SearchTutor.getCities", "onError: " + anError.errorDetail)
                    }
                })

        citySpinner.isEnabled = true

    }

    override fun onClick(v: View) {
        when (v) {
            signup_btn -> checkValidation()
            
            signup_existing_user -> 
                requireFragmentManager().transaction(now = false, allowStateLoss = false) {
                    setCustomAnimations(R.anim.left_enter, R.anim.right_enter)
                    replace(R.id.frameContainer, LoginFragment(), Utils.LOGIN_FRAGMENT)
                }
        }
    }
    

    // Check Validation Method
    private fun checkValidation() {

        // Get all edittext texts
        val fname = signup_fname.text.toString()
        val lname = signup_lname.text.toString()
        val email = signup_email.text.toString()
        val mobile = signup_mobile.text.toString()
        val city = _city.cityId
        val pswd = signup_pswd.text.toString()
        val confirmPswd = signup_confirm_pswd.text.toString()
        val mode = signup_toggle.isChecked
        
        val p = Pattern.compile(Utils.REGEX)
        val m = p.matcher(email)

        if (fname.isBlank()
                || lname.isBlank()
                || email.isBlank()
                || mobile.isBlank()
                || city <= 0
                || pswd.isBlank()
                || confirmPswd.isBlank())

            CustomToast.showToast(baseContext,
                    "All fields are required.")
        else if (!m.find())
            CustomToast.showToast(baseContext,
                    "Your Email Id is Invalid.")
        else if (confirmPswd != pswd)
            CustomToast.showToast(baseContext,
                    "Both passwords doesn't match.")
        else if (!signup_terms.isChecked)
            CustomToast.showToast(baseContext,
                    "Please accept the Terms and Conditions.")
        else {
            if (mode)
                doStudentRegister(city, fname, lname, email, mobile, pswd)
            else
                doTutorRegister(city, fname, lname, email, mobile, pswd)
        }

    }

    private fun doStudentRegister(getCity: Int, getFullName: String, getFullName2: String, getEmailId: String, getMobileNumber: String, getPassword: String) {
        AndroidNetworking.post(URLS.STUDENT_REGISTER)
                .addBodyParameter("fname", getFullName)
                .addBodyParameter("lname", getFullName2)
                .addBodyParameter("email", getEmailId)
                .addBodyParameter("mobile", getMobileNumber)
                .addBodyParameter("city", getCity.toString())
                .addBodyParameter("signup_pswd", getPassword)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.d("StuSignUpFragment", "onResponse: entered")
                        try {
                            if (response.getString("status") == "accepted") {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show()
                                val intent = Intent(activity, StudentDashboard::class.java)
                                Log.d("StuSignUpFragment", "onResponse: accepted")
                                startActivity(intent)
                            } else {
                                CustomToast.showToast(baseContext,
                                        response.getString("message"))
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }

                    override fun onError(anError: ANError) {
                        Log.d("StuSignUpFragment", "onError: " + anError.errorDetail)
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun doTutorRegister(getCity: Int, getFullName: String, getFullName2: String, getEmailId: String, getMobileNumber: String, getPassword: String) {
        AndroidNetworking.post(URLS.TUTOR_REGISTER)
                .addBodyParameter("fname", getFullName)
                .addBodyParameter("lname", getFullName2)
                .addBodyParameter("email", getEmailId)
                .addBodyParameter("mobile", getMobileNumber)
                .addBodyParameter("city", getCity.toString())
                .addBodyParameter("signup_pswd", getPassword)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.d("TutSignUpFragment", "onResponse: entered")
                        try {
                            if (response.getString("status") == "accepted") {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show()
                                val intent = Intent(activity, SignupSegment::class.java)
                                Log.d("TutSignUpFragment", "onResponse: accepted")
                                startActivity(intent)
                            } else {
                                CustomToast.showToast(baseContext,
                                        response.getString("message"))
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }

                    override fun onError(anError: ANError) {
                        Log.d("TutSignUpFragment", "onError: " + anError.errorDetail)
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    }
                })
    }
}
