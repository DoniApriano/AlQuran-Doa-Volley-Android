package com.doniapriano.alqurankulo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.doniapriano.alqurankulo.R;
import com.doniapriano.alqurankulo.adapter.AdapterAlQuran;
import com.doniapriano.alqurankulo.connection.VolleyConnection;
import com.doniapriano.alqurankulo.db.DB;
import com.doniapriano.alqurankulo.models.QuranModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuranFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuranFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rvData;
    RecyclerView.Adapter adData;
    List<QuranModel> listQuran = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    public QuranFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuranFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuranFragment newInstance(String param1, String param2) {
        QuranFragment fragment = new QuranFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quran, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvData = view.findViewById(R.id.rv_data);

        showData(DB.URL_QURAN);
    }

    private void showData(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray dataArray = response.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject surahObject = dataArray.getJSONObject(i);
                            int surahNumber = surahObject.getInt("number");
                            JSONObject translationObject = surahObject.getJSONObject("name").getJSONObject("transliteration");
                            String translationId = translationObject.getString("id");
                            QuranModel quranModel = new QuranModel();
                            quranModel.setSurah(translationId);
                            quranModel.setId(String.valueOf(surahNumber));
                            listQuran.add(quranModel);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    rvData.setLayoutManager(layoutManager);
                    adData = new AdapterAlQuran(getContext(),listQuran);
                    rvData.setAdapter(adData);
                    adData.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                System.err.println(error);
            }
            }
        );

        VolleyConnection.getInstance(getActivity()).addRequestQueue(jsonObjectRequest);
    }
}