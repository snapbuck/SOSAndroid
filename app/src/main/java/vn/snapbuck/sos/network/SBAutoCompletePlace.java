package vn.snapbuck.sos.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import vn.snapbuck.sos.R;
import vn.snapbuck.sos.app.SBApp;

/**
 * Created by sb6 on 6/11/15.
 */
public class SBAutoCompletePlace extends SBNetworkBaseTask {
    private String _query ;
    private String API_KEY = SBApp.getSBAppContext().getResources().getString(R.string.google_map_key);
    public SBAutoCompletePlace(String query){
        try {
            _query = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public SBNetworkResponse getSBNetworkResult() {
        String queryURL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+_query+"&key="+API_KEY;
        prepareData(queryURL);
        SBNetworkResponse networkResponse = getGoogleResponse();
        return networkResponse;
    }
}
