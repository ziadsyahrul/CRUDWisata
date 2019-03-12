package com.ziadsyahrul.crudwisata.UI.profile;

import android.content.Context;

import com.ziadsyahrul.crudwisata.model.Login.LoginData;

public interface ProfileContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showSuccessUpdateUser(String msg);
        void showDataUser(LoginData loginData);
    }

    interface Presenter {
        void updateDataUser(Context context, LoginData loginData);
        void getDataUser(Context context);
        void logoutSession(Context context);
    }
}
