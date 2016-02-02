package vn.snapbuck.sos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.maps.model.LatLngBounds;


import java.util.ArrayList;

import vn.snapbuck.sos.R;
import vn.snapbuck.sos.model.MapModel;


public class PlaceAutocompleteAdapter
        extends ArrayAdapter<MapModel> {
    ArrayList<MapModel> places = new ArrayList<>();

    private static final String TAG = "PlaceAutocompleteAdapter";
    /**
     * Current results returned by this adapter.
     */
    /**
     * Handles autocomplete requests.
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * The bounds used for Places Geo Data autocomplete API requests.
     */
    private LatLngBounds mBounds;

    /**
     * The autocomplete filter used to restrict queries to a specific set of place types.
     */
    private AutocompleteFilter mPlaceFilter;

    /**
     * Initializes with a resource for text rows and autocomplete query bounds.
     *
     * @see ArrayAdapter#ArrayAdapter(Context, int)
     */
    public PlaceAutocompleteAdapter(Context context, int resource, GoogleApiClient googleApiClient,
                                    LatLngBounds bounds, AutocompleteFilter filter) {
        super(context, resource);
        mGoogleApiClient = googleApiClient;
        mBounds = bounds;
        mPlaceFilter = filter;
    }

    public void intData(ArrayList<MapModel> data){
        this.places.clear();
        this.places = data;
        notifyDataSetChanged();
    }

    /**
     * Sets the bounds for all subsequent queries.
     */
    public void setBounds(LatLngBounds bounds) {
        mBounds = bounds;
    }

    /**
     * Returns the number of results received in the last autocomplete query.
     */
    @Override
    public int getCount() {
        return places.size();
    }

    /**
     * Returns an item from the last autocomplete query.
     */
    @Override
    public MapModel getItem(int position) {
        return places.get(position);
    }




    class Holder {
        TextView tvPlace;
        TextView tvAddress;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Holder viewHolder;
        final MapModel item = getItem(position);
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            row = inflater.inflate(R.layout.view_map_item, parent, false);

            viewHolder = new Holder();
            viewHolder.tvPlace = (TextView) row.findViewById(R.id.tv_map_place);
            viewHolder.tvAddress = (TextView) row.findViewById(R.id.tv_map_address);

            row.setTag(viewHolder);
        } else {
            viewHolder = (Holder)row.getTag();
        }

        viewHolder.tvAddress.setText(item.getLocation());
        viewHolder.tvPlace.setText(item.getPlaceName());
        return row;
    }
}
