package anhthu.dt.quanlychitieu.TabsLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdaper extends FragmentPagerAdapter {
    public PagerAdaper( FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
      switch (position){
          case 0:
              new ThangTruoc();
              return ThangTruoc.newInstance(0, "Tháng trước");
          case 1:
              new HienTai();
              return HienTai.newInstance(1,"Hiện tại");
          case 2:
              new ThangSau();
              return ThangSau.newInstance(2,"Tháng sau");
          default :
              new HienTai();
              return HienTai.newInstance(1,"Hiện tại");
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
