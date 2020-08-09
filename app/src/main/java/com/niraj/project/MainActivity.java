package com.niraj.project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ViewPager slider;
    private LinearLayout linlayout;
    private Slider_Adapter sliderAdapter;
    private TextView[] mDots;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences=getSharedPreferences("System_Data", Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean("log_in",false))
        {

            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Logging In ....");
            progressDialog.show();
            progressDialog.setCancelable(false);

            if(sharedPreferences.getString("role",null).equals("rentee"))
            {
                String email=sharedPreferences.getString("email",null);
                String password=sharedPreferences.getString("password",null);

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Intent i=new Intent(MainActivity.this,Rentee_Tabbed_Activity.class);
                            startActivity(i);

                        }
                        else
                        {
                            progressDialog.dismiss();
                            showErrorDialog("Check CREDENTIALS or INTERNET CONNECTION ");

                        }
                    }
                });
            }
            else if(sharedPreferences.getString("role",null).equals("owner"))
            {
                String email=sharedPreferences.getString("email",null);
                String password=sharedPreferences.getString("password",null);


                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Intent i=new Intent(MainActivity.this,Owner_Tabbed_Activity.class);
                            startActivity(i);

                        }
                        else
                        {
                            progressDialog.dismiss();
                            showErrorDialog("Check CREDENTIALS or INTERNET CONNECTION ");
                        }
                    }
                });
            }
        }//Checked Shared Preference
            bt = (Button) findViewById(R.id.start_login);
            slider = (ViewPager) findViewById(R.id.slider);
            linlayout = (LinearLayout) findViewById(R.id.linlayout);
            sliderAdapter = new Slider_Adapter(this);

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);
                }
            });


            slider.setAdapter(sliderAdapter);
            addDotsIndicator(0);
            bt.setVisibility(View.INVISIBLE);

            slider.addOnPageChangeListener(viewlistener);

    }

    public void addDotsIndicator(int pos) {
        mDots = new TextView[3];
        linlayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#x263B;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.Red));

            linlayout.addView(mDots[i]);

        }

        if (mDots.length > 0) {
            mDots[pos].setTextColor(getResources().getColor(R.color.Gold));
        }
    }

    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            if (i == 2)                  /////////////// LAST SCROLL PAGE DISPLAY LOGIN BUTTON
            {
                bt.setVisibility(View.VISIBLE);
            } else {
                bt.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);    }

    private void showErrorDialog(String message) {

        new AlertDialog.Builder(this)
                .setTitle("TRY AGAIN")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(R.mipmap.logo)
                .setCancelable(false).show();
    }//show error dialog
}//class



 /*   @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this,"Bye Bye have a Nice Day ....",Toast.LENGTH_SHORT).show();
    }
}*/