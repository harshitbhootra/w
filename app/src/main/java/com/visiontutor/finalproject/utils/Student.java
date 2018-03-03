package com.visiontutor.finalproject.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("city")
    @Expose
    private Integer city;
    @SerializedName("refercode")
    @Expose
    private String refercode;
    @SerializedName("registerprocess")
    @Expose
    private Integer registerprocess;
    @SerializedName("timestamps")
    @Expose
    private Integer timestamps;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("preftime")
    @Expose
    private Integer preftime;
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
    @SerializedName("paytmno")
    @Expose
    private String paytmno;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("studentid")
    @Expose
    private String studentid;
    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("month")
    @Expose
    private Integer month;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("dates")
    @Expose
    private String dates;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getRefercode() {
        return refercode;
    }

    public void setRefercode(String refercode) {
        this.refercode = refercode;
    }

    public Integer getRegisterprocess() {
        return registerprocess;
    }

    public void setRegisterprocess(Integer registerprocess) {
        this.registerprocess = registerprocess;
    }

    public Integer getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Integer timestamps) {
        this.timestamps = timestamps;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getPreftime() {
        return preftime;
    }

    public void setPreftime(Integer preftime) {
        this.preftime = preftime;
    }

    public Integer getReadclass() {
        return readclass;
    }

    public void setReadclass(Integer readclass) {
        this.readclass = readclass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPname() {
        return pname;
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
        return pcontact;
    }

    public void setPcontact(String pcontact) {
        this.pcontact = pcontact;
    }

    public String getPemail() {
        return pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchoolboard() {
        return schoolboard;
    }

    public void setSchoolboard(String schoolboard) {
        this.schoolboard = schoolboard;
    }

    public String getPaytmno() {
        return paytmno;
    }

    public void setPaytmno(String paytmno) {
        this.paytmno = paytmno;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

}