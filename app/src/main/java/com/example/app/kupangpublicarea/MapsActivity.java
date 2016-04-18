package com.example.app.kupangpublicarea;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        DirectionCallback,
        OnMarkerClickListener {

    public static final String TAG = MapsActivity.class.getSimpleName();

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;

    private Marker myMarker;
    private LatLng latLng;
    private LatLng spbu1 = new LatLng(-10.182282, 123.601653);
    private LatLng spbu2 = new LatLng(-10.190474, 123.607171);
    private LatLng spbu3 = new LatLng(-10.174254, 123.614981);
    private LatLng spbu4 = new LatLng(-10.156205, 123.643207);
    private LatLng spbu5 = new LatLng(-10.154213, 123.633478);
    private LatLng spbu6 = new LatLng(-10.151142, 123.603221);

    private String mapDirectionKey = "AIzaSyCOuMkMYCahosvqF0UoV-egXkvy-_g-aUY";
    private String[] colors = {"#7fff7272", "#7f31c7c5", "#7fff8a00"};

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mMap.setOnMarkerClickListener(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)        // 10 seconds, in milliseconds
                .setFastestInterval(5000); // 1 second, in milliseconds

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        setSPBU();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        latLng = new LatLng(currentLatitude, currentLongitude);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                .title("My Location");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 14));
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(mLastLocation);
        }
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        mMap.clear();
        handleNewLocation(mLastLocation);
        setSPBU();

//        List<Polyline> polylines = new ArrayList<Polyline>();
//
//        for(Polyline line : polylines)
//        {
//            line.remove();
//        }
//
//        polylines.clear();

        if (direction.isOK()) {
            for (int i = 0; i < direction.getRouteList().size(); i++) {
                Route route = direction.getRouteList().get(i);
                String color = colors[i % colors.length];
                ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.parseColor(color)));
            }
        } else {
            Toast.makeText(this, "Petunjuk arah tidak ditemukan", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();

        GoogleDirection.withServerKey(mapDirectionKey)
                    .from(latLng)
                    .to(marker.getPosition())
                    .transportMode(TransportMode.DRIVING)
                    .alternativeRoute(true)
                    .execute(this);

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup);
        dialog.setTitle("Info " + marker.getTitle());

        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(marker.getSnippet());

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


        return true;
    }

    protected void setSPBU() {
        mMap.addMarker(new MarkerOptions()
                .position(spbu1)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps))
                .title("SPBU Naikoten 1").snippet("Alamat: Jl. Soeharto No. 70, Naikoten I, Kupang, NTT \nFasilitas: Toilet, ATM"));

        mMap.addMarker(new MarkerOptions()
                .position(spbu2)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps))
                .title("SPBU Oepura").snippet("Jl. HR. Horo No. 25, Oepura, Kupang, NTT"));

        mMap.addMarker(new MarkerOptions()
                .position(spbu3)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps))
                .title("SPBU Oebufu").snippet("Jl. WJ Lalamentik, Oebobo, Kupang, NTT"));

        mMap.addMarker(new MarkerOptions()
                .position(spbu4)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps))
                .title("SPBU Oesapa").snippet("Jl. Piet A Tallo, Oesapa, Kupang, NTT"));

        mMap.addMarker(new MarkerOptions()
                .position(spbu5)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps))
                .title("SPBU Pulau Indah").snippet("Jl. Pulau Indah No.54, Oesapa, Kupang, NTT"));

        mMap.addMarker(new MarkerOptions()
                .position(spbu6)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gps))
                .title("SPBU Pasir Panjang").snippet("Jl. Raya Timor No.128, Pasir Panjang, Kupang, NTT"));
    }
}
