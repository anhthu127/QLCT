package anhthu.dt.quanlychitieu.FragInMainActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import anhthu.dt.quanlychitieu.R;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DAO;
import anhthu.dt.quanlychitieu.SQLiteDatabase.Vi;

public class TaoViFragment extends Fragment {
    Button btnCapNhat;
    TextView txtSoDu;
    EditText edtThem;
    DAO dao;
    TextView ngaythangnam;
    Button btnChon;
    ArrayList<Vi> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.taovi_fragment, container, false);

        btnCapNhat = view.findViewById(R.id.btnCapNhat);
        txtSoDu = view.findViewById(R.id.txtSoDuVi);
        dao = new DAO(getActivity());
        data = dao.getSoDu();
        if (!data.isEmpty()) {
            txtSoDu.setText(Math.round(data.get(0).getTongTien())+"");
        } else {
            txtSoDu.setText("0");
        }

        edtThem = view.findViewById(R.id.edtthemvaovi);
        ngaythangnam = view.findViewById(R.id.labelngaythangnam);
        btnChon = view.findViewById(R.id.btnchonngaythang);
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] date = new String[5];
                Vi vi = new Vi();
                vi.setNgay(ngaythangnam.getText().toString());
                vi.setTongTien(data.get(0).getTongTien());
                if (ngaythangnam.getText() != null) {
                     date= ngaythangnam.getText().toString().split("/");
                } else {
                    ngaythangnam.setText("*");
                    ngaythangnam.setBackgroundColor(R.drawable.invalid_border);
                    ngaythangnam.setTextColor(Color.RED);
                }
                try {
                    vi.setThang(date[1]);

                } catch (Exception e) {


                }
                if (edtThem.getText().toString().equals("")) {
                    vi.setTienThem(0.0);
                } else {
                    vi.setTienThem(Double.valueOf(edtThem.getText().toString()));
                }
                vi.setTongTien(vi.getTongTien() + vi.getTienThem());

                dao.ThemVi(vi);
                txtSoDu.setText(Math.round(vi.getTongTien())+"");

                if (dao.ThemVi(vi) == true) {
                    Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    edtThem.setText("");
                    txtSoDu.setText(Math.round(vi.getTongTien())+"");
                } else {
                    Toast.makeText(getActivity(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
        return view;
    }

    public void ChonNgay() {
        int ngay, thang, nam;
        final Calendar myCalendar = Calendar.getInstance();
        ngay = myCalendar.get(Calendar.DATE);
        thang = myCalendar.get(Calendar.MONTH);
        nam = myCalendar.get(Calendar.YEAR);

        final DatePickerDialog date = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                ngaythangnam.setText(simpleDateFormat.format(myCalendar.getTime()));
            }
        }, nam, thang, ngay);
        date.show();
    }
}
