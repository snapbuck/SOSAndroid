package vn.snapbuck.sos.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sb6 on 6/13/15.
 */
public class SBPlaceJSONParser {
    /** Receives a JSONObject and returns a list */
    public static List<HashMap<String,String>> parse(JSONObject jObject){

        JSONArray jPlaces = null;
        try {
            /** Retrieves all the elements in the 'places' array */
            jPlaces = jObject.getJSONArray("predictions");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getPlaces with the array of json object
         * where each json object represent a place
         */
        return getPlaces(jPlaces);
    }


    private static List<HashMap<String, String>> getPlaces(JSONArray jPlaces){
        int placesCount = jPlaces.length();
        List<HashMap<String, String>> placesList = new ArrayList<HashMap<String,String>>();
        HashMap<String, String> place = null;

        /** Taking each place, parses and adds to list object */
        for(int i=0; i<placesCount;i++){
            try {
                /** Call getPlace with place JSON object to parse the place */
                place = getPlace((JSONObject)jPlaces.get(i));
                placesList.add(place);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return placesList;
    }

    /** Parsing the Place JSON object */
    private static HashMap<String, String> getPlace(JSONObject jPlace){

        HashMap<String, String> place = new HashMap<String, String>();

        String id="";
        String reference="";
        String description="";

        try {

            description = jPlace.getString("description");
            id = jPlace.getString("place_id");
            reference = jPlace.getString("reference");

            place.put("description", description);
            place.put("_id",id);
            place.put("reference",reference);
            String[] parts = description.split(",");
            place.put("place",parts[0]);
            place.put("location", description.replace(parts[0]+", ",""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return place;
    }
}
