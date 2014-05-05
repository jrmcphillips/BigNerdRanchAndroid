package com.bignerdranch.android.criminalintent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONTokener;

import android.content.Context;
import android.os.Environment;

public class Jsonizer<T extends Jsonizable<T>> {

    File getJsonFile(Context context, String fileName) {
        File fileDir = null;
        
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            fileDir = context.getExternalFilesDir(null);
        } else {
            fileDir = context.getFilesDir();
        }

        File jsonFile = new File(fileDir, fileName);

        return jsonFile;
    }

    public void jsonize(Collection<T> jsonizables, Context context, String fileName) {

        JSONArray jsonArray = new JSONArray();

        for (Jsonizable<?> jsonizable : jsonizables) {
            jsonArray.put(jsonizable.toJson());
        }

        Writer writer = null;

        File jsonFile = getJsonFile(context, fileName);

        try {
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(jsonFile));
            writer = new OutputStreamWriter(outputStream);
            writer.write(jsonArray.toString());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public List<T> unJsonize(Class<T> jsonizableClass, Context context, String fileName) {
        List<T> collection = new ArrayList<T>();

        BufferedReader reader = null;
        InputStream inputStream = null;

        File jsonFile = getJsonFile(context, fileName);

        try {

            if (jsonFile.exists()) {
                inputStream = new BufferedInputStream(new FileInputStream(jsonFile));
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder jsonString = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {
                    jsonString.append(line);
                }

                JSONArray jsonArray = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

                for (int x = 0; x < jsonArray.length(); x++) {
                    T jsonizable = jsonizableClass.newInstance();
                    jsonizable.fromJson(jsonArray.getJSONObject(x));
                    collection.add(jsonizable);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    inputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        return collection;
    }
}
