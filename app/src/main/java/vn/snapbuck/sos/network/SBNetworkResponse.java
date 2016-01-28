package vn.snapbuck.sos.network;

import org.json.JSONObject;

/**
 * Created by sb4 on 4/20/15.
 */
public class SBNetworkResponse {
    public int errorCode;
    public JSONObject result;
    public String errorMessage;

    public boolean success;

    public SBNetworkResponse()
    {
        errorCode = -1;
        success = false;
    }
}
