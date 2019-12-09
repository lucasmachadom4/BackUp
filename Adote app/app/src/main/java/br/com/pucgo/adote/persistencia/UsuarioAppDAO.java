package br.com.pucgo.adote.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuarioAppDAO extends SQLiteOpenHelper {

    private static final String NOME_BD = "br.com.pucgo.adote";
    private static final int VERSAO_BD = 1;

    public UsuarioAppDAO(Context ctx){
        super(ctx, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario(_id integer primary key autoincrement, email text not null, senha text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table usuario;");
        onCreate(db);
    }
}
