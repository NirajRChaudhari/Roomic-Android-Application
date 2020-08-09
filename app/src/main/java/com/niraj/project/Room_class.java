package com.niraj.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room_class implements Serializable {
    public String owner_id,firestore_id,owner_name,contact_details,address,locality,district,pincode,desc,facilities,tandc1,tandc2,tandc3,tandc4,photoname1,photoname2,photoname3,rent;
    public long sequence;

    public Double latitude,longitude;

    public List<Integer> icon;

    public Room_class( ) {
        this.owner_id ="NotSet";
        this.firestore_id="NotSet";
        this.owner_name="NotSet";
        this.contact_details="NotSet";
        this.address = "NotSet";
        this.locality = "NotSet";
        this.desc = "NotSet";
        this.facilities = "NotSet";
        this.latitude=0.0;
        this.longitude=0.0;
        this.district="NotSet";
        this.pincode="NotSet";
        this.sequence=0;
        this.tandc1 = "T&C :- NotSet";
        this.tandc2 = "T&C :- NotSet";
        this.tandc3 = "T&C :- NotSet";
        this.tandc4 = "T&C :- NotSet";
        this.photoname1 = "NotSet";
        this.photoname2 = "NotSet";
        this.photoname3 = "NotSet";
        this.rent = "NotSet";
        this.icon = new ArrayList<>();
        icon.clear();
    }

}
