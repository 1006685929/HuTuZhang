package com.example.hxk.hutuzhang01;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Bean.CostBean;
import de.hdodenhof.circleimageview.CircleImageView;
import fragment.BillFragment;
import fragment.CostFragment;
import fragment.RecordFragment;
import fragment.ReportFragment;
import utils.DateUtils;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    private LinearLayout mTabCost;
    private LinearLayout mTabRecord;
    private LinearLayout mTabBill;
    private LinearLayout mTabReport;

    private ImageButton mImgCost;
    private ImageButton mImgRecord;
    private ImageButton mImgBill;
    private ImageButton mImgReport;

    private Fragment mFragmentCost;
    private Fragment mFragmentRecord;
    private Fragment mFragmentBill;
    private Fragment mFragmentReport;

    private DrawerLayout mDrawerLayout;

    private List<CostBean> mCostBeanList;

    private TextView Tv_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDrawerLayout = findViewById(R.id.drawer_Layout);
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //侧滑栏事件监听
        navView.setCheckedItem(R.id.nav_account);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_account) {
                    Toast.makeText(MainActivity.this, "点击了账户", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_friends) {
                    Toast.makeText(MainActivity.this, "点击了好友", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
                } else if (id == R.id.nav_location) {
                    Toast.makeText(MainActivity.this, "点击了地址", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_synchron) {
                    Toast.makeText(MainActivity.this, "点击了同步", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_setting) {
                    Toast.makeText(MainActivity.this, "点击了设置", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_subject) {
                    showUpdateThemeDialog();
                } else if (id == R.id.nav_about) {
                    Toast.makeText(MainActivity.this, "点击了关于", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_exit) {
                    exit();
                    Toast.makeText(MainActivity.this, "点击了退出", Toast.LENGTH_SHORT).show();
                }
                //mDrawerLayout.closeDrawers();
                return true;
            }
        });

        //登陆界面跳转
        View headerView = navView.getHeaderView(0);
        CircleImageView imageView = headerView.findViewById(R.id.icon_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        View Tv = navView.getHeaderView(0);
        final TextView Tv_user = Tv.findViewById(R.id.Tv_user);
        Tv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tv_user.setText("admin");
            }
        });


        initView();
        initEvent();
        setSelect(0);
    }

    private void exit() {
        new AlertDialog.Builder(this).setTitle("是否退出账户")
                .setNegativeButton("取消",null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    mDrawerLayout.closeDrawers();
                    return;
                    }
                }).show();
    }


    private void showUpdateThemeDialog() {
        Toast.makeText(MainActivity.this, "点击了主题", Toast.LENGTH_SHORT).show();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    //初始化事件
    private void initEvent() {
        mImgCost.setOnClickListener(this);
        mImgRecord.setOnClickListener(this);
        mImgBill.setOnClickListener(this);
        mImgReport.setOnClickListener(this);
    }

    //初始化视图
    private void initView() {

        mViewPager = findViewById(R.id.id_viewPager);

        mTabCost = findViewById(R.id.tab_cost);
        mTabRecord = findViewById(R.id.tab_record);
        mTabBill = findViewById(R.id.tab_bill);
        mTabReport = findViewById(R.id.tab_report);

        mImgCost = findViewById(R.id.tabimg_cost);
        mImgRecord = findViewById(R.id.tabimg_record);
        mImgBill = findViewById(R.id.tabimg_bill);
        mImgReport = findViewById(R.id.tabimg_report);


        mFragments = new ArrayList<Fragment>();
        Fragment mTab01 = new CostFragment();
        Fragment mTab02 = new RecordFragment();
        Fragment mTab03 = new BillFragment();
        Fragment mTab04 = new ReportFragment();
        mFragments.add(mTab01);
        mFragments.add(mTab02);
        mFragments.add(mTab03);
        mFragments.add(mTab04);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(mAdapter);
    }

    //点击后
    private void setSelect(int i) {

        switch (i) {
            case 0:
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            default:
                break;
        }
        mViewPager.setCurrentItem(i);
    }

    //点击切换界面
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tabimg_cost:
                setSelect(0);
                break;
            case R.id.tabimg_record:
                setSelect(1);
                break;
            case R.id.tabimg_bill:
                setSelect(2);
                break;
            case R.id.tabimg_report:
                setSelect(3);
                //startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            // ContentResolver实例带的方法可实现找到指定的ContentProvider并获取到ContentProvider的数据
            ContentResolver reContentResolverol = getContentResolver();
            // URI,每个ContentProvider定义一个唯一的公开的URI,用于指定到它的数据集
            Uri contactData = data.getData();
            // 查询就是输入URI等参数,其中URI是必须的,其他是可选的,如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            // 条件为联系人ID
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            // 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
            Cursor phone = reContentResolverol.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                            + contactId, null, null);
            while (phone.moveToNext()) {
                String num = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Intent call_intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
                call_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(call_intent);
            }

        }
    }
}
