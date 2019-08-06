package com.ziadsyahrul.crudwisata.UI.wisata;

import com.ziadsyahrul.crudwisata.data.remote.ApiClient;
import com.ziadsyahrul.crudwisata.data.remote.ApiInterface;
import com.ziadsyahrul.crudwisata.model.wisata.WisataResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WisataPresenter implements WisataContract.Presenter{

    private final WisataContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public WisataPresenter(WisataContract.View view) {
        this.view = view;
    }

    @Override
    public void getListWisataNews() {
        view.showProgress();

        Call<WisataResponse> call = apiInterface.getWisataBaru();
        call.enqueue(new Callback<WisataResponse>() {
            @Override
            public void onResponse(Call<WisataResponse> call, Response<WisataResponse> response) {
                view.hideProgress();

                if (response.body().getWisataDataList() != null){
                    view.showWisataNewsList(response.body().getWisataDataList());
                }else {
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<WisataResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getListWisataPopuler() {
        view.showProgress();

        Call<WisataResponse> call = apiInterface.getWisataPopuler();
        call.enqueue(new Callback<WisataResponse>() {
            @Override
            public void onResponse(Call<WisataResponse> call, Response<WisataResponse> response) {
                view.hideProgress();

                if (response.body().getWisataDataList() != null){
                    view.showWisataPopuler(response.body().getWisataDataList());
                }else {
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<WisataResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getListWisataKategory() {
        view.showProgress();

        Call<WisataResponse> call = apiInterface.getKategoriWisata();
        call.enqueue(new Callback<WisataResponse>() {
            @Override
            public void onResponse(Call<WisataResponse> call, Response<WisataResponse> response) {
                view.hideProgress();

                if (response.body().getWisataDataList() != null){
                    view.showWisataKategoryList(response.body().getWisataDataList());
                }else {
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<WisataResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
            }
        });
    }
}
