package br.com.pucgo.adote.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.pucgo.adote.entidade.Usuario;

public class UsuarioAppDAOBD {
    private SQLiteDatabase bd;

    public UsuarioAppDAOBD(Context context){
        UsuarioAppDAO auxBd = new UsuarioAppDAO(context);
        bd = auxBd.getWritableDatabase();
    }

    public void inserir(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());

        bd.insert("usuario",null, values);
    }

    public void atualizar(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());

        bd.update("usuario", values, "_id = "+ usuario.getId() , null );
    }

    public void deletar(int id){
        bd.delete("usuario","_id = " + id ,null );
    }

    public List<Usuario> buscar(){
        List<Usuario> lista = new ArrayList<Usuario>();

        String[] colunas = new String[]{"_id", "email", "senha"};
        Cursor cursor = bd.query("usuario", colunas, null, null, null, null,"_id ASC");
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Usuario usuario = new Usuario();
                usuario.setId( cursor.getInt(0) );
                usuario.setEmail( cursor.getString(1) );
                usuario.setSenha( cursor.getString(2) );
                lista.add(usuario);

            }while (cursor.moveToNext());
        }
        return lista;
    }

    public Usuario buscarEmail(String email){
        Usuario usuario = new Usuario();
        //String[] colunas = new String[]{"_id", "email", "senha"};
        //Cursor cursor = bd.query("usuario", colunas, null, null, null, null,"nome ASC");
        Cursor cursor = bd.rawQuery("SELECT _id, email, senha FROM usuario WHERE email = ?", new String[] { email });

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            usuario.setId( cursor.getInt(0) );
            usuario.setEmail( cursor.getString(1) );
            usuario.setSenha( cursor.getString(2) );

        }
        return usuario;
    }


}
