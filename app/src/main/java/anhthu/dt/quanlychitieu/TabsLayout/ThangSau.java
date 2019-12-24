package anhthu.dt.quanlychitieu.TabsLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import anhthu.dt.quanlychitieu.R;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DAO;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DTO;

public class ThangSau extends Fragment {
    int mPage;
    String mTittle;
    NextTabAdapter nextTabAdapter;
    EditText editText;
    ArrayList<DTO> itemsList = new ArrayList<DTO>();

    public ThangSau() {
    }

    public static ThangSau newInstance(int page, String tittle) {
        ThangSau thangSau = new ThangSau();
        Bundle bundle = new Bundle();
        bundle.putString("page", tittle);
        bundle.putInt("tittle", page);
        thangSau.setArguments(bundle);
        return thangSau;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("page", 1);
        mTittle = getArguments().getString("tittle");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.thang_sau, container, false);
        DAO dao = new DAO(getContext());
        editText= view.findViewById(R.id.edittext);
        itemsList = dao.ThangSau();
        nextTabAdapter = new NextTabAdapter(itemsList, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rvGd);
        recyclerView.setAdapter(nextTabAdapter);
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
        for (DTO dto : itemsList) {
            String temp;
            Log.d("hientai", "filter: "+dto.getTenGD());
            if (dto.getLoaivi() == 1) {
                temp = "Khoản thu";
            } else {
                temp = "Khoản chi";
            }
            if (dto.getTenGD().toLowerCase().contains(str)
                    || (dto.getSotien().toString().toLowerCase().contains(str)
                    || (dto.getLoaigiaodich().toLowerCase()).contains(str)
                    || (dto.getThang().toLowerCase()).contains(str) ||
                    (dto.getNam().toLowerCase()).contains(str)
                    || (dto.getNgay().toLowerCase()).contains(str)||temp.toLowerCase().contains(str))) {
                filteredList.add(dto);
            }
            nextTabAdapter.filterList(filteredList);
        }
        nextTabAdapter.filterList(filteredList);
        nextTabAdapter.notifyDataSetChanged();
    }
    public void beginSearch(String query) {
        nextTabAdapter = new NextTabAdapter(itemsList,getContext());
        nextTabAdapter.getFilter().filter(query);
    }
}
