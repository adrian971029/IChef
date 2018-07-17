package com.adrian_971029.ichef.activity;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
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
import com.adrian_971029.ichef.model.Ingredient;
import com.adrian_971029.ichef.model.Recetas;
import com.adrian_971029.ichef.model.Step;
import com.adrian_971029.ichef.utils.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends BaseActivity {

    private static final String RECETAS = "recetas";

    @BindView(R.id.image_receta_details)
    ImageView imageViewRecetas;
    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;
    @BindView(R.id.tv_recetas_name)
    TextView tvRecetasName;
    @BindView(R.id.listSteps)
    ListView mListSteps;
    @BindView(R.id.scroll_details)
    ScrollView mScrollDetails;
    @BindView(R.id.btn_favorito)
    Button btnFavorito;

    private Recetas recetas;
    private ArrayList<Ingredient> mArrayIngredients;
    private ArrayList<Step> mArraySteps;
    private List<Step> mSteps;
    private ArrayAdapter<Step> mAdapter;
    private boolean controlFavorito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        recetas = new Recetas();
        recetas = getIntent().getExtras().getParcelable(RECETAS);
        controlFavorito = false;

        defineImagenReceta();
        layoutIngredientes();
        layoutSteps();
        controlFavorito();


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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        controlFavorito();
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
           if (!deletarCadastroBanco(String.valueOf(recetas.getId()))) return;
           btnFavorito.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
           guardandoFavorito(false);
           Toast.makeText(this, R.string.delet_favoritos,Toast.LENGTH_SHORT).show();
       }
    }

    private void defineImagenReceta() {
        switch (recetas.getName()) {
            case Constants.NUTELLA_PIE:
                imageViewRecetas.setImageResource(R.drawable.nutella_pie);
                break;
            case Constants.BROWNIES:
                imageViewRecetas.setImageResource(R.drawable.brownies);
                break;
            case Constants.YELLOW_CAKE:
                imageViewRecetas.setImageResource(R.drawable.yellow_cake);
                break;
            case Constants.CHEESECAKE:
                imageViewRecetas.setImageResource(R.drawable.cheesecake);
                break;
            default:
                break;
        }
        tvRecetasName.setText(recetas.getName().toString());
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

}
