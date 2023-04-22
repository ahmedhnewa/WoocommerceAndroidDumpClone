package com.alreyada.app.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alreyada.app.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class DbWishListController {
    private Context context;

    public DbWishListController(Context context) {
        this.context = context;
    }

    public void addToWishList(Integer id) {
        SQLiteDatabase db = new DbOpenHelper(context).getWritableDatabase();
        if (!db.isOpen()) {
            db.isOpen();
        }

        ContentValues cv = new ContentValues();
        cv.put(DbConstants.WISH_LIST_COLUMN_ID, id);
        db.insert(DbConstants.WISH_LIST_TABLE, null, cv);

        if (db.isOpen()) {
            db.close();
        }
    }

    public List<Integer> getWishList() {
        SQLiteDatabase db = new DbOpenHelper(context).getReadableDatabase();
        if (!db.isOpen()) {
            db.isOpen();
        }
        List<Integer> products = new ArrayList<>();
        Cursor cursor = db.query(DbConstants.WISH_LIST_TABLE, null,
                null, null,
                null, null,
                DbConstants.WISH_LIST_COLUMN_TIMESTAMP + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(cursor.getInt(cursor.getColumnIndex(DbConstants.WISH_LIST_COLUMN_ID)));
                products.add(product.getProductId());
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return products;
    }

    public boolean isAlreadyExist(Integer id) {
        SQLiteDatabase db = new DbOpenHelper(context).getReadableDatabase();
        String query = "Select * from " + DbConstants.WISH_LIST_TABLE + " where " + DbConstants.WISH_LIST_COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    public void removeItem(int itemId) {
        SQLiteDatabase db = new DbOpenHelper(context).getWritableDatabase();
        db.delete(DbConstants.WISH_LIST_TABLE, DbConstants.WISH_LIST_COLUMN_ID + "=" + itemId, null);
        db.close();
    }

    public void deleteAllItems() {
        SQLiteDatabase db = new DbOpenHelper(context).getWritableDatabase();
        db.delete(DbConstants.WISH_LIST_TABLE, null, null);
        db.close();
    }
}
