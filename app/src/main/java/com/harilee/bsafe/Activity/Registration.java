package com.harilee.bsafe.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.harilee.bsafe.Model.RegisterModel;
import com.harilee.bsafe.R;
import com.harilee.bsafe.Utils.Config;
import com.harilee.bsafe.Utils.Utility;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Registration extends AppCompatActivity implements Presenter.RegisterUserInterface {


    @BindView(R.id.username)
    TextInputEditText username;
    @BindView(R.id.phone)
    TextInputEditText phone;
    @BindView(R.id.eme_num_1)
    TextInputEditText emeNum1;
    @BindView(R.id.eme_num_2)
    TextInputEditText emeNum2;
    @BindView(R.id.eme_num_3)
    TextInputEditText emeNum3;
    @BindView(R.id.eme_num_4)
    TextInputEditText emeNum4;
    @BindView(R.id.eme_num_5)
    TextInputEditText emeNum5;
    @BindView(R.id.register_bt)
    MaterialButton registerBt;
    private String phoneNum, userName, eme1, eme2, eme3, eme4, eme5;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private Presenter presenter;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        presenter = new Presenter(this);
        mAuth = FirebaseAuth.getInstance();
        dialog = new Dialog(this);
    }

    private void getDetails() {

        phoneNum = "+91 "+phone.getText().toString().trim();
        userName = username.getText().toString().trim();
        eme1 = emeNum1.getText().toString().trim();
        eme2 = emeNum2.getText().toString().trim();
        eme3 = emeNum3.getText().toString().trim();
        eme4 = emeNum4.getText().toString().trim();
        eme5 = emeNum5.getText().toString().trim();
        generateOtp();


    }

    @OnClick(R.id.register_bt)
    public void onViewClicked() {
        getDetails();
    }

    private void generateOtp() {
        Log.e("Tag", "generateOtp: " + phoneNum);
        try {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNum,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);
        } catch (Exception e) {
            Log.e("Tag", "generateOtp: here " + e.getLocalizedMessage());
        }

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Tag", "onVerificationFailed: "+e.getLocalizedMessage() );
                showMessages(e.getLocalizedMessage());
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("tag", "onCodeSent:" + verificationId);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Registration.this);
                bottomSheetDialog.setContentView(R.layout.enter_code);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                /*int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                dialog.getWindow().setLayout((6 * width)/7, (6 * height)/7);
*/
                bottomSheetDialog.show();

                EditText code = bottomSheetDialog.findViewById(R.id.enter_code);
                Button verifyCode = bottomSheetDialog.findViewById(R.id.verify_num);

                verifyCode.setOnClickListener(v -> {
                    bottomSheetDialog.cancel();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code.getText().toString().trim());
                    signInWithPhoneAuthCredential(credential);
                });
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Tag", "signInWithCredential:success");

                        FirebaseUser user = task.getResult().getUser();
                        registerNewsUser(user.getPhoneNumber());

                        showMessages("Number verified");
                        // ...
                    } else {
                        // Sign in failed, display a message and update the UI
                        showMessages("Sign in failed please try again.");
                        Log.w("Tag", "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            showMessages("The verification code entered was invalid");
                        }
                    }
                });
    }

    private void registerNewsUser(String phoneNumber) {
        Log.e("Tag", "registerNewsUser: " + phoneNumber);
        Utility.showGifPopup(Registration.this, true, this.dialog);
        String fcmToken = Utility.getUtilityInstance().getPreference(this, Config.FCM);
        presenter.registerUser(phoneNumber.substring(3), userName, eme1, eme2, eme3, eme4, eme5, fcmToken);
    }

    @Override
    public void showMessages(String message) {
        Utility.showGifPopup(Registration.this, false, this.dialog);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getResponse(RegisterModel registerModel) {
        Utility.showGifPopup(Registration.this, false, this.dialog);
        if (registerModel.getSuccess().equalsIgnoreCase("yes")) {
            Utility.getUtilityInstance().setPreference(this, Config.NAME, userName);
            startActivity(new Intent(Registration.this, Login.class));
        }
    }
}
