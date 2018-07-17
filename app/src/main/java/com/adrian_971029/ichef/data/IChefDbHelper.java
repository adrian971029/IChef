package com.adrian_971029.ichef.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IChefDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ichef.db";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_RECETAS = "CREATE TABLE " +
            IChefContract.IChefEntry.TABLE_NAME_RECETAS + " (" +
            IChefContract.IChefEntry.COLUMN_RECETAS_ID + " INTEGER PRIMARY KEY, " +
            IChefContract.IChefEntry.COLUMN_RECETAS_NAME + " TEXT NOT NULL, " +
            IChefContract.IChefEntry.COLUMN_RECETAS_SERVING + " INTEGER NOT NULL, " +
            IChefContract.IChefEntry.COLUMN_RECETAS_IMAGE + " TEXT" +
            "); ";

    private static final String SQL_CREATE_TABLE_INGREDIENTES = "CREATE TABLE " +
            IChefContract.IChefEntry.TABLE_NAME_INGREDIENTES + " (" +
            IChefContract.IChefEntry.COLUMN_INGREDIENTES_RECETAS + " TEXT NOT NULL, " +
            IChefContract.IChefEntry.COLUMN_INGREDIENTES_QUANTITY + " REAL NOT NULL, " +
            IChefContract.IChefEntry.COLUMN_INGREDIENTES_MEASURE + " TEXT NOT NULL, " +
            IChefContract.IChefEntry.COLUMN_INGREDIENTES_INGREDIENT + " TEXT NOT NULL, " +
            IChefContract.IChefEntry.COLUMN_RECETAS_IMAGE + " TEXT" +
            "); ";

    private static final String SQL_CREATE_TABLE_PASSOS = "CREATE TABLE " +
            IChefContract.IChefEntry.TABLE_NAME_PASSOS + " (" +
            IChefContract.IChefEntry.COLUMN_PASSOS_ID + " INTEGER PRIMARY KEY, " +
            IChefContract.IChefEntry.COLUMN_PASSOS_SHORT_DESCRIPTIONS + " TEXT NOT NULL, " +
            IChefContract.IChefEntry.COLUMN_PASSOS_DESCRIPTIONS + " TEXT NOT NULL, " +
            IChefContract.IChefEntry.COLUMN_PASSOS_VIDEO_URL + " TEXT, " +
            IChefContract.IChefEntry.COLUMN_PASSOS_THUMBNAIL_URL + " TEXT" +
            "); ";

    public IChefDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_RECETAS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_INGREDIENTES);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PASSOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IChefContract.IChefEntry.TABLE_NAME_RECETAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IChefContract.IChefEntry.TABLE_NAME_INGREDIENTES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IChefContract.IChefEntry.TABLE_NAME_PASSOS);
    }

}
