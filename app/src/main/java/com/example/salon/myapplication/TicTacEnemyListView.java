package com.example.salon.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.salon.myapplication.models.InvitesModel;
import com.example.salon.myapplication.models.TicTacAvailableUsersModle;
import com.example.salon.myapplication.models.UsersModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class TicTacEnemyListView extends ListView {
    public TicTacEnemyListView(Context context) {
        super(context);
        createAdapter(context);
    }

    public TicTacEnemyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        createAdapter(context);
    }

    public TicTacEnemyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createAdapter(context);
    }

    private void createAdapter(Context context){
        final ArrayAdapter<OtherPlayerInfo> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1);
        this.setAdapter(adapter);
        TicTacAvailableUsersModle.getTicTacAvailableUsers().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot availableUsersDataSanpshot) {
                adapter.clear();
                for (DataSnapshot availableUserDataSnapshot : availableUsersDataSanpshot.getChildren()) {
                    String id = availableUserDataSnapshot.getKey();
                    String email = availableUserDataSnapshot.getValue().toString();
                    if (!id.equals(UsersModel.getId())) {

                        adapter.add(new OtherPlayerInfo(id, email));
                    }

                    TicTacEnemyListView.this.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            InvitesModel.addInvite(UsersModel.getId(), adapter.getItem(position).getId());


                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
