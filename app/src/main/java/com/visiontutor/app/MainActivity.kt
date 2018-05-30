package com.visiontutor.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.transaction
import com.visiontutor.app.utils.Utils

class MainActivity : AppCompatActivity() {

    private val fragmentManager: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//         If savedinstnacestate is null then replace login fragment
        if (savedInstanceState == null) {
            fragmentManager.transaction(now = false, allowStateLoss = false) {
                replace(R.id.frameContainer, LoginFragment(), Utils.Login_Fragment)
            }
        }

        // On close icon click finish activity
        findViewById<View>(R.id.close_activity)
                .setOnClickListener {
                    finish()
                }
    }

    // Replace Login Fragment with animation
    fun replaceLoginFragment() {
        fragmentManager.transaction(now = false, allowStateLoss = false) {
            setCustomAnimations(R.anim.left_enter, R.anim.right_enter)
            replace(R.id.frameContainer, LoginFragment(), Utils.Login_Fragment)
        }
    }

    override fun onBackPressed() {

        // Find the tag of signup and forgot password fragment
        val SignUp_Fragment = fragmentManager
                .findFragmentByTag(Utils.SignUp_Fragment)
        val ForgotPassword_Fragment = fragmentManager
                .findFragmentByTag(Utils.ForgotPassword_Fragment)

        // Check if both are null or not
        // If both are not null then replace login fragment else do backpressed
        // task

        when {
            SignUp_Fragment != null -> replaceLoginFragment()
            ForgotPassword_Fragment != null -> replaceLoginFragment()
            else -> super.onBackPressed()
        }
    }
}
