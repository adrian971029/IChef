package com.adrian_971029.ichef.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.adrian_971029.ichef.activity.BaseActivity;
import com.adrian_971029.ichef.data.ContentProviderAccess;
import com.adrian_971029.ichef.model.Ingredient;
import com.adrian_971029.ichef.model.Recetas;

import java.util.ArrayList;

public class WidgetData extends BaseActivity implements RemoteViewsService.RemoteViewsFactory  {

    private static final String TAG = WidgetData.class.getSimpleName();
    private static final String RECETAS = "recetas";
    private static final String RECETAS_ID = "recetas_id";

    private ContentProviderAccess providerAccess;
    private Recetas recetas;
    private ArrayList<Ingredient> mArrayIngredients;
    private Context ctx;
    private Intent intent;

    public WidgetData(Context ctx, Intent intent) {
        this.ctx = ctx;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        recebeDados();
    }

    @Override
    public void onDataSetChanged() {
        recebeDados();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (recetas.getIngredients() != null) {
            return recetas.getIngredients().size();
        } else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int pos) {
        RemoteViews view = new RemoteViews(ctx.getPackageName(), android.R.layout.simple_list_item_1);

        mArrayIngredients = recetas.getIngredients();

        view.setTextViewText(android.R.id.text1, String.format("%s %s %s", recetas.getIngredients().get(pos).getQuantity(), recetas.getIngredients().get(pos).getMeasure(), recetas.getIngredients().get(pos).getIngredient()));
        Bundle extras = new Bundle();
        extras.putParcelable(RECETAS, recetas);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        view.setOnClickFillInIntent(android.R.id.text1, fillInIntent);


        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void recebeDados() {

        recetas = new Recetas();
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("widget", Context.MODE_PRIVATE);

        if(sharedPreferences != null) {
            if (sharedPreferences.getString(RECETAS_ID,null) != null) {
                String id = sharedPreferences.getString(RECETAS_ID,null);
                recetas = providerAccess.buscar(ctx,id);
            }
        }
    }

}
