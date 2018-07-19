package com.even.oa;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.even.oa.employee.EmployeeFragment;
import com.even.oa.fragment.HomeFragment;

import static android.support.v4.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private String currentMenu;

    private FragmentManager fragmentManager;
    private SwitchCompat nightModeSwitch;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);

        initViews();
        initViewsEven();
    }


    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_layout_open, R.string.drawer_layout_close);
        actionBarDrawerToggle.syncState();

        //让菜单图标回复本来的颜色，且只能在代码中设置才有效
        navigationView.setItemIconTintList(null);
        // 设置导航菜单宽度
//        ViewGroup.LayoutParams params = navigationView.getLayoutParams();
//        params.width = getResources().getDisplayMetrics().widthPixels * 2 / 3;
//        navigationView.setLayoutParams(params);
        navigationView.getMenu().getItem(0).setChecked(true);

        //默认显示home
        replaceFragment(new HomeFragment());

        nightModeSwitch = findViewById(R.id.switch_night_mode);
    }


    private void initViewsEven() {
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        replaceFragment(new HomeFragment());
                        currentMenu = getResources().getString(R.string.nav_home);
                        break;
                    case R.id.nav_employee:
                        replaceFragment(new EmployeeFragment());
                        currentMenu = getResources().getString(R.string.nav_employee);
                        break;
                    case R.id.nav_leave:
                        currentMenu = getResources().getString(R.string.nav_ask_for_leave);
                        break;
                    case R.id.nav_notice:
                        currentMenu = getResources().getString(R.string.nav_notice);
                        break;
                    case R.id.nav_me:
                        currentMenu = getResources().getString(R.string.nav_me);
                        break;
                    default:
                        break;
                }
                toolbar.setTitle(currentMenu);
                itemChecked(item);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        //夜间模式切换
        nightModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!buttonView.isPressed()) {
                        return;
                    }
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                } else {
                    if (!buttonView.isPressed()) {
                        return;
                    }
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                }

            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_holder, fragment).commit();
    }

    private void itemChecked(MenuItem item) {
        navigationView.getMenu().getItem(0).setChecked(false);
        for (int i = 0; i < navigationView.getMenu().getItem(1).getSubMenu().size(); i++) {
            navigationView.getMenu().getItem(1).getSubMenu().getItem(i).setChecked(false);
        }
        setTitle(item.getTitle());
        item.setChecked(true);
    }

    /**
     * 两次确认退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        //先判断返回栈是否为空
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            //判断抽屉是否打开
            if (!drawerLayout.isDrawerVisible(navigationView)) {
                //判断两次返回时间间隔
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(),
                            "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                    //System.exit(0);     //只能结束自己和自己的上一个Activity，不缓存彻底退出
                }
            } else {
                drawerLayout.closeDrawers();
            }
        } else {
            lockDrawer(false);
            setupToolbar(true);
            toolbar.setTitle(currentMenu);
        }
    }

    //锁定侧滑抽屉
    public void lockDrawer(boolean enable) {
        drawerLayout.setDrawerLockMode(enable ?
                LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    //设置Toolbar的返回箭头
    public void setupToolbar(boolean enable) {
        if (actionBarDrawerToggle == null) {
            return;
        }
        if (enable) {
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        } else {
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            actionBarDrawerToggle.setToolbarNavigationClickListener(v -> {
                onBackPressed();
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                    lockDrawer(false);
                }
            });
        }
    }

}