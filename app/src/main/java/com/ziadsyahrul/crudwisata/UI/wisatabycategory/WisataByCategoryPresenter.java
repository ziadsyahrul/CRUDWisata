package com.ziadsyahrul.crudwisata.UI.wisatabycategory;

import com.ziadsyahrul.crudwisata.data.remote.ApiClient;
import com.ziadsyahrul.crudwisata.data.remote.ApiInterface;
import com.ziadsyahrul.crudwisata.model.wisata.WisataResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WisataByCategoryPresenter implements WisataByCategoryContract.Presenter{

    private final WisataByCategoryContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public WisataByCategoryPresenter(WisataByCategoryContract.View view) {
        this.view = view;
    }

    @Override
    public void getListWisataByCategory(String idCategory) {
        view.showProgress();

        if (idCategory.isEmpty()){
            view.showFailureMessage("ID Category tidak ada");
            return;
        }

        Call<WisataResponse> call = apiInterface.getWisataByKaegori(Integer.valueOf(idCategory));
        call.enqueue(new Callback<WisataResponse>() {
            @Override
            public void onResponse(Call<WisataResponse> call, Response<WisataResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showWisataByCategory(response.body().getWisataDataList());
                    }else {
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
