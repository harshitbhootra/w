package com.visiontutor.app

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.visiontutor.app.utils.SearchTutorResultAdapter
import kotlinx.android.synthetic.main.activity_search_tutor_result.*

class SearchTutorResult : AppCompatActivity() {

    val TAG = "SearchTutorResult"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_tutor_result)
        val recyclerView = search_tutor_result_view

        val cityId = intent.getIntExtra("city", 0)
        val classId = intent.getIntExtra("class", 0)
        val subjectId = intent.getIntExtra("subject", 0)

        Log.d(TAG,"city:$cityId class:$classId subj:$subjectId")

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val data = ArrayList<String>()
        data.add("1")
        data.add("2")
        data.add("3")
        data.add("4")
        data.add("5")
        data.add("6")
        data.add("7")
        data.add("8")
        data.add("9")
        data.add("10")
        data.add("11")
        data.add("12")
        data.add("13")
        data.add("14")
        data.add("15")

        recyclerView.adapter = SearchTutorResultAdapter(data)
    }
}
