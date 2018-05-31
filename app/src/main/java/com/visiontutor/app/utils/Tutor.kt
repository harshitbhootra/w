package com.visiontutor.app.utils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Tutor {

    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null
        get() = "$field $lastname"

    @SerializedName("lastname")
    @Expose
    var lastname: String? = null

    @SerializedName("about")
    @Expose
    var about: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("yob")
    @Expose
    private var yob: Int = 0

    @SerializedName("tutorid")
    @Expose
    var tutorid: String? = null

    @SerializedName("medium")
    @Expose
    var medium: String? = null

    @SerializedName("timeteach")
    @Expose
    var timeteach: String? = null

    @SerializedName("area")
    @Expose
    var area: String? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    fun getYob(): String {
        return yob.toString()
    }

    fun setYob(yob: Int) {
        this.yob = yob
    }

}