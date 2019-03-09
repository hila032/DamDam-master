package com.example.salon.myapplication.activities;

import android.content.Context;
import android.icu.text.AlphabeticIndex;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.RecordPlayer;

import java.util.ArrayList;

public class PlayerAdapter  extends ArrayAdapter<RecordPlayer> {

    private ArrayList<RecordPlayer> list;
    private Context context;
    public PlayerAdapter(@NonNull Context context, ArrayList<RecordPlayer> list) {
        super(context, R.layout.player_item ,list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView =  layoutInflater.inflate(R.layout.player_item, parent,false);
        RecordPlayer player = list.get(position);

        TextView name = rowView.findViewById(R.id.tvPlayerName);
        TextView score = rowView.findViewById(R.id.tvPlayerScore);
        name.setText(player.getName());
        score.setText(""+player.getScore());

        return rowView;
    }
}
