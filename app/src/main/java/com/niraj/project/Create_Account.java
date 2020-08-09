package com.niraj.project;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class Create_Account extends AppCompatActivity {

    Button next;
    RadioButton rb1, rb2;
    EditText name, email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__account);

        next = (Button) findViewById(R.id.next5);
        rb1 = (RadioButton) findViewById(R.id.b1);
        rb2 = (RadioButton) findViewById(R.id.b2);
        name = (EditText) findViewById(R.id.create_name);
        email = (EditText) findViewById(R.id.create_email);
        password = (EditText) findViewById(R.id.create_pass);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                email.setError(null);
                password.setError(null);

                final String cname = name.getText().toString().trim();
                final String cemail = email.getText().toString().trim();
                final String cpassword = password.getText().toString().trim();

                boolean cancel = false;
                View focusView = null;

                if(TextUtils.isEmpty(cname)){
                    name.setError("Name Text-Field is EMPTY");
                    focusView=name;
                    cancel=true;
                }

                if (TextUtils.isEmpty(cemail)) {
                    email.setError("Email Text-Field is EMPTY");
                    focusView = email;
                    cancel = true;
                } else if (!isEmailValid(cemail)) {
                    email.setError("Email is INVALID");
                    focusView = email;
                    cancel = true;
                }

                if (TextUtils.isEmpty(cpassword)) {
                    password.setError("Password Text-Field is EMPTY");
                    focusView = password;
                    cancel = true;
                } else if (!isPasswordValid(cpassword)) {
                    password.setError("Password too Short");
                    focusView = password;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                            if (rb1.isChecked()) {
                                Intent i = new Intent(Create_Account.this, Create_Owner.class);
                                i.putExtra("cname", cname);
                                i.putExtra("cemail", cemail);
                                i.putExtra("cpassword", cpassword);

                                startActivity(i);
                            }
                            else if (rb2.isChecked()) {
                                Intent i2 = new Intent(Create_Account.this, Create_Rentee.class);
                                i2.putExtra("cname", cname);
                                i2.putExtra("cemail", cemail);
                                i2.putExtra("cpassword", cpassword);

                                startActivity(i2);
                            }
                        }
                    }
                });//onclick listener
            }//oncreate function
    private boolean isPasswordValid (String cpassword)
    {
        return cpassword.length() >=6;
    }

    private boolean isEmailValid (String cemail)
    {
        return cemail.contains("@");
    }

}

