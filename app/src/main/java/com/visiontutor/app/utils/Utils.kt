@file:JvmName(name = "Utils")

package com.visiontutor.app.utils

object Utils{
    //Email Validation pattern
    const val REGEX = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b"

    //Fragments Tags
    const val LOGIN_FRAGMENT = "LoginFragment"
    const val SIGN_UP_FRAGMENT = "SignUpFragment"
    const val FORGOT_PASSWORD_FRAGMENT = "ForgotPasswordFragment"
}