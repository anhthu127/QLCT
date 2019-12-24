package anhthu.dt.quanlychitieu;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import anhthu.dt.quanlychitieu.SQLiteDatabase.DAO;
import anhthu.dt.quanlychitieu.SQLiteDatabase.DTO;
import anhthu.dt.quanlychitieu.TabsLayout.HienTai;
import anhthu.dt.quanlychitieu.TabsLayout.PagerAdaper;
import anhthu.dt.quanlychitieu.TabsLayout.ThangSau;
import anhthu.dt.quanlychitieu.TabsLayout.ThangTruoc;

public class ListGD extends AppCompatActivity {
    DAO qlctDao;
    ArrayList<DTO> data = new ArrayList<>();
    ViewPager mPager;
    TabLayout mTabLayout;
    SearchView searchView;
    MenuItem itemSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_gd);

        qlctDao = new DAO(ListGD.this);
        qlctDao.open();
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListGD.this, MainActivity.class));
            }
        });
        mPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mPager);
        viewPager();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        itemSearch = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) itemSearch.getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {

                PagerAdapter fragmentPagerAdapter =  mPager.getAdapter();
//                FragmentPagerAdapter fragmentPagerAdapter = (FragmentPagerAdapter) mPager.getAdapter();
                for (int i = 0; i < fragmentPagerAdapter.getCount(); i++) {
                    Fragment viewPagerFragment = (Fragment) mPager.getAdapter().instantiateItem(mPager, i);
                    if (viewPagerFragment != null) {
                        if (viewPagerFragment instanceof HienTai==true) {
                            HienTai hienTai = (HienTai) viewPagerFragment;
                            if (hienTai != null) {
                                hienTai.beginSearch(newText);
                            }
                        } else if (viewPagerFragment instanceof ThangTruoc==true) {
                            ThangTruoc thangTruoc = (ThangTruoc) viewPagerFragment;
                            if (thangTruoc != null) {
                                thangTruoc.beginSearch(newText);
                            }
                        } else if (viewPagerFragment instanceof ThangSau==true) {
                            ThangSau thangSau = (ThangSau) viewPagerFragment;
                            if (thangSau != null) {

                                thangSau.beginSearch(newText);
                            }
                        }
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("ListGD", "onQueryTextSubmit: " + query);
                    FragmentPagerAdapter fragmentPagerAdapter = (FragmentPagerAdapter) mPager.getAdapter();
                    for (int i = 0; i < fragmentPagerAdapter.getCount(); i++) {
                        Fragment viewPagerFragment = (Fragment) mPager.getAdapter().instantiateItem(mPager, i);
                        if (viewPagerFragment != null) {
                        if (viewPagerFragment instanceof HienTai==true) {
                            HienTai hienTai = (HienTai) viewPagerFragment;
                            if (hienTai != null) {
                                hienTai.beginSearch(query);
                            }
                        } else if (viewPagerFragment instanceof ThangTruoc==true) {
                            ThangTruoc thangTruoc = (ThangTruoc) viewPagerFragment;
                            if (thangTruoc != null) {
                                thangTruoc.beginSearch(query);
                            }
                        } else if (viewPagerFragment instanceof ThangSau==true) {
                            ThangSau thangSau = (ThangSau) viewPagerFragment;
                            if (thangSau != null) {

                                thangSau.beginSearch(query);
                            }
                        }
                    }
                }
                return true;
            }
        });

        return true;

    }

    private void viewPager() {
        FragmentManager manager = getSupportFragmentManager();
        PagerAdaper adapter = new PagerAdaper(manager);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.app_bar_insert:
                Intent intent1 = new Intent(ListGD.this, CreateGD.class);
                startActivity(intent1);
            case R.id.app_bar_search:
                Log.d("listGd", "onOptionsItemSelected: ");
                Toast.makeText(ListGD.this, "search selected", Toast.LENGTH_LONG);
                return true;
            case R.id.action_thongke:
                Intent intent = new Intent(ListGD.this, ThongKeGD.class);
                startActivity(intent);
                return true;
            case R.id.action_gioithieu:
                Intent intentt = new Intent(ListGD.this, Intro.class);
                startActivity(intentt);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
