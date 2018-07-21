package com.adrian_971029.ichef.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.model.Ingredient;
import com.adrian_971029.ichef.model.Recetas;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientFragment extends Fragment {

    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;
    private ArrayList<Ingredient> mArrayIngredients;
    private Recetas recetas;

    public IngredientFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_ingredient,container,false);

        ButterKnife.bind(this,rootView);

        layoutIngredientes();

        return rootView;
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

}
