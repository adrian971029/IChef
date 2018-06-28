package com.adrian_971029.ichef.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.interfaces.OnRecyclerClick;
import com.adrian_971029.ichef.model.Recetas;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecetasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnRecyclerClick onItemClick;
    private List<Recetas> mListRecetas;
    private Context context;

    public RecetasAdapter(OnRecyclerClick onItemClick, List<Recetas> mListRecetas, Context context) {
        this.onItemClick = onItemClick;
        this.mListRecetas = mListRecetas;
        this.context = context;
        notifyDataSetChanged();
    }

    public void add(List<Recetas> recipe) {
        mListRecetas.addAll(recipe);
        notifyDataSetChanged();
    }

    public void clear() {
        mListRecetas.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recetas, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder item = (ItemHolder) holder;
            Recetas recetas = mListRecetas.get(position);
            item.textName.setText(recetas.getName());
            Picasso.with(context).load(recetas.getImage()).into(item.imageReceta);
        }
    }

    @Override
    public int getItemCount() {
        return mListRecetas.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageReceta;
        TextView textName;

        public ItemHolder(View itemView) {
            super(itemView);
            imageReceta = (ImageView) itemView.findViewById(R.id.image_receta);
            textName = (TextView) itemView.findViewById(R.id.tituloReceta);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClick.onItemClick(mListRecetas.get(getAdapterPosition()));
        }
    }

}
