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

public class ThangTruoc extends Fragment {
    int mPage;
    String mTittle;
    EditText editText;
    ArrayList<DTO> itemsList = new ArrayList<DTO>();
    ArrayList<DTO> resultSearch;
    LastAdapter lastAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thang_truoc, container, false);
        DAO dao = new DAO(getContext());
        itemsList = dao.ThangTruoc();
        LastAdapter lastAdapter = new LastAdapter(itemsList, getContext());
        lastAdapter.notifyDataSetChanged();
        editText = view.findViewById(R.id.edt);
        recyclerView = view.findViewById(R.id.rvGd);
        recyclerView.setAdapter(lastAdapter);
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

    public static ThangTruoc newInstance(int page, String tittle) {
        ThangTruoc thangTruoc = new ThangTruoc();
        Bundle bundle = new Bundle();
        bundle.putString("page", tittle);
        bundle.putInt("tittle", page);
        thangTruoc.setArguments(bundle);
        return thangTruoc;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("page", 1);
        mTittle = getArguments().getString("tittle");
    }

    private void filter(String str) {
        ArrayList<DTO> filteredList = new ArrayList<>();
        for (DTO dto : itemsList) {
            String temp;
            Log.d("hientai", "filter: " + dto.getTenGD());
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
                    || (dto.getNgay().toLowerCase()).contains(str) || temp.toLowerCase().contains(str)) {
                filteredList.add(dto);
            }
        }
        Log.d("", "filtered: " + filteredList.size());
//        lastAdapter.filterList(filteredList);
//        lastAdapter.notifyDataSetChanged();
    }

    public void beginSearch(String query) {
        lastAdapter = new LastAdapter(itemsList, getContext());
        lastAdapter.getFilter().filter(query);
        Log.d("thangtruoc", "beginSearch:thang truoc " + lastAdapter);
    }

    public ThangTruoc() {

    }
}
