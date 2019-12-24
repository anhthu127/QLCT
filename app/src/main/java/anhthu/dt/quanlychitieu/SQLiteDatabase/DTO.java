package anhthu.dt.quanlychitieu.SQLiteDatabase;

// data transfer object
public class DTO {

    private String tenGD,chiTiet, thang,ngay, nam;
    private int id;
    private Double sodu, tongtien, sotien;
    private String loaigiaodich;
    private int loaivi;

    public DTO(String tenGD,String chiTiet, String thang, String ngay, String nam, int id, Double sodu, Double tongtien, Double sotien, String loaigiaodich, int loaivi) {
        this.tenGD = tenGD;
        this.thang = thang;
        this.ngay = ngay;
        this.nam = nam;
        this.id = id;
        this.sodu = sodu;
        this.tongtien = tongtien;
        this.sotien = sotien;
        this.loaigiaodich = loaigiaodich;
        this.loaivi = loaivi;
        this.chiTiet= chiTiet;
    }

    public DTO() {
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }

    public String getTenGD() {
        return tenGD;
    }

    public void setTenGD(String tenGD) {
        this.tenGD = tenGD;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getSodu() {
        return sodu;
    }

    public void setSodu(Double sodu) {
        this.sodu = sodu;
    }

    public Double getTongtien() {
        return tongtien;
    }

    public void setTongtien(Double tongtien) {
        this.tongtien = tongtien;
    }

    public Double getSotien() {
        return sotien;
    }

    public void setSotien(Double sotien) {
        this.sotien = sotien;
    }

    public String getLoaigiaodich() {
        return loaigiaodich;
    }

    public void setLoaigiaodich(String loaigiaodich) {
        this.loaigiaodich = loaigiaodich;
    }

    public int getLoaivi() {
        return loaivi;
    }

    public void setLoaivi(int loaivi) {
        this.loaivi = loaivi;
    }
}

