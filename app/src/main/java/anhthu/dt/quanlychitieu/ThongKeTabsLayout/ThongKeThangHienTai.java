package anhthu.dt.quanlychitieu.ThongKeTabsLayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;
import java.util.Calendar;

import anhthu.dt.quanlychitieu.R;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DAO;
import anhthu.dt.quanlychitieu.SQLiteDatabase.LoaiGiaoDich;
public class ThongKeThangHienTai extends Fragment {
    int mPage;
    String mTittle;
      int count=0;
    String[] itemsList = new String[100];
    float[] numList = new float[100];
    TextView txtChiTieu;
    Context context;
    private PieChart mChart;
    DAO dao;

    RelativeLayout chartLayout;
    int month, year;
    double rut, them, thu = 0.0, chi = 0.0;
    double tongThu = 0.0, tongChi = 0.0;

    public ThongKeThangHienTai() {

    }

    public static ThongKeThangHienTai newInstance(int page, String tittle) {
        ThongKeThangHienTai thongKeThangHienTai = new ThongKeThangHienTai();
        Bundle bundle = new Bundle();
        bundle.putString("page", tittle);
        bundle.putInt("tittle", page);
        thongKeThangHienTai.setArguments(bundle);

        return thongKeThangHienTai;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d("ThongKeThangHienTai", "onCreate: ");
        mPage = getArguments().getInt("page", 1);
        mTittle = getArguments().getString("tittle");
//        Log.d("ThongKeThangHienTai", "onCreate: ");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tk_thang_hien_tai, container, false);
        context = getActivity();
        txtChiTieu = view.findViewById(R.id.txtChiTieu);

        dao = new DAO(getContext());
        Data();
//        for (int i=0; i<itemsList.length;i++) {
//            Log.d("ThongKe", "onCreateView: itemList" + itemsList[i]);
//        }
        txtChiTieu.setText(Math.round(tongChi) + "đ");
        chartLayout = view.findViewById(R.id.chart_layout);
        mChart = new PieChart(context);
        chartLayout.addView(mChart);
        chartLayout.setBackgroundColor(Color.parseColor("#07000000"));

        mChart.setUsePercentValues(true);
        mChart.setDescription("Loại Giao Dịch");
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(context,
                        itemsList[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
            }
        });

        addData();
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);
        return view;
    }

    public void addData() {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//        Log.d("hientai", "addData: "+count);
//        Log.d("thongke", "addData:itemlist " + itemsList.length);
        for (int i = 0; i < count; i++) {
            yVals1.add(new Entry(numList[i], i));
        }
//        Log.d("thongke", "addData: " + yVals1.size());
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count; i++) {
            xVals.add(itemsList[i]);
        }
//        Log.d("thongke", "addData: xVAls " + xVals.size());

        // create pie data set

        PieDataSet dataSet = new PieDataSet(yVals1, " Loai Giao Dich");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }

    public void Data() {
        Calendar calendar = Calendar.getInstance();
        year= calendar.get(Calendar.YEAR);
        month = (calendar.get(Calendar.MONTH) + 1);
        if (month == 13) {
            month = 1;
            year=year+1;
        }
        Double[] vi;
        vi = dao.ThemRut(month);
        them = vi[0];
        ArrayList<Double> giaodich;
        giaodich = dao.LoaiVi(month);
        chi = giaodich.get(0);
        thu = giaodich.get(1);
        tongThu = thu + them;
        tongChi = chi ;

        ArrayList<LoaiGiaoDich> temp = dao.LoaiGiaoDich(month, year);
        count= dao.getCount();
        for (int i = 0; i < count; i++) {
            if (temp.get(i).getLoaiVi() == 0) {
                itemsList[i] = temp.get(i).getLoaiGiaoDich();
                numList[i] = ((float) (temp.get(i).getSoTien() / tongChi)) * 100;
            }
        }

    }

}