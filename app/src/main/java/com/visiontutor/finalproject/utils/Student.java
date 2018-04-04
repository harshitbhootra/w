package com.visiontutor.finalproject.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Student implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("readclass")
    @Expose
    private Integer readclass;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("pcontact")
    @Expose
    private String pcontact;
    @SerializedName("pemail")
    @Expose
    private String pemail;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("schoolboard")
    @Expose
    private String schoolboard;
    @SerializedName("dob")
    @Expose
    private String dob;

    public  String getFullName(){return name+" "+lastname;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender.isEmpty()?"N/A":gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getReadclass() {
        return readclass;
    }

    public void setReadclass(Integer readclass) {
        this.readclass = readclass;
    }

    public String getAddress() {
        return address.isEmpty()?"N/A":address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPin() {
        return pin.isEmpty()?"N/A":pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPname() {
        return pname.isEmpty()?"N/A":pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPcontact() {
        return pcontact.isEmpty()?"N/A":pcontact;
    }

    public void setPcontact(String pcontact) {
        this.pcontact = pcontact;
    }

    public String getPemail() {
        return pemail.isEmpty()?"N/A":pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }

    public String getSchool() {
        return school.isEmpty()?"N/A":school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchoolboard() {
        return schoolboard.isEmpty()?"N/A":schoolboard;
    }

    public void setSchoolboard(String schoolboard) {
        this.schoolboard = schoolboard;
    }

    public String getDob() {
        return dob.isEmpty()?"N/A":dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}