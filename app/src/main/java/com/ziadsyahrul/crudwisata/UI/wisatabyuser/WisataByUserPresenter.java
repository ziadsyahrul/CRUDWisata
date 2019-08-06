package com.ziadsyahrul.crudwisata.UI.wisatabyuser;

import com.ziadsyahrul.crudwisata.data.remote.ApiClient;
import com.ziadsyahrul.crudwisata.data.remote.ApiInterface;
import com.ziadsyahrul.crudwisata.model.wisata.WisataResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WisataByUserPresenter implements WisataByUserContract.Presenter{

    private final WisataByUserContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public WisataByUserPresenter(WisataByUserContract.View view) {
        this.view = view;
    }

    @Override
    public void getListByUser(String idUser) {
        view.showProgress();

        if (idUser.isEmpty()){
            view.showFailureMessage("ID User tidak ada");
            return;
        }

        Call<WisataResponse> call = apiInterface.getWisataByUser(Integer.valueOf(idUser));
        call.enqueue(new Callback<WisataResponse>() {
            @Override
            public void onResponse(Call<WisataResponse> call, Response<WisataResponse> response) {
                view.hideProgress();

                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showWisataByUser(response.body().getWisataDataList());
                    }else{
                        view.showFailureMessage(response.body().getMessage());
                    }
                }else {
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<WisataResponse> call, Throwable t) {
                view.showFailureMessage(t.getMessage());
            }
        });
    }
}
