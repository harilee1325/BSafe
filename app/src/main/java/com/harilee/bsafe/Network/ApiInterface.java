package com.harilee.bsafe.Network;

import com.harilee.bsafe.Model.AssignCabModel;
import com.harilee.bsafe.Model.LoginModel;
import com.harilee.bsafe.Model.NearbyModel;
import com.harilee.bsafe.Model.PathModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
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
}
