package com.silptech.kdf;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.silptech.kdf.Utils.CacheNotification;

import java.io.File;

/**
 * Created by Amrit on 2/13/2016.
 */
public class NotesClickedActivity extends AppCompatActivity {

    private static final String TAG = "NotesClickedActivity";
    EditText notesTitle;
    EditText notesContent;
    String notesTitleText;
    String notesContentText;
    File folder;
    String xfileName = "cache_note";
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_notes);
        notesTitle = (EditText) findViewById(R.id.memo_add_title);
        notesContent = (EditText) findViewById(R.id.memo_add_contents);
        folder = new File(Environment.getExternalStorageDirectory().toString() + "/KDF/Memo");
        folder.mkdirs();
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
        if ((!notesTitleText.equals("") && !notesContentText.equals("")) ||
                (notesTitleText.equals("") && notesContentText.equals(""))) {
            onSavedNote();
            if (notesTitleText.equals("") && notesContentText.equals("")) {
                Toast.makeText(getApplicationContext(), "Empty Title and Note. Note cannot be saved", Toast.LENGTH_LONG).show();
            }
        }

        if (!notesTitleText.equals("") || !notesContentText.equals("")) {
            if (notesTitleText.equals("")) {
                Toast.makeText(getApplicationContext(), "Please input Title", Toast.LENGTH_LONG).show();
            }
            if (notesContentText.equals("")) {
                Toast.makeText(getApplicationContext(), "Please input Note", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onSavedNote() {
        if (!notesTitleText.equals("") && !notesContentText.equals("")) {
            if (folder.exists()) {
                i = folder.list().length;
            } else {
                i = 0;
            }
            String filename = xfileName + i;
            CacheNotification.writeFile(filename, ("##" + notesTitleText + "###" + notesContentText + "####"), folder);
            Toast.makeText(getApplicationContext(), "Notes successfully saved", Toast.LENGTH_LONG).show();
            this.finish();
        }
    }

    private void onGetText() {
        notesTitleText = notesTitle.getText().toString();
        notesContentText = notesContent.getText().toString();
        Log.i(TAG, "title : " + notesTitleText);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onGetText();
        onSavedNote();
    }

    @Override
    protected void onStop() {
        super.onStop();
        setResult(0);
    }
}
