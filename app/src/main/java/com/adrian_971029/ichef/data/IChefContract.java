package com.adrian_971029.ichef.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class IChefContract {

    public static final String AUTHORITY = "com.adrian_971029.ichef";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_ICHEF = "ichef";

    public static class IChefEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ICHEF).build();

        public static final String TABLE_NAME_RECETAS = "recetas";
        public static final String COLUMN_RECETAS_ID = "recetas_id";
        public static final String COLUMN_RECETAS_NAME = "name";
        public static final String COLUMN_RECETAS_SERVING = "serving";
        public static final String COLUMN_RECETAS_IMAGE = "image";

        public static final String TABLE_NAME_INGREDIENTES = "ingredientes";
        public static final String COLUMN_INGREDIENTES_RECETAS= "ingredientes_recetas";
        public static final String COLUMN_INGREDIENTES_QUANTITY = "quantity";
        public static final String COLUMN_INGREDIENTES_MEASURE = "measure";
        public static final String COLUMN_INGREDIENTES_INGREDIENT = "ingredient";

        public static final String TABLE_NAME_PASSOS = "passos";
        public static final String COLUMN_PASSOS_ID = "passos_id";
        public static final String COLUMN_PASSOS_SHORT_DESCRIPTIONS = "shortDescription";
        public static final String COLUMN_PASSOS_DESCRIPTIONS = "description";
        public static final String COLUMN_PASSOS_VIDEO_URL = "videoURL";
        public static final String COLUMN_PASSOS_THUMBNAIL_URL = "thumbnailURL";

    }

}
