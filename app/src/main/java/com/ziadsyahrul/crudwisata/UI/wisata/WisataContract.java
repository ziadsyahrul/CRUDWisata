package com.ziadsyahrul.crudwisata.UI.wisata;

import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

import java.util.List;

public interface WisataContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showWisataNewsList(List<WisataData> wisataNewsList);
        void showWisataPopuler(List<WisataData> wisataPopulerList);
        void showWisataKategoryList(List<WisataData> wisataKategoryList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListWisataNews();
        void getListWisataPopuler();
        void getListWisataKategory();
    }

}
