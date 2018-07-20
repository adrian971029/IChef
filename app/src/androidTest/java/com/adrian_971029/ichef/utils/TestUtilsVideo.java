package com.adrian_971029.ichef.utils;

import android.os.SystemClock;
import android.util.Log;

import com.adrian_971029.ichef.interfaces.ApiService;
import com.adrian_971029.ichef.io.ApiAdapter;
import com.adrian_971029.ichef.model.Recetas;
import com.adrian_971029.ichef.model.Step;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestUtilsVideo {

    public static List<Step> resultadoRecetas() throws IOException {

        ApiService service = ApiAdapter.getApiService();
        Call<List<Recetas>> recetasResponseCall = service.getBaking();
        final List<Recetas> mRecetas = new ArrayList<Recetas>();
        final List<Step> mStep = new ArrayList<Step>();

        recetasResponseCall.enqueue(new Callback<List<Recetas>>() {
            @Override
            public void onResponse(Call<List<Recetas>> call, Response<List<Recetas>> response) {
                if (!response.isSuccessful()) {
                    Log.i("TAG","Error:" + response.code());
                }
                else {
                    List<Recetas> recetasResponse = response.body();
                    if (recetasResponse != null) {
                        mRecetas.clear();
                        mRecetas.addAll(recetasResponse);
                        Log.e("FUE","PQ");
                        for (int i = 0; i < mRecetas.size(); i++) {
                            mStep.add(mRecetas.get(i).getSteps().get(i));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Recetas>> call, Throwable t) {
                Log.e("Recetas","Error:" + t.getMessage());
            }

        });
        SystemClock.sleep(3000);
        return mStep;
}

}
