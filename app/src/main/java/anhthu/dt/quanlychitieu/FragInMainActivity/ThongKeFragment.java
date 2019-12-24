package anhthu.dt.quanlychitieu.FragInMainActivity;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import anhthu.dt.quanlychitieu.R;
import anhthu.dt.quanlychitieu.SQLiteDatabase.BarDataObject;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DAO;

public class ThongKeFragment extends Fragment {
    BarDataSet bardataset;
    String TAG = "tag";
    DAO dao;
    ArrayList<BarEntry> entries = new ArrayList<>();
    ArrayList<BarDataObject> barData = new ArrayList<>();
    ArrayList<Double> temp;
    ArrayList<String> labels;
    Context context;

    public ThongKeFragment(Context context) {
        this.context = context;
        GetData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.thongke_fragment, container, false);
        Toast.makeText(getActivity(), "FragmentThongKe", Toast.LENGTH_SHORT).show();
        BarChart barChart = view.findViewById(R.id.barchart);
        barChart.setScaleX(0.9f);
        barChart.setScaleY(0.8f);
        Legend L;
        L = barChart.getLegend();
        L.setEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        leftAxis.setTextSize(10f);
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Log.d("1", "onCreateView: " + barData.get(i).getTongChi());
            Log.d("2", "onCreateView: " + barData.get(i).getTongThu());

            try {
                entries.add(new BarEntry(new float[]{barData.get(i).getTongChi(), barData.get(i).getTongThu()}, i));
                Log.d("chart ", "onCreateView: " + entries.get(i));
            } catch (Exception e) {
            }
        }
        labels = new ArrayList<String>();
        labels.add("T1");
        labels.add("T2");
        labels.add("T3");
        labels.add("T4");
        labels.add("T5");
        labels.add("T6");
        labels.add("T7");
        labels.add("T8");
        labels.add("T9");
        labels.add("T10");
        labels.add("T11");
        labels.add("T12");
        BarDataSet bardataset = new BarDataSet(entries, "Phân loại");
        BarData data = new BarData(labels, bardataset);

        barChart.setData(data); // set the data and list of lables into chart
        barChart.setDescription("");

        int[] colorArray = {android.R.color.holo_blue_light, android.R.color.holo_orange_dark};
        bardataset.setColors(colorArray, getContext());
        barChart.animateY(1000);
        return view;
    }

    public void GetData() {

        dao = new DAO(context);
        for (int i = 1; i < 13; i++) {
            BarDataObject data = new BarDataObject();
            ArrayList<Double> vi = new ArrayList<>();
            vi = dao.LoaiVi(i);
            if (vi.get(0) != null && vi.get(1) != null) {
                data.setTongChi(vi.get(0).floatValue());
                data.setTongThu(vi.get(1).floatValue());
                barData.add(data);
            } else if (vi.get(0) == null && vi.get(1) != null) {
                data.setTongChi(vi.get(0).floatValue());
                data.setTongThu(0f);
                barData.add(data);
            } else if (vi.get(0) != null && vi.get(1) == null) {
                data.setTongChi(0f);
                data.setTongThu(vi.get(1).floatValue());
                barData.add(data);
            }
        }

    }
}
