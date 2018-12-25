package com.example.android.nearvenues.API.Deserializer;

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

public class MyDeserializer implements JsonDeserializer<List<Venue>> {

    @Override
    public List<Venue> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Venue> venuesList = new ArrayList<>();
        JsonArray venues = json.getAsJsonObject().getAsJsonObject("response").getAsJsonArray("venues");
        Iterator<JsonElement> it =  venues.iterator();
        do {
            JsonObject obj = it.next().getAsJsonObject();
            String name = obj.get("name").getAsString();
            int distance = obj.getAsJsonObject("location").get("distance").getAsInt();
            String address = obj.getAsJsonObject("location").get("address").getAsString();
            venuesList.add(new Venue(name, address, distance));
        } while (it.hasNext());

        return venuesList;
    }
}
