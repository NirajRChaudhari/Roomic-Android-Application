package com.niraj.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    Spinner spinner;
    Button login,create;
    TextInputEditText email,password;
    ProgressDialog progressDialog;
    String selected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        progressDialog=new ProgressDialog(this);
        final Spinner spinner=(Spinner)findViewById(R.id.spinner);
        List<String> categories=new ArrayList<String>();
        categories.add("CLICK TO SELECT");
        categories.add("Owner");
        categories.add("Rentee");

        ArrayAdapter<String> spinadapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinadapter);

        mAuth=FirebaseAuth.getInstance();

        login=(Button)findViewById(R.id.login);
        create=(Button)findViewById(R.id.create);
        email=(TextInputEditText)findViewById(R.id.email);
        password=(TextInputEditText)findViewById(R.id.password);


        ///


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                setSpin(false);
                if(i!=0) {
                    Toast toast=(Toast)Toast.makeText(Login.this, "Selected : " + spinner.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();

                    selected=(String)spinner.getItemAtPosition(i);
                    setSpin(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Logging IN ....");
                progressDialog.show();
                progressDialog.setCancelable(false);

                email.setError(null);
                password.setError(null);

               final String temail=email.getText().toString().trim();
               final String tpassword=password.getText().toString().trim();

                boolean cancel=false;
                View focusView=null;

                if(TextUtils.isEmpty(temail))
                {
                    email.setError("Email Text-Field is EMPTY");
                    focusView=email;
                    cancel=true;
                } else if(!isEmailValid(temail))
                {
                    email.setError("Email is INVALID");
                    focusView=email;
                    cancel=true;
                }

                if (TextUtils.isEmpty(tpassword)) {
                    password.setError("Password Text-Field is EMPTY");
                    focusView = password;
                    cancel = true;
                } else if (!isPasswordValid(tpassword)) {
                    password.setError("Password too Short");
                    focusView = password;
                    cancel = true;
                }

                if(spinselected==false)
                {
                    Toast.makeText(Login.this," Select Type of LOGIN ",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                if (cancel ) {
                    focusView.requestFocus();
                    progressDialog.dismiss();
                }
                if(!cancel && spinselected)
                {
                    // LOGIN FIREBASE AUTHENTICATION
                    mAuth.signInWithEmailAndPassword(temail,tpassword).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                showErrorDialog("Check CREDENTIALS or INTERNET CONNECTION ");
                                progressDialog.dismiss();
                            }
                            else
                            {
                                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                SharedPreferences sharedPreferences=getSharedPreferences("System_Data", Context.MODE_PRIVATE);

                                // SUCCESSFUL LOGIN
                                progressDialog.dismiss();
                                if(selected=="Owner"&&user.getDisplayName().equals("owner"))
                                {
                                    Toast.makeText(Login.this," Log IN Succcessfully ",Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putBoolean("log_in",true);
                                editor.putString("role","owner");
                                editor.putString("email",temail);
                                editor.putString("password",tpassword);
                                editor.commit();

                                    Intent i=new Intent(Login.this,Owner_Tabbed_Activity.class);
                                    startActivity(i);
                                }
                                else  if(selected=="Rentee" && user.getDisplayName().equals("rentee")) //selected==rentee
                                {
                                    Toast.makeText(Login.this," Log IN Succcessfully ",Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putBoolean("log_in",true);
                                    editor.putString("role","rentee");
                                    editor.putString("email",temail);
                                    editor.putString("password",tpassword);
                                    editor.commit();

                                    Intent i=new Intent(Login.this,Rentee_Tabbed_Activity.class);
                                    startActivity(i);
                                }
                                else
                                    Toast.makeText(Login.this,"Please select type of profile wisely!",Toast.LENGTH_LONG).show();



                            }
                        }
                    });
                }

            }//login onClickListener
            private boolean isPasswordValid(String tpassword)
            {
                return tpassword.length()>4;
            }

            private boolean isEmailValid(String temail)
            {
                return temail.contains("@");
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this,Create_Account.class);
                startActivity(i);
            }
        });
    }//oncreate

    boolean spinselected=false;
    public void setSpin(boolean spin) {
        this.spinselected = spin;
    }

    private void showErrorDialog(String message) {

        new AlertDialog.Builder(this)
                .setTitle("TRY AGAIN")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(R.mipmap.logo)
                .setCancelable(false).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(Login.this,MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {  //// NEXT TIME WHEN LOGIN PAGE OPENS EARLIER CREDENTIALS SHOULD BE REMOVED
        super.onStart();
        email.setText("");
        password.setText("");
    }
}