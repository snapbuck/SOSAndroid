package vn.snapbuck.sos.network;

/**
 * Created by sb4 on 4/24/15.
 */
public class SBGetTask extends SBNetworkBaseTask {

    String _serverURL;

    public SBGetTask(String serverUrl) {
        _serverURL = serverUrl;
    }

    @Override
    public SBNetworkResponse getSBNetworkResult() {
        prepareData(_serverURL);
        return GET();
    }
}
