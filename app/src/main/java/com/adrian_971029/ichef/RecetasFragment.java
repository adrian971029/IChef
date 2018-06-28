package com.adrian_971029.ichef;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class RecetasFragment extends Fragment {

    public RecetasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recetas, container, false);

        final ImageView imageView = (ImageView) rootView.findViewById(R.id.fragment_image_recetas);

        return rootView;
    }

}
