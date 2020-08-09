package com.niraj.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Details_Roommate extends FragmentActivity {

    TextView name,email,address,contact,status,age,gender,quali,addiction,constraint;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_roommate);

        name=(TextView)findViewById(R.id.name_roomate_details1);
        email=(TextView)findViewById(R.id.emailid_roomate_details1);
        address=(TextView)findViewById(R.id.address_roomate_details1);
        addiction=(TextView)findViewById(R.id.addiction_roomate_details1);
        constraint=(TextView)findViewById(R.id.constriants_roomate_details1);
        status=(TextView)findViewById(R.id.status_roomate_details1);
        contact=(TextView)findViewById(R.id.contact_roomate_details1);
        age=(TextView)findViewById(R.id.age_roomate_details1);
        gender=(TextView)findViewById(R.id.gender_roomate_details1);
        quali=(TextView)findViewById(R.id.qualification_roomate_details1);
        imageView=(ImageView)findViewById(R.id.image_roommate_details1);


        final Bundle bundle=getIntent().getExtras();

        Log.i("details"," bundle parsed");
        if(bundle==null)
        {
            Toast.makeText(Details_Roommate.this, " Problem with Data Fetching..... ", Toast.LENGTH_SHORT).show();
        }
        else
        {
           Rentee rentee=(Rentee) bundle.getSerializable("roommate");

            Picasso.get().load(rentee.r_photo).into(imageView);
            name.setText(rentee.r_name);
            email.setText(rentee.r_email);
            address.setText(rentee.r_peraddress);
            addiction.setText(rentee.r_addiction);
            status.setText(rentee.r_status);
            constraint.setText("ADD FIELD TO FIREBASE");
            contact.setText(rentee.r_phone);
            age.setText(rentee.r_age);
            gender.setText(rentee.r_gender);
            quali.setText(rentee.r_study);
                   }


    }
}
