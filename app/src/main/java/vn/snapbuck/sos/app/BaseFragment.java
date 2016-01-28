package vn.snapbuck.sos.app;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import vn.snapbuck.sos.R;
import vn.snapbuck.sos.utils.SBEnums;


/**
 * Created by sb4 on 10/7/15.
 */
public class BaseFragment extends Fragment {
    ProgressDialog progressDialog;

    public void showProgressDialog(SBEnums.WorkingStatus status) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }

        try {
            progressDialog = new ProgressDialog(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (progressDialog != null) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
