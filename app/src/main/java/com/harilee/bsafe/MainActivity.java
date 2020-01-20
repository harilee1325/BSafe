package com.harilee.bsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.harilee.bsafe.Utils.Config;
import com.harilee.bsafe.Utils.Utility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (Utility.getUtilityInstance().getPreference(getApplicationContext(), Config.IS_LOGIN).equalsIgnoreCase("yes")){
                    startActivity(new Intent(MainActivity.this, HomeMaps.class));

                }else{
                    startActivity(new Intent(MainActivity.this, HomeMaps.class));
                }

            }
        },2000);
    }
}
