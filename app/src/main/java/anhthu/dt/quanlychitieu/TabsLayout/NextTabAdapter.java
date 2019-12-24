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


public class NextTabAdapter extends RecyclerView.Adapter<NextTabAdapter.ViewHolder> implements Filterable {

    DTO dto = new DTO();
    ArrayList<DTO> dataThangSau;
    ArrayList<DTO> mStringFilterList;
    Dialog dialog;
    NextTabAdapter.ValueFilter valueFilter;
    Context context;

    public NextTabAdapter() {
    }

    public NextTabAdapter(ArrayList<DTO> data, Context context) {
        this.context = context;
        this.dataThangSau = new ArrayList<DTO>();
        this.dataThangSau = data;
        mStringFilterList = new ArrayList<>();
        mStringFilterList = data;
//        Log.d("NextTabAdapter", "" + mStringFilterList.size());
    }

    @NonNull
    @Override
    public NextTabAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_custom,
                parent, false);

        return new NextTabAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NextTabAdapter.ViewHolder holder, final int position) {
        holder = new NextTabAdapter.ViewHolder(holder.itemView);
        (holder).txtdate.setText(dataThangSau.get(position).getNgay() + "/" + dataThangSau.get(position).getThang() + "/" + dataThangSau.get(position).getNam());
        (holder).txtSoTien.setText(Math.round(dataThangSau.get(position).getSotien()) + " đ");
        (holder).txtTenGD.setText(dataThangSau.get(position).getTenGD());
        (holder).img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog(position);

            }
        });
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
        return dataThangSau.size();
    }

    public void dialogXoa(final int position) {
        dialog = new Dialog(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.popup_window, null);
        ImageView imgCo = dialogView.findViewById(R.id.img_co);
        ImageView imgKhong = dialogView.findViewById(R.id.img_khong);
        dialog.setContentView(dialogView);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 1.0);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.70);

        dialog.getWindow().setLayout(width, height);
        dialog.show();
        imgCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAO dao = new DAO(context);
                dao.GiuXoa(dataThangSau.get(position).getId());

                if (dataThangSau.get(position).getLoaivi() == 1) {
                    Vi vi = new Vi();
                    vi.setTongTien(dao.getSoDu().get(0).getTongTien() - dataThangSau.get(position).getSotien());
                    dao.ThemVi(vi);
                } else {
                    Vi vi = new Vi();
                    vi.setTongTien(dao.getSoDu().get(0).getTongTien() + dataThangSau.get(position).getSotien());
                    dao.ThemVi(vi);
                }
                dataThangSau.remove(position);
                notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT);
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
            valueFilter = new NextTabAdapter.ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
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
            dataThangSau.clear();
            dataThangSau = (ArrayList<DTO>) results.values;
            notifyDataSetChanged();
            for (int i = 0; i < dataThangSau.size(); i++) {
                Log.d("next ", "dataThangSau:" + dataThangSau.get(i).getTenGD());

            }
        }
    }

    public void editDialog(final int position) {
        final Dialog editDialog = new Dialog(context);
        LayoutInflater inflater = LayoutInflater.from(context);
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
        edtChiTiet.setText(dataThangSau.get(position).getChiTiet());
        edtloaiGD.setText(dataThangSau.get(position).getLoaigiaodich());
        if (dataThangSau.get(position).getLoaivi() == 1) {
            edtLoaivi.setText("Khoản Thu");
        } else {
            edtLoaivi.setText("Khoản Chi");
        }
        edtSotien.setText(Math.round(dataThangSau.get(position).getSotien()) + " đ");
        edtTenGD.setText(dataThangSau.get(position).getTenGD());
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DAO dao = new DAO(context);
                DTO gd = dataThangSau.get(position);
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

                    Toast.makeText(context, "Data not updated", Toast.LENGTH_LONG);
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
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.8);
        editDialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_border);
        editDialog.getWindow().setLayout(width, height);
        editDialog.setContentView(dialogView);
        editDialog.show();

    }

    public void filterList(ArrayList<DTO> filteredList) {
        dataThangSau = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtdate, txtSoTien, txtTenGD;
        ImageView img;

        ViewHolder(View iview) {
            super(iview);
            txtdate = iview.findViewById(R.id.id_dmy);
            txtSoTien = iview.findViewById(R.id.id_sotien);
            txtTenGD = iview.findViewById(R.id.id_tengd);
            img = iview.findViewById(R.id.img);
        }
    }
}