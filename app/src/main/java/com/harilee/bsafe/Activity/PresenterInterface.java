package com.harilee.bsafe.Activity;

import com.harilee.bsafe.Model.AddVolunteerModel;
import com.harilee.bsafe.Model.AssignCabModel;
import com.harilee.bsafe.Model.LoginModel;
import com.harilee.bsafe.Model.NearbyModel;
import com.harilee.bsafe.Model.PathModel;
import com.harilee.bsafe.Model.ProfileModel;
import com.harilee.bsafe.Model.RegisterModel;
import com.harilee.bsafe.Model.RideCompleted;
import com.harilee.bsafe.Model.VolunteerModel;

public interface PresenterInterface {

    void loginUser(String number);

    void registerUser(String phoneNumber, String userName, String eme1, String eme2, String eme3, String eme4, String eme5, String fcmToken);

    void addVolunter(String vehicleNumStr, String licenceNumStr, String idNumStr, String name, String fcm, String lat, String lng);

    void searchCab(double latitude, double longitude);

    void handleSuccess(LoginModel loginModel);

    void handleSuccess(RegisterModel registerModel);

    void handleSuccess(AddVolunteerModel addVolunteerModel);

    void handleSuccess(NearbyModel searchCabModel);

    void handleFailure(Throwable throwable);

    void assignCab(String adminId, String preference, String fcmToken);

    void handleSuccess(AssignCabModel assignCabModel);

    void getPath(double latitude, double longitude, String preference, String preference1);

    void handleSuccess(PathModel pathModel);

    void rideCompleted(String preference, String preference1);

    void handleSuccess(RideCompleted rideCompleted);

    void getProfileData(String number);

    void handleSuccess(ProfileModel profileModel);

    void getVolunteerData(String name);

    void handleSuccess(VolunteerModel volunteerModel);

}
