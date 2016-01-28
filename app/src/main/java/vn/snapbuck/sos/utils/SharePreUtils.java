package vn.snapbuck.sos.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;


/**
 * Created by sb4 on 4/20/15.
 */
public class SharePreUtils{

    public static void saveStringToPreference(Context context, String key, String value) {
        removePreference(context, key);
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor prefsEditor = sharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }
    public static void saveRegistrationIDToPreference(Context context, String keyRegId, String keyAppVersion, String regId){
        SharedPreferences prefs=PreferenceManager
                .getDefaultSharedPreferences(context);
        int appVersion=CommonUtils.getAppVersionCode(context);
        Editor editor=prefs.edit();
        editor.putString(keyRegId,regId);
        editor.putInt(keyAppVersion, appVersion);
        editor.commit();
    }

    public static String getRegistrationIDFromPreference(Context context, String keyRegId, String keyAppVersion){
        SharedPreferences prefs=PreferenceManager
                .getDefaultSharedPreferences(context);
        String registrationId=prefs.getString(keyRegId, "");
        if(registrationId.isEmpty()){
            return "";
        }

        int registeredVersion= prefs.getInt(keyAppVersion,Integer.MIN_VALUE);
        int currentVersion = CommonUtils.getAppVersionCode(context);
        if(registeredVersion!= currentVersion) {
            return  "";
        }
        return  registrationId;
    }

    public static void saveBooleanToPreferencse(Context context, String key, boolean value) {
        removePreference(context,key);
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor prefsEditor = sharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public static String getStringPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        String outputString = sharedPreferences.getString(key, "");
        return outputString;
    }

    public static boolean getBooleanPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        boolean outputString = sharedPreferences.getBoolean(key, false);
        return outputString;
    }

    public static int getIntPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        int outputString = sharedPreferences.getInt(key, 0);
        return outputString;
    }

    public static void saveIntToPreference(Context context, String key, int value) {
        removePreference(context, key);
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor prefsEditor = sharedPref.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    public static void removePreference(Context context, String key) {
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor prefsEditor = sharedPref.edit();
        prefsEditor.remove(key);
        prefsEditor.commit();
    }

    public static boolean saveArray(Context context, ArrayList<String>data)
    {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor mEdit1 = sp.edit();
        mEdit1.putInt("Status_size", data.size()); /* sKey is an array */

        for(int i=0;i<data.size();i++)
        {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, data.get(i));
        }

        return mEdit1.commit();
    }
    public static ArrayList<String> loadArray(Context mContext)
    {
        ArrayList<String>result = new ArrayList<>();
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(mContext);
        int size = mSharedPreference1.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            result.add(mSharedPreference1.getString("Status_" + i, null));
        }
        return result;
    }

    public static void removeArray(Context context){
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor mEdit = sp.edit();
        int size = sp.getInt("Status_size", 0);


        for (int i=0;i<size;i++)
        {
            mEdit.remove("Status_" + i);
        }

       mEdit.commit();
    }
}
