package com.visiontutor.app.utils

import com.google.gson.annotations.SerializedName

data class City(

	@field:SerializedName("city_name")
	val cityName: String,

	@field:SerializedName("city_id")
	val cityId: Int
){
    override fun toString() = this.cityName
}