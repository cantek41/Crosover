package cantekin.crosover;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dal.entities.UserEntity;

/**
 * preferences e erişmek
 * genel ayarları
 * kaydetmek ve okumak için
 * Created by Cantekin on 11.01.2016.
 */

public final class MyPreference {
  private static String TAG = "Preference";
  public static long getCurrentUser(Context context) {
    SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
    return data.getLong("userId", 0);
  }
  public static String getCurrentUserName(Context context) {
    SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
    return  data.getString("userName", "");
  }
  public static void setCurrentUser(Context context, UserEntity user) {
    SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = data.edit();
    editor.putLong("userId", user.getId());
    editor.putString("userName", user.getName());
    editor.commit();
  }
  public static void setCurrentUser(Context context) {
    SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = data.edit();
    editor.putLong("userId", 0);
    editor.putString("userName", "");
    editor.commit();
  }
    /*
    public static void createpreferences(Context context, SettingsModel userData) {
        if (userData != null) {
            Log.i(TAG, "SharedPreferences oluşuyor");
            SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = data.edit();
            if (userData.getUserId() != null)
                editor.putString("userId", userData.getUserId());
            if (userData.getUserMail() != null)
                editor.putString("mail", userData.getUserMail());
            if (userData.getWebAddres() != null)
                editor.putString("webAddress", userData.getWebAddres());
            if (userData.getWebMethod() != null)
                editor.putString("webMethod", userData.getWebMethod());

        }
    }

    public static SettingsModel getProfileInfo(Context context) {
        SettingsModel result = new SettingsModel();
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
        result.setUserId(data.getString("userId", "36"));
        result.setUserMail(data.getString("mail", ""));
        result.setWebAddres(data.getString("webAddress", "http://demo.veribiscrm.com/"));
        result.setWebMethod(data.getString("webMethod", "api/service/SetData"));
        return result;
    }

    public static void deletePreferences(Context context) {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
        data.edit().clear().commit();
    }   */
}
