package com.niraj.project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Rentee_Tabbed_Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    String spinner_text;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    TextView upload_text;
    Button upload1,upload2;
    FloatingActionButton fab;
    ImageView imgview1,imgview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentee_tabbed_);


        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.hide();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager)findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fab.hide();
                if(tab.getPosition()==1)
                {
                    fab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                fab.hide();
                if(tab.getPosition()==1)
                {
                    fab.show();
                }

            }
        });


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(Rentee_Tabbed_Activity.this);
                View mView =getLayoutInflater().inflate(R.layout.dialog_register,null);

                boolean selected_checkbox=false;
                TextView et1,et2,et3,et4,et5,et;
                RadioButton rb;
                imgview1=mView.findViewById(R.id.YourPhotoImage);
                imgview2=mView.findViewById(R.id.RoomsPhotoImage);
                et=(TextView)mView.findViewById(R.id.dialog_title);
                et1=(TextView)mView.findViewById(R.id.dialog_text1);
                et2=(TextView)mView.findViewById(R.id.dialog_text2);
                et3=(TextView)mView.findViewById(R.id.dialog_text3);
                et4=(TextView)mView.findViewById(R.id.dialog_text4);
                et5=(TextView)mView.findViewById(R.id.dialog_text5);
                rb=mView.findViewById(R.id.AcceptRadio);
                Spinner s=(Spinner)mView.findViewById(R.id.status_spinner);
                upload1=(Button)mView.findViewById(R.id.dialog_upload1);
                upload2=mView.findViewById(R.id.dialog_upload2);

                //  final CheckBox ch1=(CheckBox)mView.findViewById(R.owner_id.acceptCheckbox);
                Button b1=(Button)mView.findViewById(R.id.dialog_Register);

                upload1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 5);
                    }
                });
                upload2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 6);
                    }
                });




                s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch(position)
                        {

                            case 0:spinner_text="Single Person with NO Room ";
                                break;

                            case 1:spinner_text="Single Person with Room ";
                                break;

                            case 2:spinner_text="Group of people with NO Room ";
                                break;

                            case 3:spinner_text="Group of people with Room ";
                                break;

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //if(ch1.isChecked())
                        //  {
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("rentee");
                        databaseReference.child(FirebaseAuth.getInstance().getUid()).child("r_status").setValue(spinner_text);
                        databaseReference.child(FirebaseAuth.getInstance().getUid()).child("rm_register").setValue(true);
                        Intent i=new Intent(getBaseContext(),Rentee_Tabbed_Activity.class);
                        startActivity(i);

                    }
                    //    else
                    //         Toast.makeText(Rentee_Tabbed_Activity.this,"Please Accept T&C",Toast.LENGTH_SHORT).show();
                    //   }
                });//Onclick of button

                mBuilder.setView(mView);
                AlertDialog dialog=mBuilder.create();
                dialog.show();

            }
        });

    }//onCreate


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0: return new Frag_Rentee_Room_Finder();


                case 1: return new Frag_Roomate_Finder();


                case 2: return new Frag_Help();

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_rentee,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id==R.id.action_logout1){
            SharedPreferences sharedPreferences=getSharedPreferences("System_Data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();

            editor.putBoolean("log_in",false);
            editor.putString("role","ND");
            editor.putString("email","ND");
            editor.putString("password","ND");
            editor.commit();

            FirebaseAuth.getInstance().signOut();

            Toast.makeText(Rentee_Tabbed_Activity.this,"Logged out",Toast.LENGTH_LONG).show();
            Intent i=new Intent(Rentee_Tabbed_Activity.this,Login.class);
            startActivity(i);
        }
        if(id==R.id.action_profile_rentee)
        {
            Intent i=new Intent(Rentee_Tabbed_Activity.this,com.niraj.project.Profile_rentee.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        //  StorageReference mstorageReference=FirebaseStorage.getInstance().getReference();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("rentee");

        if (requestCode == 5 && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {

            Uri imageUri=data.getData();
            imgview1.setImageURI(imageUri);
//            upload_text.setText(imageUri.getLastPathSegment());

            final StorageReference file = storageReference.child(System.currentTimeMillis() + " ");

            if (imageUri != null) {
                UploadTask uploadTask=file.putFile(imageUri);

                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return file.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            Uri uri=task.getResult();
                            Toast.makeText(Rentee_Tabbed_Activity.this, "Upload Successfull", Toast.LENGTH_SHORT).show();

                            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("rentee").child(FirebaseAuth.getInstance().getUid());
                            databaseReference.child("r_photo").setValue(uri.toString());
                        }
                        else
                        {
                            Toast.makeText(Rentee_Tabbed_Activity.this, "Upload Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        }

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Do you want to EXIT ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                })
                .setNegativeButton("NO", null)
                .setIcon(R.mipmap.logo)
                .setCancelable(false).show();
    }
}
