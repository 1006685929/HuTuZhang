package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Bean.CostBean;

/**
 * Created by hxk on 2018/6/28.
 */

public class DatabaseHelper extends SQLiteOpenHelper{


    public static final String COST_TITLE = "cost_title";
    public static final String COST_DATE = "cost_date";
    public static final String COST_MONEY = "cost_money";

    public DatabaseHelper(Context context) {
        super(context, "HuTu_bill", null, 1);
    }

    @Override//数据库创建
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists bill_cost("+
                "id integer primary key,"+
                "cost_title varchar,"+
                "cost_date varchar,"+
                "cost_money varchar)");
    }

    //把键值对集合存到数据库中
    public void insertCost(CostBean costBean){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COST_TITLE,costBean.costTitle);
        cv.put(COST_DATE,costBean.costDate);
        cv.put(COST_MONEY,costBean.costMoney);
        database.insert("bill_cost",null,cv);
    }

    //获取全部数据
    public Cursor getAllCostData(){
        SQLiteDatabase database = getWritableDatabase();
        return database.query("bill_cost",null,null,null,null,null,"COST_DATE "+"ASC");
    }

    public void del(int id){
        SQLiteDatabase database = getWritableDatabase();
        database.delete("bill_cost","id = ?",new String[]{String.valueOf(id)});
    }

    public void deleteAllData(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete("bill_cost",null,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
