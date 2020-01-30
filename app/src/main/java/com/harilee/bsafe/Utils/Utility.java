package com.harilee.bsafe.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;

import com.harilee.bsafe.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Objects;

import me.ibrahimsn.lib.CirclesLoadingView;

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


    public static void showGifPopup(final Context mContext, boolean show, Dialog dialog) {

        dialog.setContentView(R.layout.progress_bar);
        dialog.setCancelable(false);
        CirclesLoadingView imageView = dialog.findViewById(R.id.loader);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        if (!((Activity) mContext).isFinishing()) {
            try {
                if (show) {
                    dialog.show();
                } else {
                    dialog.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
