package com.visiontutor.app

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.visiontutor.app.utils.City
import com.visiontutor.app.utils.Class
import com.visiontutor.app.utils.Subject
import com.visiontutor.app.utils.URLS
import kotlinx.android.synthetic.main.activity_searchtutor.*

class SearchTutor : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {
    private val TAG = "SearchTutor"

    private lateinit var _city: City

    private lateinit var _class: Class
    private lateinit var _subject: Subject
    private lateinit var cityArrayAdapter: ArrayAdapter<City>
    private lateinit var classArrayAdapter: ArrayAdapter<Class>
    private lateinit var subjectArrayAdapter: ArrayAdapter<Subject>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchtutor)

        cities.onItemSelectedListener = this
        classes.onItemSelectedListener = this
        subjects.onItemSelectedListener = this

        getCities()

        review_proceed.setOnClickListener(this)
    }

    private fun getCities() {

        cities.isEnabled = false
        classes.isEnabled = false
        subjects.isEnabled = false
        review_proceed.isEnabled = false

        AndroidNetworking.get(URLS.CITIES)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(City::class.java, object : ParsedRequestListener<List<City>> {
                    override fun onResponse(response: List<City>) {
                        Log.d("SearchTutor", "getCities.onResponse: entered")
                        cityArrayAdapter = ArrayAdapter(this@SearchTutor,
                                android.R.layout.simple_spinner_dropdown_item,
                                response)
                        cities.adapter = cityArrayAdapter
                    }

                    override fun onError(anError: ANError) {
                        Log.d("SearchTutor", "getCities.onError: " + anError.errorDetail)
                    }
                })

        cities.isEnabled = true

    }

    private fun getClasses() {

        classes.isEnabled = false
        subjects.isEnabled = false
        review_proceed.isEnabled = false

        AndroidNetworking.get(URLS.CLASSES)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(Class::class.java, object : ParsedRequestListener<List<Class>> {
                    override fun onResponse(response: List<Class>) {
                        Log.d("SearchTutor", "getClasses.onResponse: entered")
                        classArrayAdapter = ArrayAdapter(this@SearchTutor,
                                android.R.layout.simple_spinner_dropdown_item,
                                response)
                        classes.adapter = classArrayAdapter
                    }

                    override fun onError(anError: ANError) {
                        Log.d("SearchTutor", "getClasses.onError: " + anError.errorDetail)
                    }
                })

        if (cities.selectedItemPosition > 0) {
            classes.isEnabled = true
        }

    }

    private fun getSubjects() {

        subjects.isEnabled = false
        review_proceed.isEnabled = false

        AndroidNetworking.get(URLS.SUBJECTS + _class.id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(Subject::class.java, object : ParsedRequestListener<List<Subject>> {
                    override fun onResponse(response: List<Subject>) {
                        Log.d("SearchTutor", "getSubjects.onResponse: entered")
                        subjectArrayAdapter = ArrayAdapter(this@SearchTutor,
                                android.R.layout.simple_spinner_dropdown_item,
                                response)
                        subjects.adapter = subjectArrayAdapter
                    }

                    override fun onError(anError: ANError) {
                        Log.d(TAG, "getSubjects.onError: " + anError.errorDetail)
                    }
                })

        if (classes.selectedItemPosition > 0) {
            subjects.isEnabled = true
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val spinnerId = parent.id
        when (spinnerId) {
            R.id.cities -> if (position > 0) {
                getClasses()
            }
            R.id.classes -> if (position > 0) {
                _class = classes.selectedItem as Class
                getSubjects()
            }
            R.id.subjects -> if (position > 0) {
                review_proceed.setBackgroundColor(Color.rgb(0,149,0))
                review_proceed.isEnabled = true
            }
        }
    }


    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    override fun onClick(v: View?) {
        if(v==review_proceed){
            _city = cities.selectedItem as City
            _subject = subjects.selectedItem as Subject

            Log.d(TAG, "review_proceed.onClick: $_city $_class $_subject")

            val intent = Intent(baseContext, SearchTutorResult::class.java)
            intent.putExtra("city", _city.cityId)
            intent.putExtra("class", _class.id)
            intent.putExtra("subject", _subject.id)

            startActivity(intent)
        }
    }
}
