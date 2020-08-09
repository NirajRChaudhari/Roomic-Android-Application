package com.niraj.project;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;


public class Details_Room extends FragmentActivity {

    private GoogleMap mMap;
    Room_class room_class;
    ImageView call,mapimage;
    TextView name_odetails, contact_odetails, address_odetails, pincode_odetails,facilities_odetails,locality_odetails,desc_odetails,district_odetails, rent_odetails, tac1, tac2, tac3,tac4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_room);

        name_odetails = (TextView) findViewById(R.id.name_odetails);
        contact_odetails = (TextView) findViewById(R.id.contact_odetails);
        address_odetails = (TextView) findViewById(R.id.address_odetails);
        locality_odetails = (TextView) findViewById(R.id.locality_odetails);
        pincode_odetails=(TextView)findViewById(R.id.pincode_odetails);
        facilities_odetails=(TextView)findViewById(R.id.facilities_odetails);
        desc_odetails=(TextView)findViewById(R.id.desc_odetails);
        rent_odetails = (TextView) findViewById(R.id.rent_odetails);
        district_odetails=(TextView)findViewById(R.id.district_odetails);

        tac1 = (TextView) findViewById(R.id.textView9);
        tac2 = (TextView) findViewById(R.id.textView10);
        tac3 = (TextView) findViewById(R.id.textView11);
        tac4= (TextView) findViewById(R.id.textView12);

        call = (ImageView) findViewById(R.id.call);
        mapimage=(ImageView)findViewById(R.id.mapsearch);



        final Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            Toast.makeText(Details_Room.this, " Problem with Data Fetching..... ", Toast.LENGTH_SHORT).show();
        } else {

            room_class=(Room_class) bundle.getSerializable("room");

            name_odetails.setText(room_class.owner_name);
            contact_odetails.setText(room_class.contact_details);
            address_odetails.setText(room_class.address);
            locality_odetails.setText(room_class.locality);
            rent_odetails.setText(room_class.rent);
            desc_odetails.setText(room_class.desc);
            district_odetails.setText(room_class.district);
            pincode_odetails.setText(room_class.pincode);
            facilities_odetails.setText(room_class.facilities);
            tac1.setText(room_class.tandc1);
            tac2.setText(room_class.tandc2);
            tac3.setText(room_class.tandc3);
            tac4.setText(room_class.tandc4);

            Frag_Image_Expand fr=new Frag_Image_Expand();
            Bundle args=new Bundle();
            args.putInt("pic_no",1);
            args.putSerializable("room",room_class);
            fr.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.image_exp_fragment,fr).commit();
        }


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+contact_odetails.getText().toString().trim()));
                if (ActivityCompat.checkSelfPermission(Details_Room.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                startActivity(i);
            }
        });
        mapimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Details_Room.this,MapsActivity_Room.class);
                i.putExtra("latitude",room_class.latitude);
                i.putExtra("longitude",room_class.longitude);

                startActivity(i);
            }
        });

    }

   /**
     *
     * // Obtain the SupportMapFragment and get notified when the map is ready to be used.
     *      //   SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
     *      //           .findFragmentById(R.owner_id.map);
     *     //    mapFragment.getMapAsync(this);
     *
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
  /*  @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/
}
