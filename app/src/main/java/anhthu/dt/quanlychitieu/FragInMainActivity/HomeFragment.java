package anhthu.dt.quanlychitieu.FragInMainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import anhthu.dt.quanlychitieu.CreateGD;
import anhthu.dt.quanlychitieu.Intro;
import anhthu.dt.quanlychitieu.ListGD;
import anhthu.dt.quanlychitieu.R;
import anhthu.dt.quanlychitieu.ThongKeGD;

public class HomeFragment extends Fragment {

    Button createGD, listGD, intro, btnThongKe;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        createGD = view.findViewById(R.id.btnCreate);
        listGD = view.findViewById(R.id.btnList);
        intro = view.findViewById(R.id.btnIntro);
        btnThongKe = view.findViewById(R.id.btnThongKe);
        createGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateGD.class);
                startActivity(intent);
            }
        });
        listGD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListGD.class);
                startActivity(intent);

            }
        });
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Intro.class);
                startActivity(intent);
            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThongKeGD.class);
                startActivity(intent);

            }
        });
        return view;
    }

}