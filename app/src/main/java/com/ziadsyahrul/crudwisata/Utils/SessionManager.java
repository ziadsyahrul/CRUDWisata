package com.ziadsyahrul.crudwisata.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ziadsyahrul.crudwisata.UI.login.LoginActivity;
import com.ziadsyahrul.crudwisata.model.Login.LoginData;

public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private final Context context;


    public SessionManager(Context context) {
        this.context = context;
        // Membuat object shared preference untuk siap digunakan
        pref
                = context.getSharedPreferences(Constant.pref_name, 0);
        // Membuat SharedPreference dengan mode edit
        editor = pref.edit();
    }

    // Membuat function untuk memasukkan data datanya
    public void createSession(LoginData loginData) {
        // Memasukkan data user yang sudah login ke dalam shared preference
        editor.putBoolean(Constant.KEY_IS_LOGIN, true);
        editor.putString(Constant.KEY_USER_ID, loginData.getId_user());
        editor.putString(Constant.KEY_USER_NAMA, loginData.getNama_user());
        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
        editor.putString(Constant.KEY_USER_USERNAME, loginData.getUsername());
        editor.putString(Constant.KEY_USER_LEVEL, loginData.getLevel());
        // Mengeksekusi penyimpanan
        editor.commit();

    }

    // Function untuk mencek apakah user sudah pernah login
    public boolean isLogin() {

        // mengembalikan nilai boolean dengan mengambil data dari pref KEY_IS_LOGIN
        return pref.getBoolean(Constant.KEY_IS_LOGIN, false);
    }

    // Membuat function untuk melakukan logout atau menghapus isi di dalam shared preference
    public void logout() {
        // Memanggil method clear untuk menghapus data sharedPreference
        editor.clear();
        // mengeksekusi perintah clear
        editor.commit();
        // Membuat intent untuk berpindah halaman
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }}
