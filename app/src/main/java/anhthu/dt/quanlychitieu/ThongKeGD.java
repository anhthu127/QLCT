package anhthu.dt.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.tabs.TabLayout;

import anhthu.dt.quanlychitieu.ThongKeTabsLayout.TK_PagerAdapter;

public class ThongKeGD extends AppCompatActivity {
    PieChart pieChart;
    ViewPager mPager;
    TabLayout mTabLayout;
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_gd);
        mainLayout = findViewById(R.id.mainLayout);
        pieChart = new PieChart(ThongKeGD.this);
        mainLayout.addView(pieChart);
        Toolbar toolbar = findViewById(R.id.tk_toolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThongKeGD.this, MainActivity.class));
            }
        });
        mPager =  findViewById(R.id.viewpagers);
        mTabLayout =  findViewById(R.id.tab);
        viewPager();
        mTabLayout.setupWithViewPager(mPager);
    }

    private void viewPager() {
        FragmentManager manager = getSupportFragmentManager();
        TK_PagerAdapter adapter = new TK_PagerAdapter(manager);
        mPager.setAdapter(adapter);
        mPager.setCurrentItem(1);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
