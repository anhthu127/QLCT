package anhthu.dt.quanlychitieu.SQLiteDatabase;

public class LoaiGiaoDich {
    String loaiGiaoDich;
    Double soTien;
    int loaiVi;

    public LoaiGiaoDich(String loaiGiaoDich, Double soTien, int loaiVi) {
        this.loaiGiaoDich = loaiGiaoDich;
        this.soTien = soTien;
        this.loaiVi= loaiVi;
    }

    public LoaiGiaoDich() {
    }

    public String getLoaiGiaoDich() {
        return loaiGiaoDich;
    }

    public int getLoaiVi() {
        return loaiVi;
    }

    public void setLoaiVi(int loaiVi) {
        this.loaiVi = loaiVi;
    }

    public void setLoaiGiaoDich(String loaiGiaoDich) {
        this.loaiGiaoDich = loaiGiaoDich;
    }

    public Double getSoTien() {
        return soTien;
    }

    public void setSoTien(Double soTien) {
        this.soTien = soTien;
    }
}
