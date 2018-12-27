package com.example.android.nearvenues.API.APIServices;

import com.example.android.nearvenues.models.Venue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/** Interface to define the calls made to FourSquare service */
public interface FoursquareService {

    /** method to retrieve list of near venues */
    @GET("v2/venues/search")
    Call<List<Venue>> listVenues(
            @Query("ll") String latLon,
            @Query("client_id") String clientId,
            @Query("client_secret") String secretKey,
            @Query("v") String version);
}
