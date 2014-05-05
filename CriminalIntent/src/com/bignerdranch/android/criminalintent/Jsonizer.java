package com.bignerdranch.android.criminalintent;

import java.io.BufferedReader;
import java.io.File;
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

public class Jsonizer<T extends Jsonizable<T>> {
    public void jsonize(Collection<T> jsonizables, Context context, String fileName) {

        JSONArray jsonArray = new JSONArray();

        for (Jsonizable<?> jsonizable : jsonizables) {
            jsonArray.put(jsonizable.toJson());
        }

        Writer writer = null;

        try {
            OutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
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

        try {

            File filesDir = context.getFilesDir();
            File jsonFile = new File(filesDir, fileName);

            if (jsonFile.exists()) {
                InputStream inputStream = context.openFileInput(fileName);
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
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        return collection;
    }
}
