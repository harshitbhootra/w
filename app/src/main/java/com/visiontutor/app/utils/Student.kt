package com.visiontutor.app.utils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Student : Serializable {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("lastname")
    @Expose
    var lastname: String? = null
    @SerializedName("mobile")
    @Expose
    var mobile: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("gender")
    @Expose
    var gender: String? = null
        get() = if (field!!.isEmpty()) "N/A" else field
    @SerializedName("readclass")
    @Expose
    var readclass: Int? = null
    @SerializedName("address")
    @Expose
    var address: String? = null
        get() = if (field!!.isEmpty()) "N/A" else field
    @SerializedName("pin")
    @Expose
    var pin: String? = null
        get() = if (field!!.isEmpty()) "N/A" else field
    @SerializedName("pname")
    @Expose
    var pname: String? = null
        get() = if (field!!.isEmpty()) "N/A" else field
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("pcontact")
    @Expose
    var pcontact: String? = null
        get() = if (field!!.isEmpty()) "N/A" else field
    @SerializedName("pemail")
    @Expose
    var pemail: String? = null
        get() = if (field!!.isEmpty()) "N/A" else field
    @SerializedName("school")
    @Expose
    var school: String? = null
        get() = if (field!!.isEmpty()) "N/A" else field
    @SerializedName("schoolboard")
    @Expose
    var schoolboard: String? = null
        get() = if (field!!.isEmpty()) "N/A" else field
    @SerializedName("dob")
    @Expose
    var dob: String? = null
        get() = if (field!!.isEmpty()) "N/A" else field

    val fullName: String
        get() = "$name $lastname"
}