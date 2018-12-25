package com.example.android.nearvenues.API;

import com.example.android.nearvenues.API.APIServices.FoursquareService;
import com.example.android.nearvenues.API.Deserializer.MyDeserializer;
import com.example.android.nearvenues.models.Venue;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    public static final String BASE_URL = "https://api.foursquare.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getApi() {
        if (retrofit == null) {

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Venue.class, new MyDeserializer());

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
        }
        return retrofit;
    }
}
