package com.ziadsyahrul.crudwisata.UI.detailwisatabyuser;

import android.content.Context;
import android.net.Uri;

import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

import java.util.List;

public interface DetailWisataByUserContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showDetailWisata(WisataData wisataByUserList);
        void showMessage(String msg);
        void successDelete();
        void successUpdate();
        void showSpinnerCategory(List<WisataData> categoryDataList);
    }

    interface Presenter{
        void getCategory();
        void getDetailWisata(String idWisata);
        void updateDataWisata(Context context, Uri filePath, String namaWisata, String descWisata, String idCategory, String namaFotoWisata, String idWisata);
        void deleteWisata(String idWisata, String namaFotoWisata);
    }
}
