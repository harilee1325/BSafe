package com.harilee.bsafe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.harilee.bsafe.R;
import com.harilee.bsafe.Utils.Config;
import com.harilee.bsafe.Utils.Utility;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Profile extends AppCompatActivity {


    @BindView(R.id.pic_user)
    CircularImageView picUser;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.mobile_number)
    TextView mobileNumber;
    @BindView(R.id.add_volunteer)
    Button addVolunteer;
    @BindView(R.id.before_v)
    LinearLayout beforeV;
    @BindView(R.id.after_v)
    CardView afterV;
    @BindView(R.id.logout)
    Button logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @OnClick({R.id.add_volunteer, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_volunteer:
                startActivity(new Intent(Profile.this, AddVolunteer.class));
                break;
            case R.id.logout:
                Utility.getUtilityInstance().setPreference(this, Config.IS_LOGIN, "no");
                startActivity(new Intent(Profile.this, Login.class));
                break;
        }
    }
}
