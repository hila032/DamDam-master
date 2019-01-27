package com.example.salon.myapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.salon.myapplication.R;
import com.example.salon.myapplication.models.AvailableUsersModel;
import com.example.salon.myapplication.models.DialogsModel;
import com.example.salon.myapplication.models.InvitesModel;
import com.example.salon.myapplication.models.RoomsModel;
import com.example.salon.myapplication.models.UsersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnemychoseActivity extends AppCompatActivity {

    private final DatabaseReference roomsReference = FirebaseDatabase.getInstance().getReference("Rooms");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enamychose);

        // TODO: big refactor.
        // make the two loops - one line.
        FirebaseDatabase.getInstance().getReference("Rooms").child(UsersModel.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Intent intent = new Intent(EnemychoseActivity.this, GameActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    intent.putExtra("id", UsersModel.getId());
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        InvitesModel.listenToInvitation(UsersModel.getId(), new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull final DataSnapshot otherIdSnapshot) {
                if (otherIdSnapshot.getValue() != null) {
                    DialogsModel.sendPlayerGameMassag(EnemychoseActivity.this, otherIdSnapshot, EnemychoseActivity.this);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final ListView listView = (ListView) findViewById(R.id.enemysList);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        FirebaseDatabase.getInstance().getReference("availableUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot availableUsersDataSanpshot) {
                adapter.clear();
                for (DataSnapshot availableUserDataSnapshot : availableUsersDataSanpshot.getChildren()) {
                    String id = availableUserDataSnapshot.getKey();
                    if (!id.equals(UsersModel.getId())) {
                        adapter.add(id);
                    }
//                    Toast.makeText(EnemychoseActivity.this, id, Toast.LENGTH_LONG).show();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String idSecondPlayer = adapter.getItem(position);
                            InvitesModel.addInvits(UsersModel.getId(),idSecondPlayer);

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        AvailableUsersModel.removeUser(UsersModel.getId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        AvailableUsersModel.addUserToAvailableUsers(UsersModel.getId(), UsersModel.getEmail());
    }
}