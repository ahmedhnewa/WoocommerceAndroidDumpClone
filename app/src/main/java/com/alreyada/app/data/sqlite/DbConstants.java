package com.alreyada.app.data.sqlite;

import android.provider.BaseColumns;

public class DbConstants implements BaseColumns {

    public static final String CART_TABLE = "cartList";
    public static final String CART_COLUMN_ID = "cartId";
    public static final String CART_COLUMN_AMOUNT = "cartProductsAmount";
    public static final String CART_COLUMN_TIMESTAMP = "cartTimestamp";

    public static final String SQL_CREATE_CART_TABLE = "CREATE TABLE " +
            CART_TABLE + " (" +
            CART_COLUMN_ID + " INTEGER PRIMARY KEY, " +
            CART_COLUMN_AMOUNT + " INTEGER NOT NULL, " +
            CART_COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");";

    public static final String SQL_DROP_CART_TABLE = "DROP TABLE IF EXISTS " + CART_TABLE;

    public static final String WISH_LIST_TABLE = "wishList";
    public static final String WISH_LIST_COLUMN_ID = "wishListId";
    public static final String WISH_LIST_COLUMN_TIMESTAMP = "wishListTimestamp";

    public static final String SQL_CREATE_WISH_LIST_TABLE = "CREATE TABLE " +
            WISH_LIST_TABLE + " (" +
            WISH_LIST_COLUMN_ID + " INTEGER PRIMARY KEY, " +
            /*CART_COLUMN_AMOUNT + " INTEGER NOT NULL, " +*/
            WISH_LIST_COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");";

    public static final String SQL_DROP_WISH_LIST_TABLE = "DROP TABLE IF EXISTS " + WISH_LIST_TABLE;

}
