package com.adrian_971029.ichef.activity;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.adapter.RecetasAdapter;
import com.adrian_971029.ichef.data.ContentProviderAccess;
import com.adrian_971029.ichef.interfaces.ApiService;
import com.adrian_971029.ichef.io.ApiAdapter;
import com.adrian_971029.ichef.model.Recetas;
import com.adrian_971029.ichef.task.RecetasTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private static final int TELA_PRINCIPAL  = 1;
    private static final int FAVORITOS  = 2;

    @BindView(R.id.grid_view)
    GridView mGridView;
    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.img_SemConexao)
    ImageView imgSemConexao;
    @BindView(R.id.tv_semConexao)
    TextView mTextMensagemSemConexao;
    @BindView(R.id.tv_semFavoritos)
    TextView mTextMensagemSemFavoritos;
    @BindView(R.id.img_atualizar)
    ImageView imgAtualizar;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_aguarde)
    TextView mTextMensagem;

    private List<Recetas> mRecetas;
    private List<Recetas> mRecetasData;
    private RecetasTask mRecetaTask;
    private ContentProviderAccess providerAccess;
    private ArrayAdapter<Recetas> mAdapter;
    private int controlLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle(R.string.lbl_tool_bar_title);
        exibirProgreso(true);
        crearLayout();

    }

    @Override
    protected void onResume() {
        super.onResume();
        controlaNumColumnsGridLayout();
        switch (controlLayout) {
            case TELA_PRINCIPAL:
                mTextMensagemSemFavoritos.setVisibility(View.GONE);
                exibirProgreso(true);
                exibirMensagemSemConexao(false);
                mAdapter.clear();
                mRecetas.clear();
                crearLayout();
                break;
            case FAVORITOS:
                mTextMensagemSemFavoritos.setVisibility(View.GONE);
                exibirProgreso(false);
                exibirMensagemSemConexao(false);
                mAdapter.clear();
                mRecetas.clear();
                crearFavoritos();
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.img_atualizar)
    public void atualizar(View view) {
        switch (controlLayout) {
            case TELA_PRINCIPAL:
                mTextMensagemSemFavoritos.setVisibility(View.GONE);
                exibirProgreso(true);
                exibirMensagemSemConexao(false);
                mAdapter.clear();
                mRecetas.clear();
                crearLayout();
                break;
            case FAVORITOS:
                mTextMensagemSemFavoritos.setVisibility(View.GONE);
                exibirProgreso(false);
                exibirMensagemSemConexao(false);
                mAdapter.clear();
                mRecetas.clear();
                crearFavoritos();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_telaPrincipal:
                mTextMensagemSemFavoritos.setVisibility(View.GONE);
                exibirProgreso(true);
                exibirMensagemSemConexao(false);
                mAdapter.clear();
                mRecetas.clear();
                crearLayout();
                break;
            case R.id.menu_favorito:
                mTextMensagemSemFavoritos.setVisibility(View.GONE);
                exibirProgreso(false);
                exibirMensagemSemConexao(false);
                mAdapter.clear();
                mRecetas.clear();
                crearFavoritos();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void controlaNumColumnsGridLayout() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGridView.setNumColumns(2);
        } else {
            mGridView.setNumColumns(1);
        }
    }

    private void crearLayout(){
        controlLayout = TELA_PRINCIPAL;
        exibirProgreso(false);
        if(mRecetas == null){
            mRecetas = new ArrayList<Recetas>();
        }
        mAdapter = new RecetasAdapter(getApplicationContext(),mRecetas);
        mGridView.setAdapter(mAdapter);

        if(temConexao(this)){
            exibirMensagemSemConexao(false);
            chamaJSon();
        }
        else{
            exibirProgreso(false);
            exibirMensagemSemConexao(true);
        }

    }

    private void crearFavoritos(){
        controlLayout = FAVORITOS;
        exibirProgreso(false);
        if(mRecetasData == null){
            mRecetasData = new ArrayList<Recetas>();
        }
        mAdapter = new RecetasAdapter(getApplicationContext(),mRecetasData);
        mGridView.setAdapter(mAdapter);

        if(mRecetaTask == null || mRecetaTask.getStatus() != AsyncTask.Status.RUNNING){
            mRecetaTask = new RecetasTask(this,providerAccess,mRecetasData,mAdapter,mTextMensagemSemFavoritos);
            mRecetaTask.execute();
        }

    }

    private void exibirProgreso(boolean exibir){
        mTextMensagem.setVisibility(exibir ? View.VISIBLE : View.GONE);

        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    private void exibirMensagemSemConexao(boolean exibir) {
        imgSemConexao.setVisibility(exibir ? View.VISIBLE : View.GONE);
        mTextMensagemSemConexao.setVisibility(exibir ? View.VISIBLE : View.GONE);
        imgAtualizar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    private void chamaJSon(){
        ApiService service = ApiAdapter.getApiService();
        Call<List<Recetas>> recetasResponseCall = service.getBaking();

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
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Recetas>> call, Throwable t) {
                Log.e("Recetas","Error:" + t.getMessage());
            }

        });

    }

}
