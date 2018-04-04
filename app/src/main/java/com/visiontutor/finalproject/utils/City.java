package com.visiontutor.finalproject.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("city_name")
    @Expose
    private String cityName;

    public City(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return  this.cityName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            if(this.cityName.equals(obj.toString())) return true;
        } else if (obj instanceof City) {
            if (this.cityName.equals(((City) obj).getCityName())) return true;
        }
        return false;
    }
}