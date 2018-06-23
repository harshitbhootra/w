package com.visiontutor.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.visiontutor.app.utils.Utils
import kotlinx.android.synthetic.main.forgot_password_layout.*
import java.util.regex.Pattern

class ForgotPasswordFragment : Fragment(), OnClickListener {

    private lateinit var baseContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        baseContext = requireContext()
        return inflater.inflate(R.layout.forgot_password_layout, container,
                false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forgot_pswd_submit.setOnClickListener(this)
        forgot_pswd_back.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            forgot_pswd_back -> MainActivity().replaceLoginFragment()

            forgot_pswd_submit -> forgotPswdSubmitButtonTask()
        }

    }

    private fun forgotPswdSubmitButtonTask() {
        val email = registered_emailid.text.toString()
        val p = Pattern.compile(Utils.REGEX)
        val m = p.matcher(email)
        if (email.isBlank())
            CustomToast.showToast(baseContext,
                    "Please enter your Email Id.")
        else if (!m.find())
            CustomToast.showToast(baseContext,
                    "Email is invalid.")
        else
            Toast.makeText(activity, "Get Forgot Password.",
                    Toast.LENGTH_SHORT).show()
    }
}