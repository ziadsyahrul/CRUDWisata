package com.ziadsyahrul.crudwisata.model.wisata;

import com.google.gson.annotations.SerializedName;

public class WisataData {

    @SerializedName("id_wisata")
    private String id_wisata;

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("nama_wisata")
    private String nama_wisata;

    @SerializedName("desc_wisata")
    private String desc_wisata;

    @SerializedName("foto_wisata" )
    private String foto_wisata;

    @SerializedName("insert_time")
    private String insert_time;

    @SerializedName("view")
    private String view;

    @SerializedName("nama_user")
    private String nama_user;

    @SerializedName("id_kategori")
    private String id_kategori;

    @SerializedName("nama_kategori")
    private String nama_kategori;

    @SerializedName("url_wisata")
    private String url_wisata;

    @SerializedName("foto_kategori")
    private String foto_kategori;

    public String getId_wisata() {
        return id_wisata;
    }

    public void setId_wisata(String id_wisata) {
        this.id_wisata = id_wisata;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getDesc_wisata() {
        return desc_wisata;
    }

    public void setDesc_wisata(String desc_wisata) {
        this.desc_wisata = desc_wisata;
    }

    public String getFoto_wisata() {
        return foto_wisata;
    }

    public void setFoto_wisata(String foto_wisata) {
        this.foto_wisata = foto_wisata;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getUrl_wisata() {
        return url_wisata;
    }

    public void setUrl_wisata(String url_wisata) {
        this.url_wisata = url_wisata;
    }

    public String getFoto_kategori() {
        return foto_kategori;
    }

    public void setFoto_kategori(String foto_kategori) {
        this.foto_kategori = foto_kategori;
    }
}
