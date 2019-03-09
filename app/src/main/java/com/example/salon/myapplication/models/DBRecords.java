package com.example.salon.myapplication.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBRecords extends SQLiteOpenHelper {
    public static final String DATABASENAME = "Score.db";
    public static final String TABLE_DUM_SCORE = "tbDumScore";
    public static final int DATABASEVERSION = 1;
    public static final String COLUMN_FIRSTNAME = "Name";
    public static final String COLUMN_SCORE = "Score";
    public static final String COLUMN_ID = "Id";
    public static final String[] allColumns = {COLUMN_ID, COLUMN_FIRSTNAME, COLUMN_SCORE};

    private SQLiteDatabase database;
    private String sortOrder = COLUMN_SCORE + " DESC";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_DUM_SCORE + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY,"+
            COLUMN_FIRSTNAME + " TEXT," +
            COLUMN_SCORE + " INTEGER );";

    public DBRecords(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DUM_SCORE);
        onCreate(db);
    }
    public void createDumPlayer (RecordPlayer player){
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, player.getName());
        values.put(COLUMN_SCORE, player.getScore());
        long id =database.insert(TABLE_DUM_SCORE, null, values);
        player.setId(id);
        database.close();
    }

    public ArrayList <RecordPlayer> getAllPlayers(){
        database = getReadableDatabase();
        ArrayList <RecordPlayer> list = new ArrayList<>();
        Cursor cursor = database.query(TABLE_DUM_SCORE, allColumns, null, null, null, null, sortOrder);
        if (cursor.getColumnCount() > 0){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME));
                int score = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                RecordPlayer player = new RecordPlayer(name, score, id);
                list.add(player);

            }

        }
        database.close();
        return list;
    }
    public void deleteAll(){
        database = getWritableDatabase();
        database.delete(TABLE_DUM_SCORE, null,null);
        database.close();
    }
    public void deletePlayerByRow(long id){
        database = getWritableDatabase();
        database.delete(TABLE_DUM_SCORE,COLUMN_ID + "=" + id, null);
        database.close();
    }
    public long updateById(RecordPlayer player){
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, player.getId());
        values.put(COLUMN_FIRSTNAME, player.getName());
        values.put(COLUMN_SCORE, player.getScore());
        long id =database.update(TABLE_DUM_SCORE,values,COLUMN_ID + "=" + player.getId(), null);
        database.close();
        return id;
    }
}
