package com.visiontutor.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity

class Requirment : AppCompatActivity() {

    lateinit var b1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requirment)
        b1 = findViewById(R.id.reqbutton)

        b1.setOnClickListener {
            val i = Intent(applicationContext, RequirmentForm::class.java)
            startActivity(i)
        }

    }
}
