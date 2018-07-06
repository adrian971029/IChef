package com.adrian_971029.ichef.interfaces;

import com.adrian_971029.ichef.model.Recetas;
import com.adrian_971029.ichef.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET(Constants.URL_RECETAS)
    Call<List<Recetas>> getBaking();

}
