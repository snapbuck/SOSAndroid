package vn.snapbuck.sos.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import vn.snapbuck.sos.fragment.CrimeMapFragment;
import vn.snapbuck.sos.fragment.NotifyFragment;
import vn.snapbuck.sos.fragment.ProfileFragment;

/**
 * Created by sb04 on 2/2/16.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    public static int pos = 0;

    private List<Fragment> myFragments;

    public FragmentAdapter(FragmentManager fm, List<Fragment> myFrags) {
        super(fm);
        myFragments = myFrags;
    }

    @Override
    public Fragment getItem(int position) {

        return myFragments.get(position);

    }

    @Override
    public int getCount() {

        return myFragments.size();
    }



    public static int getPos() {
        return pos;
    }

    public static void setPos(int pos) {
        FragmentAdapter.pos = pos;
    }
}