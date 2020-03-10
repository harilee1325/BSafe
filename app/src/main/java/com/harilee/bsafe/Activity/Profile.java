package com.harilee.bsafe.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.harilee.bsafe.Model.ProfileModel;
import com.harilee.bsafe.Model.VolunteerModel;
import com.harilee.bsafe.R;
import com.harilee.bsafe.Utils.Config;
import com.harilee.bsafe.Utils.Utility;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Profile extends AppCompatActivity implements Presenter.ProfileInterface {


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
    @BindView(R.id.vol_name)
    TextView volName;
    @BindView(R.id.vol_id)
    TextView volId;
    @BindView(R.id.vol_dv)
    TextView volDv;
    private Presenter presenter;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ButterKnife.bind(this);
        dialog = new Dialog(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        presenter = new Presenter(this);
        userName.setText(Utility.getUtilityInstance().getPreference(this, Config.NAME));
        mobileNumber.setText(Utility.getUtilityInstance().getPreference(this, Config.NUM));
        Utility.showGifPopup(this, true, dialog);
        presenter.getProfileData(mobileNumber.getText().toString().trim());

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

    @Override
    public void showMessages(String message) {
        Utility.showGifPopup(this, false, dialog);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getResponse(ProfileModel profileModel) {
        Utility.showGifPopup(this, false, dialog);

        if (profileModel.getResultData().getIsVol().equalsIgnoreCase("0")) {
            addVolunteer.setVisibility(View.VISIBLE);
        } else {
            presenter.getVolunteerData(profileModel.getResultData().getName());
            addVolunteer.setVisibility(View.GONE);

        }
    }

    @Override
    public void getResponse(VolunteerModel volunteerModel) {
        Utility.showGifPopup(this, false, dialog);

        afterV.setVisibility(View.VISIBLE);
        volName.setText(volunteerModel.getResultData().getName());
        volId.setText(volunteerModel.getResultData().getIdentification());
        volDv.setText(volunteerModel.getResultData().getLicenceNumber());
    }

}
