package com.example.android.sqlview;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DBHandler  extends SQLiteOpenHelper{
    private static final String DB_NAME="demodb";
    private static final int DB_VERSION=2;
    private static final String TABLE_NAME="record";
    private static final String ID_COL="id";
    private static final String NAME_COL="name";
    private static final String MARKS_COL="marks";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+" ("+ID_COL+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME_COL+" TEXT,"+MARKS_COL+" INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create table again
        onCreate(db);
    }

    public void insertRecord(String name,String marks){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(NAME_COL,name);
        values.put(MARKS_COL,marks);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public String getRecords(String id){
        String query=("SELECT * FROM record WHERE id = "+id);
        StringBuilder result= new StringBuilder();
        SQLiteDatabase db=this.getReadableDatabase();
        //	db.q((TABLE_NAME,"id=?",new String[]{id});
        @SuppressLint("Recycle") Cursor cursor=db.rawQuery(query,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            result.append(cursor.getString(0)).append(" ").append(cursor.getString(1)).append(" ").append(cursor.getString(2)).append("\n");
            cursor.moveToNext();
        }

        db.close();
        return result.toString();
    }

    public void updateRecord(String id,String name,String marks){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NAME_COL,name);
        values.put(MARKS_COL,marks);

        db.update(TABLE_NAME,values,"id=?",new String[]{id});
        db.close();
    }

    public void deleteRecord(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"id=?",new String[]{id});

        db.close();
    }
}
