package com.visiontutor.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.squareup.picasso.Picasso
import com.visiontutor.app.utils.Student
import com.visiontutor.app.utils.URLS

import androidx.appcompat.app.AppCompatActivity

class StudentProfile : AppCompatActivity() {

    private lateinit var userImage: ImageView
    private lateinit var name: TextView
    private lateinit var edit: Button
    private lateinit var gender: TextView
    private lateinit var dob: TextView
    private lateinit var phoneNum: TextView
    private lateinit var email: TextView
    private lateinit var address: TextView
    private lateinit var city: TextView
    private lateinit var pincode: TextView
    private lateinit var school: TextView
    private lateinit var board: TextView
    private lateinit var class_: TextView
    private lateinit var guardianName: TextView
    private lateinit var guardianPhoneNum: TextView
    private lateinit var parentEmail: TextView
    private lateinit var student: Student


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentprofile)

        initviews()
        AndroidNetworking.initialize(applicationContext)
        val sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val stuid = sharedPref.getString("stuid", null)
        if (stuid == null) Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
        getStudentDetails(stuid)

        edit.setOnClickListener {
            val i = Intent(this@StudentProfile, EditProfileStudent::class.java)
            i.putExtra("student", student)
        }
    }

    private fun getStudentDetails(stuid: String?) {
        AndroidNetworking.get(URLS.STUDENT_PROFILE + stuid!!)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(Student::class.java, object : ParsedRequestListener<Student> {
                    override fun onResponse(response: Student) {
                        Log.d("StudentProfile", "getStudentDetails.onResponse: entered")
                        Log.d("StudentProfile", "onResponse: image : " + response.image!!)
                        Picasso.get()
                                .load(response.image)
                                .into(userImage)

                        name.text = response.fullName
                        gender.text = response.gender
                        dob.text = response.dob
                        phoneNum.text = response.mobile
                        email.text = response.email
                        address.text = response.address
                        city.text = response.city
                        pincode.text = response.pin
                        school.text = response.school
                        board.text = response.schoolboard
                        class_.text = if (response.readclass == 0) "N/A" else response.readclass!!.toString()
                        guardianName.text = response.pname
                        guardianPhoneNum.text = response.pcontact
                        parentEmail.text = response.pemail
                    }

                    override fun onError(anError: ANError) {
                        Log.d("StudentProfile", "getStudentDetails.onError: " + anError.errorDetail)
                        Toast.makeText(this@StudentProfile, anError.errorDetail, Toast.LENGTH_LONG).show()
                    }
                })
    }

    private fun initviews() {
        userImage = findViewById(R.id.userimg)
        name = findViewById(R.id.firstname)
        edit = findViewById(R.id.edit)
        gender = findViewById(R.id.gender)
        dob = findViewById(R.id.dob)
        phoneNum = findViewById(R.id.pno)
        email = findViewById(R.id.email)
        address = findViewById(R.id.address)
        city = findViewById(R.id.city)
        pincode = findViewById(R.id.pincode)
        school = findViewById(R.id.school)
        board = findViewById(R.id.board)
        class_ = findViewById(R.id.clazz)
        guardianName = findViewById(R.id.pname)
        guardianPhoneNum = findViewById(R.id.parentpno)
        parentEmail = findViewById(R.id.pemail)
    }
}
