package com.niraj.project;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity_Room extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps__room);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            latitude=extras.getDouble("latitude");
            longitude=extras.getDouble("longitude");
        }
        else
        {
            latitude=18.52;
            longitude=73.85;
        }

        mapFragment.getMapAsync(this);

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
        LatLng latLng1 = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng1).title("Room's Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
        CameraUpdateFactory.newLatLngZoom(latLng1, 20);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng1)
                .zoom(20)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
