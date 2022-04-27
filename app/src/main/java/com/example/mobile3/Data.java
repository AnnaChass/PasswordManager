package com.example.mobile3;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Data {
    private String key;
    private Context context;
    private Cursor cursor;
    private DataHelper helper;
    private SQLiteDatabase database;

    public Data(Context context_, String key_) {
        context = context_;
        key = key_;
        helper = new DataHelper(context);
        database = helper.getWritableDatabase();
    }

    public String[] getAllResources() {
        cursor = database.query(DataHelper.TABLE_NAME, null, null, null, null, null, null);
        String[] resourcesList = new String[cursor.getCount()];

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int nameColInd = cursor.getColumnIndex(DataHelper.KEY_RESOURCE);

            int i = 0;
            do {
                resourcesList[i] = new String (decrypt(cursor.getString(nameColInd)));
                i++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        return resourcesList;
    }

    public long createEntry(String resourceName, String login, String password, String note) {
        if(resourceName.length() == 0)
            return 0;

        ContentValues cv = new ContentValues();

        cv.put(DataHelper.KEY_RESOURCE, encrypt(resourceName));
        cv.put(DataHelper.KEY_LOGIN, encrypt(login));
        cv.put(DataHelper.KEY_PASSWORD, encrypt(password));
        cv.put(DataHelper.KEY_NOTE, encrypt(note));

        return database.insert(DataHelper.TABLE_NAME, null, cv);
    }

    public long updateEntry(String oldResourceName, String resourceName, String login, String password, String note) {
        if(resourceName.length() == 0)
            return 0;
        String[] argsOldResource = new String[]{encrypt(oldResourceName)};

        ContentValues cv = new ContentValues();
        cv.put(DataHelper.KEY_LOGIN, encrypt(login));
        cv.put(DataHelper.KEY_PASSWORD, encrypt(password));
        cv.put(DataHelper.KEY_NOTE, encrypt(note));
        cv.put(DataHelper.KEY_RESOURCE, encrypt(resourceName));
        int res = database.update(DataHelper.TABLE_NAME, cv, "resource = ?", argsOldResource);

        return res;
    }

    public long deleteEntry(String resourceName) {
        database.delete(DataHelper.TABLE_NAME, DataHelper.KEY_RESOURCE + " = ?", new String[] {encrypt(resourceName)});
        return 0;
    }

    public int getSize() {
        cursor = database.query(DataHelper.TABLE_NAME, null, null, null, null, null, null);
        int res = cursor.getCount();
        cursor.close();
        return res;
    }

    public long setKey(String key_) {
        database.delete(DataHelper.KEY_TABLE_NAME, DataHelper.KEY_EXIST + " = ?", new String[] {encrypt("true")});
        cursor = database.query(DataHelper.KEY_TABLE_NAME, null, null, null, null, null, null);
        int res = cursor.getCount();

        key = key_;

        ContentValues cv = new ContentValues();

        cv.put(DataHelper.KEY_EXIST, encrypt("true"));
        cv.put(DataHelper.KEY_KEY, encrypt(key_));

        return database.insert(DataHelper.KEY_TABLE_NAME, null, cv);
    }

    public String getKey() {
        cursor = database.query(DataHelper.KEY_TABLE_NAME, null, null, null, null, null, null);
        String result = null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int nameColInd = cursor.getColumnIndex(DataHelper.KEY_KEY);
            result = new String (decrypt(cursor.getString(nameColInd)));
        }

        cursor.close();
        return result;
    }

    public String[] getEntryByPosition(int position) {
        cursor = database.query(DataHelper.TABLE_NAME, null, null, null, null, null, null);
        String resources[] = new String[4];

        if (cursor.getCount() > 0) {
            cursor.moveToPosition(position);
            int nameColInd = cursor.getColumnIndex(DataHelper.KEY_RESOURCE);
            resources[0] = new String (decrypt(cursor.getString(nameColInd)));
            nameColInd = cursor.getColumnIndex(DataHelper.KEY_LOGIN);
            resources[1] = new String (decrypt(cursor.getString(nameColInd)));
            nameColInd = cursor.getColumnIndex(DataHelper.KEY_PASSWORD);
            resources[2] = new String (decrypt(cursor.getString(nameColInd)));
            nameColInd = cursor.getColumnIndex(DataHelper.KEY_NOTE);
            resources[3] = new String (decrypt(cursor.getString(nameColInd)));
        }

        cursor.close();
        return resources;
    }

    private String encrypt(String openedText) {

        CryptoProvider cp = new CryptoProvider();
        String cipherText = "";

        try {
            cipherText = cp.encryptMessage(openedText, key);
        }
        catch (Exception e) {
            ;
        }

        return cipherText;
    }

    private String decrypt(String cipherText) {
        CryptoProvider cp = new CryptoProvider();
        String openedText = "";

        try {
            openedText = cp.decryptMessage(cipherText, key);
        }
        catch (Exception e) { }
        return openedText;
    }

    public void close() {
        helper.close();
        database.close();
    }
}
