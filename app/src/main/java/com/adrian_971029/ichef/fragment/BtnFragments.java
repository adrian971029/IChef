package com.adrian_971029.ichef.fragment;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.data.ContentProviderAccess;
import com.adrian_971029.ichef.model.Recetas;
import com.adrian_971029.ichef.widget.IChefWidgetProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class BtnFragments extends Fragment {

    private static final String RECETAS_ID = "recetas_id";

    @BindView(R.id.btn_favorito)
    Button btnFavorito;
    @BindView(R.id.btn_widget)
    Button btnWidget;
    private SharedPreferences sharedPreferences;
    protected SharedPreferences sharedPrefs;
    private boolean controlFavorito;
    private boolean controlWidget;
    private Recetas recetas;

    public BtnFragments() {
    }

    public Recetas getRecetas() {
        return recetas;
    }

    public void setRecetas(Recetas recetas) {
        this.recetas = recetas;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_btn_favorites_and_widget,container,false);

        ButterKnife.bind(this,rootView);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences = getContext().getSharedPreferences("widget", MODE_PRIVATE);

        return  rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        controlFavorito();
        controlWidget();
    }

    @Override
    public void onResume() {
        super.onResume();
        controlFavorito();
        controlWidget();
    }

    @OnClick(R.id.btn_favorito)
    public void favorito(View view) {
        if(((ColorDrawable)btnFavorito.getBackground()).getColor() == getResources().getColor(R.color.colorPrimaryLight)) {
            inserirCadastroBanco();
            btnFavorito.setBackgroundColor(getResources().getColor(R.color.dorado));
            guardandoFavorito(true);
            Toast.makeText(getContext(), R.string.add_favoritos,Toast.LENGTH_SHORT).show();
        }
        else {
            if (((ColorDrawable)btnWidget.getBackground()).getColor() == getResources().getColor(R.color.colorPrimaryLight)) {
                if (!deletarCadastroBanco(String.valueOf(recetas.getId()))) return;
                btnFavorito.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                guardandoFavorito(false);
                Toast.makeText(getContext(), R.string.delet_favoritos,Toast.LENGTH_SHORT).show();
            } else {
                if (!deletarCadastroBanco(String.valueOf(recetas.getId()))) return;
                btnFavorito.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                guardandoFavorito(false);
                btnWidget.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                guardandoFavorito(false);
                Toast.makeText(getContext(), R.string.msg_delet_fav_and_widget,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.btn_widget)
    public void widget(View view) {
        if(((ColorDrawable)btnWidget.getBackground()).getColor() == getResources().getColor(R.color.colorPrimaryLight)) {
            if(((ColorDrawable)btnFavorito.getBackground()).getColor() == getResources().getColor(R.color.colorPrimaryLight)) {
                inserirCadastroBanco();
                btnFavorito.setBackgroundColor(getResources().getColor(R.color.dorado));
                btnWidget.setBackgroundColor(getResources().getColor(R.color.dorado));
                guardandoFavorito(true);
                guardandoWidget(true);
                Toast.makeText(getContext(), R.string.add_widget_favoritos,Toast.LENGTH_SHORT).show();
            } else {
                guardandoWidget(true);
                btnWidget.setBackgroundColor(getResources().getColor(R.color.dorado));
                Toast.makeText(getContext(), R.string.msg_add_widget,Toast.LENGTH_SHORT).show();
            }
        }
        else {
            controlFavorito();
            if(controlFavorito) {
                guardandoWidget(false);
                btnWidget.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                Toast.makeText(getContext(), R.string.msg_delet_widget,Toast.LENGTH_SHORT).show();
            } else {
                if (!deletarCadastroBanco(String.valueOf(recetas.getId()))) return;
                btnFavorito.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                btnWidget.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                guardandoFavorito(false);
                guardandoWidget(false);
                Toast.makeText(getContext(), R.string.msg_delet_fav_and_widget,Toast.LENGTH_SHORT).show();
            }
        }
        atualizarWidget();
    }

    private void inserirCadastroBanco() {
        try {
            ContentProviderAccess.inserirContentProvider(getContext(),recetas);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean deletarCadastroBanco(String id){
        boolean sucesso = false;
        try {
            sucesso = ContentProviderAccess.deletar(recetas,id,getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sucesso;
    }

    private void guardandoFavorito(boolean favorito){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(String.valueOf(recetas.getId()),favorito);
        editor.apply();
    }

    private void guardandoWidget(boolean guardarWidget){
        if(guardarWidget){
            sharedPreferences.edit().putString(RECETAS_ID, String.valueOf(recetas.getId())).apply();
        } else {
            sharedPreferences.edit().putString(RECETAS_ID, null).apply();
        }
    }

    private void controlFavorito() {
        if(sharedPrefs != null) {
            controlFavorito = sharedPrefs.getBoolean(String.valueOf(recetas.getId()),false);
        }

        if(controlFavorito) {
            btnFavorito.setBackgroundColor(getResources().getColor(R.color.dorado));
        }
        else {
            btnFavorito.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        }
    }

    private void controlWidget() {
        if (sharedPreferences != null) {
            if (sharedPreferences.getString(RECETAS_ID,null) != null){
                if (sharedPreferences.getString(RECETAS_ID,null).equals(String.valueOf(recetas.getId()))) {
                    controlWidget = true;
                }
                else {
                    controlWidget = false;
                }
            }
        }

        if(controlWidget) {
            btnWidget.setBackgroundColor(getResources().getColor(R.color.dorado));
        }
        else {
            btnWidget.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
        }
    }

    private void atualizarWidget() {
        Context context = getContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, IChefWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_item_widget);
    }

}
