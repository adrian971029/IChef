package com.adrian_971029.ichef.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.adapter.RecetasAdapter;
import com.adrian_971029.ichef.model.Recetas;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_main)
    RecyclerView recyclerView;

    private List<Recetas> mRecetas;
    private ArrayAdapter<Recetas> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);



    }


}
