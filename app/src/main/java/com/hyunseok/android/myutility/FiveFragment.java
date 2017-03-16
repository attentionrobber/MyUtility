package com.hyunseok.android.myutility;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 */
public class FiveFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;

    RecyclerView recyclerView;
    MyItemRecyclerViewAdapter adapter;

    private static View view = null;

    // 사진정보 데이터 저장소
    List<String> datas = new ArrayList<>();

    // 사진 촬영 후 임시로 저장할 공간
    Uri fileUri = null; // Image

    private final int REQ_PERMISSION = 100; // 권한 요청 코드
    private final int REQ_CAMERA = 101; // 카매라 요청 코드
    private final int REQ_GALLERY = 102; // 갤러리 요청 코드

    Button btn_camera;

    public FiveFragment() {
    }

    @SuppressWarnings("unused")
    public static FiveFragment newInstance(int columnCount) {
        FiveFragment fragment = new FiveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    } // onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view != null)
            return view;

        Context context = getContext();

        view = inflater.inflate(R.layout.fragment_item_list, container, false);
        // Widget 세팅
        btn_camera = (Button) view.findViewById(R.id.btn_camera);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // Listener 세팅
        btn_camera.setOnClickListener(clickListener);

        // datas에 이미지 데이터 불러오기
        datas = loadData();

        // Set the adapter
        //if (view instanceof RecyclerView) {
        //    RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            adapter = new MyItemRecyclerViewAdapter(context, datas);
            recyclerView.setAdapter(adapter);
        //}
        return view;
    } // onCreateView

    private List<String> loadData() {

        List<String> datas = new ArrayList<>();

        // 폰에서 이미지를 가져온 후 datas에 세팅한다.
        ContentResolver resolver = getContext().getContentResolver();
        // 1. 데이터 URI 정의
        Uri target = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 2. Projection 정의
        String projection[] = { MediaStore.Images.Media.DATA }; // DATA : image 경로가 있는 컬럼명
        // 정렬 추가
        String order = MediaStore.MediaColumns.DATE_ADDED + " DESC";
        // 3. 데이터 가져오기
        Cursor cursor = resolver.query(target, projection, null, null, order);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                //int idx = cursor.getColumnIndex(projection[0]);
                String uriStr = cursor.getString(0);
                datas.add(uriStr);
            }
            cursor.close();
        }
        return datas;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.btn_camera :
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 카메라 촬영 후 미디어 컨텐트 Uri를 생성해서 외부저장소에 저장한다.
                    // 마시멜로 이상 버전은 아래 코드를 반영해야함.
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        // 저장할 미디어 속성을 정의하는 클래스
                        ContentValues values = new ContentValues(1);
                        // 속성중에 파일의 종류를 정의
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                        // 전역변수로 정의한 fileUri에 외부저장소 컨텐츠가 있는 Uri 를 임시로 생성해서 넣어준다.
                        fileUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        // 위에서 생성한 fileUri를 사진저장공간으로 사용하겠다고 설정
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        // Uri에 읽기와 쓰기 권한을 시스템에 요청
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        // --- 여기까지 컨텐트 Uri강제 세팅 ---
                    }
                    startActivityForResult(intent, REQ_CAMERA); // forResult??
                    break;
            }
        }
    };

    //startActivityForResult() 후에 실행되는 메소드
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CAMERA:
                // 마시멜로버전 이상인 경우에만 getData()에 null이 넘어올것임.
                if (requestCode == REQ_CAMERA && resultCode == RESULT_OK) { // resultCode OK이면 완료되었다는 뜻.
                    datas = loadData();
                    adapter = new MyItemRecyclerViewAdapter(getContext(), datas);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
