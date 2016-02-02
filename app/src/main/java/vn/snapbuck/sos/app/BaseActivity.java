package vn.snapbuck.sos.app;

import android.app.ProgressDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import vn.snapbuck.sos.R;
import vn.snapbuck.sos.utils.SBEnums;

/**
 * Created by sb4 on 7/22/15.
 */
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ProgressDialog progressDialog;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    public void initDrawer(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.setDrawerListener(toggle);
//        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void openMenu(){
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    public void showProgressDialog(SBEnums.WorkingStatus status) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        String message = getResources().getString(R.string.status_loading);
        switch (status) {
            case LOADING:
                message = getResources().getString(R.string.status_loading);
                break;
            case LOGGING:
                message = getResources().getString(R.string.status_logging);
                break;
            case SIGN_UP:
                message = getResources().getString(R.string.status_registering);
                break;
            case WAITING:
                message = getResources().getString(R.string.status_waiting);
                break;
            case SENDING:
                message = getResources().getString(R.string.status_sending);
                break;
            case UPDATING:
                message = getResources().getString(R.string.status_updating);
                break;
            case PROCESSING:
                message = getResources().getString(R.string.status_processing);
                break;
            case SHARING:
                message = getResources().getString(R.string.status_sharing);
                break;
        }
        progressDialog.setMessage(message);
        try {
            progressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
