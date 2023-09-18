package com.doniapriano.alqurankulo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doniapriano.alqurankulo.R;
import com.doniapriano.alqurankulo.models.QuranModel;
import com.doniapriano.alqurankulo.models.SurahModel;

import java.util.List;

public class AdapterSurah extends RecyclerView.Adapter<AdapterSurah.HolderData> {

    List<SurahModel> listSurah;
    Context context;

    public AdapterSurah(Context context, List<SurahModel> listSurah) {
        this.listSurah = listSurah;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSurah.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_surah, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSurah.HolderData holder, int position) {
        holder.ayat.setText(listSurah.get(position).getAyat());
        holder.noAyat.setText(listSurah.get(position).getNoAyat());
        holder.arti.setText(listSurah.get(position).getArti());
    }

    @Override
    public int getItemCount() {
        return listSurah.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        TextView ayat,noAyat,arti;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            arti = itemView.findViewById(R.id.tv_arti);
            ayat = itemView.findViewById(R.id.tv_ayat);
            noAyat = itemView.findViewById(R.id.tv_no_ayat);
        }
    }
}
