package com.example.android.nearvenues.API.Deserializer;

import android.util.Log;

import com.example.android.nearvenues.models.Venue;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Class to deserialize the JSON obtained from the FourSquare service.
 * Extracts the list of venues in JSON
 */
public class MyDeserializer implements JsonDeserializer<List<Venue>> {

    @Override
    public List<Venue> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Venue> venuesList = new ArrayList<>();
        JsonArray venues = json.getAsJsonObject().getAsJsonObject("response").getAsJsonArray("venues");
        Iterator<JsonElement> it = venues.iterator();
        do {
            JsonObject obj = it.next().getAsJsonObject();
            String name = obj.get("name").getAsString();

            obj = obj.getAsJsonObject("location");

            int distance = obj.get("distance").getAsInt();
            String address = obj.has("address") ? obj.get("address").getAsString() : "";
            venuesList.add(new Venue(name, address, distance));
        } while (it.hasNext());

        return venuesList;
    }
}
