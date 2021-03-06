package com.example.android.nearvenues.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.nearvenues.API.API;
import com.example.android.nearvenues.API.APIServices.FoursquareService;
import com.example.android.nearvenues.adapters.MyAdapter;
import com.example.android.nearvenues.R;
import com.example.android.nearvenues.models.Venue;
import com.example.android.nearvenues.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /* Layout resources */
    private Button searchButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    /* API call resources */
    /** {@link com.example.android.nearvenues.API.APIServices.FoursquareService} used to manage
     * API calls
     */
    private FoursquareService service;
    /** List of {@link com.example.android.nearvenues.models.Venue} objects to display */
    private List<Venue> results;
    /** LocationManager used to retrieve user last location */
    private LocationManager locationManager;
    /** Permission code for requesting GPS location provider */
    private final int REQUEST_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = API.getApi().create(FoursquareService.class);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        bindUI();
    }

    /** Binds UI elements with activity's elements */
    private void bindUI() {
        searchButton = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            callFourSquareService(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        }
        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            callFourSquareService(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        } else {
            Toast.makeText(this, "No location provider found", Toast.LENGTH_LONG).show();
        }
    }

    /** Makes the call to the FourSquare API and loads it into the adapter */
    private void callFourSquareService(Location lastLocation) {

        Call<List<Venue>> venues = service.listVenues(
                Utils.getLatLon(lastLocation),
                API.FOURSQUARE_CLIENT_KEY,
                API.FOURSQUARE_CLIENT_SECRET,
                API.getVersion());

        venues.enqueue(new Callback<List<Venue>>() {
            @Override
            public void onResponse(Call<List<Venue>> call, Response<List<Venue>> response) {
                results = response.body();
                adapter = new MyAdapter(R.layout.item_layout, results);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Venue>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        callFourSquareService(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
                    } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        callFourSquareService(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
                    } else {
                        Toast.makeText(this, "No location provider found", Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
}
