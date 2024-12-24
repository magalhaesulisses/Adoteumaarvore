package com.example.adoteumarvore;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class dbConnect extends SQLiteOpenHelper {

    private static final String dbName = "adotearvore";
    private static final String dbTableUsers = "Usuarios";
    private static final int dbVersion = 1;

    public dbConnect(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query =  "CREATE TABLE IF NOT EXISTS "+dbName+"."+dbTableUsers+" ( "+
                "       id INTEGER PRIMARY KEY AUTOINCREMENT,             "+
                "       nome TEXT NOT NULL,                               "+
                "       sobrenome TEXT NOT NULL,                          "+
                "       login TEXT NOT NULL UNIQUE,                       "+
                "       datanascimento TEXT NOT NULL,                     "+
                "       email TEXT NOT NULL UNIQUE,                       "+
                "       fone TEXT NOT NULL,                               "+
                "       senha TEXT NOT NULL);                             ";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + dbTableUsers);
    }

    public void addUsuario(Usuarios usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", usuario.getNome());
        values.put("sobrenome", usuario.getSobrenome());
        values.put("login", usuario.getLogin());
        values.put("data", usuario.getDatanascimento().toString());
        values.put("email", usuario.getEmail());
        values.put("fone", usuario.getFone());
        values.put("senha", usuario.getSenha());
        db.insert(dbTableUsers, null, values);
    }
}
