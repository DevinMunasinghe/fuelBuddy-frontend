package com.example.fuelapp.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    Context context;
    public static final String DATABASE_NAME="FUELBUDDY.db";

    public DBHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + UsersMaster.Users.TABLE_NAME + "(" +
                        UsersMaster.Users.COLUMN_NAME_USER_NAME + " TEXT NOT NULL,"+
                        UsersMaster.Users.COLUMN_NAME_USER_PHONE+ " TEXT NOT NULL UNIQUE,"+
                        UsersMaster.Users.COLUMN_NAME_USER_EMAIL+" TEXT NOT NULL UNIQUE,"+
                        UsersMaster.Users.COLUMN_NAME_USER_TYPE + " TEXT NOT NULL,"+
                        UsersMaster.Users.COLUMN_NAME_USER_ID+" TEXT NOT NULL,"+
                        UsersMaster.Users.COLUMN_NAME_USER_TOKEN+" TEXT NOT NULL)";

        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+UsersMaster.Users.TABLE_NAME);
        onCreate(db);
    }

    //Insertion of the user details to sqlite database for authentication puposes
    public long insertInfo(String name, String phone, String email, String type, String id, String token){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USER_NAME,name);
        values.put(UsersMaster.Users.COLUMN_NAME_USER_PHONE,phone);
        values.put(UsersMaster.Users.COLUMN_NAME_USER_EMAIL,email);
        values.put(UsersMaster.Users.COLUMN_NAME_USER_TYPE,type);
        values.put(UsersMaster.Users.COLUMN_NAME_USER_ID,id);
        values.put(UsersMaster.Users.COLUMN_NAME_USER_TOKEN,token);

        long result = db.insert(UsersMaster.Users.TABLE_NAME,null,values);
        db.close();
        return result;
    }

    //Authentication Username -> check username from data base in login page
    public Users checkusername(String email, String pwd){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" Select * from " + UsersMaster.Users.TABLE_NAME + " where " + UsersMaster.Users.COLUMN_NAME_USER_EMAIL + " = ? " , new String[] {email});
        Users user = new Users();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USER_NAME)));
            user.setUserPhone(cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USER_PHONE)));
            user.setUserMail(cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USER_EMAIL)));
            user.setUserType(cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USER_TYPE)));
            user.setUserId(cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USER_ID)));
            user.setUserToken(cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USER_TOKEN)));

            cursor.close();
            return user;

        }else{
            user.setUserType(String.valueOf(cursor.getCount()));
            return user;
        }

    }

}
