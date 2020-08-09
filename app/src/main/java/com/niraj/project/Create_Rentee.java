package com.niraj.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Create_Rentee extends AppCompatActivity {

    FirebaseAuth mAuth;
     DatabaseReference databaseReference;

    Button signup;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__rentee);

        mAuth= FirebaseAuth.getInstance();
        signup=(Button)findViewById(R.id.signup_rentee);
        progressDialog =new ProgressDialog(this);

        final Bundle data=getIntent().getExtras();
        if(data== null)
        {
            new AlertDialog.Builder(this).setTitle("Failed to Proceed ....").setMessage("Please , Enter Details Properly .....").setPositiveButton(android.R.string.ok,null).setIcon(R.mipmap.logo).setCancelable(false).show();
        }

        // Declared strings


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Creating Rentee Account....");
                progressDialog.show();

                String cemail=data.getString("cemail");
                String cpassword=data.getString("cpassword");

                mAuth.createUserWithEmailAndPassword(cemail,cpassword).addOnCompleteListener(Create_Rentee.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {

                            new AlertDialog.Builder(Create_Rentee.this).setTitle("RENTEE REGISTRATION FAILED").setMessage("Check CREDENTIALS or INTERNET CONNECTION and TRY AGAIN .....").setCancelable(false).setIcon(R.mipmap.logo).setPositiveButton("OK",null).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            FirebaseUser user=mAuth.getCurrentUser();
                            UserProfileChangeRequest profileup=new UserProfileChangeRequest.Builder().setDisplayName("rentee").build();
                            user.updateProfile(profileup);
                            Rentee rentee=new Rentee();

                            rentee.r_name=data.getString("cname");
                            rentee.r_email=data.getString("cemail");
                            rentee.r_password=data.getString("cpassword");

                            rentee.r_age= ((TextView)findViewById(R.id.age)).getText().toString().trim();
                            rentee.r_study=((TextView)findViewById(R.id.study)).getText().toString().trim();
                            rentee.r_peraddress=((TextView)findViewById(R.id.rentee_per_address)).getText().toString().trim();
                            rentee.r_phone=((TextView)findViewById(R.id.rentee_mobile)).getText().toString().trim();
                            rentee.r_addiction=((TextView)findViewById(R.id.addiction)).getText().toString().trim();

                            RadioGroup rd=(RadioGroup)findViewById(R.id.radio_gender);
                            RadioButton sel_gender=(RadioButton) findViewById(rd.getCheckedRadioButtonId());
                            rentee.r_gender=sel_gender.getText().toString().trim();

                            Toast.makeText(Create_Rentee.this,rentee.r_name+" ACCOUNT CREATED SUCCESSFULLY ......" ,Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();


                            /// Store to Database

                            databaseReference=FirebaseDatabase.getInstance().getReference().child("rentee");
                            rentee.r_id=FirebaseAuth.getInstance().getUid();

                            databaseReference.child(rentee.r_id).setValue(rentee);
                            Intent i=new Intent(Create_Rentee.this,Rentee_Tabbed_Activity.class);
                            startActivity(i);

                        }
                    }
                });//addOn Complete Listener

            }//on CLICK

        });//sign up setOnClickListener

    }
}