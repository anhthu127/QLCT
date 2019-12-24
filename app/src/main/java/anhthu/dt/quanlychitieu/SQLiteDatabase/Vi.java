package anhthu.dt.quanlychitieu.SQLiteDatabase;

    public class Vi{
        Double tongTien;
        Double  tienThem;
        String ngay, thang;

        public Vi(Double tongTien, Double tienThem, String ngay, String thang) {
            this.tongTien = tongTien;
            this.tienThem = tienThem;
            this.ngay = ngay;
            this.thang= thang;

        }

        public String getNgay() {
            return ngay;
        }

        public void setNgay(String ngay) {
            this.ngay = ngay;
        }


        public String getThang() {
            return thang;
        }

        public void setThang(String thang) {
            this.thang = thang;
        }


        public Double getTienThem() {
            return tienThem;
        }

        public void setTienThem(Double tienThem) {
            this.tienThem = tienThem;
        }

        public Double getTongTien() {
            return tongTien;
        }

        public void setTongTien(Double tongTien) {
            this.tongTien = tongTien;
        }

        public Vi() {
        }

        public Vi(Double tongTien) {
            this.tongTien = tongTien;
        }
    }
