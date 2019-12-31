package com.example.irfan.storeexpressagas.extras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.irfan.storeexpressagas.models.Cart;
import com.example.irfan.storeexpressagas.models.ShoppingList;
import com.example.irfan.storeexpressagas.models.ShoppingListItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DeviceDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "DeviceDb";

    // Audio and dataUsage table name
    private static final String TABLE_NAME = "Cart";

    private static final String TABLE_NAME_SHOPPINGLIST = "ShopingList";
    private static final String TABLE_NAME_LISTITEM = "ListItem";

    private static final String TABLE_NAME_LISTITEM_HISTOREY = "ListItemHSt";
    ;
    //columns Name for Call tables
    private static final String CART_ID = "Id";
    private static final String CART_ITEM_ID = "item_ID";
    private static final String CART_ITEM_NAME = "item_name";
    private static final String CART_ITEM_QTY = "item_qty";
    private static final String CART_ITEM_PRICE = "item_price";
    private static final String CART_ITEM_IMG = "item_img";


    private static final String SPL_ID = "Id";
    private static final String SPL_NAME = "ListName";
    private static final String SPL_DATE = "ListDate";


    private static final String SPLI_ID = "Id";
    private static final String SPLI_SPLID = "ListId";
    private static final String SPLI_ITEM = "ListItem";
    private static final String SPLI_ITEM_MARK = "Mark";

    private static final String SPLIH_ID = "Id";
    private static final String SPLIH_ITEM = "ListItemHst";





    private static DeviceDatabaseHandler sInstance;

    public static synchronized DeviceDatabaseHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DeviceDatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    private DeviceDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CART_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CART_ITEM_ID + " INTEGER,"
                + CART_ITEM_NAME + " TEXT," + CART_ITEM_QTY + " INTEGER," + CART_ITEM_PRICE + " INTEGER,"+CART_ITEM_IMG  + " TEXT "+ ")";


        String SPL_TABLE = "CREATE TABLE " + TABLE_NAME_SHOPPINGLIST + "("
                + SPL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SPL_NAME + " TEXT,"
                + SPL_DATE + " TEXT "+")";


        String SPLI_TABLE = "CREATE TABLE " + TABLE_NAME_LISTITEM + "("
                + SPLI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SPLI_SPLID + " INTEGER,"
                + SPLI_ITEM + " TEXT,"+SPLI_ITEM_MARK+" INTEGER)";



        String SPLIHST_TABLE = "CREATE TABLE " + TABLE_NAME_LISTITEM_HISTOREY + "("
                + SPLIH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + SPLIH_ITEM + " TEXT"+")";

        db.execSQL(CART_TABLE);
        db.execSQL(SPLIHST_TABLE);
        db.execSQL(SPL_TABLE);
        db.execSQL(SPLI_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SHOPPINGLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LISTITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LISTITEM_HISTOREY);
        // Create tables again
        onCreate(db);
    }


    public void addToCart(Cart data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CART_ITEM_ID, data.ItemID);
        values.put(CART_ITEM_NAME, data.ItemName);
        values.put(CART_ITEM_QTY, data.ItemQty);
        values.put(CART_ITEM_PRICE, data.ItemPrice);
        values.put(CART_ITEM_IMG, data.ItemImg);
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public List<Cart> getCart(int limit) {
        if (limit == 0) limit = 5;
        List<Cart> cartItemList = new ArrayList<Cart>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart cartItem = new Cart();
                cartItem.ID = cursor.getInt(0);
                cartItem.ItemID = cursor.getInt(1);
                cartItem.ItemName = cursor.getString(2);
                cartItem.ItemQty = cursor.getInt(3);
                cartItem.ItemPrice = cursor.getInt(4);
                cartItem.ItemImg = cursor.getString(5);
                // Adding contact to list
                cartItemList.add(cartItem);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
        // return contact list
        Gson gson = new Gson();
        String Reslog= gson.toJson(cartItemList);
        Log.d("test", Reslog);
        return cartItemList;
    }

    public Cart getCartItem(int ItemID) {


        Cart cartItem = new Cart();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " where " + CART_ITEM_ID + "=" + ItemID + " limit 1 ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                cartItem.ID = cursor.getInt(0);
                cartItem.ItemID = cursor.getInt(1);
                cartItem.ItemName = cursor.getString(2);
                cartItem.ItemQty = cursor.getInt(3);
                cartItem.ItemPrice = cursor.getInt(4);
                cartItem.ItemImg = cursor.getString(5);
                // Adding contact to list

            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            //   db.close();
        }

return cartItem;
    }

    public void setItemQty(int ItemID,int Qty,int price  ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues data=new ContentValues();
        data.put(CART_ITEM_QTY,Qty);
        data.put(CART_ITEM_PRICE,price);
        db.update(TABLE_NAME, data, CART_ITEM_ID + "=" + ItemID, null);
        db.close(); // Closing database connection


    }

    public void removeCartItem(int ItemID ) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, CART_ITEM_ID + " = ?",
                new String[]{String.valueOf(ItemID)});

        db.close();

    }

    public void ClearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);

        db.close();

    }



    public int getItemCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


   ;

    public long addShoppingList(ShoppingList data) {
        SQLiteDatabase db = this.getWritableDatabase();
long id=0;
        ContentValues values = new ContentValues();
       // values.put(SPL_ID, data.Id);
        values.put(SPL_NAME, data.Name);
        values.put(SPL_DATE, data.Date);

        // Inserting Row
      id=  db.insert(TABLE_NAME_SHOPPINGLIST, null, values);
        db.close(); // Closing database connection
        return  id;
    }


    public void addShoppingListItem(ShoppingListItem data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(SPLI_ID, data.itemId);
        values.put(SPLI_SPLID, data.ShoppingListId);
        values.put(SPLI_ITEM, data.itemName);
        values.put(SPLI_ITEM_MARK, data.mark);

        // Inserting Row
        db.insert(TABLE_NAME_LISTITEM, null, values);
        db.close(); // Closing database connection
    }



    public void addShoppingListItemHst(ShoppingListItem data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(SPLIH_ID, data.itemId);
        values.put(SPLIH_ITEM, data.itemName);


        // Inserting Row
        db.insert(TABLE_NAME_LISTITEM_HISTOREY, null, values);
        db.close(); // Closing database connection
    }



    public List<ShoppingList> getShoppingList(int limit) {
        if (limit == 0) limit = 5;
        List<ShoppingList> shoppingList = new ArrayList<ShoppingList>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_SHOPPINGLIST ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ShoppingList obj = new ShoppingList();
                obj.Id = cursor.getInt(0);
                obj.Name = cursor.getString(1);
                obj.Date = cursor.getString(2);

                // Adding contact to list
                shoppingList.add(obj);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
        // return contact list
        Gson gson = new Gson();
        String Reslog= gson.toJson(shoppingList);
        Log.d("testme", Reslog);
        return shoppingList;
    }




    public List<ShoppingListItem> getShoppingListItem(int limit,int listId) {
        if (limit == 0) limit = 5;
        List<ShoppingListItem> shoppingListItem = new ArrayList<ShoppingListItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_LISTITEM +" where " + SPLI_SPLID + "=" + listId +"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ShoppingListItem obj = new ShoppingListItem();
                obj.itemId = cursor.getInt(0);
                obj.ShoppingListId = cursor.getInt(1);
                obj.itemName = cursor.getString(2);
                obj.mark = cursor.getInt(3);

                // Adding contact to list
                shoppingListItem.add(obj);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
        // return contact list
        Gson gson = new Gson();
        String Reslog= gson.toJson(shoppingListItem);
        Log.d("test", Reslog);
        return shoppingListItem;
    }



    public List<ShoppingListItem> getShoppingListItemHst(int limit,String key) {
        if (limit == 0) limit = 5;
        List<ShoppingListItem> shoppingListItem = new ArrayList<ShoppingListItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_LISTITEM_HISTOREY +" where " + SPLIH_ITEM + " LIKE " + "'%" +key + "%'" +"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ShoppingListItem obj = new ShoppingListItem();
                obj.itemId = cursor.getInt(0);
                obj.itemName = cursor.getString(1);


                // Adding contact to list
                shoppingListItem.add(obj);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
        // return contact list
        Gson gson = new Gson();
        String Reslog= gson.toJson(shoppingListItem);
        Log.d("testme", Reslog);
        return shoppingListItem;
    }


    public void updateShoppingList(int ListID,String name  ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues data=new ContentValues();
        data.put(SPL_NAME,name);

        db.update(TABLE_NAME_SHOPPINGLIST, data, SPL_ID + "=" + ListID, null);
        db.close(); // Closing database connection


    }


    public void removeShoppingList(int ListId ) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_LISTITEM, SPLI_SPLID + " = ?",
                new String[]{String.valueOf(ListId)});


        db.delete(TABLE_NAME_SHOPPINGLIST, SPL_ID + " = ?",
                new String[]{String.valueOf(ListId)});

        db.close();

    }

    public void removeShoppingListItem(int ItemId ) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_LISTITEM, SPLI_ID + " = ?",
                new String[]{String.valueOf(ItemId)});



        db.close();

    }


    public void setItemMark(int ItemID,int mark  ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues data=new ContentValues();
        data.put(SPLI_ITEM_MARK,mark);

        db.update(TABLE_NAME_LISTITEM, data, SPLI_ID + "=" + ItemID, null);
        db.close(); // Closing database connection


    }


}
