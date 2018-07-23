package com.adrian_971029.ichef.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.activity.DetailsActivity;
import com.adrian_971029.ichef.adapter.StepsAdapter;
import com.adrian_971029.ichef.model.Recetas;
import com.adrian_971029.ichef.model.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsListFragment extends Fragment {

    private static final String RECETAS_STEPS = "recetas_steps";

    @BindView(R.id.listSteps)
    ListView mListSteps;
    private ArrayList<Step> mArraySteps;
    private List<Step> mSteps;
    private ArrayAdapter<Step> mAdapter;
    private Recetas recetas;
    private DetailsActivity detailsActivity;
    private boolean tablet;

    public StepsListFragment() {
    }

    public Recetas getRecetas() {
        return recetas;
    }

    public void setRecetas(Recetas recetas) {
        this.recetas = recetas;
    }

    public DetailsActivity getDetailsActivity() {
        return detailsActivity;
    }

    public void setDetailsActivity(DetailsActivity detailsActivity) {
        this.detailsActivity = detailsActivity;
    }

    public boolean isTablet() {
        return tablet;
    }

    public void setTablet(boolean tablet) {
        this.tablet = tablet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_list,container,false);

        ButterKnife.bind(this,rootView);

        if (savedInstanceState != null) {
            recetas = savedInstanceState.getParcelable(RECETAS_STEPS);
        }

        if (recetas != null) {
            layoutSteps();
        }

        return rootView;
    }

    private void layoutSteps() {
        if(mSteps == null){
            mSteps = new ArrayList<Step>();
        }
        mAdapter = new StepsAdapter(getContext(),mSteps,isTablet(),getDetailsActivity());
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(RECETAS_STEPS,recetas);
    }
}
