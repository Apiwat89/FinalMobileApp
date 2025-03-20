package com.example.afinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.material.tabs.TabLayout;

public class Database extends SQLiteOpenHelper {
    private static final String DataName = "Cafes.db";
    private static final int Version = 2;

    private static final String TableName = "Cafe_Table";
    private static final String ColumnCafeID = "CafeID";
    private static final String ColumnCafeName = "CafeName";
    private static final String ColumnCafeValue = "CafeValue";

    private static final String CreateTable = "CREATE TABLE " + TableName + " (" +
            ColumnCafeID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ColumnCafeName + " TEXT, " + ColumnCafeValue + " TEXT);";

    public Database (Context context) {
        super(context, DataName, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String drink, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ColumnCafeName, drink);
        values.put(ColumnCafeValue, value);
        long res = db.insert(TableName, null, values);
        return res == -1 ? false : true;
    }

    public int getCountList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TableName, null);
        int count = 0;
        if (res != null) {
            while (res.moveToNext()) {
                count++;
            }
        } return count;
    }

    public Cursor getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TableName, null);
        return (res != null && res.getCount() > 0) ? res : null;
    }
}
