package com.cals.doctorcita.adaptador;

import android.content.Context;
import android.content.SharedPreferences;

import com.cals.doctorcita.entidad.Usuario;

public class UtilitarioManager {
    private static UtilitarioManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "citas";

    private UtilitarioManager(Context context) {
        mCtx = context;
    }

    public static synchronized UtilitarioManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UtilitarioManager(context);
        }
        return mInstance;
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
