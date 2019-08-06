package com.ziadsyahrul.crudwisata.UI.uploadwisata;

import android.content.Context;
import android.net.Uri;

import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

import java.util.List;

public interface UploadWisataContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showMessage(String msg);
        void successUpload();
        void showSpinnerCategori(List<WisataData> categoryDataList);
    }

    interface Presenter{
        void getCategory();
        void uploadWisata(Context context, Uri filePath, String namaWisata, String descWisata, String idCategory);

    }
}
