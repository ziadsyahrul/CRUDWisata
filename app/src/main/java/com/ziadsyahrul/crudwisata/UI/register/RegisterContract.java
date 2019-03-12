package com.ziadsyahrul.crudwisata.UI.register;

import com.ziadsyahrul.crudwisata.model.Login.LoginData;

public interface RegisterContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showError(String msg);
        void showRegisterSuccess(String msg);
    }

    interface Presenter{
        void doRegisterUser(LoginData loginData);
    }
}
