package com.example.assiduity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Scan_adapter extends RecyclerView.Adapter<Scan_adapter.MyViewHolder>{
    private Context context;
    private ArrayList<savescan> saveModelList;

    public Scan_adapter(history context, ArrayList<savescan> s){
        this.context = context;
        saveModelList = s;
    }

    public void add(savescan savescan){
        saveModelList.add(savescan);
        notifyDataSetChanged();
    }

    public  void clear(){
        saveModelList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Scan_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.saves,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Scan_adapter.MyViewHolder holder, int position) {
        savescan savescan = saveModelList.get(position);
        holder.nom.setText(savescan.getSaves());
        holder.date.setText(savescan.getDate());

    }

    @Override
    public int getItemCount() {
        return saveModelList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nom, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nom = itemView.findViewById(R.id.textview);
            date = itemView.findViewById(R.id.date);

        }
    }
}
