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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        new Handler().postDelayed(() -> {

            if (Utility.getUtilityInstance().getPreference(getApplicationContext()
                    , Config.IS_LOGIN).equalsIgnoreCase("yes")){
                startActivity(new Intent(MainActivity.this, HomeMaps.class));

            }else{
                startActivity(new Intent(MainActivity.this, Login.class));
            }

        },2000);
    }
}
