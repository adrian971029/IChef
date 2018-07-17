package com.adrian_971029.ichef.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.adrian_971029.ichef.R;

public class IChefContentProvider extends ContentProvider {

    private IChefDbHelper iChefDbHelper;

    private static final int RECETAS          = 300;
    private static final int RECETAS_WITH_ID  = 301;
    private static final int INGREDIENTES          = 400;
    private static final int INGREDIENTES_WIHT_ID  = 401;
    private static final int PASSOS          = 500;
    private static final int PASSOS_WITH_ID  = 501;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        String with_id = "/#";
        String path_with_id = IChefContract.PATH_ICHEF + with_id;

        uriMatcher.addURI(IChefContract.AUTHORITY, IChefContract.PATH_ICHEF, RECETAS);
        uriMatcher.addURI(IChefContract.AUTHORITY, path_with_id, RECETAS_WITH_ID);
        uriMatcher.addURI(IChefContract.AUTHORITY, IChefContract.PATH_ICHEF, INGREDIENTES);
        uriMatcher.addURI(IChefContract.AUTHORITY, path_with_id, INGREDIENTES_WIHT_ID);
        uriMatcher.addURI(IChefContract.AUTHORITY, IChefContract.PATH_ICHEF, PASSOS);
        uriMatcher.addURI(IChefContract.AUTHORITY, path_with_id, PASSOS_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        iChefDbHelper = new IChefDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = iChefDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor cursor;


        switch (match) {
            case RECETAS:
                cursor = db.query(IChefContract.IChefEntry.TABLE_NAME_RECETAS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case INGREDIENTES:
                cursor = db.query(IChefContract.IChefEntry.TABLE_NAME_INGREDIENTES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case PASSOS:
                cursor = db.query(IChefContract.IChefEntry.TABLE_NAME_PASSOS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.uri_desconhecida) + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = iChefDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case RECETAS:
                long idReceta = db.insert(IChefContract.IChefEntry.TABLE_NAME_RECETAS, null, contentValues);
                if (idReceta > 0) {
                    returnUri = ContentUris.withAppendedId(IChefContract.IChefEntry.CONTENT_URI, idReceta);
                } else {
                    throw new android.database.SQLException(getContext().getString(R.string.error_inserir) + uri);
                }
                break;
            case INGREDIENTES:
                long idIngredientes = db.insert(IChefContract.IChefEntry.TABLE_NAME_INGREDIENTES, null, contentValues);
                if (idIngredientes > 0) {
                    returnUri = ContentUris.withAppendedId(IChefContract.IChefEntry.CONTENT_URI, idIngredientes);
                } else {
                    throw new android.database.SQLException(getContext().getString(R.string.error_inserir) + uri);
                }
                break;
            case PASSOS:
                long idPassos = db.insert(IChefContract.IChefEntry.TABLE_NAME_PASSOS, null, contentValues);
                if (idPassos > 0) {
                    returnUri = ContentUris.withAppendedId(IChefContract.IChefEntry.CONTENT_URI, idPassos);
                } else {
                    throw new android.database.SQLException(getContext().getString(R.string.error_inserir) + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.uri_desconhecida) + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = iChefDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);


        int movieDeleted;

        switch (match) {
            case RECETAS_WITH_ID:
                String idRecetas = uri.getPathSegments().get(1);
                movieDeleted = db.delete(IChefContract.IChefEntry.TABLE_NAME_RECETAS, IChefContract.IChefEntry.COLUMN_RECETAS_ID + "=?", new String[]{idRecetas});
                break;
            case INGREDIENTES_WIHT_ID:
                String idIngredientes = uri.getPathSegments().get(1);
                movieDeleted = db.delete(IChefContract.IChefEntry.TABLE_NAME_INGREDIENTES, IChefContract.IChefEntry.COLUMN_INGREDIENTES_ID + "=?", new String[]{idIngredientes});
                break;
            case PASSOS_WITH_ID:
                String idPassos = uri.getPathSegments().get(1);
                movieDeleted = db.delete(IChefContract.IChefEntry.TABLE_NAME_PASSOS, IChefContract.IChefEntry.COLUMN_PASSOS_ID + "=?", new String[]{idPassos});
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.uri_desconhecida) + uri);
        }

        if (movieDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return movieDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}
