package ru.nickbesk.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DBActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        DataBase mydb = new DataBase(this);
        SQLiteDatabase sqldb = mydb.getWritableDatabase();
        TextView dataView = findViewById(R.id.dataView);
        sqldb.execSQL("create table if not exists users(id integer primary key autoincrement, name varchar(255))");
        var userCursor = sqldb.rawQuery("select * from " + DataBase.TABLE_NAME, null);
        dataView.setText("");
        while (userCursor.moveToNext()) {
            String name = userCursor.getString(1);
            String id = userCursor.getString(0);
            dataView.append("Имя: " + name + "\nID: " + id + "\n\n");
        }
        userCursor.close();
        sqldb.close();
        mydb.close();

    }
    public void addUser(View view) {
        TextView v = findViewById(R.id.queryInput);
        DataBase mydb = new DataBase(this);
        SQLiteDatabase sqldb = mydb.getWritableDatabase();
        String name = v.getText().toString();
        if (!name  .equals(""))
            sqldb.execSQL("insert into users (name) values ('"+name+"')");
        sqldb.close();
        mydb.close();
        v.setText("");
    }
}
