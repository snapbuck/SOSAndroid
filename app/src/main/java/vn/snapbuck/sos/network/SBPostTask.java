package vn.snapbuck.sos.network;

import org.json.JSONObject;


/**
 * Created by sb3 on 6/8/15.
 */
public class SBPostTask extends SBNetworkBaseTask {

    @Override
    public SBNetworkResponse getSBNetworkResult() {
        JSONObject postData = new JSONObject();
        String url = "";
        prepareData(url, postData);
        SBNetworkResponse networkResponse = POST();
        return networkResponse;
    }
}
