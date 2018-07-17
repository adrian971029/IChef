package com.adrian_971029.ichef.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.adrian_971029.ichef.model.Ingredient;
import com.adrian_971029.ichef.model.Recetas;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderAccessIngredientes {

    public static void inserirContentProvider(Context context, Ingredient ingredient, String recetasName) throws Exception {
        ContentValues values = new ContentValues();
        values.put(IChefContract.IChefEntry.COLUMN_INGREDIENTES_RECETAS,recetasName);
        values.put(IChefContract.IChefEntry.COLUMN_INGREDIENTES_QUANTITY,ingredient.getQuantity());
        values.put(IChefContract.IChefEntry.COLUMN_INGREDIENTES_MEASURE,ingredient.getMeasure());
        values.put(IChefContract.IChefEntry.COLUMN_INGREDIENTES_INGREDIENT,ingredient.getIngredient());

        context.getContentResolver().insert(IChefContract.IChefEntry.CONTENT_URI, values);

    }

    public static List<Ingredient> buscarTodos(Context context, String recetasName) {

        IChefDbHelper iChefDbHelper = new IChefDbHelper(context);
        final SQLiteDatabase db = iChefDbHelper.getReadableDatabase();

        List<Ingredient> listaIngredientes = new ArrayList<>();

        String[] columns = {IChefContract.IChefEntry.COLUMN_INGREDIENTES_RECETAS,
                IChefContract.IChefEntry.COLUMN_INGREDIENTES_QUANTITY,
                IChefContract.IChefEntry.COLUMN_INGREDIENTES_MEASURE,
                IChefContract.IChefEntry.COLUMN_INGREDIENTES_INGREDIENT,
        };

        Cursor cursor = db.query(IChefContract.IChefEntry.TABLE_NAME_INGREDIENTES,columns,null,null,null,null,null);

        if (cursor.moveToFirst()) {

            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setName(cursor.getString(cursor.getColumnIndex(columns[0])));
                ingredient.setQuantity(cursor.getInt(cursor.getColumnIndex(columns[1])));
                ingredient.setMeasure(cursor.getString(cursor.getColumnIndex(columns[2])));
                ingredient.setIngredient(cursor.getString(cursor.getColumnIndex(columns[3])));

                listaIngredientes.add(ingredient);

            }while (cursor.moveToNext());

            return listaIngredientes;

        }

        return null;
    }

    public static boolean deletar(Ingredient ingredient, String id, Context context) throws Exception {

        IChefDbHelper iChefDbHelper = new IChefDbHelper(context);
        final SQLiteDatabase db = iChefDbHelper.getReadableDatabase();

        String idString = id;
        Uri uri = IChefContract.IChefEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idString).build();

        return context.getContentResolver().delete(uri,null,null) > 0;

    }

}
