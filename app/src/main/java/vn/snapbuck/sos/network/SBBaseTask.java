package vn.snapbuck.sos.network;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import vn.snapbuck.sos.app.SBApp;
import vn.snapbuck.sos.utils.SBNetworkUtils;


/**
 * Created by sb4 on 4/20/15.
 */
public abstract class SBBaseTask extends AsyncTask<JSONObject, JSONObject, JSONObject> {
    protected SBTaskListener _listener;
    protected boolean _isFail;

    public void setTaskListener(SBTaskListener listener) {
        _listener = listener;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected JSONObject doInBackground(JSONObject... objects) {
        if (!SBNetworkUtils.hasInternetConnection(SBApp.getSBAppContext())) {
            _isFail = true;
            JSONObject error = new JSONObject();
            try {
                error.put("statusCode", -100);
                error.put("message", "No internet connection");
            } catch (Exception e) {

            }
            return error;
        }

        JSONObject result = getResult();
        if (result == null) {
            _isFail = true;
        }
        return result;
    }

    public abstract JSONObject getResult();

    @Override
    protected void onPostExecute(JSONObject object) {
        if (_listener != null) {
            if (_isFail) {
                try {
                    _listener.onTaskFailed(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    _listener.onTaskSucceeded(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface SBTaskListener {

        void onTaskSucceeded(JSONObject object) throws JSONException;

        void onTaskFailed(JSONObject object) throws JSONException;
    }
}