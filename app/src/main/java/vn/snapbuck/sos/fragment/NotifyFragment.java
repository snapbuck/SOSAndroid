package vn.snapbuck.sos.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.snapbuck.sos.R;
import vn.snapbuck.sos.app.BaseFragment;

/**
 * Created by sb04 on 2/2/16.
 */
public class NotifyFragment extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_notify, container, false);
        return V;
    }
}
