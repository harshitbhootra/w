package com.visiontutor.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
                replace(R.id.frameContainer, LoginFragment(), Utils.LOGIN_FRAGMENT)
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
            replace(R.id.frameContainer, LoginFragment(), Utils.LOGIN_FRAGMENT)
        }
    }

    override fun onBackPressed() {

        // Find the tag of signup and forgot password fragment
        val signUpFragment = fragmentManager
                .findFragmentByTag(Utils.SIGN_UP_FRAGMENT)
        val forgotPasswordFragment = fragmentManager
                .findFragmentByTag(Utils.FORGOT_PASSWORD_FRAGMENT)

        // Check if both are null or not
        // If both are not null then replace login fragment else do backpressed
        // task

        when {
            signUpFragment != null -> replaceLoginFragment()
            forgotPasswordFragment != null -> replaceLoginFragment()
            else -> super.onBackPressed()
        }
    }
}
