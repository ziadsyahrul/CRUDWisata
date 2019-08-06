package com.ziadsyahrul.crudwisata.UI.detailwisata;

import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

public interface DetailWisataContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showDetailWisata(WisataData wisataData);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getDetailWisata(String idWisata);
    }
}
