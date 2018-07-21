package com.adrian_971029.ichef.activity;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.adapter.StepsAdapter;
import com.adrian_971029.ichef.data.ContentProviderAccess;
import com.adrian_971029.ichef.fragment.ImageRecetasFragment;
import com.adrian_971029.ichef.model.Ingredient;
import com.adrian_971029.ichef.model.Recetas;
import com.adrian_971029.ichef.model.Step;
import com.adrian_971029.ichef.utils.Constants;
import com.adrian_971029.ichef.widget.IChefWidgetProvider;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends BaseActivity {

    private static final String RECETAS = "recetas";
    private static final String RECETAS_ID = "recetas_id";

    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;
    @BindView(R.id.listSteps)
    ListView mListSteps;
    @BindView(R.id.scroll_details)
    ScrollView mScrollDetails;
    @BindView(R.id.btn_favorito)
    Button btnFavorito;
    @BindView(R.id.btn_widget)
    Button btnWidget;

    private Recetas recetas;
    private ArrayList<Ingredient> mArrayIngredients;
    private ArrayList<Step> mArraySteps;
    private List<Step> mSteps;
    private ArrayAdapter<Step> mAdapter;
    private boolean controlFavorito;
    private boolean controlWidget;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        recetas = new Recetas();
        recetas = getIntent().getExtras().getParcelable(RECETAS);
        sharedPreferences = getSharedPreferences("widget", MODE_PRIVATE);
        controlFavorito = false;
        controlWidget = false;

        criarFragmentImageRecetas();
        layoutIngredientes();
        layoutSteps();
        controlFavorito();
        controlWidget();

    }

    @Override
    protected void onStart() {
        super.onStart();
        focaScrollNoComenzo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        controlFavorito();
        controlWidget();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        controlFavorito();
        controlWidget();
    }

    private void criarFragmentImageRecetas() {
        ImageRecetasFragment imageRecetasFragment = new ImageRecetasFragment();
        imageRecetasFragment.setName(recetas.getName());

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.image_recetas_container, imageRecetasFragment)
                .commit();
    }

    @OnClick(R.id.btn_favorito)
    public void favorito(View view) {
       if(((ColorDrawable)btnFavorito.getBackground()).getColor() == getResources().getColor(R.color.colorPrimaryLight)) {
           inserirCadastroBanco();
           btnFavorito.setBackgroundColor(getResources().getColor(R.color.dorado));
           guardandoFavorito(true);
           Toast.makeText(this, R.string.add_favoritos,Toast.LENGTH_SHORT).show();
       }
       else {
           if (((ColorDrawable)btnWidget.getBackground()).getColor() == getResources().getColor(R.color.colorPrimaryLight)) {
               if (!deletarCadastroBanco(String.valueOf(recetas.getId()))) return;
               btnFavorito.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
               guardandoFavorito(false);
               Toast.makeText(this, R.string.delet_favoritos,Toast.LENGTH_SHORT).show();
           } else {
               if (!deletarCadastroBanco(String.valueOf(recetas.getId()))) return;
               btnFavorito.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
               guardandoFavorito(false);
               btnWidget.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
               guardandoFavorito(false);
               Toast.makeText(this, R.string.msg_delet_fav_and_widget,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, R.string.add_widget_favoritos,Toast.LENGTH_SHORT).show();
            } else {
                guardandoWidget(true);
                btnWidget.setBackgroundColor(getResources().getColor(R.color.dorado));
                Toast.makeText(this, R.string.msg_add_widget,Toast.LENGTH_SHORT).show();
            }
        }
        else {
            controlFavorito();
            if(controlFavorito) {
                guardandoWidget(false);
                btnWidget.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                Toast.makeText(this, R.string.msg_delet_widget,Toast.LENGTH_SHORT).show();
            } else {
                if (!deletarCadastroBanco(String.valueOf(recetas.getId()))) return;
                btnFavorito.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                btnWidget.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                guardandoFavorito(false);
                guardandoWidget(false);
                Toast.makeText(this, R.string.msg_delet_fav_and_widget,Toast.LENGTH_SHORT).show();
            }
        }
        atualizarWidget();
    }

    private void layoutIngredientes() {
        mArrayIngredients = recetas.getIngredients();
        if(mArrayIngredients == null) {
            mArrayIngredients = new ArrayList<Ingredient>();
        }
        for (int i=0; i<mArrayIngredients.size(); i++){
            tvIngredients.append("- " + mArrayIngredients.get(i).getIngredient() + " (" + mArrayIngredients.get(i).getQuantity() + " " + mArrayIngredients.get(i).getMeasure() + ") \n");
        }
    }

    private void layoutSteps() {
        if(mSteps == null){
            mSteps = new ArrayList<Step>();
        }
        mAdapter = new StepsAdapter(getApplicationContext(),mSteps);
        mListSteps.setAdapter(mAdapter);
        if(mArraySteps == null) {
            mArraySteps = new ArrayList<Step>();
        }
        mArraySteps = recetas.getSteps();
        if(mArraySteps != null){
            mSteps.clear();
            mSteps.addAll(mArraySteps);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void focaScrollNoComenzo() {
        mScrollDetails.setFocusableInTouchMode(true);
        mScrollDetails.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
    }

    private void inserirCadastroBanco() {
        try {
            ContentProviderAccess.inserirContentProvider(this,recetas);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean deletarCadastroBanco(String id){
        boolean sucesso = false;
        try {
            sucesso = ContentProviderAccess.deletar(recetas,id,this);
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
        Context context = getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, IChefWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_item_widget);
    }

}
