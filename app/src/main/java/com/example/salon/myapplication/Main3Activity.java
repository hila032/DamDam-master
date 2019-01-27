package com.example.salon.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    private TextView name;
    private TextView age;
    private EditText otherName;
    private Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        name = (TextView) findViewById(R.id.nameTV);
        age = (TextView) findViewById(R.id.ageTV);
        otherName = (EditText) findViewById(R.id.prtnerNamePT);
        ok = (Button) findViewById(R.id.ok);
        Intent intent = getIntent();
        String name1 = intent.getStringExtra("name");
        String age1 = intent.getStringExtra("age");
        name.setText(name1);
        age.setText(age1);
    }


    public void ok(View view) {
        String name1 = getIntent().getExtras().toString();
        String age1 = getIntent().getExtras().toString();
        getIntent().putExtra("mame", RESULT_OK);
        Toast.makeText(this, name1 + ", "+ age1, Toast.LENGTH_LONG).show();
        setResult(RESULT_OK);
        finish();
    }
}
