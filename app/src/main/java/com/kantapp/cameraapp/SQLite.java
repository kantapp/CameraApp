package com.kantapp.cameraapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kantapp.cameraapp.Model.User;

import java.util.ArrayList;

/**
 * Created by Kantapp Inc. on 02-07-2018.
 */
public class SQLite extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="USER_DB";
    private static final String TABLE_NAME="USER_TABLE";
    private static final String COL1="id";
    private static final String COL2="fullname";
    private static final String COL3="phone";
    private static final String COL4="email";
    public SQLite(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"("+COL1+" INTEGER PRIMARY KEY autoincrement,"+COL2+" TEXT,"+COL3+" TEXT,"+COL4+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(String fullname, String phone, String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COL2,fullname);
        values.put(COL3,phone);
        values.put(COL4,email);

        long result = db.insert(TABLE_NAME,null ,values);
        return result != -1;

    }

    public ArrayList<User> getUsersList()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME,null);
        ArrayList<User> users=new ArrayList<>();



        while (cursor.moveToNext())
        {
            User user=new User();
            user.setId(String.valueOf(cursor.getInt(0)));
            user.setFullname(String.valueOf(cursor.getString(1)));
            user.setPhone(String.valueOf(cursor.getString(2)));
            user.setEmail(String.valueOf(cursor.getString(3)));
            users.add(user);
        }

        return users;
    }


}
