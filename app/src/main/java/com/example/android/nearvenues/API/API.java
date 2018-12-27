package com.example.android.nearvenues.API;

import com.example.android.nearvenues.API.APIServices.FoursquareService;
import com.example.android.nearvenues.API.Deserializer.MyDeserializer;
import com.example.android.nearvenues.models.Venue;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Contains all elements to establish connection with FourSquare service */
public class API {

    private static final String BASE_URL = "https://api.foursquare.com/";
    public static final String FOURSQUARE_CLIENT_KEY = "0HFL5R5ESQ0TWGTC1CSLG0A2P4DKVVMKFT0PAH4BV41JRV2Y";
    public static final String FOURSQUARE_CLIENT_SECRET = "A2RPHC4CDZIPV2LU0YJFCXVUGRUWUT0OEOT4YR4BVTXMDQWP";

    private static Retrofit retrofit = null;

    public static Retrofit getApi() {
        if (retrofit == null) {

            Type listType = new TypeToken<List<Venue>>(){}.getType();

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(listType, new MyDeserializer());

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
        }
        return retrofit;
    }

    /** Param needed to make the API get call */
    public static String getVersion() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(Calendar.getInstance().getTime());
    }
}
