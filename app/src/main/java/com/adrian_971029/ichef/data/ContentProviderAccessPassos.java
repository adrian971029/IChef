package com.adrian_971029.ichef.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.adrian_971029.ichef.model.Recetas;
import com.adrian_971029.ichef.model.Step;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderAccessPassos {

    public static void inserirContentProvider(Context context, Step step) throws Exception {
        ContentValues values = new ContentValues();
        values.put(IChefContract.IChefEntry.COLUMN_PASSOS_ID,step.getId());
        values.put(IChefContract.IChefEntry.COLUMN_PASSOS_SHORT_DESCRIPTIONS,step.getShortDescription());
        values.put(IChefContract.IChefEntry.COLUMN_PASSOS_DESCRIPTIONS,step.getDescription());
        values.put(IChefContract.IChefEntry.COLUMN_PASSOS_VIDEO_URL,step.getVideoURL());
        values.put(IChefContract.IChefEntry.COLUMN_PASSOS_THUMBNAIL_URL,step.getThumbnailURL());

        context.getContentResolver().insert(IChefContract.IChefEntry.CONTENT_URI, values);

    }

    public static List<Step> buscarTodos(Context context) {

        IChefDbHelper iChefDbHelper = new IChefDbHelper(context);
        final SQLiteDatabase db = iChefDbHelper.getReadableDatabase();

        List<Step> listaSteps = new ArrayList<>();

        String[] columns = {IChefContract.IChefEntry.COLUMN_PASSOS_ID,
                IChefContract.IChefEntry.COLUMN_PASSOS_SHORT_DESCRIPTIONS,
                IChefContract.IChefEntry.COLUMN_PASSOS_DESCRIPTIONS,
                IChefContract.IChefEntry.COLUMN_PASSOS_VIDEO_URL,
                IChefContract.IChefEntry.COLUMN_PASSOS_THUMBNAIL_URL,
        };

        Cursor cursor = db.query(IChefContract.IChefEntry.TABLE_NAME_PASSOS,columns,null,null,null,null,null);

        if (cursor.moveToFirst()) {

            do {
                Step step = new Step();
                step.setId(cursor.getInt(cursor.getColumnIndex(columns[0])));
                step.setShortDescription(cursor.getString(cursor.getColumnIndex(columns[1])));
                step.setDescription(cursor.getString(cursor.getColumnIndex(columns[2])));
                step.setVideoURL(cursor.getString(cursor.getColumnIndex(columns[3])));
                step.setThumbnailURL(cursor.getString(cursor.getColumnIndex(columns[4])));

                listaSteps.add(step);

            }while (cursor.moveToNext());

            return listaSteps;

        }

        return null;
    }

    public static boolean deletar(Step step, String id, Context context) throws Exception {

        IChefDbHelper iChefDbHelper = new IChefDbHelper(context);
        final SQLiteDatabase db = iChefDbHelper.getReadableDatabase();

        String idString = id;
        Uri uri = IChefContract.IChefEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(idString).build();

        return context.getContentResolver().delete(uri,null,null) > 0;

    }

}
