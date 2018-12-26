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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button searchButton;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FoursquareService service;
    private List<Venue> results;
    private LocationManager locationManager;
    private Location lastLocation;

    private final int REQUEST_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();
    }

    private void bindUI() {
        searchButton = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new MyAdapter(R.layout.item_layout, results);
        layoutManager = new LinearLayoutManager(this);
        service = API.getApi().create(FoursquareService.class);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
                    return;
                }
                lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                callFourSquareService(lastLocation);

            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    callFourSquareService(lastLocation);
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

    private void callFourSquareService(Location lastLocation) {

        DecimalFormat format = new DecimalFormat("#.##");
        String latLon = format.format(lastLocation.getLatitude()) + "," + format.format(lastLocation.getLongitude());
        Call<List<Venue>> venues = service.listVenues(
                latLon,
                Utils.FOURSQUARE_CLIENT_KEY,
                Utils.FOURSQUARE_CLIENT_SECRET);

        venues.enqueue(new Callback<List<Venue>>() {
            @Override
            public void onResponse(Call<List<Venue>> call, Response<List<Venue>> response) {
                results = response.body();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Venue>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}