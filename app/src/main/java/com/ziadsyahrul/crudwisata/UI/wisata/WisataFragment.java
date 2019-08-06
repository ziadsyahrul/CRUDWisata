package com.ziadsyahrul.crudwisata.UI.wisata;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ziadsyahrul.crudwisata.R;
import com.ziadsyahrul.crudwisata.UI.uploadwisata.UploadWisataActivity;
import com.ziadsyahrul.crudwisata.adapter.WisataAdapter;
import com.ziadsyahrul.crudwisata.model.wisata.WisataData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class WisataFragment extends Fragment implements WisataContract.View {


    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.rv_wisata_news)
    RecyclerView rvWisataNews;
    @BindView(R.id.rv_wisata_populer)
    RecyclerView rvWisataPopuler;
    @BindView(R.id.rv_kategori)
    RecyclerView rvKategori;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;

    private WisataPresenter mWisataPresenter = new WisataPresenter(this);

    public WisataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mWisataPresenter.getListWisataNews();
        mWisataPresenter.getListWisataPopuler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wisata, container, false);
        unbinder = ButterKnife.bind(this, view);

        mWisataPresenter.getListWisataNews();
        mWisataPresenter.getListWisataPopuler();
        mWisataPresenter.getListWisataKategory();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWisataPresenter.getListWisataNews();
                mWisataPresenter.getListWisataPopuler();
                mWisataPresenter.getListWisataKategory();
            }
        });

        return view;
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
    public void showWisataNewsList(List<WisataData> wisataNewsList) {
        rvWisataNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvWisataNews.setAdapter(new WisataAdapter(WisataAdapter.TYPE_1, getContext(), wisataNewsList));
    }

    @Override
    public void showWisataPopuler(List<WisataData> wisataPopulerList) {
        rvWisataPopuler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvWisataPopuler.setAdapter(new WisataAdapter(WisataAdapter.TYPE_2, getContext(), wisataPopulerList));
    }

    @Override
    public void showWisataKategoryList(List<WisataData> wisataKategoryList) {
        rvKategori.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvKategori.setAdapter(new WisataAdapter(WisataAdapter.TYPE_3, getContext(), wisataKategoryList));
    }

    @Override
    public void showFailureMessage(String msg) {
        Toasty.error(getContext(), msg, Toasty.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.floating_action_button)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), UploadWisataActivity.class));
    }
}
