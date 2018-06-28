package com.adrian_971029.ichef.interfaces;

import com.adrian_971029.ichef.utils.Constants;
import com.adrian_971029.ichef.io.response.RecetasResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET(Constants.URL_RECETAS)
    Call<RecetasResponse> getBaking();

}
