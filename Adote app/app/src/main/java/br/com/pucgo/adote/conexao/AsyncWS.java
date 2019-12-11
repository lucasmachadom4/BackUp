package br.com.pucgo.adote.conexao;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class AsyncWS extends AsyncTask<Void, Void, String> {

    private String json, comando;
    private final String WS_URL = "http://10.0.0.100:8081/adote/";

    public AsyncWS(String comando) {
        this.comando = comando;
    }

    @Override
    public String doInBackground(Void... voids) {
        try {
            json = getJSONObjectFromURL(WS_URL + comando + ".json");
        } catch (JSONException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(String json){
        if(json != null){
            //Usuario usuario = getTranslation(json, Usuario.class);
            Log.e("Pega JSON: ", json);
        }
    }
    //Obtem o texto JSON de uma determina URL
    public String getJSONObjectFromURL(String urlString) throws IOException, JSONException{
        String line, newjson = "";
        URL urls = new URL(urlString);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(urls.openStream(), "UTF-8")) ) {
            while((line = reader.readLine() ) != null ){
                newjson +=line;
            }
            return newjson;
        }
    }
    //Cria um objeto de uma String em json
    public  <T extends Object> T getTranslation(String json, Class<T> cl){
        return new Gson().fromJson(json, cl);
    }


}
