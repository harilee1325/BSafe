package com.harilee.bsafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VolunteerModel {

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


        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("vehicle_number")
        @Expose
        private String vehicleNumber;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("assigned")
        @Expose
        private String assigned;
        @SerializedName("identification")
        @Expose
        private String identification;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("licence_number")
        @Expose
        private String licenceNumber;
        @SerializedName("fcm_token")
        @Expose
        private String fcmToken;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVehicleNumber() {
            return vehicleNumber;
        }

        public void setVehicleNumber(String vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAssigned() {
            return assigned;
        }

        public void setAssigned(String assigned) {
            this.assigned = assigned;
        }

        public String getIdentification() {
            return identification;
        }

        public void setIdentification(String identification) {
            this.identification = identification;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLicenceNumber() {
            return licenceNumber;
        }

        public void setLicenceNumber(String licenceNumber) {
            this.licenceNumber = licenceNumber;
        }


        public String getFcmToken() {
            return fcmToken;
        }

        public void setFcmToken(String fcmToken) {
            this.fcmToken = fcmToken;
        }
    }
}
