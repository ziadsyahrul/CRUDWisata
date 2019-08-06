package com.ziadsyahrul.crudwisata.UI.detailwisatabyuser;

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
import com.ziadsyahrul.crudwisata.model.detailwisata.DetailWisataResponse;
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

public class DetailWisataByUserPresenter implements DetailWisataByUserContract.Presenter{

    private final DetailWisataByUserContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private File imageFile;

    public DetailWisataByUserPresenter(DetailWisataByUserContract.View view) {
        this.view = view;
    }

    @Override
    public void getCategory() {
        view.showProgress();

        Call<WisataResponse> call = apiInterface.getKategoriWisata();
        call.enqueue(new Callback<WisataResponse>() {
            @Override
            public void onResponse(Call<WisataResponse> call, Response<WisataResponse> response) {
                view.hideProgress();

                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showSpinnerCategory(response.body().getWisataDataList());
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<WisataResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getDetailWisata(String idWisata) {
        view.showProgress();

        if (idWisata == null){
            view.showMessage("ID Makanan tidak ada");
            view.hideProgress();
            return;
        }

        Call<DetailWisataResponse> call = apiInterface.getDetailWisata(Integer.valueOf(idWisata));
        call.enqueue(new Callback<DetailWisataResponse>() {
            @Override
            public void onResponse(Call<DetailWisataResponse> call, Response<DetailWisataResponse> response) {
                view.hideProgress();

                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showDetailWisata(response.body().getWisataData());
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else{
                    view.showMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<DetailWisataResponse> call, Throwable t) {
                view.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void updateDataWisata(Context context, Uri filePath, String namaWisata, String descWisata, String idCategory, String namaFotoWisata, String idWisata) {
        view.showProgress();

        if (namaWisata.isEmpty()) {
            view.showMessage("Nama Wisata tidak ada");
            view.hideProgress();
            return;
        }

        if (descWisata.isEmpty()) {
            view.showMessage("Desc Wisata tidak ada");
            view.hideProgress();
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(Constant.pref_name, 0);
        String idUser = pref.getString(Constant.KEY_USER_ID, "");

        String sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        RequestBody mNamaWisata = RequestBody.create(MediaType.parse("multipart/form-data"), namaWisata);
        RequestBody mDescWisata = RequestBody.create(MediaType.parse("multipart/form-data"), descWisata);
        RequestBody mNamaFotoWisata = RequestBody.create(MediaType.parse("multipart/form-data"), namaFotoWisata);
        RequestBody dateTime = RequestBody.create(MediaType.parse("multipart/form-data"), sdf);


        if (filePath != null) {
            File myFile = new File(filePath.getPath());
            Uri selectedImage = getImageContentUri(context, myFile, filePath);
            String partImage = getPath(context, selectedImage);
            imageFile = new File(partImage);

            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
            MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);


            Call<WisataResponse> call = apiInterface.updateWisata(
                    Integer.valueOf(idWisata),
                    Integer.valueOf(idCategory),
                    mNamaWisata,
                    mDescWisata,
                    mNamaFotoWisata,
                    dateTime,
                    mPartImage
            );
            call.enqueue(new Callback<WisataResponse>() {
                @Override
                public void onResponse(Call<WisataResponse> call, Response<WisataResponse> response) {
                    view.hideProgress();

                    if (response.body() != null) {
                        if (response.body().getResult() == 1) {
                            view.successUpdate();
                            view.showMessage(response.body().getMessage());
                        } else {
                            view.showMessage(response.body().getMessage());
                        }
                    } else {
                        view.showMessage("Data Kurang atau endpoint bermasalah");
                    }
                }

                @Override
                public void onFailure(Call<WisataResponse> call, Throwable t) {
                    view.hideProgress();
                    view.showMessage(t.getMessage());
                }
            });

        }else {
            MultipartBody.Part mPartImage = null;
            Call<WisataResponse> call = apiInterface.updateWisata(
                    Integer.valueOf(idWisata),
                    Integer.valueOf(idCategory),
                    mNamaWisata,
                    mDescWisata,
                    mNamaFotoWisata,
                    dateTime,
                    mPartImage
            );
            call.enqueue(new Callback<WisataResponse>() {
                @Override
                public void onResponse(Call<WisataResponse> call, Response<WisataResponse> response) {
                    view.hideProgress();

                    if (response.body() != null) {
                        if (response.body().getResult() == 1) {
                            view.successUpdate();
                            view.showMessage(response.body().getMessage());
                        } else {
                            view.showMessage(response.body().getMessage());
                        }
                    } else {
                        view.showMessage("Data Kurang atau endpoint bermasalah");
                    }
                }

                @Override
                public void onFailure(Call<WisataResponse> call, Throwable t) {
                    view.hideProgress();
                    view.showMessage(t.getMessage());
                }
            });
        }
    }

    @Override
    public void deleteWisata(String idWisata, String namaFotoWisata) {
        view.showProgress();

        if (idWisata.isEmpty()){
            view.showMessage("ID Wisata tidak ada");
            return;
        }

        if (namaFotoWisata.isEmpty()){
            view.showMessage("Nama Foto Wisata tidak ada");
            return;
        }

        Call<WisataResponse> call = apiInterface.deleteWisata(Integer.valueOf(idWisata), namaFotoWisata);
        call.enqueue(new Callback<WisataResponse>() {
            @Override
            public void onResponse(Call<WisataResponse> call, Response<WisataResponse> response) {
                view.hideProgress();

                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showMessage(response.body().getMessage());
                        view.successDelete();
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<WisataResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
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
