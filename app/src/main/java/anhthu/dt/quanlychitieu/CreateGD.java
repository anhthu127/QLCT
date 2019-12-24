package anhthu.dt.quanlychitieu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import anhthu.dt.quanlychitieu.SQLiteDatabase.DAO;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DTO;
import anhthu.dt.quanlychitieu.SQLiteDatabase.QLCTDatabaseManager;
import anhthu.dt.quanlychitieu.SQLiteDatabase.Vi;

import static anhthu.dt.quanlychitieu.R.drawable.invalid_border;

public class CreateGD extends AppCompatActivity {
    Button btnCreateGD, btnDialog;
    EditText edtTenGD, edtSoTien, edtDialog, edtMota;
    RadioGroup rdg;
    Spinner spinner;
    String spinnerData;
    TextView lbNgayThang, txtVi;
    Toolbar toolbar;
    ImageView img;
    QLCTDatabaseManager data = new QLCTDatabaseManager(CreateGD.this);
    DAO dao;
    int type;
    int ngay, thang, nam;
    String[] date;
    Vi vi = new Vi();
    final DAO qlctDao = new DAO(CreateGD.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        dao = new DAO(CreateGD.this);
        vi = new Vi();
        ArrayList<Vi> data = qlctDao.getSoDu();
        btnCreateGD = findViewById(R.id.btntaogiaodich);
        lbNgayThang = findViewById(R.id.labelngaythangnam);
        edtSoTien = findViewById(R.id.edtsotien);
        edtTenGD = findViewById(R.id.edtTenGd);
        edtMota= findViewById(R.id.edtMota);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
      toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateGD.this, MainActivity.class));
            }
        });
        txtVi = findViewById(R.id.txtVi);
        if (data.isEmpty()) {
            txtVi.setText("0 đ");
        } else {
            txtVi.setText(Math.round(data.get(0).getTongTien())+" đ");
        }
        img = findViewById(R.id.imageView2);
        rdg = findViewById(R.id.rdgParents);
        spinner = findViewById(R.id.spinnerloaigiaodich);
        btnDialog = findViewById(R.id.btnchonngaythang);

        final ArrayList<String> arrL = new ArrayList<String>();
        arrL.add("Hóa Đơn");
        arrL.add("Đi Lại");
        arrL.add("Ăn uống");
        arrL.add("Đầu Tư");
        arrL.add("Khác");
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, arrL);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        // đổ dữ liệu vào spinner qua ArrayAdapter
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CreateGD.this, arrL.get(position),
                        Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerData = spinner.getSelectedItem().toString();
                if (spinnerData.equals("Khác")) {
                    final Dialog dialog = new Dialog(CreateGD.this);
                    LayoutInflater inflater = LayoutInflater.from(CreateGD.this);
                    View dialogView = inflater.inflate(R.layout.custom_spinner_dialog, null);
                    edtDialog = dialogView.findViewById(R.id.custom_spinner);
                    dialog.setContentView(dialogView);
                    dialog.setTitle("Thêm Loại Giao Dịch");
                    dialog.setTitle("Loại Giao Dịch");
                    int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
                    int height = (int)(getResources().getDisplayMetrics().heightPixels*0.30);

                    dialog.getWindow().setLayout(width, height);
                    dialog.getWindow();
                    dialog.show();
                    Button btnDialog = dialogView.findViewById(R.id.btn_dialog);
                    btnDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String temp = edtDialog.getText().toString();
                            arrL.remove(arrL.size() - 1);
                            arrL.add(temp);
                            arrL.add("Khác");
                            arrayAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        qlctDao.open();
        btnCreateGD.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DTO giaodich = new DTO();
                        qlctDao.getSoDu();
                        if (lbNgayThang.getText().toString() != "") {
                            date = lbNgayThang.getText().toString().split("/");
                            giaodich.setNam(date[2]);
                            giaodich.setNgay(date[0]);
                            giaodich.setThang(date[1]);
                        } else if (lbNgayThang.getText().toString() == "") {
                            lbNgayThang.setText("*");
                            lbNgayThang.setTextColor(Color.RED);
                            lbNgayThang.setBackgroundColor(invalid_border);
                        }
                        if (!spinnerData.equals("Khác")) {

                            giaodich.setLoaigiaodich(spinnerData);
                        } else {
                            giaodich.setLoaigiaodich(arrL.get(arrL.size()-2));
                        }

                        giaodich.setLoaivi(type);
                        if (edtTenGD.getText() != null) {
                            giaodich.setTenGD(edtTenGD.getText().toString());
                        } else {
                            edtTenGD.setText("*");
                            edtTenGD.setTextColor(Color.RED);
                            edtTenGD.setBackgroundColor(invalid_border);
                        }
                        giaodich.setChiTiet(edtMota.getText()+"");
                        if (edtSoTien.getText() != null) {
                            giaodich.setSotien(Double.valueOf(edtSoTien.getText().toString()));
                        } else {
                            edtSoTien.setText("*");
                            edtSoTien.setTextColor(Color.RED);
                            edtSoTien.setBackgroundColor(invalid_border);

                        }
                        Boolean a = qlctDao.ThemGiaoDich(giaodich);
                        if (a == true) {
                            Toast.makeText(CreateGD.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                            edtTenGD.setText("");
                            edtSoTien.setText("");
                            lbNgayThang.setText("");
                            edtMota.setText("");
                            ArrayList<Vi> data = qlctDao.getSoDu();
                            if (type == 0) {
                                if (data.size() > 0) {
                                    txtVi.setText(Math.round(data.get(0).getTongTien() - giaodich.getSotien())+" đ");
                                    vi.setTongTien(data.get(0).getTongTien() - giaodich.getSotien());
                                    giaodich.setTongtien(vi.getTongTien());
                                    vi.setTongTien(giaodich.getTongtien());
                                    vi.setTienThem(0.0);
                                    vi.setNgay(lbNgayThang.getText().toString());
                                    qlctDao.ThemVi(vi);
                                } else {
                                    txtVi.setText(("-"+Math.round(giaodich.getSotien())+" đ"));
                                    vi.setTongTien(-giaodich.getSotien());
                                    giaodich.setTongtien(vi.getTongTien());
                                    vi.setTongTien(giaodich.getTongtien());
                                    vi.setTienThem(0.0);
                                    vi.setNgay(lbNgayThang.getText().toString());
                                    qlctDao.ThemVi(vi);
                                }


                            } else if (type == 1) {
                                if (data.size() > 0) {
                                    txtVi.setText(Math.round(data.get(0).getTongTien() + giaodich.getSotien())+" đ");
                                    vi.setTongTien(data.get(0).getTongTien() + giaodich.getSotien());
                                    giaodich.setTongtien(vi.getTongTien());
                                    vi.setTongTien(giaodich.getTongtien());
                                    vi.setTienThem(0.0);
                                    vi.setNgay(lbNgayThang.getText().toString());
                                    qlctDao.ThemVi(vi);
                                } else {
                                    txtVi.setText("" + Math.round(giaodich.getSotien())+" đ");
                                    vi.setTongTien(giaodich.getSotien());
                                    giaodich.setTongtien(vi.getTongTien());
                                    vi.setTongTien(giaodich.getTongtien());
                                    vi.setTienThem(0.0);
                                    vi.setNgay(lbNgayThang.getText().toString());
                                    qlctDao.ThemVi(vi);
                                }
                            }
                        } else {
                            Toast.makeText(CreateGD.this, "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );

        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdChi:
//                        Toast.makeText(CreateGD.this, "", Toast.LENGTH_SHORT).show();
                        type = 0;
                        break;
                    case R.id.rdThu:
                        type = 1;
//                        Toast.makeText(CreateGD.this, "checked rdthus", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
    }

    public void ChonNgay() {
//        Date date= new Date();

        final Calendar myCalendar = Calendar.getInstance();
//        Log.d("create", "ChonNgay: " + myCalendar.get(Calendar.MONTH) + "__" + myCalendar.get(Calendar.DATE) + "__" + myCalendar.get(Calendar.YEAR));
        ngay = myCalendar.get(Calendar.DATE);
        thang = myCalendar.get(Calendar.MONTH);
        nam = myCalendar.get(Calendar.YEAR);

        final DatePickerDialog date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                lbNgayThang.setText(simpleDateFormat.format(myCalendar.getTime()));
            }
        }, nam, thang, ngay);
        date.show();
    }


}
