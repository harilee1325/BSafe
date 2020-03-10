package com.harilee.bsafe.Network;

import com.harilee.bsafe.Model.AddVolunteerModel;
import com.harilee.bsafe.Model.AssignCabModel;
import com.harilee.bsafe.Model.LoginModel;
import com.harilee.bsafe.Model.NearbyModel;
import com.harilee.bsafe.Model.PathModel;
import com.harilee.bsafe.Model.ProfileModel;
import com.harilee.bsafe.Model.RegisterModel;
import com.harilee.bsafe.Model.VolunteerModel;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("login/{number}")
    Observable<LoginModel> loginUser(@Path("number") String number);


    @GET("get-police-cab/{lat}/{lng}")
    Observable<NearbyModel> searchCab(@Path("lat") String valueOf,@Path("lng")  String valueOf1);

    @GET("assign-cab/{adminId}/{number}/{fcm}")
    Observable<AssignCabModel> assignCab(@Path("adminId") String adminId, @Path("number") String preference
            ,@Path("fcm") String fcmToken);

    @GET("get-path/{user_lat}/{user_lng}/{lat}/{lng}")
    Observable<PathModel> getPath(@Path("user_lat") String valueOf,@Path("user_lng") String valueOf1
            ,@Path("lat") String preference,@Path("lng") String preference1);

    @POST("sign-up")
    @FormUrlEncoded
    Observable<RegisterModel> registeruser(@Field("number") String phoneNumber,@Field("name") String userName
            ,@Field("emergency_contact1") String eme1,@Field("emergency_contact2")  String eme2
            ,@Field("emergency_contact3")  String eme3,@Field("emergency_contact4")  String eme4
            ,@Field("emergency_contact5")  String eme5,@Field("fcm_token")  String fcmToken
            ,@Field("identification") String identification, @Field("is_vol") String isVol);

    @POST("add-volunteer")
    @FormUrlEncoded
    Observable<AddVolunteerModel> addVolunteer(@Field("vehicle_number") String vehicleNumStr
            ,@Field("licence_number") String licenceNumStr,@Field("identification") String idNumStr
            ,@Field("name") String name,@Field("fcm_token") String fcm,@Field("latitude") String lat
            ,@Field("longitude") String lng, @Field("status")String status, @Field("assigned") String assigned);

    @GET("get-profile/{number}")
    Observable<ProfileModel> getProfile(@Path("number") String number);

    @GET("get-volunteer-data/{name}")
    Observable<VolunteerModel> getVolunteer(@Path("name") String name);
}
