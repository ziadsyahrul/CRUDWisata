package com.ziadsyahrul.crudwisata.UI.wisatabycategory;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ziadsyahrul.crudwisata.R;
import com.ziadsyahrul.crudwisata.Utils.Constant;
import com.ziadsyahrul.crudwisata.adapter.WisataAdapter;
import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WisataByCategoryActivity extends AppCompatActivity implements WisataByCategoryContract.View {

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rv_wisata)
    RecyclerView rvWisata;
    @BindView(R.id.sr_wisata)
    SwipeRefreshLayout srWisata;

    private WisataByCategoryPresenter mWisataByCategoryPresenter = new WisataByCategoryPresenter(this);
    private String idCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wisata_by_category);
        ButterKnife.bind(this);

        idCategory = getIntent().getStringExtra(Constant.KEY_EXTRA_ID_CATEGORY);

        mWisataByCategoryPresenter.getListWisataByCategory(idCategory);

        srWisata.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srWisata.setRefreshing(false);
                mWisataByCategoryPresenter.getListWisataByCategory(idCategory);
            }
        });
    }

    @Override
    public void showProgress() {
        rlProgress.setVisibility(View.VISIBLE);
        srWisata.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        rlProgress.setVisibility(View.GONE);
        rvWisata.setVisibility(View.VISIBLE);
        srWisata.setVisibility(View.VISIBLE);
    }

    @Override
    public void showWisataByCategory(List<WisataData> wisataNewsList) {
        rvWisata.setLayoutManager(new LinearLayoutManager(this));
        rvWisata.setAdapter(new WisataAdapter(WisataAdapter.TYPE_4, this, wisataNewsList));
    }

    @Override
    public void showFailureMessage(String msg) {
        srWisata.setVisibility(View.VISIBLE);
        rvWisata.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        txtInfo.setText(msg);
    }
}
