package com.example.jsonapirestfulconparmetrosenelheader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListadoActivity extends AppCompatActivity {
    private ListView lvDatos;
    private RequestQueue mQueuE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        lvDatos = (ListView) findViewById(R.id.lvDatos);
        //mQueue = Volley.newRequestQueue(this);
        GETVolley();
    }

    private void GETVolley(){
        String llave="F2HPyB8C8f2XDadCHaahczA5GPGYg0S3cLbi7hMC";
        String DATA_URL = "https://api.nasa.gov/planetary/apod?api_key=F2HPyB8C8f2XDadCHaahczA5GPGYg0S3cLbi7hMC";
        String URL="https://api.nasa.gov/planetary/apod";
        final ProgressDialog loading = ProgressDialog.show(this, "Por favor espere...", "Actualizando datos",false,false);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, DATA_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loading.dismiss();
                showListView(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error request:"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {
            //@org.jetbrains.annotations.NotNull
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                //  headers.put("name", "public-merchant-id");
                headers.put("PrivateKey", "84e1d0de1fbf437e9779fd6a52a9ca18");
                return headers;
            }
        };
        //mQueue.add(jsonObjectRequest);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    private void showListView(JSONObject obj){
        try {
            List<String> contes = new ArrayList<String>();
            JSONArray lista = obj.optJSONArray("data");
            for(int i=0; i<lista.length();i++){
                JSONObject json_data = lista.getJSONObject(i);
                String conte =  "Codigo: " + json_data.getString("id")+"\n"+
                        "Nombre: " + json_data.getString("name")+"\n"+
                        "Email: "  + json_data.getString("email")+"\n"+
                        "Genero: " + json_data.getString("gender")+"\n"+
                        "Estado: " + json_data.getString("status")+"\n\n";;
                contes.add(conte);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,contes);
            lvDatos.setAdapter(adapter);

        } catch (Exception ex) {
            Toast.makeText(this,"Error al cargar Lista" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
        }


    }
}