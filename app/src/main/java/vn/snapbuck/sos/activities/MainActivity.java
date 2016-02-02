package vn.snapbuck.sos.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import vn.snapbuck.sos.R;
import vn.snapbuck.sos.adapter.FragmentAdapter;
import vn.snapbuck.sos.app.BaseActivity;
import vn.snapbuck.sos.fragment.CrimeMapFragment;
import vn.snapbuck.sos.fragment.NotifyFragment;
import vn.snapbuck.sos.fragment.ProfileFragment;
import vn.snapbuck.sos.utils.CommonUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private ViewPager viewPagerMain;
    private FragmentAdapter fmAdapter;
    private ImageView imgMap,imgProfile,imgNotify;
    private TextView tvMap, tvProfile, tvNotify;
    private LinearLayout llMap, llProfile, llNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initDrawer();
        initView();
        initListener();
        highlightView(R.id.img_map);

    }

    private void initView(){
        llMap = (LinearLayout)findViewById(R.id.ll_map);
        llProfile = (LinearLayout)findViewById(R.id.ll_profile);
        llNotify = (LinearLayout)findViewById(R.id.ll_notify);
        viewPagerMain = (ViewPager)findViewById(R.id.view_pager_main);
        viewPagerMain.setOffscreenPageLimit(3);
        imgMap = (ImageView) findViewById(R.id.img_map);
        imgNotify = (ImageView) findViewById(R.id.img_notify);
        imgProfile = (ImageView) findViewById(R.id.img_profile);
        tvMap = (TextView) findViewById(R.id.tv_map);
        tvProfile = (TextView) findViewById(R.id.tv_profile);
        tvNotify = (TextView) findViewById(R.id.tv_notify);
        findViewById(R.id.ll_map).setOnClickListener(this);
        findViewById(R.id.ll_profile).setOnClickListener(this);
        findViewById(R.id.ll_notify).setOnClickListener(this);
        initFragment();

    }

    private void initFragment(){
        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this,CrimeMapFragment.class.getName(),null));
        fragments.add(Fragment.instantiate(this,ProfileFragment.class.getName(),null));
        fragments.add(Fragment.instantiate(this,NotifyFragment.class.getName(),null));
        fmAdapter  = new FragmentAdapter(super.getSupportFragmentManager(), fragments);
        viewPagerMain.setAdapter(fmAdapter);
    }

    private void initListener(){
        viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        highlightView(R.id.img_map);
                        break;
                    case 1:
                        highlightView(R.id.img_profile);
                        break;
                    case 2:
                        highlightView(R.id.img_notify);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void highlightView(int id){
        removeHighlight();
        switch (id){
            case R.id.img_map:
                imgMap.setBackgroundResource(R.drawable.ic_map_blue);
                tvMap.setTextColor(getResources().getColor(R.color.blue));
                llMap.setAlpha(1f);
                break;
            case R.id.img_notify:
                imgNotify.setBackgroundResource(R.drawable.ic_notify_blue);
                tvNotify.setTextColor(getResources().getColor(R.color.blue));
                llNotify.setAlpha(1f);
                break;
            case R.id.img_profile:
                imgProfile.setBackgroundResource(R.drawable.ic_profile_blue);
                tvProfile.setTextColor(getResources().getColor(R.color.blue));
                llProfile.setAlpha(1f);
                break;
        }


    }

    private void removeHighlight(){
        imgMap.setBackgroundResource(R.drawable.ic_map_black);
        imgNotify.setBackgroundResource(R.drawable.ic_noti_black);
        imgProfile.setBackgroundResource(R.drawable.ic_profile_black);
        tvMap.setTextColor(getResources().getColor(R.color.black));
        tvProfile.setTextColor(getResources().getColor(R.color.black));
        tvNotify.setTextColor(getResources().getColor(R.color.black));
        llMap.setAlpha(0.54f);
        llProfile.setAlpha(0.54f);
        llNotify.setAlpha(0.54f);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_map:
                viewPagerMain.setCurrentItem(0);
                break;
            case R.id.ll_profile:
                viewPagerMain.setCurrentItem(1);
                break;
            case R.id.ll_notify:
                viewPagerMain.setCurrentItem(2);
                break;
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}
