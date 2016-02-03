package vn.snapbuck.sos.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.snapbuck.sos.R;
import vn.snapbuck.sos.adapter.PlaceAutocompleteAdapter;
import vn.snapbuck.sos.app.BaseFragment;
import vn.snapbuck.sos.app.SBApp;
import vn.snapbuck.sos.model.MapModel;
import vn.snapbuck.sos.network.SBAutoCompletePlace;
import vn.snapbuck.sos.network.SBBaseTask;
import vn.snapbuck.sos.utils.CommonUtils;
import vn.snapbuck.sos.utils.SBLocationListener;
import vn.snapbuck.sos.utils.SBPlaceJSONParser;


/**
 * Created by sb04 on 2/2/16.
 */
public class CrimeMapFragment extends BaseFragment implements OnMapReadyCallback, View.OnClickListener {
    private ImageView imgLocation;
    private LinearLayout llSearch;
    public static final double DEFAULT_LATITUDE = 10.7386064;
    public static final double DEFAULT_LONGITUDE = 106.7075334;
    private EditText mAutocompleteView;
    private ListView lvMapResult;
    private PlaceAutocompleteAdapter mAdapter;
    protected GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds BOUNDS_GREATER_HCM = new LatLngBounds(
            new LatLng(10.773862, 106.628673), new LatLng(10.873862, 106.728673));
    private LinearLayout llReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_crime_map, container, false);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        // Showing status

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), requestCode);
            dialog.show();

        } else { // Google Play Services are available
            // Getting reference to the SupportMapFragment of activity_main.xml
            MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
                    .findFragmentById(R.id.fm_map);
            if (mapFragment != null)
                mapFragment.getMapAsync(this);
        }

        imgLocation = (ImageView) V.findViewById(R.id.img_location);
        mAutocompleteView = (EditText) V.findViewById(R.id.et_search);
        lvMapResult = (ListView) V.findViewById(R.id.lv_map_result);
        llSearch = (LinearLayout) V.findViewById(R.id.view_search);
        llReport = (LinearLayout) V.findViewById(R.id.ll_report);
        llReport.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundResource(R.drawable.custom_button_fab_pressed);
                        return true;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundResource(R.drawable.custom_button_fab);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        v.setBackgroundResource(R.drawable.custom_button_fab);
                        break;
                }
                return false;
            }
        });

        return V;
    }


    @Override
    public void onMapReady(final GoogleMap map) {
        imgLocation.bringToFront();
        llSearch.bringToFront();
        if (SBApp.getLatitude() == 0) {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(DEFAULT_LATITUDE,DEFAULT_LONGITUDE)));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(DEFAULT_LATITUDE,DEFAULT_LONGITUDE), 15));
        }else{
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(SBApp.getLatitude(),SBApp.getLongitude())));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(SBApp.getLatitude(),SBApp.getLongitude()), 15));
        }
        imgLocation.setOnClickListener(this);
        imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detectLocation(map);
            }
        });
//        initSearch();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hideSearch();
            }
        });


    }

    @Override
    public void onClick(View v) {

    }

    private void initSearch(){
        mAdapter = new PlaceAutocompleteAdapter(getActivity(), R.layout.custom_autocomplete,
                mGoogleApiClient, BOUNDS_GREATER_HCM, null);

        lvMapResult.setAdapter(mAdapter);
        mAutocompleteView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mAutocompleteView.getText().toString().trim().equals("")) {
                    lvMapResult.setVisibility(View.GONE);
                } else {
                    lvMapResult.setVisibility(View.VISIBLE);
                    searchAddress(mAutocompleteView.getText().toString().trim());
                    lvMapResult.bringToFront();
                }
            }


        });

    }

    private ArrayList<MapModel> searchAddress(final String query) {
        final ArrayList<MapModel> data = new ArrayList<MapModel>();
        SBAutoCompletePlace task = new SBAutoCompletePlace(query);
        task.execute();
        task.setTaskListener(new SBBaseTask.SBTaskListener() {
            @Override
            public void onTaskSucceeded(JSONObject object) throws JSONException {
                JSONObject result = new JSONObject();
                JSONArray _result = new JSONArray();
                List<HashMap<String, String>> places = null;
                try {

                    _result = object.getJSONArray("predictions");
//                    placeID = _result.getJSONObject(0).getString("place_id");
//                    // Creating ParserTask


                    places = SBPlaceJSONParser.parse(object);
                    MapModel temp = null;

                    for (int i = 0; i < 5; i++) {
                        String address, placeID, placeName, location;
                        address = places.get(i).get("description");
                        placeID = places.get(i).get("_id");
                        placeName = places.get(i).get("place");
                        location = places.get(i).get("location");
                        temp = new MapModel(placeID, placeName, location, address, true);
                        data.add(temp);
                    }


                } catch (Exception e) {
                }
                mAdapter.intData(data);

            }

            @Override
            public void onTaskFailed(JSONObject object) throws JSONException {
                JSONObject o = object;
            }

        });
        return data;
    }

    private void detectLocation(final GoogleMap map) {
        if(SBApp.getLatitude()!=0){
            map.clear();
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(SBApp.getLatitude(),SBApp.getLongitude())));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(SBApp.getLatitude(),SBApp.getLongitude()), 15));
        }
        SBApp.startLocationListener(5000);
//        SBApp.SBListenLocation listener = new SBApp.SBListenLocation() {
//            @Override
//            public boolean onListenSucceeded(boolean isLocationGet) {
//                map.clear();
//                map.addMarker(new MarkerOptions()
//                        .position(new LatLng(SBApp.getLatitude(),SBApp.getLongitude())));
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(SBApp.getLatitude(),SBApp.getLongitude()), 15));
//                return false;
//            }
//        };
//        SBApp.setLocationListener(listener);

    }

    private void hideSearch() {
        lvMapResult.setVisibility(View.VISIBLE);
        CommonUtils.hideKeyboard(getActivity());
        mAutocompleteView.setText(null);

    }
}
