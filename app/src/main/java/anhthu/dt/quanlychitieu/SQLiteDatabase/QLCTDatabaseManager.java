package anhthu.dt.quanlychitieu.SQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QLCTDatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuanLyChiTieu";

    public static final String TABLE_NAME = "CHITIEU";
    public static final String ID_CHITIEU = "ID_CHITIEU";
    public static final String NGAY = "NGAY";
    public static final String ID_VI = "ID_VI";
    public static final String THANG = "THANG";
    public static final String NAM = "NAM";
    public static final String LOAIGIAODICH = "LOAIGIAODICH";
    public static final String TONGTIEN = "TONGTIEN";
    public static final String SOTIEN = "SOTIEN";
    public static final String CHITIET = "CHITIET";
    public static final String THEM = "THEM";
    public static final String LOAIVI = "LOAIVI";
    public static final String TENGIAODICH = "TENGIAODICH";
    public static int VERSION = 1;

    private String SQLQuery = "CREATE TABLE CHITIEU " +
            "(ID_CHITIEU INTEGER PRIMARY KEY AUTOINCREMENT," +
            "NGAY VARCHAR, THANG VARCHAR, NAM VARCHAR , CHITIET VARCHAR ," +
            " SOTIEN DOUBLE , TENGIAODICH VARCHAR, LOAIGIAODICH VARCHAR, LOAIVI VARCHAR );";

    public QLCTDatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery);
        db.execSQL("CREATE TABLE VI ( ID_VI INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TONGTIEN DOUBLE, THEM DOUBLE, NGAY VARCHAR, THANG VARCHAR );");
        Log.d("tag", "onCreate: created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + "VI");
        onCreate(db);
    }

}