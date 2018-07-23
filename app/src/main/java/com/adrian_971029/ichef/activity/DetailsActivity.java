package com.adrian_971029.ichef.activity;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.adapter.StepsAdapter;
import com.adrian_971029.ichef.fragment.BtnFragments;
import com.adrian_971029.ichef.fragment.ImageRecetasFragment;
import com.adrian_971029.ichef.fragment.IngredientFragment;
import com.adrian_971029.ichef.fragment.StepsListFragment;
import com.adrian_971029.ichef.model.Recetas;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity {

    private static final String RECETAS = "recetas";

    @Nullable
    @BindView(R.id.scroll_details)
    ScrollView mScrollDetails;

    private Recetas recetas;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);


        if (findViewById(R.id.tablet_linear_layout) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

        recetas = new Recetas();
        recetas = getIntent().getExtras().getParcelable(RECETAS);

        criarFragments();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (findViewById(R.id.scroll_details) != null) {
            focaScrollNoComenzo();
        }
    }

    private void criarFragments() {
        ImageRecetasFragment imageRecetasFragment = new ImageRecetasFragment();
        imageRecetasFragment.setName(recetas.getName());

        BtnFragments btnFragments = new BtnFragments();
        btnFragments.setRecetas(recetas);

        IngredientFragment ingredientFragment = new IngredientFragment();
        ingredientFragment.setRecetas(recetas);

        StepsListFragment stepsListFragment = new StepsListFragment();
        stepsListFragment.setRecetas(recetas);
        stepsListFragment.setDetailsActivity(this);
        stepsListFragment.setTablet(mTwoPane);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.image_recetas_container, imageRecetasFragment)
                .commit();

        fragmentManager.beginTransaction()
                .add(R.id.btn_container, btnFragments)
                .commit();

        if (!StepsAdapter.isStepVisible()) {
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_container, ingredientFragment)
                    .commit();
        }

        fragmentManager.beginTransaction()
                .add(R.id.steps_list_container, stepsListFragment)
                .commit();

    }

    private void focaScrollNoComenzo() {
        mScrollDetails.setFocusableInTouchMode(true);
        mScrollDetails.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mTwoPane) {
            StepsAdapter.setStepVisible(false);
        }
    }
}
