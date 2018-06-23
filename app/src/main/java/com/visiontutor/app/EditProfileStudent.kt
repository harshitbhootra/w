package com.visiontutor.app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.visiontutor.app.utils.City
import com.visiontutor.app.utils.Student
import com.visiontutor.app.utils.URLS
import java.util.Objects

import androidx.appcompat.app.AppCompatActivity

class EditProfileStudent : AppCompatActivity() {

    lateinit var fname: TextView
    lateinit var lname: TextView
    lateinit var email: TextView
    lateinit var phone: TextView
    lateinit var dob: TextView
    lateinit var address: TextView
    lateinit var pincode: TextView
    lateinit var school: TextView
    lateinit var board: TextView
    lateinit var classes: TextView
    lateinit var pname: TextView
    lateinit var pemail: TextView
    lateinit var pno: TextView
    lateinit var male: RadioButton
    lateinit var female: RadioButton
    lateinit var citySpinner: Spinner
    lateinit var cityArrayAdapter: ArrayAdapter<City>
    lateinit var _city: City
    lateinit var submit: Button
    lateinit var student: Student

    private fun initviews() {
        citySpinner = findViewById(R.id.city)
        male = findViewById(R.id.male)
        female = findViewById(R.id.female)
        fname = findViewById(R.id.sfname)
        lname = findViewById(R.id.slname)
        email = findViewById(R.id.semail)
        phone = findViewById(R.id.sphone)
        dob = findViewById(R.id.dob)
        address = findViewById(R.id.address)
        pincode = findViewById(R.id.pincode)
        school = findViewById(R.id.school)
        board = findViewById(R.id.board)
        classes = findViewById(R.id.classes)
        pname = findViewById(R.id.pname)
        pno = findViewById(R.id.pno)
        pemail = findViewById(R.id.pemail)
        submit = findViewById(R.id.savebutton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofilestudent)
        initviews()
        val sharedPref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val stuid = sharedPref.getString("stuid", null)
        if (stuid == null) Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
        student = Objects.requireNonNull(intent.extras).get("student") as Student
        AndroidNetworking.initialize(baseContext)
        getCities()
        setStudentValues()
        setUpdatedValues()

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position > 0) {
                    _city = citySpinner.selectedItem as City
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }

        }


    }

    private fun setUpdatedValues() {

        if (fname.text.toString().isEmpty()) student.name = fname.text.toString()
        if (lname.text.toString().isEmpty()) student.lastname = lname.text.toString()
        if (email.text.toString().isEmpty()) student.email = email.text.toString()
        if (phone.text.toString().isEmpty()) student.mobile = phone.text.toString()
        if (dob.text.toString().isEmpty()) student.dob = dob.text.toString()
        if (address.text.toString().isEmpty()) student.address = address.text.toString()
        if (pincode.text.toString().isEmpty()) student.pin = pincode.text.toString()
        if (school.text.toString().isEmpty()) student.readclass = Integer.valueOf(school.text.toString())
        if (board.text.toString().isEmpty()) student.name = fname.text.toString()
        if (classes.text.toString().isEmpty()) student.name = fname.text.toString()
        if (pname.text.toString().isEmpty()) student.name = fname.text.toString()
        if (pno.text.toString().isEmpty()) student.name = fname.text.toString()
        if (pemail.text.toString().isEmpty()) student.name = fname.text.toString()
        if (male.isSelected) student.gender = "Male"
        if (female.isSelected) student.gender = "Female"
        student.city = _city.cityId.toString()
    }

    private fun setStudentValues() {
        male.isSelected = false
        female.isSelected = false
        if (student.name != null || student.name != "N/A") fname.text = student.name
        if (student.lastname != null || student.lastname != "N/A") lname.text = student.lastname
        if (student.email != null || student.email != "N/A") email.text = student.email
        if (student.mobile != null || student.mobile != "N/A") phone.text = student.pcontact
        if (student.dob != null || student.dob != "N/A") dob.text = student.dob
        if (student.gender != null || student.gender != "N/A" || student.gender == "Male") male.isSelected = true
        if (student.gender != null || student.gender != "N/A" || student.gender == "Female") female.isSelected = true
        if (student.address != null || student.address != "N/A") address.text = student.address
        if (student.pin != null || student.pin != "N/A") pincode.text = student.pin
        if (student.school != null || student.school != "N/A") school.text = student.school
        if (student.schoolboard != null || student.schoolboard != "N/A") board.text = student.schoolboard
        if (student.readclass != 0 || student.readclass.toString() != "N/A") classes.text = student.readclass.toString()
        if (student.pname != null || student.pname != "N/A") pname.text = student.pname
        if (student.pcontact != null || student.pcontact != "N/A") pno.text = student.pcontact
        if (student.pemail != null || student.pemail != "N/A") pemail.text = student.pemail
        // TODO: 31-05-2018 Check whats wrong later
        //        int pos = cityArrayAdapter.getPosition(new City(student.getCity()));
        //        citySpinner.setSelection(pos);
    }

    private fun getCities() {

        citySpinner.isEnabled = false

        AndroidNetworking.get(URLS.CITIES)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(City::class.java, object : ParsedRequestListener<List<City>> {
                    override fun onResponse(response: List<City>) {
                        Log.d("SearchTutor.getCities", "onResponse: entered")
                        cityArrayAdapter = ArrayAdapter(this@EditProfileStudent,
                                android.R.layout.simple_spinner_dropdown_item,
                                response)
                        citySpinner.adapter = cityArrayAdapter
                    }

                    override fun onError(anError: ANError) {
                        Log.d("SearchTutor.getCities", "onError: " + anError.errorDetail)
                    }
                })

        citySpinner.isEnabled = true

    }
}
