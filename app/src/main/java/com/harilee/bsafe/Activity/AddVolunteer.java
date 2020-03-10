package com.harilee.bsafe.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.harilee.bsafe.Model.AddVolunteerModel;
import com.harilee.bsafe.R;
import com.harilee.bsafe.Utils.Config;
import com.harilee.bsafe.Utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddVolunteer extends AppCompatActivity implements Presenter.AddVolunteerInterface {

    @BindView(R.id.username)
    TextInputEditText username;
    @BindView(R.id.vehicle_num)
    TextInputEditText vehicleNum;
    @BindView(R.id.licence_num)
    TextInputEditText licenceNum;
    @BindView(R.id.id_num)
    TextInputEditText idNum;
    @BindView(R.id.register_bt)
    MaterialButton registerBt;
    private View view;
    private Presenter presenter;
    private Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ButterKnife.bind(this);
         dialog = new Dialog(this);
        presenter = new Presenter(this);
    }



    @OnClick(R.id.register_bt)
    public void onViewClicked() {

        String vehicleNumStr = vehicleNum.getText().toString().trim();
        String licenceNumStr = licenceNum.getText().toString().trim();
        String idNumStr = idNum.getText().toString().trim();
        Utility.showGifPopup(this, true, dialog);
        presenter.addVolunter(vehicleNumStr, licenceNumStr, idNumStr
                , Utility.getUtilityInstance().getPreference(this, Config.NAME)
                , Utility.getUtilityInstance().getPreference(this, Config.FCM)
                , Utility.getUtilityInstance().getPreference(this, Config.LAT)
                , Utility.getUtilityInstance().getPreference(this, Config.LNG));


    }

    @Override
    public void showMessages(String message) {
        Utility.showGifPopup(this, false, dialog);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getResponse(AddVolunteerModel addVolunteerModel) {
        Utility.showGifPopup(this, false, dialog);
        showMessages(addVolunteerModel.getMessage());
        startActivity(new Intent(this, Profile.class));
    }
}
