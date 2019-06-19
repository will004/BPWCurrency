package com.example.bpwcurrency;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bpwcurrencyModel.Currency;
import com.example.bpwcurrencyModel.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "User.db";
    private static final String TABLE_USER = "user";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_BALANCEACCOUNT = "balanceAccount";
    private static final String COLUMN_BALANCEUSD = "balanceUSD";
    private static final String COLUMN_BALANCESGD = "balanceSGD";
    private static final String COLUMN_BALANCEJPY = "balanceJPY";

    private static String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_EMAIL + " TEXT NOT NULL,"
            + COLUMN_PASSWORD + " TEXT NOT NULL,"
            + COLUMN_PHONE + " TEXT NOT NULL,"
            + COLUMN_BALANCEACCOUNT + " REAL NOT NULL,"
            + COLUMN_BALANCEUSD + " REAL NOT NULL,"
            + COLUMN_BALANCESGD + " REAL NOT NULL,"
            + COLUMN_BALANCEJPY + " REAL NOT NULL" + ")";

    private static String CREATE_TABLE_CURRENCY = String.format(
            "CREATE TABLE currency (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT NOT NULL," +
                    "%s REAL NOT NULL," +
                    "%s REAL NOT NULL)",
            "id", "currency_name", "currency_sell", "currency_buy"
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_CURRENCY);
        db.execSQL("INSERT INTO user (name,email,password,phone,balanceAccount, balanceUSD, balanceSGD, balanceJPY) VALUES ('Budi Kosasih','budikos@gmail.com','00000000','087774362588',1000000,0,0,0)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS currency");
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_BALANCEACCOUNT, user.getBalanceAccount());
        values.put(COLUMN_BALANCEUSD, user.getBalanceUSD());
        values.put(COLUMN_BALANCESGD, user.getBalanceSGD());
        values.put(COLUMN_BALANCEJPY, user.getBalanceJPY());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addCurrency(ArrayList<Currency> currencies) {
        //if table currency already exist, drop it and create it again
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS currency");
        sqLiteDatabase.execSQL(CREATE_TABLE_CURRENCY);
        sqLiteDatabase.close();

        for (int i = 0; i < currencies.size(); i++) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("currency_name", currencies.get(i).getCurrency_name());
            values.put("currency_sell", currencies.get(i).getCurrency_value_sell());
            values.put("currency_buy", currencies.get(i).getCurrency_value_buy());

            db.insert("currency", null, values);
            db.close();
        }

    }

    public Cursor getAllCurrency() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM currency", null);
        return cursor;
    }


    public User checkUser(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER,// Selecting Table
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_PHONE,
                        COLUMN_BALANCEACCOUNT, COLUMN_BALANCEUSD, COLUMN_BALANCESGD, COLUMN_BALANCEJPY},//Selecting columns want to query
                COLUMN_EMAIL + "=?",
                new String[]{user.getEmail()},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getDouble(5), cursor.getDouble(6), cursor.getDouble(7), cursor.getDouble(8));

            //Match both passwords check they are same or not
            if (user.getPassword().equalsIgnoreCase(user1.getPassword())) {
                cursor.close();
                return user1;
            }
            cursor.close();
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public User showUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_USER+" WHERE "+COLUMN_EMAIL+" = ?", new String[]{email});
        User temp = null;

        if(cursor != null && cursor.moveToFirst() && cursor.getCount() > 0){
            temp = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    cursor.getDouble(5), cursor.getDouble(6), cursor.getDouble(7), cursor.getDouble(8));
        }
        cursor.close();

        return temp;
    }

    public void updateBalance(double newBalance, String email){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values =  new ContentValues();
        values.put(COLUMN_BALANCEACCOUNT, newBalance);

        db.update(TABLE_USER, values, COLUMN_EMAIL + " = ?", new String[]{email});
    }

    public void increaseCurrency(double newBalance, String email, String currency){
        SQLiteDatabase db = this.getReadableDatabase();

        User logged = showUser(email);

        ContentValues values = new ContentValues();


        if(currency.equals("USD")){
            values.put(COLUMN_BALANCEUSD, logged.getBalanceUSD()+newBalance);
        }
        else if(currency.equals("SGD")){
            values.put(COLUMN_BALANCESGD, logged.getBalanceSGD()+newBalance);
        }
        else if(currency.equals("JPY")){
            values.put(COLUMN_BALANCEJPY, logged.getBalanceJPY()+newBalance);
        }

        db.update(TABLE_USER, values, COLUMN_EMAIL + " = ?", new String[]{email});
    }

    public void decreaseCurrency(double newBalance, String email, String currency){
        SQLiteDatabase db = this.getReadableDatabase();

        User logged = showUser(email);

        ContentValues values = new ContentValues();


        if(currency.equals("USD")){
            values.put(COLUMN_BALANCEUSD, logged.getBalanceUSD()-newBalance);
        }
        else if(currency.equals("SGD")){
            values.put(COLUMN_BALANCESGD, logged.getBalanceSGD()-newBalance);
        }
        else if(currency.equals("JPY")){
            values.put(COLUMN_BALANCEJPY, logged.getBalanceJPY()-newBalance);
        }

        db.update(TABLE_USER, values, COLUMN_EMAIL + " = ?", new String[]{email});
    }
}
