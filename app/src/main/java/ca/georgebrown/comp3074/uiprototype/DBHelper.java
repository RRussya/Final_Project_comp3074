package ca.georgebrown.comp3074.uiprototype;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Version of the database
    private static final int DB_VERSION = 2; // Incremented version

    // Name of the database
    private static final String DB_NA = "Menu.db";

    // Table name
    private static final String TABLE_NAME = "MENU_TABLE";

    // Columns
    private static final String COLUMN_ID = "MENU_ID";
    private static final String COLUMN_CATEGORY = "MENU_CATEGORY";
    private static final String COLUMN_NAME = "MENU_NAME";
    private static final String COLUMN_PRICE = "MENU_PRICE";

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PRICE + " REAL" // Assuming price is a decimal value
                + ")";
        db.execSQL(CREATE_TABLE_QUERY);
    }

    // Updating Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle migrations or updates if needed
        // For simplicity, we'll drop and recreate the table in this example
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert or update a menu item
    public boolean saveOrUpdate(MenuModel menuModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY, menuModel.getCategory());
        cv.put(COLUMN_NAME, menuModel.getName());
        cv.put(COLUMN_PRICE, menuModel.getPrice());

        if (menuModel.getId() == 0) {
            // Insert new item
            long insert = db.insert(TABLE_NAME, null, cv);
            db.close();
            return insert != -1;
        } else {
            // Update existing item
            int updatedRows = db.update(TABLE_NAME, cv, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(menuModel.getId())});
            db.close();
            return updatedRows > 0;
        }
    }

    public boolean deleteOne(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return deletedRows > 0;
    }

    public List<MenuModel> getAllItems() {
        List<MenuModel> returnList = new ArrayList<>();

        String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.rawQuery(SELECT_ALL_QUERY, null)) {
            while (cursor.moveToNext()) {
                int menuIdColumnIndex = cursor.getColumnIndex(COLUMN_ID);
                int menuCategoryColumnIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
                int menuNameColumnIndex = cursor.getColumnIndex(COLUMN_NAME);
                int menuPriceColumnIndex = cursor.getColumnIndex(COLUMN_PRICE);

                // Check if the column exists in the cursor
                if (menuIdColumnIndex != -1 && menuCategoryColumnIndex != -1
                        && menuNameColumnIndex != -1 && menuPriceColumnIndex != -1) {

                    int menuId = cursor.getInt(menuIdColumnIndex);
                    String menuCategory = cursor.getString(menuCategoryColumnIndex);
                    String menuName = cursor.getString(menuNameColumnIndex);
                    double menuPrice = cursor.getDouble(menuPriceColumnIndex);

                    MenuModel newItem = new MenuModel(menuId, menuCategory, menuName, menuPrice);
                    returnList.add(newItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close db
            db.close();
        }

        return returnList;
    }
}
