package com.ziadsyahrul.crudwisata.UI.main;

import android.content.Context;

import com.ziadsyahrul.crudwisata.Utils.SessionManager;

public class MainPresenter implements MainContract.Presenter{
    @Override
    public void logoutSession(Context context) {
        // object session manager
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();
    }
}
