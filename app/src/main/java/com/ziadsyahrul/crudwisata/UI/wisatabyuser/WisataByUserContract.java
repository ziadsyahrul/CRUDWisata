package com.ziadsyahrul.crudwisata.UI.wisatabyuser;

import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

import java.util.List;

public interface WisataByUserContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showWisataByUser(List<WisataData> wisataByUserList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListByUser(String idUser);
    }
}
