package com.niraj.project;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button mset_button,msearch_button;
    EditText msearch_string;
    String location,locality,address,facilities,rent,district,pincode;
    Double latitude,longitude;
    Boolean mapset;
    LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            mapset=extras.getBoolean("mapset");
            location=extras.getString("complete_address_frag");
            address=extras.getString("paddress");
            locality=extras.getString("plocality");
            facilities=extras.getString("pfacilities");
            rent=extras.getString("prent");
            district=extras.getString("pdistrict");
            pincode=extras.getString("ppincode");
            latitude=extras.getDouble("platitude");
            longitude=extras.getDouble("plongitude");
        }

        msearch_button=findViewById(R.id.search_button);
        msearch_string=findViewById(R.id.search_string);
        mset_button=findViewById(R.id.set_location_button);

        mset_button.setEnabled(false);
        mset_button.setText(" PLEASE  SEARCH  FOR  VALID  ADDRESS ");

            msearch_string.setText(location);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        final MarkerOptions markerOptions=new MarkerOptions();

        if(latitude==0.0 && longitude==0.0)
        {
            latitude=18.52;
            longitude=73.85;
        }
        final LatLng Pune = new LatLng(latitude, longitude);
        CameraUpdateFactory.zoomTo(20);
        CameraPosition cameraPosition=new CameraPosition.Builder()
                .target(Pune)
                .zoom(20)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Marker perth = mMap.addMarker(new MarkerOptions()
                .position(Pune)
                .title(" Hold And Drag To Change Marker's Position ")
                .draggable(true));


        msearch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                location=msearch_string.getText().toString();
                Log.d("loc",location);

                Geocoder geocoder=new Geocoder(MapsActivity.this);
                List<Address>addressList= new ArrayList<>();
                try {
                    addressList = geocoder.getFromLocationName(location,1);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(addressList.size()!=0) {

                    mapset=true;
                    mset_button.setEnabled(true);
                    mset_button.setText("CLICK  TO  SAVE  LOCATION");
                    Address address = addressList.get(0);
                    LatLng latLng1 = new LatLng(address.getLatitude(), address.getLongitude());
                    latLng = latLng1;

                    CameraUpdateFactory.newLatLngZoom(latLng1, 20);
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(latLng1)
                            .zoom(20)
                            .build();
                    //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(latLng1)
                            .title(" Hold And Drag To Change Marker's Position ")
                            .draggable(true)
                    );
                }//if
                else
                {
                    Toast.makeText(MapsActivity.this," Invalid Address.... Please Enter Correct Address ",Toast.LENGTH_LONG).show();
                }
            }//onClick
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                latLng=marker.getPosition();
                          }
        });



        mset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(MapsActivity.this, Frag_Reg_Owner_2.class);

                    intent.putExtra("platitude",latLng.latitude);
                    intent.putExtra("plongitude",latLng.longitude);
                    intent.putExtra("paddress",address);
                    intent.putExtra("plocality",locality);
                    intent.putExtra("pdistrict",district);
                    intent.putExtra("ppincode",pincode);
                    intent.putExtra("pfacilites",facilities);
                    intent.putExtra("prent",rent);
                    intent.putExtra("mapset",mapset);

                startActivity(intent);
            }
        });
    }

}