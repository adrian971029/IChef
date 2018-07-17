package com.adrian_971029.ichef.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrian_971029.ichef.data.ContentProviderAccess;
import com.adrian_971029.ichef.model.Recetas;

import java.util.List;

public class RecetasTask  extends AsyncTask<Void,Void,List<Recetas>>  {

    private ContentProviderAccess providerAccess;
    private List<Recetas> mRecetasData;
    private ArrayAdapter<Recetas> mAdapter;
    private TextView mTextMensagemSemFavoritos;
    private Context context;

    public RecetasTask(Context context, ContentProviderAccess providerAccess, List<Recetas> mMovieData, ArrayAdapter<Recetas> mAdapter, TextView mTextMensagemSemFavoritos) {
        this.context = context;
        this.providerAccess = providerAccess;
        this.mRecetasData = mMovieData;
        this.mAdapter = mAdapter;
        this.mTextMensagemSemFavoritos = mTextMensagemSemFavoritos;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Recetas> doInBackground(Void... voids) {
        return providerAccess.buscarTodos(context);
    }

    @Override
    protected void onPostExecute(List<Recetas> recetas) {
        super.onPostExecute(recetas);
        if(recetas != null){
            if(recetas.size() > 0){
                mTextMensagemSemFavoritos.setVisibility(View.GONE);
                mRecetasData.clear();
                mRecetasData.addAll(recetas);
                mAdapter.notifyDataSetChanged();
            }else {
                mTextMensagemSemFavoritos.setVisibility(View.VISIBLE);
            }
        }
        else {
            mTextMensagemSemFavoritos.setVisibility(View.VISIBLE);
        }

    }

}
