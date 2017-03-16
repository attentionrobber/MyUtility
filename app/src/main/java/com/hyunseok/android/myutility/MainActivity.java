package com.hyunseok.android.myutility;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    // Tab 및 Pager 속성을 정의
    final int TAB_COUNT = 5;
    private int pagePosotion = 0; // ViewPager의 현재 페이지
    Stack<Integer> stack = new Stack<>(); // ViewPager의 페이지 이동경로를 저장하는 스택
    boolean backPressed = false;

    ViewPager viewPager;

    OneFragment one;
    TwoFragment two;
    ThreeFragment three;
    FourFragment four;
    FiveFragment five;

    private LocationManager locationManager; // 위치정보 관리자

    public LocationManager getLocationManager() {
        return locationManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // 메소드 추적 시작 ----
        //Debug.startMethodTracing("trace_result");

        // Fragment 초기화
        one = new OneFragment();
        two = new TwoFragment();
        three = new ThreeFragment();
        four = new FourFragment();
        five = FiveFragment.newInstance(3); // Grid의 ColumCount를 넘겨받는다.

        // Tab Layout 정의
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        // Tab 생성 및 타이틀 입력
        tabLayout.addTab(tabLayout.newTab().setText("Calculator"));
        tabLayout.addTab(tabLayout.newTab().setText("Unit Converter"));
        tabLayout.addTab(tabLayout.newTab().setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setText("Google Map"));
        tabLayout.addTab(tabLayout.newTab().setText("Gallery"));

        // Fragment 페이저 작성
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        // 아답터 생성
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // 1. 페이저가 변경되었을 때 탭을 바꿔주는 리스너(Pager Listener)
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        stack.push(0);
        // 2. 페이지의 변경사항을 체크한다.
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPagePush(pagePosotion); // Page의 스택을 Push한다.
                pagePosotion = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 2. 탭이 변경되었을 때 페이지를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


        // 버전체크해서 마시멜로우보다 낮으면 런타임권한 체크를 하지 않는다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            init();
        }

        // 메소드 추적 종료.
        //Debug.stopMethodTracing();
    } // onCreate

    private void init() {
        // Location Manager 객체를 얻어온다.
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // GPS 센서가 켜져있는지 확인
        // 꺼져있다면 GPS를 켜는 페이지로 이동.
        if( gpsCheck() == false ) {
            // 0. 팝업창 만들기
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            // 1. 팝업창 제목
            alertDialog.setTitle("GPS 켜기");
            // 2. 팝업창 메시지
            alertDialog.setMessage("GPS가 꺼져 있습니다.\n 설정창으로 이동하시겠습니까?");
            // 3. Yes버튼 만들기
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
            });
            // 4. Cancle 버튼 만들기
            alertDialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            // 5. show함수로 팝업창을 띄운다.
            alertDialog.show();
        }
    }

    // GPS 센서가 켜져있는지 체크 롤리팝 이하버전
    private boolean gpsCheck() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } else {
            String gps = android.provider.Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if(gps.matches(",*gps.*")) {
                return true;
            } else {
                return false;
            }
        }
    }

    // ViewPager의 Stack Push와 Pop
    private void viewPagePush(int position) {
        if(backPressed == false)
            stack.push(position);
    }
    private void viewPagePop() {
        if(stack.isEmpty() == false)
            viewPager.setCurrentItem(stack.pop());
    }


    private final int REQ_CODE = 100;

    // 1. 권한 체크
    @TargetApi(Build.VERSION_CODES.M) // Target 지정 Annotation
    private void checkPermission() {
        // 1.1 런타임 권한 체크
        if( checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { // Permission이 없을 경우. 쓰기만하면 읽기도 자동으로 가능.

            // 1.2 요청할 권한 목록 작성
            String perArr[] = {
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE };

            // 1.3 시스템에 권한 요청.
            requestPermissions(perArr, REQ_CODE);

        } else { // Permission이 있을 경우.
            init();
        }
    }

    // 2. 권한 체크 후 콜백(사용자가 확인 후 시스템이 호출하는 함수)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQ_CODE) {
            // 배열에 넘긴 런타임권한을 체크해서 승인이 된 경우 // 두개의 권한 [0] [1] 모두 그랜트 되었을 경우
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED ) {
                init();
            } else {
                Toast.makeText(this, "권한을 실행하지 않으면 프로그램이 실행되지 않습니다.", Toast.LENGTH_SHORT).show();

                // 선택 종료,
            }
        }
    }


    // Three Fragment(Web View)의 Back키 동작 함수
    @Override
    public void onBackPressed() {

        backPressed = true;

        switch (pagePosotion) {
            case 2 :
                if(three.goBack()) { // 뒤로가기가 가능하면 아무런 동작을 하지 않음.
                } else {
                    viewPagePop();
                }
                break;
            default :
                viewPagePop();
                if(stack.size() < 1) {
                    super.onBackPressed(); // Page의 스택크기가 1보다 작을 때 앱 종료
                }
                break;
        }

        backPressed = false;
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch (position) {
                case 0 : fragment = one;
                    break;
                case 1 : fragment = two;
                    break;
                case 2 : fragment = three;
                    break;
                case 3 :  fragment = four;
                    break;
                case 4 : fragment = five;
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }


    } // PagerAdapter
}
