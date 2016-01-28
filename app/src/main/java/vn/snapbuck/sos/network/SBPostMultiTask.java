package vn.snapbuck.sos.network;

import org.json.JSONObject;

/**
 * Created by sb4 on 4/24/15.
 */
public class SBPostMultiTask extends SBNetworkBaseTask {


    @Override
    public SBNetworkResponse getSBNetworkResult() {
        String queryURL = "";
        JSONObject postData = new JSONObject();
        JSONObject filePost = new JSONObject();
        prepareData(queryURL, postData, filePost);
        SBNetworkResponse networkResponse = POSTMULTI();
        return networkResponse;
    }
}
