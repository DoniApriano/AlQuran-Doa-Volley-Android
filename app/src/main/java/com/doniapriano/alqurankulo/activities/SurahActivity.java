package com.doniapriano.alqurankulo.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.doniapriano.alqurankulo.R;
import com.doniapriano.alqurankulo.adapter.AdapterAlQuran;
import com.doniapriano.alqurankulo.adapter.AdapterSurah;
import com.doniapriano.alqurankulo.connection.VolleyConnection;
import com.doniapriano.alqurankulo.db.DB;
import com.doniapriano.alqurankulo.models.QuranModel;
import com.doniapriano.alqurankulo.models.SurahModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SurahActivity extends AppCompatActivity {

    RecyclerView rvData;
    RecyclerView.Adapter adData;
    List<SurahModel> listSurah = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surah);

        rvData = findViewById(R.id.rv_surah);

        String idSurah = getIntent().getStringExtra("id");
        String urlSurah = DB.URL_QURAN + "/" + idSurah;
        System.out.println(urlSurah);

        urlShow(urlSurah);
    }

    private void urlShow(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject dataObject = response.getJSONObject("data");
                            JSONArray versesArray = dataObject.getJSONArray("verses");
                            for (int i = 0; i < versesArray.length(); i++) {
                                JSONObject verseObject = versesArray.getJSONObject(i);
                                JSONObject textObject = verseObject.getJSONObject("text");
                                JSONObject number = verseObject.getJSONObject("number");
                                int noAyat = number.getInt("inSurah");
                                String arab = textObject.getString("arab");
                                JSONObject translationObject = verseObject.getJSONObject("translation");
                                String arti = translationObject.getString("id");
                                SurahModel surahModel = new SurahModel();

                                surahModel.setAyat(arab);
                                surahModel.setArti(arti);
                                surahModel.setNoAyat(String.valueOf(noAyat));

                                listSurah.add(surahModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        layoutManager = new LinearLayoutManager(SurahActivity.this, LinearLayoutManager.VERTICAL, false);
                        rvData.setLayoutManager(layoutManager);
                        adData = new AdapterSurah(SurahActivity.this,listSurah);
                        rvData.setAdapter(adData);
                        adData.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        VolleyConnection.getInstance(SurahActivity.this).addRequestQueue(jsonObjectRequest);
    }
}