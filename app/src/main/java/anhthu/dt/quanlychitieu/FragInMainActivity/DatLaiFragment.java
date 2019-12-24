package anhthu.dt.quanlychitieu.FragInMainActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import anhthu.dt.quanlychitieu.R;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DAO;

public class DatLaiFragment extends Fragment {


    DAO dao;
    LinearLayout firstRow, secondRow, thirdRow;

    public DatLaiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.datlai_fragment, container, false);
        firstRow = view.findViewById(R.id.first_row);
//        secondRow = view.findViewById(R.id.second_row);
//        thirdRow = view.findViewById(R.id.third_row);

        dao = new DAO(getContext());
        firstRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDeleteAll();
            }
        });
        firstRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogDeleteAll();
                return false;
            }
        });
//        secondRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogDeleteVi();
//            }
//        });
//        secondRow.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                DialogDeleteVi();
//                return false;
//            }
//        });
//        thirdRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogDeleteChiTieu();
//            }
//        });
//        thirdRow.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                DialogDeleteChiTieu();
//                return false;
//            }
//        });
        return view;
    }

    public void DialogDeleteAll() {
        final Dialog dialog = new Dialog(getContext());
        View dialogView = View.inflate(getContext(), R.layout.custom_main_dialog, null);
        ImageView imgYes, imgNo;
        imgNo = dialogView.findViewById(R.id.img_no);
        imgYes = dialogView.findViewById(R.id.img_yes);
        dialog.setContentView(dialogView);
        dialog.show();
        imgNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        imgYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.ResetChiTieu();
                dao.ResetVi();
//                CreateGD createGD= new CreateGD();
                dialog.dismiss();
            }
        });
    }

    public void DialogDeleteChiTieu() {
        final Dialog dialog = new Dialog(getContext());
        View dialogView = View.inflate(getContext(), R.layout.custom_main_dialog, null);
        ImageView imgYes, imgNo;
        imgNo = dialogView.findViewById(R.id.img_no);
        imgYes = dialogView.findViewById(R.id.img_yes);
        dialog.setContentView(dialogView);
        dialog.show();
        imgNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        imgYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.ResetChiTieu();
                dialog.dismiss();
            }
        });
    }

    public void DialogDeleteVi() {
        final Dialog dialog = new Dialog(getContext());
        View dialogView = View.inflate(getContext(), R.layout.custom_main_dialog, null);
        ImageView imgYes, imgNo;
        imgNo = dialogView.findViewById(R.id.img_no);
        imgYes = dialogView.findViewById(R.id.img_yes);
        dialog.setContentView(dialogView);
        dialog.show();
        imgNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        imgYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.ResetVi();
                dialog.dismiss();
            }
        });
    }
}