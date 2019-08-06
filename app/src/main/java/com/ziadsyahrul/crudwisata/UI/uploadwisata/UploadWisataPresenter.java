package com.ziadsyahrul.crudwisata.UI.uploadwisata;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.ziadsyahrul.crudwisata.Utils.Constant;
import com.ziadsyahrul.crudwisata.data.remote.ApiClient;
import com.ziadsyahrul.crudwisata.data.remote.ApiInterface;
import com.ziadsyahrul.crudwisata.model.uploadwisata.UploadWisataResponse;
import com.ziadsyahrul.crudwisata.model.wisata.WisataResponse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadWisataPresenter implements UploadWisataContract.Presenter{

    private final UploadWisataContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public UploadWisataPresenter(UploadWisataContract.View view) {
        this.view = view;
    }

    @Override
    public void getCategory() {
        view.showProgress();

        Call<WisataResponse> call = mApiInterface.getKategoriWisata();
        call.enqueue(new Callback<WisataResponse>() {
            @Override
            public void onResponse(Call<WisataResponse> call, Response<WisataResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showSpinnerCategori(response.body().getWisataDataList());
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    view.showMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<WisataResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
                Log.i("Cek Failure", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void uploadWisata(Context context, Uri filePath, String namaWisata, String descWisata, String idCategory) {
        view.showProgress();

        if (namaWisata.isEmpty()){
            view.showMessage("Nama Makanan tidak boleh kosong");
            view.hideProgress();
            return;
        }

        if (descWisata.isEmpty()){
            view.showMessage("Desc Makanan tidak boleh kosong");
            view.hideProgress();
            return;
        }

        if (filePath == null){
            view.showMessage("Silahkan pilih gambar");
            view.hideProgress();
            return;
        }

        // Memilih alamat file image
        File myFile = new File(filePath.getPath());
        Uri selectedImage = getImageContentUri(context, myFile, filePath);
        String partImage = getPath(context, selectedImage);
        File imageFile = new File(partImage);

        // Mengambil iduser di dalam shared preference
        SharedPreferences pref = context.getSharedPreferences(Constant.pref_name, 0);
        String idUser = pref.getString(Constant.KEY_USER_ID, "");

        // Mengambil date sekrang untuk waktu upload wisata
        String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Memasukkan data yang diberikan ke dalam request body dengan tipe form-data
        // Memasukkan image  file ke dalam requestbody.part
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

        // Memasukkan nama, desc, dan insertTime
        RequestBody mNamaWisata = RequestBody.create(MediaType.parse("multipart/form-data"), namaWisata);
        RequestBody mDescWisata = RequestBody.create(MediaType.parse("multipart/form-data"), descWisata);
        RequestBody mInsertTime = RequestBody.create(MediaType.parse("multipart/form-data"), dateNow);

        Call<UploadWisataResponse> call = mApiInterface.uploadWisata(Integer.valueOf(idUser), Integer.valueOf(idCategory), mNamaWisata, mDescWisata, mInsertTime, mPartImage);
        call.enqueue(new Callback<UploadWisataResponse>() {
            @Override
            public void onResponse(Call<UploadWisataResponse> call, Response<UploadWisataResponse> response) {
                view.hideProgress();

                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showMessage(response.body().getMessage());
                        view.successUpload();
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<UploadWisataResponse> call, Throwable t) {

            }
        });

    }

    private String getPath(Context context, Uri filePath) {

        Cursor cursor = context.getContentResolver().query(filePath, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private Uri getImageContentUri(Context context, File imageFile, Uri filePath) {

        String fileAbsolutePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{fileAbsolutePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Apabila file gambar sudah pernah diapakai namun ada kondisi lain yang belum diketahui
            // Apabila file gambar sudah pernah dipakai pengambilan bukan di galery

            Log.i("Isi Selected if", "Masuk cursor ada");
            return filePath;

        } else {
            Log.i("Isi Selected else", "cursor tidak ada");
            if (imageFile.exists()) {
                // Apabila file gambar baru belum pernah di pakai
                Log.i("Isi Selected else", "imagefile exists");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, fileAbsolutePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                // Apabila file gambar sudah pernah dipakai
                // Apabila file gambar sudah pernah dipakai di galery
                Log.i("Isi Selected else", "imagefile tidak exists");
                return filePath;
            }
        }
    }
}

