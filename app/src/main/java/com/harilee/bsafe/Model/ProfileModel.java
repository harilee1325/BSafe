package com.harilee.bsafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private ResultData ResultData;
    @SerializedName("success")
    @Expose
    private String success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultData getResultData() {
        return ResultData;
    }

    public void setResultData(ResultData ResultData) {
        this.ResultData = ResultData;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public class ResultData {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("number")
        @Expose
        private String number;
        @SerializedName("emergency_contact4")
        @Expose
        private String emergencyContact4;
        @SerializedName("emergency_contact5")
        @Expose
        private String emergencyContact5;
        @SerializedName("identification")
        @Expose
        private String identification;
        @SerializedName("emergency_contact1")
        @Expose
        private String emergencyContact1;
        @SerializedName("emergency_contact2")
        @Expose
        private String emergencyContact2;
        @SerializedName("emergency_contact3")
        @Expose
        private String emergencyContact3;
        @SerializedName("is_vol")
        @Expose
        private String isVol;

        @SerializedName("fcm_token")
        @Expose
        private String fcmToken;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getEmergencyContact4() {
            return emergencyContact4;
        }

        public void setEmergencyContact4(String emergencyContact4) {
            this.emergencyContact4 = emergencyContact4;
        }

        public String getEmergencyContact5() {
            return emergencyContact5;
        }

        public void setEmergencyContact5(String emergencyContact5) {
            this.emergencyContact5 = emergencyContact5;
        }

        public String getIdentification() {
            return identification;
        }

        public void setIdentification(String identification) {
            this.identification = identification;
        }

        public String getEmergencyContact1() {
            return emergencyContact1;
        }

        public void setEmergencyContact1(String emergencyContact1) {
            this.emergencyContact1 = emergencyContact1;
        }

        public String getEmergencyContact2() {
            return emergencyContact2;
        }

        public void setEmergencyContact2(String emergencyContact2) {
            this.emergencyContact2 = emergencyContact2;
        }

        public String getEmergencyContact3() {
            return emergencyContact3;
        }

        public void setEmergencyContact3(String emergencyContact3) {
            this.emergencyContact3 = emergencyContact3;
        }

        public String getIsVol() {
            return isVol;
        }

        public void setIsVol(String isVol) {
            this.isVol = isVol;
        }


        public String getFcmToken() {
            return fcmToken;
        }

        public void setFcmToken(String fcmToken) {
            this.fcmToken = fcmToken;
        }
    }
}
