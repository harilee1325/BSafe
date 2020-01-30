package com.harilee.bsafe.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.harilee.bsafe.R;
import com.harilee.bsafe.Utils.Config;
import com.harilee.bsafe.Utils.Utility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.theme_white));

        new Handler().postDelayed(() -> {

            if (Utility.getUtilityInstance().getPreference(getApplicationContext(), Config.IS_LOGIN).equalsIgnoreCase("yes")){
                startActivity(new Intent(MainActivity.this, HomeMaps.class));

            }else{
                startActivity(new Intent(MainActivity.this, Login.class));
            }

        },2000);
    }
}
