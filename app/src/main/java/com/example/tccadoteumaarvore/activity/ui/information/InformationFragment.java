package com.example.tccadoteumaarvore.activity.ui.information;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tccadoteumaarvore.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class InformationFragment extends Fragment{
    private Button btnResultado;
    private TextView textResultado;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnResultado.findViewById(R.id.btnRecuperar);
        textResultado.findViewById(R.id.txtInformation);

        btnResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTask task = new MyTask();
                String urlApi = "https://blockchain.info/ticker";
                task.execute(urlApi);
            }
        });
    }

    class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;

            try {

                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                // Recupera os dados em Bytes
                inputStream = conexao.getInputStream();

                //inputStreamReader lê os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader( inputStream );

                //Objeto utilizado para leitura dos caracteres do InpuStreamReader
                BufferedReader reader = new BufferedReader( inputStreamReader );
                buffer = new StringBuffer();
                String linha = "";

                while((linha = reader.readLine()) != null){
                    buffer.append( linha );
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            String objetoValor = null;
            String valorMoeda = null;
            String simbolo = null;

            try {
                JSONObject jsonObject = new JSONObject(resultado);
                objetoValor = jsonObject.getString("BRL");

                JSONObject jsonObjectReal = new JSONObject(objetoValor);
                valorMoeda = jsonObjectReal.getString("last");
                simbolo = jsonObjectReal.getString("symbol");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            textResultado.setText(simbolo+" "+valorMoeda);
        }
    }
}