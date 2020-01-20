package com.harilee.bsafe.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Utility {

    private static Utility utilityInstance;

    private Utility() {
    }

    public static synchronized Utility getUtilityInstance() {
        if (null == utilityInstance) {
            utilityInstance = new Utility();
        }
        return utilityInstance;
    }

    public void setPreference(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("bsafe", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();

    }

    public String getPreference(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("bsafe", Context.MODE_PRIVATE);
        String result = prefs.getString(key, "");
        //String a = prefs.getString(key,"");
        return result;
    }
}
