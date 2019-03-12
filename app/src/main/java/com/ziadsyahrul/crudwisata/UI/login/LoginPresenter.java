package com.ziadsyahrul.crudwisata.UI.login;

import android.content.Context;

import com.ziadsyahrul.crudwisata.Utils.SessionManager;
import com.ziadsyahrul.crudwisata.data.remote.ApiClient;
import com.ziadsyahrul.crudwisata.data.remote.ApiInterface;
import com.ziadsyahrul.crudwisata.model.Login.LoginData;
import com.ziadsyahrul.crudwisata.model.Login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter{

    private final LoginContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private SessionManager mSessionManager;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void doLogin(String username, String password) {
        if (username.isEmpty()) {
            view.usernameError("Username is Empty");
            return;
        }

        if (password.isEmpty()){
            view.passwordError("Password is Empty");
            return;
        }

        view.showProgress();
        Call<LoginResponse> call = apiInterface.loginUser(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        if (response.body().getData() != null){
                            LoginData loginData = response.body().getData();
                            String message = response.body().getMessage();
                            view.loginSuccess(message, loginData);
                        }else {
                            view.hideProgress();
                            view.loginFailure("Data tidak ada");
                        }
                    }else {
                        view.hideProgress();
                        view.loginFailure(response.body().getMessage());
                    }
                } else {
                    view.hideProgress();
                    view.loginFailure("Data tidak ada");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.loginFailure(t.getMessage());
            }
        });
    }

    @Override
    public void saveDataUser(Context context, LoginData loginData) {
        mSessionManager = new SessionManager(context);
        mSessionManager.createSession(loginData);
    }

    @Override
    public void checkLogin(Context context) {
        mSessionManager = new SessionManager(context);

        Boolean isLogin = mSessionManager.isLogin();

        if (isLogin){
            view.isLogin();
        }
    }
}
