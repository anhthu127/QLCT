package anhthu.dt.quanlychitieu.TabsLayout;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import anhthu.dt.quanlychitieu.R;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DAO;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DTO;
import anhthu.dt.quanlychitieu.SQLiteDatabase.Vi;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.ViewHolder> implements Filterable {
    Context c;
    ArrayList<DTO> data;
    ArrayList<DTO> mStringFilterList;
    Dialog dialog;
    ValueFilter valueFilter;
    View view;

    public TabAdapter(ArrayList<DTO> data, Context context) {
        this.c = context;
        this.data = new ArrayList<DTO>();
        mStringFilterList = new ArrayList<>();
        this.data = data;
        mStringFilterList = data;
    }

    public TabAdapter(ArrayList<DTO> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_custom,
                parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder = new ViewHolder(holder.itemView);
        (holder).txtdate.setText(data.get(position).getNgay() + "/" + data.get(position).getThang() + "/" + data.get(position).getNam());
        (holder).txtSoTien.setText(Math.round(data.get(position).getSotien()) + " đ");
        (holder).txtTenGD.setText(data.get(position).getTenGD());
        (holder).img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog(position);
                notifyDataSetChanged();
            }
        });
        (holder).txtdate.setText(data.get(position).getNgay() + "/" + data.get(position).getThang() + "/" + data.get(position).getNam());
        (holder).txtSoTien.setText(Math.round(data.get(position).getSotien()) + " đ");
        (holder).txtTenGD.setText(data.get(position).getTenGD());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dialogXoa(position);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogXoa(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void dialogXoa(final int position) {
        dialog = new Dialog(c);
        LayoutInflater inflater = LayoutInflater.from(c);
        View dialogView = inflater.inflate(R.layout.popup_window, null);
        ImageView imgCo = dialogView.findViewById(R.id.img_co);
        ImageView imgKhong = dialogView.findViewById(R.id.img_khong);
        dialog.setContentView(dialogView);
        dialog.setTitle("Xác nhận");
        int width = (int) (c.getResources().getDisplayMetrics().widthPixels * 0.9);
        int height = (int) (c.getResources().getDisplayMetrics().heightPixels * 0.30);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_border);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imgCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAO dao = new DAO(c);
                dao.GiuXoa(data.get(position).getId());
                if (data.get(position).getLoaivi() == 1) {
                    Vi vi = new Vi();
                    vi.setTongTien(dao.getSoDu().get(0).getTongTien() - data.get(position).getSotien());
                    dao.ThemVi(vi);
                } else {
                    Vi vi = new Vi();
                    vi.setTongTien(dao.getSoDu().get(0).getTongTien() + data.get(position).getSotien());
                    dao.ThemVi(vi);
                }
                data.remove(position);
                notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(c, "Xóa thành công", Toast.LENGTH_SHORT);
            }
        });
        imgKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {

        public ValueFilter() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String str = constraint.toString().toLowerCase();
            ArrayList<DTO> filterList = new ArrayList<DTO>();

            if (constraint != null && constraint.length() > 0) {

                for (DTO dto : mStringFilterList) {
                    String temp;
                    if (dto.getLoaivi() == 1) {
                        temp = "Khoản thu";
                    } else {
                        temp = "Khoản chi";
                    }
                    if (dto.getTenGD().toLowerCase().contains(str)
                            || (dto.getLoaigiaodich().toLowerCase()).contains(str)
                            || (dto.getThang().toLowerCase()).contains(str) ||
                            (dto.getNam().toLowerCase()).contains(str)
                            || (dto.getNgay().toLowerCase()).contains(str)) {

                        filterList.add(dto);
                    }
                }

            } else {
                filterList.addAll(mStringFilterList);
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data = (ArrayList<DTO>) results.values;
            notifyDataSetChanged();
            Log.d("hientai ", "hientai: size " + data.size());
            for (int i = 0; i < data.size(); i++) {
                Log.d("hientai ", "hientai: tengd " + data.get(i).getTenGD());

            }
        }
    }

    public void editDialog(final int position) {
        Log.d("", "editDialog: " + position);
        final Dialog editDialog = new Dialog(c);
        LayoutInflater inflater = LayoutInflater.from(c);
        View dialogView = inflater.inflate(R.layout.dialog_info_item, null);
        final EditText edtTenGD, edtloaiGD, edtSotien, edtChiTiet;
        final TextView edtLoaivi;
        Button btnCapNhat, btnThoat;
        edtChiTiet = dialogView.findViewById(R.id.edtMota);
        edtTenGD = dialogView.findViewById(R.id.edtTenGd);
        edtSotien = dialogView.findViewById(R.id.edtSoTien);
        edtloaiGD = dialogView.findViewById(R.id.edtLoaigf);
        edtLoaivi = dialogView.findViewById(R.id.edtLoaiVi);
        btnCapNhat = dialogView.findViewById(R.id.id_btn_Capnhat);
        btnThoat = dialogView.findViewById(R.id.id_btn_thoat);
        edtChiTiet.setText(data.get(position).getChiTiet());
        edtloaiGD.setText(data.get(position).getLoaigiaodich());
        if (data.get(position).getLoaivi() == 1) {
            edtLoaivi.setText("Khoản Thu");
        } else {
            edtLoaivi.setText("Khoản Chi");
        }
        edtSotien.setText(Math.round(data.get(position).getSotien()) + " đ");
        edtTenGD.setText(data.get(position).getTenGD());
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DAO dao = new DAO(c);
                DTO gd = data.get(position);
                gd.setChiTiet(edtChiTiet.getText() + "");
                gd.setTenGD(edtTenGD.getText() + "");
                gd.setLoaigiaodich(edtloaiGD.getText() + "");

                    String temp = "";
                    String str = edtSotien.getText() + "";
                    for (int i = 0; i < str.length(); i++) {
                        if (str.charAt(i) == '.' || str.charAt(i) == ' ' || str.charAt(i) == 'đ') {
                            continue;
                        }
                        temp += str.charAt(i);

                    }
                    gd.setSotien(Double.valueOf(temp));

                if (edtLoaivi.getText().toString().equals("Khoản Thu")) {
                    gd.setLoaivi(1);
                } else {
                    gd.setLoaivi(0);
                }
                int result = dao.update(gd.getId(), gd);
                if (result == 1) {
                    gd.setChiTiet(edtChiTiet.getText() + "");
                    gd.setTenGD(edtTenGD.getText() + "");
                    gd.setLoaigiaodich(edtloaiGD.getText() + "");
                    String tempp = "";
                    String strr = edtSotien.getText() + "";
                    for (int i = 0; i < strr.length(); i++) {
                        if (strr.charAt(i) == '.' || strr.charAt(i) == ' ' || strr.charAt(i) == 'đ') {
                            continue;
                        }
                        tempp += strr.charAt(i);
                    }
                    gd.setSotien(Double.valueOf(tempp));
                } else {

                    Toast.makeText(c, "Data not updated", Toast.LENGTH_LONG);
                }
                editDialog.dismiss();
                notifyDataSetChanged();

            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
                notifyDataSetChanged();
            }
        });
        int width = (int) (c.getResources().getDisplayMetrics().widthPixels * 1.5);
        int height = (int) (c.getResources().getDisplayMetrics().heightPixels * 0.8);
        editDialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_border);
        editDialog.getWindow().setLayout(width, height);
        editDialog.setContentView(dialogView);
        editDialog.show();

    }

    public void filterList(ArrayList<DTO> filteredList) {
        data = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtdate, txtSoTien, txtTenGD;
        ImageView img;
//        ItemClickListener itemClickListener;//khai báo interface

        ViewHolder(View iview) {
            super(iview);
            txtdate = iview.findViewById(R.id.id_dmy);
            txtSoTien = iview.findViewById(R.id.id_sotien);
            txtTenGD = iview.findViewById(R.id.id_tengd);
            img = iview.findViewById(R.id.img);
        }
    }
}
