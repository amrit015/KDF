package com.silptech.kdf.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is used to save String to a file in the storage.
 * Both storage location and filename along with String (to save to file) are sent to the writeFile function.
 * Only filename and file location are provided to the readFile function and string is returned.
 */
public class CacheNotification {

    private static final String TAG = "CacheNotification";
    public CacheNotification() {
        //constructor
    }

    //writing notification string to a file
    public static File writeFile(String fileName, String notification, File folder) {
        Log.i(TAG, "folder path : " + folder);
        //saving the path to the folder as string
        String extStorageDirectory = folder.toString();

        //Creating the file inside the folder
        File cacheFile = new File(extStorageDirectory, fileName);
        try {
            FileWriter fw = new FileWriter(cacheFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(notification);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            // on exception null will be returned
            cacheFile = null;
        }
        return cacheFile;
    }

    //reading notification string from a file
    public static String readFile(String fileName, File folder) throws IOException {
        //saving the path to the folder as string
        String extStorageDirectory = folder.toString();

        File cacheFile = new File(extStorageDirectory, fileName);
        StringBuilder builder = new StringBuilder();
        try {
            String currentLine;
            BufferedReader br = new BufferedReader(new FileReader(cacheFile));
            while ((currentLine = br.readLine()) != null) {
                builder.append(currentLine);
                builder.append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fromCache = builder.toString();
        return fromCache;
    }
}
