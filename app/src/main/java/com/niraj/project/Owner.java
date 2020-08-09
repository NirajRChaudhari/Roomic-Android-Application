package com.niraj.project;

import java.util.ArrayList;
import java.util.List;

public class Owner {
    public boolean o_stored_room;
    public String o_id,o_email,o_name,o_password,o_company,o_phone,o_peraddress;
    //public List<Room_class> o_roomlist;

    public Owner() {
    //    this.o_stored_room=false;
        this.o_id="NOT SET";
        this.o_email = "NOT SET";
        this.o_name = "NOT SET";
        this.o_password ="NOT SET";
        this.o_company = "NOT SET";
        this.o_phone = "NOT SET";
        this.o_peraddress = "NOT SET";
     //   this.o_roomlist=new ArrayList<Room_class>();
    }

    public Owner(String o_id,String o_email, String o_name, String o_password, String o_company, String o_phone, String o_peraddress) {
        this.o_id=o_id;
        this.o_email = o_email;
        this.o_name = o_name;
        this.o_password = o_password;
        this.o_company = o_company;
        this.o_phone = o_phone;
        this.o_peraddress = o_peraddress;
    }

}
