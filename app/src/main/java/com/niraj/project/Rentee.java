package com.niraj.project;

import java.io.Serializable;

public class Rentee implements Serializable {
    public String r_id,r_name,r_email,r_password,r_age,r_study,r_peraddress,r_phone,r_addiction,r_gender,r_status,r_photo;
    public boolean rm_register;

    public Rentee() {

        rm_register=false;

        this.r_photo="NOT SET";
        this.r_name = "NOT SET";
        this.r_email = "NOT SET";
        this.r_password = "NOT SET";
        this.r_age = "NOT SET";
        this.r_study = "NOT SET";
        this.r_peraddress = "NOT SET";
        this.r_phone = "NOT SET";
        this.r_addiction = "NOT SET";
        this.r_gender = "NOT SET";
        this.r_status="NOT SET";

    }

    public Rentee(String r_name, String r_email, String r_password, String r_age, String r_study, String r_peraddress, String r_phone, String r_addiction, String r_gender) {
        this.r_name = r_name;
        this.r_email = r_email;
        this.r_password = r_password;
        this.r_age = r_age;
        this.r_study = r_study;
        this.r_peraddress = r_peraddress;
        this.r_phone = r_phone;
        this.r_addiction = r_addiction;
        this.r_gender = r_gender;
    }
}
