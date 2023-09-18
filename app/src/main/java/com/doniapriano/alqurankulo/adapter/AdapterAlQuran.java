package com.doniapriano.alqurankulo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doniapriano.alqurankulo.R;
import com.doniapriano.alqurankulo.activities.MainActivity;
import com.doniapriano.alqurankulo.activities.SurahActivity;
import com.doniapriano.alqurankulo.models.QuranModel;

import java.util.List;

public class AdapterAlQuran extends RecyclerView.Adapter<AdapterAlQuran.HolderData> {

    private Context context;
    private List<QuranModel> listQuran;

    public AdapterAlQuran(Context context, List<QuranModel> listQuran) {
        this.context = context;
        this.listQuran = listQuran;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_quran, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        holder.tvSurah.setText(listQuran.get(position).getSurah());
        holder.idSurah.setText(listQuran.get(position).getId());
        holder.btnSurah.setOnClickListener(i -> {
            Intent intent = new Intent(context, SurahActivity.class);
            intent.putExtra("id", listQuran.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listQuran.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvSurah;
        TextView idSurah;
        LinearLayout btnSurah;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvSurah = itemView.findViewById(R.id.tv_surah);
            btnSurah = itemView.findViewById(R.id.btn_surah);
            idSurah = itemView.findViewById(R.id.tv_number_surah);
        }
    }
}
