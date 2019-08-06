package com.ziadsyahrul.crudwisata.UI.wisatabyuser;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WisataByUserFragment extends Fragment implements WisataByUserContract.View {

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rl_progress_by_user)
    RelativeLayout rlProgressByUser;
    @BindView(R.id.rv_wisata)
    RecyclerView rvWisata;
    @BindView(R.id.sr_makanan_by_user)
    SwipeRefreshLayout srMakananByUser;

    private WisataByUserPresenter mWisataByUserPresenter = new WisataByUserPresenter(this);
    private String idUser;
    Unbinder unbinder;


    public WisataByUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mWisataByUserPresenter.getListByUser(idUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wisata_by_user, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Mengambil idUser dari sharedpref
        SharedPreferences pref = getContext().getSharedPreferences(Constant.pref_name, 0);

        // Memasukkan idUser yang sudah diambil ke dalam variable
        idUser = pref.getString(Constant.KEY_USER_ID, "");

        // Merequest data makanan by user
        mWisataByUserPresenter.getListByUser(idUser);

        srMakananByUser.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srMakananByUser.setRefreshing(false);
                // request data makanan by user
                mWisataByUserPresenter.getListByUser(idUser);
            }
        });

        return view;
    }

    @Override
    public void showProgress() {
        rlProgressByUser.setVisibility(View.VISIBLE);
        srMakananByUser.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        rlProgressByUser.setVisibility(View.GONE);
        rvWisata.setVisibility(View.VISIBLE);
        srMakananByUser.setVisibility(View.VISIBLE);
    }

    @Override
    public void showWisataByUser(List<WisataData> wisataByUserList) {
        rvWisata.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWisata.setAdapter(new WisataAdapter(WisataAdapter.TYPE_5, getContext(), wisataByUserList));
    }

    @Override
    public void showFailureMessage(String msg) {
        srMakananByUser.setVisibility(View.VISIBLE);
        rlProgressByUser.setVisibility(View.VISIBLE);
        rvWisata.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        txtInfo.setText(msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
