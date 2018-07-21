package com.adrian_971029.ichef.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.utils.Constants;

public class ImageRecetasFragment extends Fragment {

    private String name;

    public ImageRecetasFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_recetas,container,false);

        ImageView mImageRecetas = (ImageView) rootView.findViewById(R.id.image_receta_details);
        TextView mTextViewRecetas  = (TextView) rootView.findViewById(R.id.tv_recetas_name);

        switch (name) {
            case Constants.NUTELLA_PIE:
                mImageRecetas.setImageResource(R.drawable.nutella_pie);
                break;
            case Constants.BROWNIES:
                mImageRecetas.setImageResource(R.drawable.brownies);
                break;
            case Constants.YELLOW_CAKE:
                mImageRecetas.setImageResource(R.drawable.yellow_cake);
                break;
            case Constants.CHEESECAKE:
                mImageRecetas.setImageResource(R.drawable.cheesecake);
                break;
            default:
                break;
        }
        mTextViewRecetas.setText(name);

        return rootView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
