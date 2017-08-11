package com.example.admin.zoo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.ViewPropertyAnimatorListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/10/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "myZOODB";

    // ZOO table name
    private static final String TABLE_ZOO = "ZOO";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SEX ="sex";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_DIET = "diet";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ZOO_TABLE = "CREATE TABLE " + TABLE_ZOO + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_LOCATION + " TEXT,"
                + KEY_SEX + " TEXT," + KEY_DIET + " TEXT" + ")";
        db.execSQL(CREATE_ZOO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZOO);

        // Create tables again
        onCreate(db);
    }

    public void addNewZOO(Zoo ZOO) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, ZOO.getName());
        values.put(KEY_LOCATION, ZOO.getLocation());
        values.put(KEY_SEX, ZOO.getSex());
        values.put(KEY_DIET, ZOO.getDiet());

        // inserting this record
        db.insert(TABLE_ZOO, null, values);
        db.close(); // Closing database connection
    }

    public void deleteZOO(Zoo ZOO) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ZOO, KEY_ID + " = ?", new String[]{String.valueOf(ZOO.getId())});
        db.close();
    }


    public int updateZOO(Zoo ZOO) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, ZOO.getName());
        values.put(KEY_LOCATION, ZOO.getLocation());
        values.put(KEY_SEX, ZOO.getSex());
        values.put(KEY_DIET, ZOO.getDiet());

        // updating row
        return db.update(TABLE_ZOO, values, KEY_ID + " = ?", new String[]{String.valueOf(ZOO.getId())});
    }

    public List<Zoo> getAllZOOs() {
        List<Zoo> ZOOList = new ArrayList<>();

        // select query
        String selectQuery = "SELECT  * FROM " + TABLE_ZOO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all table records and adding to list
        if (cursor.moveToFirst()) {
            do {
                Zoo ZOO = new Zoo();
                ZOO.setId(Integer.parseInt(cursor.getString(0)));
                ZOO.setName(cursor.getString(1));
                ZOO.setLocation(cursor.getString(2));
                ZOO.setSex(cursor.getString(3));
                ZOO.setDiet(cursor.getString(4));

                // Adding ZOO to list
                ZOOList.add(ZOO);
            } while (cursor.moveToNext());
        }

        return ZOOList;
    }

    public int getContactsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor dataCount = db.rawQuery("select " + KEY_ID + " from " + TABLE_ZOO, null);

        int count = dataCount.getCount();
        dataCount.close();
        db.close();

        return count;
    }

}