package com.example.salon.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.DBRecords;
import com.example.salon.myapplication.models.PlayerAdapter;
import com.example.salon.myapplication.models.RecordPlayer;

import java.util.ArrayList;

public class RecordTicTacActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private ArrayList <RecordPlayer> listPlayers;
    private DBRecords playerDataBase;
    private PlayerAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_dum);
        listView = findViewById(R.id.lvPlayers);
        playerDataBase = new DBRecords (this);
        listPlayers = playerDataBase.getAllPlayers();
        adapter = new PlayerAdapter(this, listPlayers);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        listPlayers.get(position);
        playerDataBase.deletePlayerByRow(listPlayers.get(position).getId());
        listPlayers.remove(listPlayers.get(position));
        adapter.notifyDataSetChanged();

        return true;
    }
}
