package com.adrian_971029.ichef.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.model.Ingredient;
import com.adrian_971029.ichef.model.Recetas;
import com.adrian_971029.ichef.utils.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    private static final String RECETAS = "recetas";

    @BindView(R.id.image_receta_details)
    ImageView imageViewRecetas;
    @BindView(R.id.tv_ingredients)
    TextView tvIngredients;
    @BindView(R.id.tv_recetas_name)
    TextView tvRecetasName;
    @BindView(R.id.listSteps)
    ListView mListSteps;

    private Recetas recetas;
    private ArrayList<Ingredient> mArrayIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        recetas = new Recetas();
        recetas = getIntent().getExtras().getParcelable(RECETAS);

        defineImagenReceta();
        layoutIngredientes();

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

    private void layoutIngredientes(){
        mArrayIngredients = recetas.getIngredients();
        if(mArrayIngredients == null) {
            mArrayIngredients = new ArrayList<Ingredient>();
        }
        for (int i=0; i<mArrayIngredients.size(); i++){
            tvIngredients.append("- " + mArrayIngredients.get(i).getIngredient() + " (" + mArrayIngredients.get(i).getQuantity() + " " + mArrayIngredients.get(i).getMeasure() + ") \n");
        }
    }

}
