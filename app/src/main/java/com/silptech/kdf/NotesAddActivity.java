package com.silptech.kdf;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Amrit on 2/13/2016.
 * This activity is used to insert the title and notes on the database.
 */
public class NotesAddActivity extends AppCompatActivity {

    private static final String TAG = "NotesAddActivity";
    EditText notesTitle;
    EditText notesContent;
    String notesTitleText;
    String notesContentText;
    File folder;
    DatabaseHelperNotes db;
    CacheModule CacheModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        notesTitle = (EditText) findViewById(R.id.memo_add_title);
        notesContent = (EditText) findViewById(R.id.memo_add_contents);
        folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Notes");
        folder.mkdirs();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(notesTitle, InputMethodManager.SHOW_FORCED);
        Log.i(TAG, "folder : " + folder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notes_save:
                SavingNotes();
                break;
            case R.id.notes_cancel:
                this.finish();
                break;
            default:
                break;
        }
        return false;
    }

    private void SavingNotes() {
        onGetText();

        /* 2 conditions :
            A. if both title and contents aren't empty
            B. if both title and contents are empty
         */
        if ((notesTitleText.length() > 0) && (notesContentText.length() > 0) ||
                ((notesTitleText.length() == 0) && (notesContentText.length() == 0))) {
            onSavedNote();
            if ((notesTitleText.length() == 0) && (notesContentText.length() == 0)) {
                Toast.makeText(getApplicationContext(), "Empty Title and Note. Note cannot be saved", Toast.LENGTH_SHORT).show();
            }
        }

        //if onyl one of the two fields i.e. title and note is empty
        if ((notesTitleText.length() == 0) || (notesContentText.length() == 0)) {
            if (notesTitleText.length() == 0) {
                Toast.makeText(getApplicationContext(), "Please input Title", Toast.LENGTH_SHORT).show();
            }
            if (notesContentText.length() == 0) {
                Toast.makeText(getApplicationContext(), "Please input Note", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //saving title and notes to the database
    private void onSavedNote() {
        if ((notesTitleText.length() > 0) && (notesContentText.length() > 0)) {
            db = new DatabaseHelperNotes(folder, getApplicationContext());
            CacheModule = new CacheModule();
            CacheModule.title = notesTitleText;
            CacheModule.notes = notesContentText;
            db.addNotes(CacheModule);
            Toast.makeText(getApplicationContext(), "Notes successfully saved", Toast.LENGTH_SHORT).show();
        }
        this.finish();
    }

    //getting text from edittext
    private void onGetText() {
        notesTitleText = notesTitle.getText().toString();
        notesContentText = notesContent.getText().toString();
        Log.i(TAG, "title : " + notesTitleText);
    }

    //on pressing back, notes are saved
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onGetText();
        onSavedNote();
    }

    //for startActivityforResult (NotesFragment)
    @Override
    protected void onStop() {
        super.onStop();
        setResult(0);
    }
}
