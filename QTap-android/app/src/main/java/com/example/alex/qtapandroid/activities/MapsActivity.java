package com.example.alex.qtapandroid.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.alex.qtapandroid.R;

import java.security.Permission;

import static java.security.AccessController.getContext;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
        LatLng sydney = new LatLng(44.228185, -76.492447);
        mMap.addMarker(new MarkerOptions().position(sydney).title("ILC :)"));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(44.228185, -76.492447)).zoom(16).build();

        // create marker
        MarkerOptions marker = new MarkerOptions().position(new LatLng(44.224625, -76.497790)).title("Stirling");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        // adding marker
        mMap.addMarker(marker);


        PackageManager pm = this.getApplicationContext().getPackageManager();   // check if permission was granted for user location
        int fineLoc = pm.checkPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                this.getApplicationContext().getPackageName());
        int corseLoc = pm.checkPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                this.getApplicationContext().getPackageName());
        if (fineLoc == PackageManager.PERMISSION_GRANTED || corseLoc == PackageManager.PERMISSION_GRANTED);
            mMap.setMyLocationEnabled(true); // false to disable
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
