package com.visiontutor.app

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class StudentDashboard : AppCompatActivity(), View.OnClickListener {


    private var addreview: CardView? = null
    private var searchtutor: CardView? = null
    private var stuProfile: CardView? = null
    private var tutProfile: CardView? = null
    private var req: CardView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentdashboard)

        addreview = findViewById(R.id.reviewtutor)
        searchtutor = findViewById(R.id.searchtutor)
        stuProfile = findViewById(R.id.att)
        tutProfile = findViewById(R.id.att2)
        req = findViewById(R.id.postreq)

        addreview!!.setOnClickListener(this)
        searchtutor!!.setOnClickListener(this)
        stuProfile!!.setOnClickListener(this)
        tutProfile!!.setOnClickListener(this)
        req!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.reviewtutor -> intent = Intent(this, AddReview::class.java)
            R.id.searchtutor -> intent = Intent(this, SearchTutor::class.java)
            R.id.att -> intent = Intent(this, StudentProfile::class.java)
            R.id.att2 -> intent = Intent(this, TutorProfile::class.java)
            R.id.postreq -> intent = Intent(this, Requirment::class.java)
        }

        startActivity(intent)
    }
}
