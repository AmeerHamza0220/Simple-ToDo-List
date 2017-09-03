package com.example.lablnet.simplenote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lablnet on 8/25/2017.
 */

public  class DatabaseHelper extends SQLiteOpenHelper {
    public static String mDatabaseName = "sNote.db";
    public String mTableName = "Notes";
    SQLiteDatabase db = this.getWritableDatabase();

    public DatabaseHelper(Context context) {
        super(context, mDatabaseName, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + mTableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Description TEXT, Date TEXT, ReminderDate TEXT, ReminderTime TEXT,ShowAlarmIcon INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       
    }

    public void SaveData(String mTitle, String mDescription, String mDate,String mReminderDate,String mReminderTime,int showicon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", mTitle);
        contentValues.put("Description", mDescription);
        contentValues.put("Date", mDate);
        contentValues.put("ReminderDate", mReminderDate);
        contentValues.put("ReminderTime", mReminderTime);
        contentValues.put("ShowAlarmIcon", showicon);
        db.insert(mTableName, null, contentValues);
    }

    public Cursor ReadData() {
        String query = "SELECT * FROM " + mTableName;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void DeleteRecord(int id) {
        db.delete(mTableName, "ID ='"+ id+"'", null);
    }

public void UpdateDatabase(String mTitle, String mDescription,int Id){
    ContentValues contentValues=new ContentValues();
    contentValues.put("Title",mTitle);
    contentValues.put("Description",mDescription);
    String whereClause="ID ='"+Id+"'";
    db.update(mTableName,contentValues,whereClause,null);
}
}

