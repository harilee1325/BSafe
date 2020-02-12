package com.harilee.bsafe.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.harilee.bsafe.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddVolunteer extends AppCompatActivity {

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ButterKnife.bind(this);
    }



    @OnClick(R.id.register_bt)
    public void onViewClicked() {
    }
}
