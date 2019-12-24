package anhthu.dt.quanlychitieu.TabsLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import anhthu.dt.quanlychitieu.R;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DAO;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DTO;

public class HienTai extends Fragment {
    int mPage;
    String mTittle;
    EditText editText;
    ArrayList<DTO> itemList = new ArrayList<DTO>();
    TabAdapter tabAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hien_tai, container, false);
        DAO dao = new DAO(getContext());
        editText = view.findViewById(R.id.edittext);
        itemList = dao.HienTai();
        tabAdapter = new TabAdapter(itemList, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rvGd);
        recyclerView.setAdapter(tabAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void filter(String str) {
        ArrayList<DTO> filteredList = new ArrayList<>();
        for (DTO dto : itemList) {
            String temp;
            Log.d("hientai", "filter: "+dto.getTenGD());
            if (dto.getLoaivi() == 1) {
                temp = "Khoản thu";
            } else {
                temp = "Khoản chi";
            }
            if (dto.getTenGD().toLowerCase().contains(str)
                    || (dto.getSotien().toString().toLowerCase().contains(str))
                    || (dto.getLoaigiaodich().toLowerCase()).contains(str)
                    || (dto.getThang().toLowerCase()).contains(str) ||
                    (dto.getNam().toLowerCase()).contains(str)
                    || (dto.getNgay().toLowerCase()).contains(str)||temp.toLowerCase().contains(str)) {
                filteredList.add(dto);
            }

        }

        tabAdapter.filterList(filteredList);
        tabAdapter.notifyDataSetChanged();
    }

    public static HienTai newInstance(int page, String tittle) {
        HienTai hienTai = new HienTai();
        Bundle bundle = new Bundle();
        bundle.putString("page", tittle);
        bundle.putInt("tittle", page);
        hienTai.setArguments(bundle);
        return hienTai;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("page", 1);
        mTittle = getArguments().getString("tittle");
    }

    public void beginSearch(String query) {
        tabAdapter = new TabAdapter(itemList, getContext());
        tabAdapter.getFilter().filter(query);
    }

    public HienTai() {
    }

}
