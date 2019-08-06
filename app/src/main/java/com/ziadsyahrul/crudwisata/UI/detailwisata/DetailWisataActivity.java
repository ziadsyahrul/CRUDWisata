package com.ziadsyahrul.crudwisata.UI.detailwisata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ziadsyahrul.crudwisata.R;
import com.ziadsyahrul.crudwisata.Utils.Constant;
import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailWisataActivity extends AppCompatActivity implements DetailWisataContract.View {

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rv_progress)
    RelativeLayout rvProgress;
    @BindView(R.id.img_wisata_detail)
    ImageView imgWisataDetail;
    @BindView(R.id.txt_name_wisata_detail)
    TextView txtNameWisataDetail;
    @BindView(R.id.txt_time_wisata_detail)
    TextView txtTimeWisataDetail;
    @BindView(R.id.txt_name_user)
    TextView txtNameUser;
    @BindView(R.id.txt_desc_wisata)
    TextView txtDescWisata;
    @BindView(R.id.card_view_detail)
    CardView cardViewDetail;
    @BindView(R.id.sv_detail)
    ScrollView svDetail;

    private DetailWisataPresenter mDetailWisataPresenter = new DetailWisataPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Detail Wisata");
        String idWisata = getIntent().getStringExtra(Constant.KEY_EXTRA_ID_MAKANAN);
        mDetailWisataPresenter.getDetailWisata(idWisata);
    }

    @Override
    public void showProgress() {
        rvProgress.setVisibility(View.VISIBLE);
        svDetail.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        rvProgress.setVisibility(View.GONE);
        svDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDetailWisata(WisataData wisataData) {
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image_black_24dp).placeholder(R.drawable.ic_broken_image_black_24dp);
        Glide.with(this).load(wisataData.getUrl_wisata()).apply(options).into(imgWisataDetail);

        txtNameWisataDetail.setText(wisataData.getNama_wisata());
        txtDescWisata.setText(wisataData.getDesc_wisata());
        txtNameUser.setText(wisataData.getNama_user());
        txtTimeWisataDetail.setText(wisataData.getInsert_time());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            }
        }
        return true;

    }

    @Override
    public void showFailureMessage(String msg) {
        svDetail.setVisibility(View.GONE);
        rvProgress.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
        txtInfo.setText(msg);
    }

    public String newDate(String insert_time) {
        // Mmebuat variable penampung tanggal
        Date date = null;
        // Membuat Penampung date untuk format yang baru
        String newDate = insert_time;

        // Membuat date dengan format sesuai dengan tanggal yang sudah dimiliki
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Mengubah tanggal yang dimiliki menjadi tipe date
        try {
            date = sdf.parse(insert_time);
        }catch (ParseException e){
            e.printStackTrace();
        }
        // Kita cek format date yang kita miliki sesuai dengan yang kita inginkan
        if (date != null){
            // Mengubah date yang dimiliki menjadi format date yang baru
            newDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(date);
        }
        return newDate;
    }
}
