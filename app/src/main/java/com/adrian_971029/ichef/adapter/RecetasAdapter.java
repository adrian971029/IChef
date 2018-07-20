package com.adrian_971029.ichef.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.activity.DetailsActivity;
import com.adrian_971029.ichef.model.Recetas;
import com.adrian_971029.ichef.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecetasAdapter extends ArrayAdapter<Recetas> {

    private List<Recetas> mListRecetas;
    private static final String RECETAS = "recetas";

    @Override
    public int getCount() {
        return mListRecetas.size();
    }

    public RecetasAdapter(Context context, List<Recetas> objects) {
        super(context,0, objects);
        mListRecetas = objects;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Recetas recetas = getItem(position);

        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_recetas,null);
            holder = new ViewHolder();
            holder.imgCapa = (ImageView)convertView.findViewById(R.id.image_receta);
            holder.mTitulo = (TextView)convertView.findViewById(R.id.tituloReceta);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        if(!recetas.getImage().equals("")){
            Glide.with(getContext()).load(recetas.getImage()).into(holder.imgCapa);
        } else {
            switch (recetas.getName()) {
                case Constants.NUTELLA_PIE:
                    Uri path = Uri.parse("android.resource://com.adrian_971029.ichef/" + R.drawable.nutella_pie);
                    Glide.with(getContext()).load(path).into(holder.imgCapa);
                    break;
                case Constants.BROWNIES:
                    Uri path1 = Uri.parse("android.resource://com.adrian_971029.ichef/" + R.drawable.brownies);
                    Glide.with(getContext()).load(path1).into(holder.imgCapa);
                    break;
                case Constants.YELLOW_CAKE:
                    Uri path2 = Uri.parse("android.resource://com.adrian_971029.ichef/" + R.drawable.yellow_cake);
                    Glide.with(getContext()).load(path2).into(holder.imgCapa);
                    break;
                case Constants.CHEESECAKE:
                    Uri path3 = Uri.parse("android.resource://com.adrian_971029.ichef/" + R.drawable.cheesecake);
                    Glide.with(getContext()).load(path3).into(holder.imgCapa);
                    break;
                default:
                    break;
            }
        }

        holder.mTitulo.setText(recetas.getName());

        holder.imgCapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DetailsActivity.class);
                i.putExtra(RECETAS,recetas);
                getContext().startActivity(i);
            }
        });

        holder.mTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DetailsActivity.class);
                i.putExtra(RECETAS,recetas);
                getContext().startActivity(i);
            }
        });


        return convertView;

    }

    static class ViewHolder{
        ImageView imgCapa;
        TextView mTitulo;
    }

}
