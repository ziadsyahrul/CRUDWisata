package com.ziadsyahrul.crudwisata.model.wisata;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WisataResponse {

    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<WisataData> wisataDataList;

    @SerializedName("url")
    private String url;

    @SerializedName("name")
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public List<WisataData> getWisataDataList() {
        return wisataDataList;
    }

    public void setWisataDataList(List<WisataData> wisataDataList) {
        this.wisataDataList = wisataDataList;
    }
}
