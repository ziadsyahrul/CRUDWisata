package com.ziadsyahrul.crudwisata.UI.profile;

import android.content.Context;
import android.content.SharedPreferences;

import com.ziadsyahrul.crudwisata.Utils.Constant;
import com.ziadsyahrul.crudwisata.Utils.SessionManager;
import com.ziadsyahrul.crudwisata.data.remote.ApiClient;
import com.ziadsyahrul.crudwisata.data.remote.ApiInterface;
import com.ziadsyahrul.crudwisata.model.Login.LoginData;
import com.ziadsyahrul.crudwisata.model.Login.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter{

    private final ProfileContract.View view;
    private SharedPreferences pref;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
    }


    @Override
    public void updateDataUser(final Context context, final LoginData loginData) {

        // Show Progress
        view.showProgress();

        // Membuat object call dan memanggil method updateUser serta mengirimkan datanya
        Call<LoginResponse> call = apiInterface.updateUser(Integer.valueOf(loginData.getId_user()),
                loginData.getNama_user(), loginData.getAlamat(), loginData.getJenkel(),
                loginData.getNo_telp());

        // Mengeksekusi call
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                // menghilangkan progress
                view.hideProgress();

                // Mencek response dan isi body
                if (response.isSuccessful() && response.body() != null){
                    // Mencek result apakah 1
                    if (response.body().getResult() == 1){
                        // setelah berhasil update ke server online lalu update ke sharedPreference
                        // Membuat object sharedpreference yang sudah ada di SessionManager
                        pref = context.getSharedPreferences(Constant.pref_name, 0);
                        // Mengubah mode sharedPreference menjadi edit
                        SharedPreferences.Editor editor = pref.edit();
                        // Memasukkan data kedalam sharedPreference
                        editor.putString(Constant.KEY_USER_NAMA, loginData.getNama_user());
                        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
                        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
                        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
                        // Apply perubahan
                        editor.apply();
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }else {
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();

                view.showSuccessUpdateUser(t.getMessage());
            }
        });

    }

    @Override
    public void getDataUser(Context context) {
        // Pengambilan data dari SharedPreference
        pref = context.getSharedPreferences(Constant.pref_name, 0);

        // Membuat object model logindata untuk menampung
        LoginData loginData = new LoginData();

        // Memasukkan data SharedPreference ke dalam model logindata
        loginData.setId_user(pref.getString(Constant.KEY_USER_ID, ""));
        loginData.setNama_user(pref.getString(Constant.KEY_USER_NAMA, ""));
        loginData.setAlamat(pref.getString(Constant.KEY_USER_ALAMAT, ""));
        loginData.setNo_telp(pref.getString(Constant.KEY_USER_NOTELP, ""));
        loginData.setJenkel(pref.getString(Constant.KEY_USER_JENKEL, ""));

        // kirim data model loginData ke view
        view.showDataUser(loginData);

    }

    @Override
    public void logoutSession(Context context) {
        // Membuat object session manager untuk memanggil method logout
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();
    }
}

