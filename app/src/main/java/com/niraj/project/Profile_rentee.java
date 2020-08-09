package com.niraj.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;

public class Profile_rentee extends AppCompatActivity {

    ImageButton edit;
    EditText name,address,contact,email,age,gender,qualification,addiction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_rentee);
        edit=(ImageButton)findViewById(R.id.profile_rentee_edit);
        name=(EditText)findViewById(R.id.profile_rentee_name);
        address=(EditText)findViewById(R.id.profile_rentee_address);
        contact=(EditText)findViewById(R.id.profile_rentee_contact);
        email=(EditText)findViewById(R.id.profile_rentee_emailid);
        age=(EditText)findViewById(R.id.profile_rentee_age);
        gender=(EditText)findViewById(R.id.profile_rentee_gender);
        qualification=(EditText)findViewById(R.id.profile_rentee_qualification);
        addiction=(EditText)findViewById(R.id.profile_rentee_addiction);
       /* name.setFocusable(false);
        address.setFocusable(false);
        contact.setFocusable(false);
        email.setFocusable(false);
        age.setFocusable(false);
        gender.setFocusable(false);
        qualification.setFocusable(false);
*/

       /* edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setFocusableInTouchMode(true);
                address.setFocusableInTouchMode(true);
                contact.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);
                age.setFocusableInTouchMode(true);
                gender.setFocusableInTouchMode(true);
                qualification.setFocusable(true);
                addiction.setFocusableInTouchMode(true);
            }
        });*/
    }
}
