package com.ziadsyahrul.crudwisata.UI.main;

import android.content.Context;

public interface MainContract {

    interface View{

    }

    interface Presenter{
        void logoutSession(Context context);
    }
}
