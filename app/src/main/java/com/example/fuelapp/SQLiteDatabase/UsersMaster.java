package com.example.fuelapp.SQLiteDatabase;

import android.provider.BaseColumns;

public class UsersMaster {

    public UsersMaster() {
    }

    //define the table name and column names

    public static class Users implements BaseColumns {

        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_NAME_USER_NAME = "name";
        public static final String COLUMN_NAME_USER_PHONE = "phone";
        public static final String COLUMN_NAME_USER_EMAIL = "email";
        public static final String COLUMN_NAME_USER_TYPE = "type";
        public static final String COLUMN_NAME_USER_ID = "id";
        public static final String COLUMN_NAME_USER_TOKEN = "token";



    }
}
