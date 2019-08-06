package com.ziadsyahrul.crudwisata.model.detailwisata;

import com.google.gson.annotations.SerializedName;
import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

public class DetailWisataResponse {

    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private WisataData wisataData;


    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WisataData getWisataData() {
        return wisataData;
    }

    public void setWisataData(WisataData wisataData) {
        this.wisataData = wisataData;
    }
}
