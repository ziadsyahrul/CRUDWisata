package com.ziadsyahrul.crudwisata.UI.wisatabycategory;

import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

import java.util.List;

public interface WisataByCategoryContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showWisataByCategory(List<WisataData> wisataNewsList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListWisataByCategory(String idCategory);
    }
}
