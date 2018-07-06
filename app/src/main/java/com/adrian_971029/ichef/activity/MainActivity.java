package com.adrian_971029.ichef.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.adapter.RecetasAdapter;
import com.adrian_971029.ichef.interfaces.ApiService;
import com.adrian_971029.ichef.io.ApiAdapter;
import com.adrian_971029.ichef.model.Recetas;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    @BindView(R.id.grid_view)
    GridView mGridView;

    private List<Recetas> mRecetas;
    private ArrayAdapter<Recetas> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        crearLayout();

    }

    private void crearLayout(){
        if(mRecetas == null){
            mRecetas = new ArrayList<Recetas>();
        }
        mAdapter = new RecetasAdapter(getApplicationContext(),mRecetas);
        mGridView.setAdapter(mAdapter);

        if(temConexao(this)){
           // exibirMensagemSemConexao(false);
            chamaJSon();
        }
        else{
            //exibirProgress(false);
            //exibirMensagemSemConexao(true);
            //Toast.makeText(this, R.string.lbl_tente_novamente,Toast.LENGTH_SHORT).show();
        }

    }

    private void chamaJSon(){
        ApiService service = ApiAdapter.getApiService();
        Call<List<Recetas>> recetasResponseCall = service.getBaking();

        recetasResponseCall.enqueue(new Callback<List<Recetas>>() {
            @Override
            public void onResponse(Call<List<Recetas>> call, Response<List<Recetas>> response) {
                if (!response.isSuccessful()) {
                    Log.i("TAG","Error:" + response.code());
                    //exibirProgress(false);
                }
                else {
                    //exibirProgress(false);
                    List<Recetas> recetasResponse = response.body();
                    if (recetasResponse != null) {
                        mRecetas.clear();
                        mRecetas.addAll(recetasResponse);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Recetas>> call, Throwable t) {
                //exibirProgress(false);
                Log.e("Recetas","Error:" + t.getMessage());
            }

        });

    }

}
