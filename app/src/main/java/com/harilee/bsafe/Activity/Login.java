package com.harilee.bsafe.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.harilee.bsafe.Model.LoginModel;
import com.harilee.bsafe.Activity.Presenter.LoginInterface;
import com.harilee.bsafe.R;
import com.harilee.bsafe.Utils.Config;
import com.harilee.bsafe.Utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity implements LoginInterface {


    @BindView(R.id.number_et)
    EditText numberEt;
    @BindView(R.id.login_bt)
    Button loginBt;
    private Presenter presenter;
    private Dialog dialog;
    private String number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.theme_white));
        ButterKnife.bind(this);
        dialog = new Dialog(this);
        presenter = new Presenter(this);
    }

    @Override
    public void callApi(String number) {
        Utility.showGifPopup(this, true, dialog);
        presenter.loginUser(number);
    }

    @Override
    public void getResponse(LoginModel loginModel) {
        Utility.showGifPopup(this, false, dialog);
        if (loginModel.getSuccess().equalsIgnoreCase("yes")){
            Utility.getUtilityInstance().setPreference(this, Config.IS_LOGIN, "yes");
            Utility.getUtilityInstance().setPreference(this, Config.NUM, number);
            startActivity(new Intent(Login.this, HomeMaps.class));
        }else {
            showMessage(loginModel.getMessage());
        }
    }

    @Override
    public void showMessage(String message) {
        Utility.showGifPopup(this, false, dialog);
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.login_bt)
    public void onViewClicked() {

        number = numberEt.getText().toString().trim();
        if (number.isEmpty()){
            numberEt.setError("Please enter mobile number");
        }else{
            callApi(number);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
    }
}
