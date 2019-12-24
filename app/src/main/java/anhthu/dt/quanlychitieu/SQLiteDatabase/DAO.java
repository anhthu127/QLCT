package anhthu.dt.quanlychitieu.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

// data access object
public class DAO {
    int count = 0;
    SQLiteDatabase db;
    QLCTDatabaseManager QLCTData;
    Context context;
    String TAG = "TAG";

    public DAO(Context context) {
        QLCTData = new QLCTDatabaseManager(context);
        this.context = context;
    }

    public void open() {

        db = QLCTData.getWritableDatabase();
    }

    public boolean ThemVi(Vi vi) {
        open();
        ContentValues valueVi = new ContentValues();
        valueVi.put("TONGTIEN", vi.getTongTien());
        valueVi.put("THEM", vi.getTienThem());
        valueVi.put("NGAY", vi.getNgay());
        valueVi.put("THANG", vi.getThang());
        long ii = db.insert("VI", null, valueVi);
        String msg;
        if (ii > 0) {
            msg = "Success";
            Log.d("dao", "ThemVi  : " + msg + ii);
            return true;
        } else
            msg = "falure";
        Log.d("dao", "ThemVi: " + msg + "____" + ii);
        return false;
    }

    public boolean ThemGiaoDich(DTO gd) {
        open();
        ContentValues values = new ContentValues();
        values.put("NGAY", gd.getNgay());
        values.put("THANG", gd.getThang());
        values.put("NAM", gd.getNam());
        values.put("SOTIEN", gd.getSotien());
        values.put("LOAIGIAODICH", gd.getLoaigiaodich());
        values.put("TENGIAODICH", gd.getTenGD());
        values.put("LOAIVI", gd.getLoaivi());
        values.put("CHITIET", gd.getChiTiet());
        long i = db.insert(QLCTDatabaseManager.TABLE_NAME, null, values);
        String msg;
        if (i > 0) {
            msg = "Success";
//            Log.d("dao", "ThemGiaoDich  : " + msg + i);
            return true;
        } else
            msg = "falure";
//        Log.d("dao", "ThemGiaoDich  : " + msg + i + "____");
        return false;
    }

    public ArrayList<DTO> LayDuLieu() {
        open();
        ArrayList<DTO> data = new ArrayList<>();
        Cursor cursor;
        String selectQuery = "SELECT  * FROM 'CHITIEU'";
        SQLiteDatabase db = QLCTData.getWritableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() != true) {
                DTO dto = new DTO();
                dto.setNgay(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.NGAY)));
                dto.setThang(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.THANG)));
                dto.setNam(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.NAM)));
                dto.setSotien(cursor.getDouble(cursor.getColumnIndex(QLCTDatabaseManager.SOTIEN)));
                dto.setLoaigiaodich(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.LOAIGIAODICH)));
                dto.setTenGD(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.TENGIAODICH)));
                dto.setLoaivi(cursor.getInt(cursor.getColumnIndexOrThrow(QLCTDatabaseManager.LOAIVI)));
                dto.setId(cursor.getInt(cursor.getColumnIndexOrThrow(QLCTDatabaseManager.ID_CHITIEU)));
                data.add(dto);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return data;
    }

    public ArrayList<Vi> getSoDu() {
        open();
        Vi vi = new Vi();
        ArrayList<Vi> data = new ArrayList<>();
        Cursor cursor;
        SQLiteDatabase db = QLCTData.getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM 'VI' WHERE ID_VI = (SELECT MAX (ID_VI) FROM 'VI')", null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                vi.setTongTien(cursor.getDouble(cursor.getColumnIndex(QLCTDatabaseManager.TONGTIEN)));
                vi.setTienThem(cursor.getDouble(cursor.getColumnIndex(QLCTDatabaseManager.THEM)));
                data.add(vi);

            } else Log.d("daoViTien", "getSoDu: cursor.moveToFirst null " + cursor.moveToFirst());
        }
        ;
        cursor.close();

        return data;
    }

    public ArrayList<DTO> HienTai() {
        open();
        ArrayList<DTO> dataHienTai = new ArrayList<DTO>();
        Calendar myCalendar = Calendar.getInstance();
        int month = myCalendar.get(Calendar.MONTH);
        Cursor cursor;
        String query = "SELECT * FROM 'CHITIEU' WHERE THANG =" + (month + 1) + " ORDER BY NGAY ASC ;";
        cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() != true) {
                DTO dto = new DTO();
                dto.setNgay(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.NGAY)));
                dto.setThang(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.THANG)));
                dto.setNam(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.NAM)));
                dto.setSotien(cursor.getDouble(cursor.getColumnIndex(QLCTDatabaseManager.SOTIEN)));
                dto.setLoaigiaodich(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.LOAIGIAODICH)));
                dto.setTenGD(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.TENGIAODICH)));
                dto.setChiTiet(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.CHITIET)));
//                Log.d("dao", "HienTai: "+cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.TENGIAODICH)));
//                Log.d("dao", "HienTai: "+cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.THANG)));
                dto.setLoaivi(cursor.getInt(cursor.getColumnIndexOrThrow(QLCTDatabaseManager.LOAIVI)));
                dto.setId(cursor.getInt(cursor.getColumnIndexOrThrow(QLCTDatabaseManager.ID_CHITIEU)));
                dataHienTai.add(dto);
//                Log.d("dao", "HienTaihihk: " +dto.getTenGD());
                cursor.moveToNext();
            }
        }
        cursor.close();
//        for(int i=0; i<dataHienTai.size();i++){
//            Log.d("dao", "dataTenGD: "+i+"__"+dataHienTai.get(i).getTenGD());
//            Log.d("dao", "dataThang: "+i+"____"+dataHienTai.get(i).getThang());}

        return dataHienTai;
    }

    public ArrayList<DTO> ThangTruoc() {
        open();
        ArrayList<DTO> dataThangTruoc = new ArrayList<DTO>();
        Calendar myCalendar = Calendar.getInstance();
        int month = myCalendar.get(Calendar.MONTH);
        Cursor cursor;
        String query = "SELECT * FROM 'CHITIEU' WHERE THANG =" + (month) + " ORDER BY NGAY ASC ;";
        cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() != true) {
                DTO dto = new DTO();
                dto.setNgay(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.NGAY)));
                dto.setThang(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.THANG)));
                dto.setNam(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.NAM)));
                dto.setSotien(cursor.getDouble(cursor.getColumnIndex(QLCTDatabaseManager.SOTIEN)));
                dto.setLoaigiaodich(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.LOAIGIAODICH)));
                dto.setTenGD(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.TENGIAODICH)));
                dto.setLoaivi(cursor.getInt(cursor.getColumnIndexOrThrow(QLCTDatabaseManager.LOAIVI)));
                dto.setId(cursor.getInt(cursor.getColumnIndexOrThrow(QLCTDatabaseManager.ID_CHITIEU)));
                dto.setChiTiet(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.CHITIET)));
                dataThangTruoc.add(dto);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return dataThangTruoc;
    }

    public ArrayList<DTO> ThangSau() {
        open();
        String query;
        ArrayList<DTO> dataThangSau = new ArrayList<DTO>();
        Calendar myCalendar = Calendar.getInstance();
        int month = myCalendar.get(Calendar.MONTH);
        Cursor cursor;

        if (month != 11) {
            query = "SELECT * FROM 'CHITIEU' WHERE THANG =" + (month + 2) + " ORDER BY NGAY ASC ;";

        } else {
            query = "SELECT * FROM 'CHITIEU' WHERE THANG = '01'" + " ORDER BY NGAY ASC ;";
        }
        cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
//            Log.d("dao", "ThangHienTai.isAfterLast()___movetoFirst: " + cursor.isAfterLast() + "____" + cursor.moveToFirst());
            while (cursor.isAfterLast() != true) {
                DTO dto = new DTO();
                dto.setNgay(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.NGAY)));
                dto.setThang(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.THANG)));
                dto.setNam(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.NAM)));
                dto.setSotien(cursor.getDouble(cursor.getColumnIndex(QLCTDatabaseManager.SOTIEN)));
                dto.setLoaigiaodich(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.LOAIGIAODICH)));
                dto.setTenGD(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.TENGIAODICH)));
                dto.setLoaivi(cursor.getInt(cursor.getColumnIndexOrThrow(QLCTDatabaseManager.LOAIVI)));
                dto.setId(cursor.getInt(cursor.getColumnIndexOrThrow(QLCTDatabaseManager.ID_CHITIEU)));
                dto.setChiTiet(cursor.getString(cursor.getColumnIndex(QLCTDatabaseManager.CHITIET)));
                dataThangSau.add(dto);
                cursor.moveToNext();
            }
        }
        cursor.close();

        return dataThangSau;
    }

    public void GiuXoa(int id) {
        open();
        Log.d("dao", "GiuXoa: " + id);
        try {
            db.delete("CHITIEU", "ID_CHITIEU= " + id, null);
        } catch (Exception e) {
            Log.d("dao", "GiuXoa: " + e.toString());
        }
    }

    public int update(int id, DTO gd) {
        open();
        ContentValues values = new ContentValues();
        values.put("SOTIEN", gd.getSotien());
        values.put("LOAIGIAODICH", gd.getLoaigiaodich());
        values.put("TENGIAODICH", gd.getTenGD());
        values.put("LOAIVI", gd.getLoaivi());
        values.put("CHITIET", gd.getChiTiet());
        int result = db.update("CHITIEU", values, "ID_CHITIEU =" + id, null);
        Log.d(TAG, "update: " + result);
    return result;
    }

    public ArrayList<Double> LoaiVi(int i) {
        open();
        ArrayList<Double> data = new ArrayList<>();
        Cursor cursor;
        Double thu = 0.0, chi = 0.0;
        String month = String.valueOf(i);
        String selectQuery = "SELECT LOAIVI ,SUM (SOTIEN) AS SOTIEN FROM 'CHITIEU' WHERE THANG = " + (month) + " GROUP BY LOAIVI;";
        cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() != true) {
                if (cursor.getInt(0) == 0) {
                    chi += cursor.getDouble(1);
                } else {
                    thu += cursor.getDouble(1);
                }
                cursor.moveToNext();
            }
        } else {
            chi = 0.0;
            thu = 0.0;

        }
        data.add(chi);
        data.add(thu);

        return data;
    }

    public ArrayList<LoaiGiaoDich> LoaiGiaoDich(int i, int ii) {
        open();
        String month;
        if (i >= 10) {
            month = String.valueOf(i);
        } else {
            month = "0" + i;

        }
//        Log.d("dao", "month: " + month);

        ArrayList<LoaiGiaoDich> data = new ArrayList<>();
        Cursor cursor;
        String selectQuery = "SELECT  LOAIGIAODICH,SUM(SOTIEN) AS SOTIEN, LOAIVI FROM CHITIEU WHERE THANG=" + "'" + month + "'" + " AND LOAIVI=0 GROUP BY LOAIGIAODICH";
        cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() != true) {
                LoaiGiaoDich loaiGiaoDich = new LoaiGiaoDich();
                loaiGiaoDich.setLoaiGiaoDich(cursor.getString(0));
                loaiGiaoDich.setSoTien(cursor.getDouble(1));
                loaiGiaoDich.setLoaiVi(cursor.getInt(2));
                data.add(loaiGiaoDich);
                cursor.moveToNext();
            }
        }

        count = data.size();
//        for (int a = 0; a < count; a++) {
//            Log.d("daao", "LoaiGiaoDich: " + data.get(a).getLoaiGiaoDich());
//
//        }
//        ;
        db.close();

        return data;
    }

    public Double[] ThemRut(int i) {
        open();
        String month = String.valueOf(i);
        Double them = 0.0;
        Double[] tongThem = new Double[1];
        Cursor cursorThem;
        cursorThem = db.rawQuery("select sum(THEM) as THEM FROM VI WHERE THANG=" + month + ";", null);
        if (cursorThem != null) {
            cursorThem.moveToFirst();
            if (cursorThem.isAfterLast() != true) {
                cursorThem.moveToFirst();
                them += cursorThem.getDouble(cursorThem.getColumnIndex(cursorThem.getColumnName(0)));
            }
        } else {
            them = 0.0;
        }
        tongThem[0] = them;
        cursorThem.close();
        return tongThem;
    }

    public boolean ResetChiTieu() {
        open();
        int result = db.delete("CHITIEU", null, null);
        Log.d(TAG, "ResetChiTieu: " + result);
        db.close();
        return true;
    }

    public boolean ResetVi() {
        open();
        String query = "DELETE FROM VI";
        int result = db.delete("VI", null, null);
        Log.d(TAG, "ResetVi: " + result);
        db.close();
        return true;
    }

    public int getCount() {
        Log.d("dao", "getCount: " + count);
        return count;
    }
}