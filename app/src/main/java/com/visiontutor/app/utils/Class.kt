package com.visiontutor.app.utils

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Class(

        @SerializedName("id")
        @Expose
        var id: Int,

        @SerializedName("name")
        @Expose
        var name: String) {

    override fun toString() = this.name

}