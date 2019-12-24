package anhthu.dt.quanlychitieu.ThongKeTabsLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class TK_PagerAdapter  extends FragmentPagerAdapter {
    public TK_PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                new ThongKeThangTruoc();
                return ThongKeThangTruoc.newInstance(0, "Tháng trước");
            case 1:
                new ThongKeThangHienTai();
                return ThongKeThangHienTai.newInstance(1,"Hiện tại");
            case 2:
                new ThongKeThangSau();
                return ThongKeThangSau.newInstance(2,"Tháng sau");
            default :
                new ThongKeThangTruoc();
                return ThongKeThangTruoc.newInstance(1,"Hiện tại");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Tháng trước";
            case 1:
                return "Hiện tại";
            case 2:
                return "Tháng sau";
            default:
                return "Hiện tại";
        }
    }
}
