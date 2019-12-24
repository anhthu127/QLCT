package anhthu.dt.quanlychitieu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import anhthu.dt.quanlychitieu.SQLiteDatabase.DTO;

public class ListGDAdapter extends BaseAdapter {
    ArrayList<DTO> arrayData;
    Activity activity;
    Context context;

    public ListGDAdapter(Context context, ArrayList<DTO> arrayData) {
        this.arrayData = arrayData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayData.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {

        TextView txtdate, txtLoaiGD, txtSoTien;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.listview_custom, parent, false);
            holder = new ViewHolder();
            holder.txtdate = view.findViewById(R.id.id_dmy);
            holder.txtLoaiGD = view.findViewById(R.id.id_tengd);
            holder.txtSoTien = view.findViewById(R.id.id_sotien);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        DTO items = (DTO) getItem(position);
        holder.txtSoTien.setText(String.valueOf(items.getSotien()));
        holder.txtLoaiGD.setText(String.valueOf(items.getLoaigiaodich()));
        holder.txtdate.setText(((items.getNgay() + "/" + items.getThang() + "/" + items.getNam())));

        return view;
    }
}
