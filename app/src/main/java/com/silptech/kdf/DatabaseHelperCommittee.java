package com.silptech.kdf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.silptech.kdf.Utils.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Amrit on 3/12/2016.
 */
public class DatabaseHelperCommittee extends SQLiteOpenHelper {

    private final static String TAG = "DatabaseHelperCommittee";
    private final Context myContext;
    private static final int DATABASE_VERSION = 1;
    private String pathToSaveDBFile;
    InputStream database_assets;
    private static String DATABASE_TABLE ;


    public DatabaseHelperCommittee(Context context, String filePath, String DATABASE_NAME, String DATABASE_TABLE,InputStream assets_path) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        //defining database path and assigning its path
        pathToSaveDBFile = new StringBuffer(filePath).append("/").append(DATABASE_NAME).toString();
        database_assets = assets_path;
        DatabaseHelperCommittee.DATABASE_TABLE = DATABASE_TABLE;
    }

    public void copyDataBase() throws IOException {
        OutputStream os = new FileOutputStream(pathToSaveDBFile);
        Log.i(TAG, "inputstream : " + database_assets);
        //getting database to inputstream from asset folder
        InputStream is = database_assets;
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
        is.close();
        os.flush();
        os.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<CommitteeModule> getCommitteeModule() {
        //access database file
        SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        //accessing column elements from table kdfdatatable
        String query = "SELECT serial, post, name, cell, phone FROM "+DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        //listing elements in array from table
        ArrayList<CommitteeModule> list = new ArrayList<CommitteeModule>();
        //inserting values in object member from database
        while (cursor.moveToNext()) {
            CommitteeModule module = new CommitteeModule();
            module.setPost(cursor.getString(1));
            module.setName(cursor.getString(2));
            module.setCell(cursor.getString(3));
            module.setPhone(cursor.getString(4));
            list.add(module);
        }
        db.close();
        return list;
    }
}
