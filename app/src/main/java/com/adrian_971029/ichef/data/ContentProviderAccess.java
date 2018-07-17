package com.adrian_971029.ichef.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.adrian_971029.ichef.model.Ingredient;
import com.adrian_971029.ichef.model.Recetas;
import com.adrian_971029.ichef.model.Step;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderAccess {

    public static void inserirContentProvider(Context context, Recetas recetas) throws Exception {

        String jsonIngredient = new GsonBuilder().setPrettyPrinting().create().toJson(recetas.getIngredients());
        String jsonSteps = new GsonBuilder().setPrettyPrinting().create().toJson(recetas.getSteps());

        ContentValues values = new ContentValues();
        values.put(IChefContract.IChefEntry.COLUMN_RECETAS_ID,recetas.getId());
        values.put(IChefContract.IChefEntry.COLUMN_RECETAS_NAME,recetas.getName());
        values.put(IChefContract.IChefEntry.COLUMN_RECETAS_INGREDIENT,jsonIngredient);
        values.put(IChefContract.IChefEntry.COLUMN_RECETAS_STEPS,jsonSteps);
        values.put(IChefContract.IChefEntry.COLUMN_RECETAS_SERVING,recetas.getServings());
        values.put(IChefContract.IChefEntry.COLUMN_RECETAS_IMAGE,recetas.getImage());

        context.getContentResolver().insert(IChefContract.IChefEntry.CONTENT_URI, values);

    }

    public static List<Recetas> buscarTodos(Context context) {

        IChefDbHelper iChefDbHelper = new IChefDbHelper(context);
        final SQLiteDatabase db = iChefDbHelper.getReadableDatabase();

        List<Recetas> listaRecetas = new ArrayList<>();

        String[] columns = {IChefContract.IChefEntry.COLUMN_RECETAS_ID,
                IChefContract.IChefEntry.COLUMN_RECETAS_NAME,
                IChefContract.IChefEntry.COLUMN_RECETAS_INGREDIENT,
                IChefContract.IChefEntry.COLUMN_RECETAS_STEPS,
                IChefContract.IChefEntry.COLUMN_RECETAS_SERVING,
                IChefContract.IChefEntry.COLUMN_RECETAS_IMAGE,
        };

        Cursor cursor = db.query(IChefContract.IChefEntry.TABLE_NAME_RECETAS,columns,null,null,null,null,null);

        if (cursor.moveToFirst()) {

            do {

                String ingredient  = cursor.getString(cursor.getColumnIndex(columns[2]));
                String step  = cursor.getString(cursor.getColumnIndex(columns[3]));

                ArrayList<Ingredient> listaIngredientes = new Gson().fromJson(ingredient, new TypeToken<ArrayList<Ingredient>>() {}.getType());
                ArrayList<Step> listaPassos = new Gson().fromJson(step, new TypeToken<ArrayList<Step>>() {}.getType());

                Recetas recetas = new Recetas();
                recetas.setId(cursor.getInt(cursor.getColumnIndex(columns[0])));
                recetas.setName(cursor.getString(cursor.getColumnIndex(columns[1])));
                recetas.setIngredients(listaIngredientes);
                recetas.setSteps(listaPassos);
                recetas.setServings(cursor.getInt(cursor.getColumnIndex(columns[4])));
                recetas.setImage(cursor.getString(cursor.getColumnIndex(columns[5])));

                listaRecetas.add(recetas);

            }while (cursor.moveToNext());

            return listaRecetas;

        }

        return null;
    }

    public static boolean deletar(Recetas recetas, String id, Context context) throws Exception {

        IChefDbHelper iChefDbHelper = new IChefDbHelper(context);
        final SQLiteDatabase db = iChefDbHelper.getReadableDatabase();

        String idString = id;
        Uri uri = IChefContract.IChefEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idString).build();

        return context.getContentResolver().delete(uri,null,null) > 0;

    }

}
