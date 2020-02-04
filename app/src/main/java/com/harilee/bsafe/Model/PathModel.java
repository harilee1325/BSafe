package com.harilee.bsafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PathModel {

    @SerializedName("PathData")
    @Expose
    private PathData PathData;
    @SerializedName("success")
    @Expose
    private String success;

    public PathData getPathData() {
        return PathData;
    }

    public void setPathData(PathData PathData) {
        this.PathData = PathData;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public class PathData {
        @SerializedName("lat")
        @Expose
        private List<String> lat = null;
        @SerializedName("lng")
        @Expose
        private List<String> lng = null;

        public List<String> getLat() {
            return lat;
        }

        public void setLat(List<String> lat) {
            this.lat = lat;
        }

        public List<String> getLng() {
            return lng;
        }

        public void setLng(List<String> lng) {
            this.lng = lng;
        }
    }
}
