package com.niraj.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Create_Owner extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button signup;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__owner);

        progressDialog =new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        signup=(Button)findViewById(R.id.signup_owner);

        final Bundle data=getIntent().getExtras();
        if(data== null)
        {
            new AlertDialog.Builder(this).setTitle("Failed to Proceed ....").setMessage("Please , Enter Details Properly .....").setPositiveButton(android.R.string.ok,null).setIcon(R.mipmap.logo).setCancelable(false).show();
        }

        final String cname=data.getString("cname");
        final String cemail=data.getString("cemail");
        final String cpassword=data.getString("cpassword");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Creating Owner Account....");
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(cemail,cpassword).addOnCompleteListener(Create_Owner.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            new AlertDialog.Builder(Create_Owner.this).setTitle("OWNER REGISTRATION FAILED").setMessage("Check CREDENTIALS or INTERNET CONNECTION and TRY AGAIN .....").setCancelable(false).setIcon(R.mipmap.logo).setPositiveButton("OK",null).show();
                            progressDialog.dismiss();
                        }
                        else
                        {
                            FirebaseUser user=mAuth.getCurrentUser();
                            UserProfileChangeRequest profileup=new UserProfileChangeRequest.Builder().setDisplayName("owner").build();
                            user.updateProfile(profileup);

                            Owner owner=new Owner();

                            owner.o_name=data.getString("cname");
                            owner.o_email=data.getString("cemail");
                            owner.o_password=data.getString("cpassword");

                            owner.o_company=((EditText)findViewById(R.id.company)).getText().toString();
                            owner.o_peraddress=((EditText)findViewById(R.id.owner_per_address)).getText().toString();
                            owner.o_phone=((EditText)findViewById(R.id.owner_mobile)).getText().toString();

                            Toast.makeText(Create_Owner.this,cname+" ACCOUNT CREATED SUCCESSFULLY ......",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            // Store to Firebase

                            databaseReference=FirebaseDatabase.getInstance().getReference().child("owner");
                            owner.o_id=FirebaseAuth.getInstance().getUid();

                            databaseReference.child(owner.o_id).setValue(owner);
                            Intent i=new Intent(Create_Owner.this,Owner_Tabbed_Activity.class);
                            startActivity(i);
                        }
                    }
                });//addOn Complete Listener

            }//on CLICK

        });//sign up setOnClickListener

    }//oncreate

}//class
