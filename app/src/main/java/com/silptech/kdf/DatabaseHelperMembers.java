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

/*
    This is a database class which is used by the MembersFragment and SearchQueryActivity class to get the data from the saved database.db
    The filepath(phone storage location) is provided to this class. Database is initialized and accessed.
 */

public class DatabaseHelperMembers extends SQLiteOpenHelper {
    private final static String TAG = "DatabaseHelperMembers";
    private final Context myContext;
    private static final String DATABASE_NAME = "database.db";
    //defining sqlite database file on assets folder named as database.db
    private static final int DATABASE_VERSION = 1;
    //initial version of database
    private String pathToSaveDBFile;
    InputStream database_assets;

    public DatabaseHelperMembers(Context context, String filePath, InputStream assets_path) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        //defining database path and assigning its path
        pathToSaveDBFile = new StringBuffer(filePath).append("/").append(DATABASE_NAME).toString();
        database_assets = assets_path;
    }

    public void prepareDatabase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.d(TAG, "Database exists.");
            //getting database version from database offline table in asset folder
            int currentDBVersion = getVersionId();
            if (DATABASE_VERSION < currentDBVersion) {
                Log.d(TAG, "Database version is higher than old.");
                deleteDb();
                try {
                    copyDataBase();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        } else {
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private boolean checkDataBase() {
        boolean checkDB = false;
        try {
            File file = new File(pathToSaveDBFile);
            checkDB = file.exists();
        } catch (SQLiteException e) {
            Log.d(TAG, e.getMessage());
        }
        return checkDB;
    }

    private void copyDataBase() throws IOException {
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

    public void deleteDb() {
        File file = new File(pathToSaveDBFile);
        if (file.exists()) {
            file.delete();
            Log.d(TAG, "Database deleted.");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<MembersModule> getMembersModule() {
        //access database file
        SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        //accessing column elements from table kdfdatatable
        String query = "SELECT id, name, address, phone, amount FROM kdfdatatable";
        Cursor cursor = db.rawQuery(query, null);
        //listing elements in array from table
        ArrayList<MembersModule> list = new ArrayList<MembersModule>();
        //inserting values in object member from database
        while (cursor.moveToNext()) {
            MembersModule member = new MembersModule();
            member.setId(cursor.getInt(0));
            member.setName(cursor.getString(1));
            member.setAddress(cursor.getString(2));
            member.setPhone(cursor.getString(3));
            member.setAmount(cursor.getInt(4));
            list.add(member);
        }
        db.close();
        return list;
    }

    private int getVersionId() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(pathToSaveDBFile, null, SQLiteDatabase.OPEN_READONLY);
        //access version of current database defined in table on asset folder
        String query = "SELECT version_id FROM dbVersion";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        int v = cursor.getInt(0);
        db.close();
        return v;
    }
} 