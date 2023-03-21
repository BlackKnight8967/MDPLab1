package ru.nickbesk.myapplication;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

import java.io.*;

public class DataBase extends SQLiteOpenHelper {
    private static String DBPATH;
    public static final String DBNAME = "database.db",
        TABLE_NAME = "users", UID = "uid", UNAME = "uname";
    private static final int DBVERSION = 1;
    private static final String CREATE_STD = "create table " + TABLE_NAME + " (" + UID +
            " integer primary key autoincrement, " + UNAME + " varchar(256));";
    private static final String DELETE_STD = "drop table if exists " + TABLE_NAME;
    private final Context context;
    public DataBase(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
        DBPATH = context.getFilesDir().getPath() + DBNAME;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public SQLiteDatabase open() throws SQLException {
        return SQLiteDatabase.openDatabase(DBPATH, null, SQLiteDatabase.OPEN_READWRITE);
    }



}
