package com.visiontutor.app

import android.app.Dialog
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity

class AddReview : AppCompatActivity() {

    lateinit var b1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addreview)

        b1 = findViewById(R.id.review_proceed)

        b1.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.reviewpopup)
            dialog.show()
        }

    }
}
