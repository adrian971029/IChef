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
import com.adrian_971029.ichef.fragment.BtnFragments;
import com.adrian_971029.ichef.fragment.ImageRecetasFragment;
import com.adrian_971029.ichef.fragment.IngredientFragment;
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

public class DetailsActivity extends BaseActivity {

    private static final String RECETAS = "recetas";

    @BindView(R.id.listSteps)
    ListView mListSteps;
    @BindView(R.id.scroll_details)
    ScrollView mScrollDetails;

    private Recetas recetas;
    private ArrayList<Step> mArraySteps;
    private List<Step> mSteps;
    private ArrayAdapter<Step> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        recetas = new Recetas();
        recetas = getIntent().getExtras().getParcelable(RECETAS);

        criarFragments();
        layoutSteps();

    }

    @Override
    protected void onStart() {
        super.onStart();
        focaScrollNoComenzo();
    }

    private void criarFragments() {
        ImageRecetasFragment imageRecetasFragment = new ImageRecetasFragment();
        imageRecetasFragment.setName(recetas.getName());

        BtnFragments btnFragments = new BtnFragments();
        btnFragments.setRecetas(recetas);

        IngredientFragment ingredientFragment = new IngredientFragment();
        ingredientFragment.setRecetas(recetas);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.image_recetas_container, imageRecetasFragment)
                .commit();

        fragmentManager.beginTransaction()
                .add(R.id.btn_container, btnFragments)
                .commit();

        fragmentManager.beginTransaction()
                .add(R.id.ingredient_container, ingredientFragment)
                .commit();

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

}
