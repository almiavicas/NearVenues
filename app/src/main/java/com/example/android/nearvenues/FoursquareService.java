package com.example.android.nearvenues;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoursquareService {
    @GET("v2/venues/search")
    Call<List<Venue>> listVenues(
            @Query("ll") String latLon,
            @Query("client_id") String clientId,
            @Query("client_secret") String secretKey);
}
