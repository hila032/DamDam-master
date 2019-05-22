package com.example.salon.myapplication.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBRecords extends SQLiteOpenHelper {
    private static final String DATABASENAME = "Score.db";
    private static final String TABLE_TIC_TAC = "tbIicTac";
    private static final int DATABASEVERSION = 2;
    private static final String COLUMN_FIRSTNAME = "Name";
    private static final String COLUMN_WIN = "win";
    private static final String COLUMN_LOSS = "loss";
    private static final String COLUMN_TIe = "tie";
    private static final String COLUMN_ID = "Id";
    private static final String[] allColumns = {COLUMN_ID, COLUMN_FIRSTNAME, COLUMN_WIN, COLUMN_LOSS, COLUMN_TIe};

    private SQLiteDatabase database;
    private String sortOrder = COLUMN_WIN + " DESC";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_TIC_TAC + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY,"+
            COLUMN_FIRSTNAME + " TEXT," +
            COLUMN_WIN + " INTEGER, " +
            COLUMN_LOSS +  " INTEGER, " +
            COLUMN_TIe +  " INTEGER ); ";

    public DBRecords(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIC_TAC);
        onCreate(db);
    }
    public void createTicTacPlayer(RecordPlayer player){
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRSTNAME, player.getName());
        values.put(COLUMN_WIN, player.getWin());
        values.put(COLUMN_LOSS, player.getLoss());
        values.put(COLUMN_TIe, player.getTie());
        long id =database.insert(TABLE_TIC_TAC, null, values);
        player.setId(id);
        database.close();
    }

    public ArrayList <RecordPlayer> getAllPlayers(){
        database = getReadableDatabase();
        ArrayList <RecordPlayer> list = new ArrayList<>();
        Cursor cursor = database.query(TABLE_TIC_TAC, allColumns, null, null, null, null, sortOrder);
        if (cursor.getColumnCount() > 0){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME));
                int win = cursor.getInt(cursor.getColumnIndex(COLUMN_WIN));
                int loss = cursor.getInt(cursor.getColumnIndex(COLUMN_LOSS));
                int tie = cursor.getInt(cursor.getColumnIndex(COLUMN_TIe));
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
                RecordPlayer player = new RecordPlayer(name, win, loss, tie, id);
                list.add(player);

            }

        }
        database.close();
        return list;
    }
    public void deleteAll(){
        database = getWritableDatabase();
        database.delete(TABLE_TIC_TAC, null,null);
        database.close();
    }
    public void deletePlayerByRow(long id){
        database = getWritableDatabase();
        database.delete(TABLE_TIC_TAC,COLUMN_ID + "=" + id, null);
        database.close();
    }
    public long updateById(RecordPlayer player){
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, player.getId());
        values.put(COLUMN_FIRSTNAME, player.getName());
        values.put(COLUMN_WIN, player.getWin());
        values.put(COLUMN_LOSS, player.getLoss());
        values.put(COLUMN_TIe, player.getTie());
        long id =database.update(TABLE_TIC_TAC,values,COLUMN_ID + "=" + player.getId(), null);
        database.close();
        return id;
    }
}
