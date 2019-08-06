package com.ziadsyahrul.crudwisata.UI.detailwisatabyuser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ziadsyahrul.crudwisata.R;
import com.ziadsyahrul.crudwisata.UI.main.MainActivity;
import com.ziadsyahrul.crudwisata.Utils.Constant;
import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class DetailWisataByUserActivity extends AppCompatActivity implements DetailWisataByUserContract.View {

    @BindView(R.id.img_picture)
    ImageView imgPicture;
    @BindView(R.id.fab_choose_picture)
    FloatingActionButton fabChoosePicture;
    @BindView(R.id.layoutPicture)
    CardView layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.spin_category)
    Spinner spinCategory;
    @BindView(R.id.layoutSaveMakanan)
    CardView layoutSaveMakanan;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private DetailWisataByUserPresenter mDetailWisataByUserPresenter = new DetailWisataByUserPresenter(this);
    private Uri filePath;
    private String[] midCategory;
    private String idWisata, idCategory;
    private WisataData mWisataData;
    private String namaFotoWisata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata_by_user);
        ButterKnife.bind(this);

        // Melakukan pengecekan dan permission untuk bisa mengakses gallery
        PermissionGallery();

        // Menangkap idMakanan yang dikirim dari activity sebelumnya
        idWisata = getIntent().getStringExtra(Constant.KEY_EXTRA_ID_MAKANAN);

        // Mengambil data category untuk ditampilkan di spinner
        mDetailWisataByUserPresenter.getCategory();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);

                // Mengambil data category untuk ditampilkan di spinner
                mDetailWisataByUserPresenter.getCategory();
            }
        });
    }

    private void PermissionGallery() {
        // Mencek apakah user sudah memberikan permission untuk mengakses external storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.STORAGE_PERMISSION_CODE);
    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showDetailWisata(WisataData wisataData) {
        mWisataData  = wisataData;
        namaFotoWisata = wisataData.getFoto_wisata();
        idCategory = wisataData.getId_kategori();
        edtName.setText(wisataData.getNama_wisata());
        edtDesc.setText(wisataData.getDesc_wisata());

        for (int i = 0 ;i < midCategory.length; i++){
            if (Integer.valueOf(midCategory[i]).equals(Integer.valueOf(idCategory))){
                spinCategory.setSelection(i);
            }
        }

        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
        Glide.with(this).load(wisataData.getUrl_wisata()).apply(options).into(imgPicture);
    }

    @Override
    public void showMessage(String msg) {
        Toasty.success(this, msg, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void successDelete() {
        finish();
    }

    @Override
    public void successUpdate() {
        finish();

    }

    @Override
    public void showSpinnerCategory(final List<WisataData> categoryDataList) {
        String[] namaCategory = new String[categoryDataList.size()];
        midCategory = new String[categoryDataList.size()];

        for (int i = 0 ;i < categoryDataList.size(); i++){
            namaCategory[i] = categoryDataList.get(i).getNama_kategori();
            midCategory[i] = categoryDataList.get(i).getId_kategori();

        }

        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, namaCategory);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinCategory.setAdapter(categorySpinnerAdapter);

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCategory = categoryDataList.get(position).getId_kategori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDetailWisataByUserPresenter.getDetailWisata(idWisata);
    }

    @OnClick({R.id.fab_choose_picture, R.id.btn_update, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                showFileChooser();
                break;
            case R.id.btn_update:
                mDetailWisataByUserPresenter.updateDataWisata(this,
                        filePath,
                        edtName.getText().toString(),
                        edtDesc.getText().toString(),
                        idCategory,
                        namaFotoWisata,
                        idWisata);
                break;
            case R.id.btn_delete:
                mDetailWisataByUserPresenter.deleteWisata(idWisata, namaFotoWisata);
                break;
        }
    }

    private void showFileChooser() {
        // Membuat object intent untuk dapat memilih data
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGallery, "select pictures"), Constant.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE &&
                resultCode == RESULT_OK &&
                data != null &&
                data.getData() != null){
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgPicture.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
