package com.visiontutor.app

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class TutorDashboard : AppCompatActivity(), View.OnClickListener {

    private lateinit var tutProfile: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutordashboard)

        tutProfile = findViewById(R.id.tutorprofile)

        tutProfile.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.tutorprofile -> intent = Intent(this, TutorProfile::class.java)
        }

        startActivity(intent)
    }
}
