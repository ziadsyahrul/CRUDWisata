package com.ziadsyahrul.crudwisata.UI.detailwisata;

import com.ziadsyahrul.crudwisata.data.remote.ApiClient;
import com.ziadsyahrul.crudwisata.data.remote.ApiInterface;
import com.ziadsyahrul.crudwisata.model.detailwisata.DetailWisataResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailWisataPresenter implements DetailWisataContract.Presenter{

    private final DetailWisataContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public DetailWisataPresenter(DetailWisataContract.View view) {
        this.view = view;
    }

    @Override
    public void getDetailWisata(String idWisata) {
        view.showProgress();

        if (idWisata == null){
            view.showFailureMessage("ID wisata tidak ada");
            return;
        }

        Call<DetailWisataResponse> call = mApiInterface.getDetailWisata(Integer.valueOf(idWisata));
        call.enqueue(new Callback<DetailWisataResponse>() {
            @Override
            public void onResponse(Call<DetailWisataResponse> call, Response<DetailWisataResponse> response) {
                view.hideProgress();

                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showDetailWisata(response.body().getWisataData());
                    }else {
                        view.showFailureMessage(response.body().getMessage());
                    }
                }else {
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<DetailWisataResponse> call, Throwable t) {
                view.showFailureMessage(t.getMessage());
            }
        });
    }
}
