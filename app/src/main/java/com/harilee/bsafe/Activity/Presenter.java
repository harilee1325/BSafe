package com.harilee.bsafe.Activity;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.harilee.bsafe.Model.AddVolunteerModel;
import com.harilee.bsafe.Model.AssignCabModel;
import com.harilee.bsafe.Model.LoginModel;
import com.harilee.bsafe.Model.NearbyModel;
import com.harilee.bsafe.Model.PathModel;
import com.harilee.bsafe.Model.RegisterModel;
import com.harilee.bsafe.Model.RideCompleted;
import com.harilee.bsafe.Network.ApiClient;
import com.harilee.bsafe.Network.ApiInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Presenter implements PresenterInterface {


    private LoginInterface loginInterface;
    private CompositeDisposable compositeDisposable;
    private ApiInterface apiInterFace;
    public Presenter(LoginInterface loginInterface) {

        this.loginInterface = loginInterface;
        this.compositeDisposable = new CompositeDisposable();
        this.apiInterFace = ApiClient.getClient().create(ApiInterface.class);
    }


    private MapsInterface maps;
    private HomeMaps homeMaps;
    public Presenter(MapsInterface maps, HomeMaps homeMaps) {
        this.maps = maps;
        this.compositeDisposable = new CompositeDisposable();
        this.apiInterFace = ApiClient.getClient().create(ApiInterface.class);
        this.homeMaps = homeMaps;
    }

    private RegisterUserInterface registerUserInterface;
    public Presenter(RegisterUserInterface registerUserInterface) {

        this.registerUserInterface = registerUserInterface;
        this.compositeDisposable = new CompositeDisposable();
        this.apiInterFace = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void loginUser(String number) {
        Observable<LoginModel> observable = apiInterFace.loginUser(number);
        compositeDisposable.add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleFailure));
    }

    @Override
    public void handleSuccess(LoginModel loginModel) {
        loginInterface.getResponse(loginModel);
    }

    @Override
    public void registerUser(String phoneNumber, String userName, String eme1, String eme2, String eme3, String eme4, String eme5, String fcmToken) {
        Observable<RegisterModel> observable = apiInterFace.registeruser(phoneNumber, userName
        , eme1, eme2, eme3, eme4, eme5, fcmToken, "gagagagaga");
        compositeDisposable.add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleFailure));
    }

    @Override
    public void handleSuccess(RegisterModel registerModel) {
        registerUserInterface.getResponse(registerModel);
    }

    @Override
    public void addVolunter() {

    }

    @Override
    public void handleSuccess(AddVolunteerModel addVolunteerModel) {

    }

    @Override
    public void searchCab(double latitude, double longitude) {
        Observable<NearbyModel> observable = apiInterFace.searchCab(String.valueOf(latitude), String.valueOf(longitude));
        compositeDisposable.add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleFailure));
    }

    @Override
    public void handleSuccess(NearbyModel searchCabModel) {


        if (searchCabModel.getSuccess().equalsIgnoreCase("yes")) {
            maps.getResponse(searchCabModel);
            /*NearbyClass nearbyClass = new NearbyClass();
            nearbyClass.getResponse(searchCabModel);
            homeMaps.startActivity(new Intent(homeMaps, NearbyClass.class));*/
            maps.cancelLoader();
        }else{
            Log.e("Tag", "handleSuccess: "+"here");
            maps.showMessages(searchCabModel.getMessage());
        }
    }

    @Override
    public void handleFailure(Throwable throwable) {

        if (loginInterface!=null){
            loginInterface.showMessage("Could not login, Please check your credentials and try again.");
        }
        if (maps!=null){
            Log.e("Tag", "handleSuccess: 1"+throwable.getLocalizedMessage() );
            maps.showMessages("Could not find any cabs nearby");
        }
        if (registerUserInterface!=null){
            Log.e("Tag", "handleFailure: "+throwable.getLocalizedMessage() );
            registerUserInterface.showMessages(throwable.getLocalizedMessage());
        }

    }

    @Override
    public void assignCab(String adminId, String preference, String fcmToken) {
        Observable<AssignCabModel> observable = apiInterFace.assignCab(adminId, preference, fcmToken);
        compositeDisposable.add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleFailure));
    }

    @Override
    public void handleSuccess(AssignCabModel assignCabModel) {
        if (assignCabModel.getSuccess().equalsIgnoreCase("yes")) {
            maps.getResponse(assignCabModel);
        }else{
            maps.showMessages(assignCabModel.getMessage());
        }
    }

    @Override
    public void getPath(double latitude, double longitude, String preference, String preference1) {
        Observable<PathModel> observable = apiInterFace.getPath(String.valueOf(latitude), String.valueOf(longitude), preference
        , preference1);
        compositeDisposable.add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleFailure));
    }

    @Override
    public void handleSuccess(PathModel pathModel) {
        maps.getResponse(pathModel);
    }

    @Override
    public void rideCompleted(String preference, String preference1) {

    }

    @Override
    public void handleSuccess(RideCompleted rideCompleted) {

    }

    protected interface LoginInterface {

        void callApi(String number);

        void getResponse(LoginModel loginModel);

        void showMessage(String message);
    }

    protected interface MapsInterface {

        void showMessages(String message);

        void cancelLoader();

        void getResponse(NearbyModel nearbyModel);

        void getResponse(AssignCabModel assignCabModel);

        void getResponse(PathModel pathModel);

        void getResponse(RideCompleted rideCompleted);
    }

    protected  interface RegisterUserInterface {

        void showMessages(String message);

        void getResponse(RegisterModel registerModel);
    }
}
