package com.harilee.bsafe;

import com.harilee.bsafe.Model.AddVolunteerModel;
import com.harilee.bsafe.Model.LoginModel;
import com.harilee.bsafe.Model.RegisterModel;
import com.harilee.bsafe.Model.SearchCabModel;

public interface PresenterInterface {

    void loginUser();

    void registerUser();

    void addVolunter();

    void searchCab();

    void handleSuccess(LoginModel loginModel);

    void handleSuccess(RegisterModel registerModel);

    void handleSuccess(AddVolunteerModel addVolunteerModel);

    void handleSuccess(SearchCabModel searchCabModel);

    void handleFailure(Throwable throwable);


}
