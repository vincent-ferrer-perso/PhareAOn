package com.example.phareaon;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class JsonParsing extends AsyncTask<Void,Void,Void> {
    private static final String urlString = "http://www.laurent-freund.fr/cours/android/phares/versions.json";
    private String burredData = "";
    private String finalData;

    @Override
    protected Void doInBackground(Void... voids) {
        URL url = null;
        try {
            url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line!=null){
                line = bufferedReader.readLine();
                burredData=burredData+line;
            }
            JSONObject jsonObject = new JSONObject(burredData);
            JSONObject jsonVersion = jsonObject.getJSONObject("version");
            finalData = "Dernière mise à jour de la BD : " +jsonVersion.get("date") + "\n" +
                    "Auteur : " + jsonVersion.get("author") + "\n" +
                    "Nom du fichier : " + jsonVersion.get("filename");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(MainActivity.getContext(),
                finalData,Toast.LENGTH_SHORT).show();

    }
}
