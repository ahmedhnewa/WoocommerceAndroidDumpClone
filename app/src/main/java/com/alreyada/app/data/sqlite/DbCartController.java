package com.alreyada.app.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alreyada.app.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class DbCartController {
    private Context context;

    public DbCartController(Context context) {
        this.context = context;
    }

    public void addToCart(Integer id, int amount) {
        SQLiteDatabase db = new DbOpenHelper(context).getWritableDatabase();
        if (!db.isOpen()) {
            db.isOpen();
        }

        ContentValues cv = new ContentValues();
        cv.put(DbConstants.CART_COLUMN_ID, id);
        cv.put(DbConstants.CART_COLUMN_AMOUNT, amount);
        db.insert(DbConstants.CART_TABLE, null, cv);

        if (db.isOpen()) {
            db.close();
        }
    }

    public List<Integer> getCartList() {
        SQLiteDatabase db = new DbOpenHelper(context).getReadableDatabase();
        if (!db.isOpen()) {
            db.isOpen();
        }
        List<Integer> products = new ArrayList<>();
        Cursor cursor = db.query(DbConstants.CART_TABLE, null,
                null, null,
                null, null,
                DbConstants.CART_COLUMN_TIMESTAMP + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(cursor.getInt(cursor.getColumnIndex(DbConstants.CART_COLUMN_ID)));
                products.add(product.getProductId());
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return products;
    }

    public int getQuantityOfItem(int itemId) {
        SQLiteDatabase db = new DbOpenHelper(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbConstants.CART_TABLE + " WHERE " + DbConstants.CART_COLUMN_ID + "=" + itemId, null, null);

        int quantity = 0;
        if (cursor != null && cursor.moveToFirst()) {
            quantity = cursor.getInt(cursor.getColumnIndex(DbConstants.CART_COLUMN_AMOUNT));
            cursor.close();
        }

        db.close();
        return quantity;
    }

    public void decreaseItem(int itemId) {
        SQLiteDatabase db = new DbOpenHelper(context).getWritableDatabase();
        int amount = getQuantityOfItem(itemId);
        amount--;
        ContentValues cv = new ContentValues();
        cv.put(DbConstants.CART_COLUMN_AMOUNT, amount);
        db.update(DbConstants.CART_TABLE, cv, DbConstants.CART_COLUMN_ID + " = ?", new String[]{String.valueOf(itemId)});
        db.close();
    }

    public void increaseItem(int itemId) {
        SQLiteDatabase db = new DbOpenHelper(context).getWritableDatabase();
        int amount = getQuantityOfItem(itemId);
        amount++;
        ContentValues cv = new ContentValues();
        cv.put(DbConstants.CART_COLUMN_AMOUNT, amount);
        db.update(DbConstants.CART_TABLE, cv, DbConstants.CART_COLUMN_ID + " = ?", new String[]{String.valueOf(itemId)});
        db.close();
    }

    public boolean isAlreadyExist(Integer id) {
        SQLiteDatabase db = new DbOpenHelper(context).getReadableDatabase();
        String query = "Select * from " + DbConstants.CART_TABLE + " where " + DbConstants.CART_COLUMN_ID + " = " + id;
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
        db.delete(DbConstants.CART_TABLE, DbConstants.CART_COLUMN_ID + "=" + itemId, null);
        db.close();
    }

    public void deleteAllItems() {
        SQLiteDatabase db = new DbOpenHelper(context).getWritableDatabase();
        db.delete(DbConstants.CART_TABLE, null, null);
        db.close();
    }
}
