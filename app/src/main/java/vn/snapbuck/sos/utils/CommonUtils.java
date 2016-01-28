package vn.snapbuck.sos.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import vn.snapbuck.sos.security.AESEncryptDecrypt;


/**
 * Created by sb4 on 4/20/15.
 */
public class CommonUtils {

    public static String getAppVersion(Context context) {
        String versionName = "";
        try {
            versionName = context
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            0).versionName;
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
        return versionName;
    }

    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getAccessTokenSercu(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String outputString = sharedPreferences.getString("accessToken", "");
        String getResultString = "";
        byte[] decrypted = null;
        if (!outputString.equals("")) {
            byte[] byteAccessToken = Base64
                    .decode(outputString, Base64.NO_WRAP);
            try {
                AESEncryptDecrypt encrypter = new AESEncryptDecrypt(SBConstant.KEY);
                decrypted = encrypter.decrypt(byteAccessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (decrypted != null) {
                getResultString = Base64.encodeToString(decrypted,
                        Base64.NO_WRAP);
            }

        }
        return getResultString;
    }

    public static String getClientAppID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String outputString = sharedPreferences.getString("clientAppID", "");
        String getResultString = "";
        byte[] decrypted = null;
        if (!outputString.equals("")) {
            byte[] byteAccessToken = Base64
                    .decode(outputString, Base64.NO_WRAP);
            try {
                AESEncryptDecrypt encrypter = new AESEncryptDecrypt(SBConstant.KEY);
                decrypted = encrypter.decrypt(byteAccessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (decrypted != null) {
                getResultString = Base64.encodeToString(decrypted,
                        Base64.NO_WRAP);
            }

        }
        return getResultString;
    }


    public static String ranDom() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(SBConstant.MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String getAccessToken(JSONObject jsonObject) {
        JSONObject jsonBody = null;
        try {
            jsonBody = jsonObject.getJSONObject("body");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonBody != null) {
            JSONObject jsonData = null;
            try {
                jsonData = jsonBody.getJSONObject("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonData != null) {
                String accessToken = "";
                try {
                    accessToken = jsonData.getString("accessToken");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (accessToken != "") {
                    return accessToken;
                }
            }
        }
        return "";
    }

    public static File getOutputMediaFileDownload(Context context, String fileName) {
        ContextWrapper applicationDir = new ContextWrapper(context);
        File mediaStorageDir = new File(applicationDir.getFilesDir()
                .getAbsolutePath(), " .SnapbuckImages");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + fileName);
        return mediaFile;
    }

    public static File getOutputMediaFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .getAbsolutePath()
                + File.separator
                + "Camera"
                + File.separator
                + timeStamp + ".jpg");
        return mediaFile;
    }

    public static boolean dir_exists(String dir_path)
    {
        boolean ret = false;
        File dir = new File(dir_path);
        if(dir.exists() && dir.isDirectory())
            ret = true;
        return ret;
    }

    public static File getOutputMediaFileVoice() {
        String dir_path = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .getAbsolutePath() +File.separator  + "VoiceVN"+ File.separator;
        if (!dir_exists(dir_path)){
            File directory = new File(dir_path);
            directory.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .getAbsolutePath()
                + File.separator
                + "VoiceVN"
                + File.separator
                + timeStamp + ".jpg");
        return mediaFile;
    }

    public static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[SBConstant.BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    public static JSONObject toJSON (Bundle bundle){
//        JSONObject jsonObject= new JSONObject();
//        Set<String> keys= bundle.keySet();
//        for(String key : keys){
//            try{
//                jsonObject.put(key,JSONObject.wrap(bundle.get(key)));
//            } catch (JSONException e){
//
//            }
//        }
//        return jsonObject;
//    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    private String ShowDistance(double input) {
        try {
            double km = Math.floor(input / 1000);
            double m = Math.round(input % 1000);
            String output = "";
            if (km > 0) {
                output = km + "km ";
            }
            output += m + "m";
            return output;

        } catch (Exception e) {
            return "N/A";
        }
    }


    public static void clearButtonEffect(ImageButton btn) {
        btn.getBackground().clearColorFilter();
        btn.invalidate();
    }

    public static void setBackground(View v, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(drawable);
        } else {
            v.setBackgroundDrawable(drawable);
        }
    }


    //On off keyboard
    public static void hideKeyboard(Activity mActivity) {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Activity mActivity) {
        InputMethodManager imm = (InputMethodManager)
                mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }



    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371; // kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
                * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c * 1000;
        return dist;
    }

}
