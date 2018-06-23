package com.visiontutor.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.squareup.picasso.Picasso
import com.visiontutor.app.utils.Tutor
import com.visiontutor.app.utils.URLS

import androidx.appcompat.app.AppCompatActivity

class TutorProfile : AppCompatActivity() {

    private lateinit var userImg: ImageView
    private lateinit var fname: TextView
    //    RatingBar ratingBar;
    private lateinit var about: TextView
    private lateinit var gender: TextView
    private lateinit var age: TextView
    private lateinit var city: TextView
    private lateinit var vid: TextView
    private lateinit var instruction: TextView
    private lateinit var time: TextView
    private lateinit var area: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorprofile)

        initViews()
        AndroidNetworking.initialize(applicationContext)
        val sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val tutid = sharedPref.getString("tutid", null)
        if (tutid == null) Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
        getTutorDetails(tutid)
    }

    private fun getTutorDetails(tutid: String?) {
        AndroidNetworking.get(URLS.TUTOR_PROFILE + tutid!!)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(Tutor::class.java, object : ParsedRequestListener<Tutor> {
                    override fun onResponse(response: Tutor) {
                        Log.d("TutorProfile", "getTutorDetails.onResponse: entered")
                        Log.d("TutorProfile", "onResponse: image : " + response.image!!)
                        Picasso.get()
                                .load(response.image)
                                .into(userImg)

                        fname.text = response.name
                        gender.text = response.gender
                        about.text = response.about
                        age.text = response.getYob()
                        city.text = response.city
                        vid.text = response.tutorid
                        instruction.text = response.medium
                        time.text = response.timeteach
                        area.text = response.area
                    }

                    override fun onError(anError: ANError) {
                        Log.d("TutorProfile", "getTutorDetails.onError: " + anError.errorDetail)
                        Toast.makeText(this@TutorProfile, anError.errorDetail, Toast.LENGTH_LONG).show()
                    }
                })
    }

    private fun initViews() {
        userImg = findViewById(R.id.userimg2)
        fname = findViewById(R.id.fname)
        //        ratingBar=findViewById(R.id.rating);
        about = findViewById(R.id.about)
        gender = findViewById(R.id.gender)
        age = findViewById(R.id.age)
        city = findViewById(R.id.city)
        vid = findViewById(R.id.vid)
        instruction = findViewById(R.id.instruction)
        time = findViewById(R.id.time)
        area = findViewById(R.id.area)
    }
}
