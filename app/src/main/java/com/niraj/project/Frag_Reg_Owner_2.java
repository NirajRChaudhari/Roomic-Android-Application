package com.niraj.project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

////////// ITS ACTIVITY NAMED JUST FRAGMENT
public class Frag_Reg_Owner_2 extends AppCompatActivity {

    Button addroombutton, select_photo1,select_photo2,select_photo3, upload_photo1,upload_photo2,upload_photo3;
    Spinner mspinner;
    String complete_address;
    ImageButton mapButton;
    EditText mtandc1,mtandc2,mtandc3,mtandc4;
    TextView mphotoname1,mphotoname2,mphotoname3,set_map_textview;
    Room_class room_class;
    boolean spinselected=false,mapset=false;
    ProgressDialog progressDialog;
    ImageView imgView1,imgView2,imgView3;
    Uri imageUri1,imageUri2,imageUri3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_owner_reg2);

        room_class=new Room_class();
        progressDialog=new ProgressDialog(this);
        Bundle extras=getIntent().getExtras();

        if(extras!=null)
        {
            mapset=extras.getBoolean("mapset");
            room_class.address=extras.getString("paddress");
            room_class.locality=extras.getString("plocality");
            room_class.facilities=extras.getString("pfacilities");
            room_class.rent=extras.getString("prent");
            room_class.district=extras.getString("pdistrict");
            room_class.pincode=extras.getString("ppincode");
            room_class.latitude=extras.getDouble("platitude");
            room_class.longitude=extras.getDouble("plongitude");

            // Directly Assign
            DatabaseReference ref_owner=FirebaseDatabase.getInstance().getReference().child("owner").child(FirebaseAuth.getInstance().getUid());
            ref_owner.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        HashMap<String,String> td = (HashMap<String, String>) dataSnapshot.getValue();

                        room_class.owner_name=td.get("o_name");
                        room_class.contact_details=td.get("o_phone");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    ///Give Alert Box and later intent to re login as firebase problem

                }
            });
        }

        select_photo1 =(Button)findViewById(R.id.select_photo1);
        select_photo2=(Button)findViewById(R.id.select_photo2);
        select_photo3=(Button)findViewById(R.id.select_photo3);
        upload_photo1=(Button)findViewById(R.id.upload_photo1);
        upload_photo2=(Button)findViewById(R.id.upload_photo2);
        upload_photo3=(Button)findViewById(R.id.upload_photo3);

        addroombutton= (Button) findViewById(R.id.addroombutton);
        mtandc1=(EditText)findViewById(R.id.termsandconditions1);
        mtandc2=(EditText)findViewById(R.id.termsandconditions2);
        mtandc3=(EditText)findViewById(R.id.termsandconditions3);
        mtandc4=(EditText)findViewById(R.id.termsandconditions4);
        mspinner=(Spinner)findViewById(R.id.spinner);
        mapButton=(ImageButton) findViewById(R.id.mapButton);

        set_map_textview=(TextView) findViewById(R.id.set_map_textview);
        imgView1=(ImageView) findViewById(R.id.imgView1);
        imgView2=(ImageView) findViewById(R.id.imgView2);
        imgView3=(ImageView) findViewById(R.id.imgView3);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complete_address = room_class.address +","+ room_class.locality +","+ room_class.district+"," + room_class.pincode+".";

                Intent intent = new Intent(Frag_Reg_Owner_2.this, MapsActivity.class);

                intent.putExtra("complete_address_frag", complete_address);
                intent.putExtra("paddress",room_class.address);
                intent.putExtra("plocality",room_class.locality);
                intent.putExtra("pdistrict",room_class.district);
                intent.putExtra("ppincode",room_class.pincode);
                intent.putExtra("pfacilites",room_class.facilities);
                intent.putExtra("prent",room_class.rent);
                intent.putExtra("platitude",room_class.latitude);
                intent.putExtra("plongitude",room_class.longitude);
                intent.putExtra("mapset",mapset);

                startActivity(intent);
            }
        });

        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                setSpin(false);
                if(i!=0) {
                    room_class.desc=(String)mspinner.getItemAtPosition(i);
                    setSpin(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });


        select_photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);

            }
        });
        select_photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);

            }
        });
        select_photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 3);

            }
        });

        upload_photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageUri1!=null) {
                    progressDialog.setMessage("UPLOADING FIRST IMAGE.....");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    final StorageReference storageReference = FirebaseStorage.getInstance().getReference("uploads");

                    final StorageReference file = storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "  " + System.currentTimeMillis());

                    UploadTask uploadTask = file.putFile(imageUri1);

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                            if (task.isSuccessful()) {
                                Uri uri = task.getResult();
                                Toast.makeText(Frag_Reg_Owner_2.this, "Upload Successfull", Toast.LENGTH_SHORT).show();
                                room_class.photoname1 = uri.toString();
                                select_photo1.setEnabled(false);
                                upload_photo1.setEnabled(false);
                                progressDialog.dismiss();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Frag_Reg_Owner_2.this, "Upload Failed... TRY AGAIN ", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }//if
                else
                {
                    Toast.makeText(Frag_Reg_Owner_2.this, " NO IMAGE SELECTED ", Toast.LENGTH_SHORT).show();
                }
            }//onClick
        });//upload1

        upload_photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageUri2!=null)
                {
                progressDialog.setMessage("UPLOADING SECOND IMAGE.....");
                progressDialog.setCancelable(false);
                progressDialog.show();

                final StorageReference storageReference = FirebaseStorage.getInstance().getReference("uploads");

                final StorageReference file = storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "  " + System.currentTimeMillis());

                UploadTask uploadTask=file.putFile(imageUri2);

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
                            Toast.makeText(Frag_Reg_Owner_2.this,"Upload Successfull",Toast.LENGTH_SHORT).show();
                            room_class.photoname2 = uri.toString();
                            select_photo2.setEnabled(false);
                            upload_photo2.setEnabled(false);
                            progressDialog.dismiss();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(Frag_Reg_Owner_2.this, "Upload Failed... TRY AGAIN ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                }//if
                else
                {
                    Toast.makeText(Frag_Reg_Owner_2.this, " NO IMAGE SELECTED ", Toast.LENGTH_SHORT).show();
                }
            }//onClick
        });//upload2

        upload_photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imageUri3!=null)
                {
                progressDialog.setMessage("UPLOADING THIRD IMAGE.....");
                progressDialog.setCancelable(false);
                progressDialog.show();

                final StorageReference storageReference = FirebaseStorage.getInstance().getReference("uploads");

                final  StorageReference file = storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + "  " + System.currentTimeMillis());
                UploadTask uploadTask=file.putFile(imageUri3);

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
                            Toast.makeText(Frag_Reg_Owner_2.this,"Upload Successfull",Toast.LENGTH_SHORT).show();
                            room_class.photoname3 = uri.toString();
                            select_photo3.setEnabled(false);
                            upload_photo3.setEnabled(false);
                            progressDialog.dismiss();
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(Frag_Reg_Owner_2.this,"Upload Failed... TRY AGAIN",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                }//if
                else
                {
                    Toast.makeText(Frag_Reg_Owner_2.this, " NO IMAGE SELECTED ", Toast.LENGTH_SHORT).show();
                }
            }//onClick
        });//upload3

        addroombutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmptiness();
            }
        });
    }

    public void checkEmptiness()
    {
        mtandc1.setError(null);
        mtandc2.setError(null);
        mtandc3.setError(null);
        mtandc4.setError(null);
        // mspinner.setError(null);
       // mphotoname1.setError(null);
        //mphotoname2.setError(null);
       // mphotoname3.setError(null);


        room_class.tandc1=mtandc1.getText().toString();
        room_class.tandc2=mtandc2.getText().toString();
        room_class.tandc3=mtandc3.getText().toString();
        room_class.tandc4=mtandc4.getText().toString();

        boolean cancel=false;
        View focusview=null;

        if(mapset==false)
        {
            set_map_textview.setError("Click Below to set MAP ");
            focusview=set_map_textview;
            cancel=true;
        }
        if(TextUtils.isEmpty(room_class.photoname1))
        {   mphotoname1.setError("You have to add image here");
            focusview=mphotoname1;
            cancel=true;

        }
        else if (TextUtils.isEmpty(room_class.photoname2)) {
            mphotoname1.setError("You have to add image here");
            focusview=mphotoname2;
            cancel=true;

        }
        else if(TextUtils.isEmpty(room_class.photoname3))
        { mphotoname3.setError("You have to add image here");
            focusview=mphotoname3;
            cancel=true;

        }

        // Main Comparison
        if(cancel)
        {
            focusview.requestFocus();
        }
        else if(spinselected==false)
        {
            Toast.makeText(Frag_Reg_Owner_2.this," Please Select Type of FLAT ",Toast.LENGTH_SHORT).show();
        }
        else
        {
           //////// ADD SUCCESS AND FAILURE LISENERS

            //FS
            room_class.owner_id =FirebaseAuth.getInstance().getUid();

            final FirebaseFirestore db=FirebaseFirestore.getInstance();

            db.collection("count").document("room").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    room_class.sequence=documentSnapshot.getLong("value");
                    db.collection("count").document("room").update("value",(room_class.sequence+1));

  /*          FirebaseDatabase.getInstance().getReference().child("room_count").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Long value=dataSnapshot.getValue(Long.class);
                    FirebaseDatabase.getInstance().getReference().child("room_count").setValue(value+1);
                    room_class.sequence=value;
*/
                    new AlertDialog.Builder(Frag_Reg_Owner_2.this).setTitle("ROOM REGISTERED").setMessage("We are 24*7 available to Serve you....").setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            db.collection("room").add(room_class).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    documentReference.update("firestore_id",documentReference.getId());
                                }
                            });

                            Intent i=new Intent(Frag_Reg_Owner_2.this,Owner_Tabbed_Activity.class);
                            startActivity(i);
                        }
                    }).setCancelable(false).setIcon(R.mipmap.logo).show();

                }

                /*@Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }*/
            });



        }

    }//checkEmptiness


    public void setSpin(boolean spin) {

        this.spinselected = spin;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            imageUri1=data.getData();
            imgView1.setImageURI(imageUri1);

        }
        else  if(requestCode==2 && resultCode==RESULT_OK &&
                data!=null && data.getData()!=null){
            imageUri2=data.getData();
            imgView2.setImageURI(imageUri2);

            }

        else if(requestCode==3 && resultCode==RESULT_OK &&
                data!=null && data.getData()!=null){
            imageUri3=data.getData();
            imgView3.setImageURI(imageUri3);

            }
            else
            {
                Toast.makeText(Frag_Reg_Owner_2.this," NO IMAGE SELECTED ",Toast.LENGTH_SHORT).show();
            }
    }//onActivityResult

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        new AlertDialog.Builder(Frag_Reg_Owner_2.this).setTitle("CANCEL ROOM REGISTRATION ").setMessage("All Data Entered will be LOST.... Are you Sure you want to EXIT ? ").setPositiveButton(" YES ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(!room_class.photoname1.equals("NotSet"))
                {
                    Log.i("hasd","enter");
FirebaseStorage.getInstance().getReferenceFromUrl(room_class.photoname1).delete();
                }
                if(!room_class.photoname2.equals("NotSet"))
                {
                    FirebaseStorage.getInstance().getReferenceFromUrl(room_class.photoname2).delete();
                }
                if(!room_class.photoname3.equals("NotSet"))
                {
                    FirebaseStorage.getInstance().getReferenceFromUrl(room_class.photoname3).delete();
                }

                Intent i=new Intent(Frag_Reg_Owner_2.this,Owner_Tabbed_Activity.class);
                startActivity(i);
            }//onClick
        }).setNegativeButton(" NO ",null).setCancelable(false).setIcon(R.mipmap.logo).show();
    }
}//class

/*
    final StorageReference storageReference = FirebaseStorage.getInstance().getReference("uploads");

    final  StorageReference file = storageReference.child(System.currentTimeMillis() + " ");*/
