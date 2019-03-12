package com.ziadsyahrul.crudwisata.model.Login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private LoginData Data;

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

    public LoginData getData() {
        return Data;
    }

    public void setData(LoginData loginData) {
        this.Data = Data;
    }
}
