package com.visiontutor.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.transaction
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.visiontutor.app.utils.URLS
import com.visiontutor.app.utils.Utils
import kotlinx.android.synthetic.main.login_layout.*
import org.json.JSONException
import org.json.JSONObject
import java.util.regex.Pattern

class LoginFragment : Fragment(), OnClickListener {

    private val TAG = "LoginFragment"
    private lateinit var fragManager: FragmentManager
    private lateinit var shakeAnimation: Animation
    private lateinit var baseContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragManager = requireFragmentManager()
        shakeAnimation = AnimationUtils.loadAnimation(activity, R.anim.shake)
        baseContext = requireContext()

        return inflater.inflate(R.layout.login_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBtn.setOnClickListener(this)
        forgot_password.setOnClickListener(this)
        register.setOnClickListener(this)
        show_password
                .setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {

                        show_password.setText(R.string.hide_pwd)
                        login_password.inputType = InputType.TYPE_CLASS_TEXT
                        login_password.transformationMethod = HideReturnsTransformationMethod
                                .getInstance()
                    } else {
                        show_password.setText(R.string.show_pwd)
                        login_password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        login_password.transformationMethod = PasswordTransformationMethod
                                .getInstance()// hide password

                    }
                }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.loginBtn -> checkValidation()

            R.id.forgot_password -> requireFragmentManager().transaction(now = false, allowStateLoss = false) {
                setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                replace(R.id.frameContainer,
                        ForgotPasswordFragment(),
                        Utils.FORGOT_PASSWORD_FRAGMENT)
            }

            R.id.register -> requireFragmentManager().transaction(now = false, allowStateLoss = false) {
                setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                replace(R.id.frameContainer, SignUpFragment(),
                        Utils.SIGN_UP_FRAGMENT)
            }
        }

    }

    private fun checkValidation() {
        //TODO: Remove defaults in production
        val email = if (login_emailid.text.toString().isEmpty()) "bhootrah@gmail.com" else login_emailid.text.toString()
        val pass = if (login_password.text.toString().isEmpty()) "harshit1" else login_password.text.toString()
        val mode = login_toggle.isChecked

        val p = Pattern.compile(Utils.REGEX)
        val m = p.matcher(email)

        if (email.isBlank() || pass.isBlank()) {
            login_layout.startAnimation(shakeAnimation)
            CustomToast.showToast(baseContext, getString(R.string.login_both_empty))

        } else if (!m.find())
            CustomToast.showToast(baseContext, getString(R.string.login_email_invalid))
        else {
            if (mode)
                doStudentLogin(email, pass)
            else
                doTutorLogin(email, pass)
        }
    }

    private fun doTutorLogin(email: String, pass: String) {
        AndroidNetworking.post(URLS.TUTOR_LOGIN)
                .addBodyParameter("email", email)
                .addBodyParameter("password", pass)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.d(TAG, "onResponse: entered")
                        try {
                            if (response.getBoolean("status")) {
                                val intent = Intent(activity, TutorDashboard::class.java)
                                val tutid = response.getString("tutorid")
                                Log.d(TAG, "onResponse: tutid$tutid")
                                val sharedPref = activity!!.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                                sharedPref.edit {
                                    putString("tutid", tutid)
                                }
                                startActivity(intent)
                            } else {
                                login_layout.startAnimation(shakeAnimation)
                                CustomToast.showToast(baseContext, getString(R.string.login_any_invalid))
                            }
                        } catch (e: JSONException) {
                            //TODO: Remove stack traces in production
                            e.printStackTrace()
                        }

                    }

                    override fun onError(anError: ANError) {
                        Log.d(TAG, "onError: " + anError.errorDetail)
                        CustomToast.showToast(baseContext, getString(R.string.error_occured))
                    }
                })
    }

    private fun doStudentLogin(email: String, pass: String) {
        AndroidNetworking.post(URLS.STUDENT_LOGIN)
                .addBodyParameter("email", email)
                .addBodyParameter("password", pass)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.d(TAG, "onResponse: entered")
                        try {
                            if (response.getBoolean("status")) {
                                val intent = Intent(activity, StudentDashboard::class.java)
                                val stuid = response.getString("stuid")
                                Log.d(TAG, "onResponse: stuid$stuid")
                                val sharedPref = activity!!.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                                val editor = sharedPref.edit()
                                editor.putString("stuid", stuid)
                                editor.apply()
                                startActivity(intent)
                            } else {
                                login_layout.startAnimation(shakeAnimation)
                                CustomToast.showToast(baseContext, "Wrong email or password")
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }

                    override fun onError(anError: ANError) {
                        Log.d(TAG, "onError: " + anError.errorDetail)
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    }
                })
    }
}
