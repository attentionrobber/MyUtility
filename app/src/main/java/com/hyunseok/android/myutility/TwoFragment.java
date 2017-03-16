package com.hyunseok.android.myutility;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {

    Button btn_length, btn_area, btn_weight;
    EditText editText_length, editText_area, editText_weight;
    TextView tv_length_output, tv_area_output, tv_weight_output;
    RelativeLayout layout_length, layout_area, layout_weight;
    Spinner spinner_length_from, spinner_length_to, spinner_area_from, spinner_area_to, spinner_weight_from, spinner_weight_to;

    String spinner_length[] = {"밀리미터(mm)", "센티미터(cm)", "미터(m)", "킬로미터(km)", "인치(inch)", "피트(ft)", "야드(yd)", "마일(mile)"};
    String spinner_area[] = {"제곱미터(m^2)", "제곱키로미터(km^2)", "제곱피트(ft^2)", "제곱야드(yd^2)", "아르(a)", "헥타르(ha)", "에이커(ac)", "평"};
    String spinner_weight[] = {"밀리그램(mg)", "그램(g)", "킬로그램(kg)", "톤(t)", "킬로톤(kt)", "파운드(lb)", "그레인(gr)", "온스(oz)"};

    int sign_length_from = 0;
    int sign_length_to = 0;
    int sign_area_from = 0;
    int sign_area_to = 0;
    int sign_weight_from = 0;
    int sign_weight_to = 0;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);


        btn_length = (Button) view.findViewById(R.id.btn_length);
        btn_area = (Button) view.findViewById(R.id.btn_area);
        btn_weight = (Button) view.findViewById(R.id.btn_weight);

        btn_length.setOnClickListener(clickListener);
        btn_area.setOnClickListener(clickListener);
        btn_weight.setOnClickListener(clickListener);

        editText_length = (EditText) view.findViewById(R.id.editText_length);
        editText_area = (EditText) view.findViewById(R.id.editText_area);
        editText_weight = (EditText) view.findViewById(R.id.editText_weight);
        tv_length_output = (TextView) view.findViewById(R.id.tv_length_output);
        tv_area_output = (TextView) view.findViewById(R.id.tv_area_output);
        tv_weight_output = (TextView) view.findViewById(R.id.tv_weight_output);

        spinner_length_from = (Spinner) view.findViewById(R.id.spinner_length_from);
        spinner_length_to = (Spinner) view.findViewById(R.id.spinner_length_to);
        spinner_area_from =(Spinner) view.findViewById(R.id.spinner_area_from);
        spinner_area_to =(Spinner) view.findViewById(R.id.spinner_area_to);
        spinner_weight_from =(Spinner) view.findViewById(R.id.spinner_weight_from);
        spinner_weight_to =(Spinner) view.findViewById(R.id.spinner_weight_to);

        layout_length = (RelativeLayout) view.findViewById(R.id.layout_length);
        layout_area = (RelativeLayout) view.findViewById(R.id.layout_area);
        layout_weight = (RelativeLayout) view.findViewById(R.id.layout_weight);


        // editText에 문자가 입력될때마다의 리스너를 생성한다.
        editText_length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!(s.toString().equals(""))) {
                    //Toast.makeText(UnitConverterActivity.this, length_sign_from+"", Toast.LENGTH_SHORT).show();
                    convertLength(sign_length_from, sign_length_to);
                } else if(s.toString().equals("")) {
                    tv_length_output.setText("0");
                }
            }
        });
        editText_area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!(s.toString().equals(""))) {
                    convertArea(sign_area_from, sign_area_to);
                } else if(s.toString().equals("")) {
                    tv_area_output.setText("0");
                }
            }
        });
        editText_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!(s.toString().equals(""))) {
                    convertWeight(sign_weight_from, sign_weight_to);
                } else if(s.toString().equals("")) {
                    tv_weight_output.setText("0");
                }
            }
        });


        // 스피너 데이터(length, area, weight)를 어댑터로 생성
        ArrayAdapter<String> adapter_length = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_dropdown_item, spinner_length); // R. 은 내가 정의한 리소스 android.R. 은 android에 정의되어있는 리소스
        ArrayAdapter<String> adapter_area = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_dropdown_item, spinner_area);
        ArrayAdapter<String> adapter_weight = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_dropdown_item, spinner_weight);

        // 스피너 어댑터에 등록
        spinner_length_from.setAdapter(adapter_length);
        spinner_length_to.setAdapter(adapter_length);
        spinner_area_from.setAdapter(adapter_area);
        spinner_area_to.setAdapter(adapter_area);
        spinner_weight_from.setAdapter(adapter_weight);
        spinner_weight_to.setAdapter(adapter_weight);

        // 스피너 리스너에 등록
        spinner_length_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        sign_length_from = 1;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 1:
                        sign_length_from = 2;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 2:
                        sign_length_from = 3;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 3:
                        sign_length_from = 4;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 4:
                        sign_length_from = 5;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 5:
                        sign_length_from = 6;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 6:
                        sign_length_from = 7;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 7:
                        sign_length_from = 8;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_length_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        sign_length_to = 1;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 1:
                        sign_length_to = 2;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 2:
                        sign_length_to = 3;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 3:
                        sign_length_to = 4;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 4:
                        sign_length_to = 5;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 5:
                        sign_length_to = 6;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 6:
                        sign_length_to = 7;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                    case 7:
                        sign_length_to = 8;
                        convertLength(sign_length_from, sign_length_to);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_area_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        sign_area_from = 1;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 1:
                        sign_area_from = 2;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 2:
                        sign_area_from = 3;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 3:
                        sign_area_from = 4;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 4:
                        sign_area_from = 5;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 5:
                        sign_area_from = 6;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 6:
                        sign_area_from = 7;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 7 :
                        sign_area_from = 8;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_area_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        sign_area_to = 1;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 1:
                        sign_area_to = 2;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 2:
                        sign_area_to = 3;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 3:
                        sign_area_to = 4;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 4:
                        sign_area_to = 5;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 5:
                        sign_area_to = 6;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 6:
                        sign_area_to = 7;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                    case 7:
                        sign_area_to = 8;
                        convertArea(sign_area_from, sign_area_to);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_weight_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        sign_weight_from = 1;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 1:
                        sign_weight_from = 2;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 2:
                        sign_weight_from = 3;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 3:
                        sign_weight_from = 4;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 4:
                        sign_weight_from = 5;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 5:
                        sign_weight_from = 6;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 6:
                        sign_weight_from = 7;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 7:
                        sign_weight_from = 8;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_weight_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position) {
                    case 0:
                        sign_weight_to = 1;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 1:
                        sign_weight_to = 2;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 2:
                        sign_weight_to = 3;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 3:
                        sign_weight_to = 4;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 4:
                        sign_weight_to = 5;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 5:
                        sign_weight_to = 6;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 6:
                        sign_weight_to = 7;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                    case 7:
                        sign_weight_to = 8;
                        convertWeight(sign_weight_from, sign_weight_to);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    } // onCreateView

    // 버튼 클릭리스너 생성
    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btn_length :
                    layout_length.setVisibility(View.VISIBLE);
                    layout_area.setVisibility(View.GONE);
                    layout_weight.setVisibility(View.GONE);
                    break;
                case R.id.btn_area :
                    layout_length.setVisibility(View.GONE);
                    layout_area.setVisibility(View.VISIBLE);
                    layout_weight.setVisibility(View.GONE);
                    break;
                case R.id.btn_weight :
                    layout_length.setVisibility(View.GONE);
                    layout_area.setVisibility(View.GONE);
                    layout_weight.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };


    /**
     * 길이를 변환하는 함수
     * @param slF
     * @param slT
     */
    public void convertLength(int slF, int slT) {

        // length sign is
        // 1 is mm,  2 is cm,  3 is m,  4 is km,  5 is inch,  6 is ft,  7 is yd,  8 is mile
        // 기준 단위는 미터(m)

        double input = 0;
        double output = 0;
        if( !(editText_length.getText().toString().equals("")) ) {
            input = Double.parseDouble(editText_length.getText().toString());
        }

        if( slF == slT ) {
            output = input;
        }
        // mm -> cm and REV
        if( ((slF == 1) && (slT == 2)) || ((slF == 2) && (slT == 1)) ) {
            if(slF == 1) {
                output = input * 0.1;
            }
            else if(slF == 2) {
                output = input * 10;
            }
        }
        // mm -> m and REV
        if( ((slF == 1) && (slT == 3)) || ((slF == 3) && (slT == 1)) ) {
            if(slF == 1) {
                output = input * 0.001;
            }
            else if(slF == 3) {
                output = input * 1000;
            }
        }
        // mm -> km and REV
        if( ((slF == 1) && (slT == 4)) || ((slF == 4) && (slT == 1)) ) {
            if(slF == 1) {
                output = input * 0.000001;
            }
            else if(slF == 4) {
                output = input * 1000000;
            }
        }
        // mm -> inch and REV
        if( ((slF == 1) && (slT == 5)) || ((slF == 5) && (slT == 1)) ) {
            if(slF == 1) {
                output = input * 0.03937;
            }
            else if(slF == 5) {
                output = input * 25.4;
            }
        }
        // mm -> ft and REV
        if( ((slF == 1) && (slT == 6)) || ((slF == 6) && (slT == 1)) ) {
            if(slF == 1) {
                output = input * 0.003281;
            }
            else if(slF == 6) {
                output = input * 304.8;
            }
        }
        // mm -> yd and REV
        if( ((slF == 1) && (slT == 7)) || ((slF == 7) && (slT == 1)) ) {
            if(slF == 1) {
                output = input * 0.001094;
            }
            else if(slF == 7) {
                output = input * 914.4;
            }
        }
        // mm -> yd and REV
        if( ((slF == 1) && (slT == 8)) || ((slF == 8) && (slT == 1)) ) {
            if(slF == 1) {
                output = input * 0.000000621;
            }
            else if(slF == 8) {
                output = input * 1609344;
            }
        }
        // cm -> m and REV
        if( ((slF == 2) && (slT == 3)) || ((slF == 3) && (slT == 2)) ) {
            if(slF == 2) {
                output = input * 0.01;
            }
            else if(slF == 3) {
                output = input * 100;
            }
        }
        // cm -> km and REV
        if( ((slF == 2) && (slT == 4)) || ((slF == 4) && (slT == 2)) ) {
            if(slF == 2) {
                output = input * 0.00001;
            }
            else if(slF == 4) {
                output = input * 100000;
            }
        }
        // cm -> inch and REV
        if( ((slF == 2) && (slT == 5)) || ((slF == 5) && (slT == 2)) ) {
            if(slF == 2) {
                output = input * 0.393701;
            }
            else if(slF == 5) {
                output = input * 2.54;
            }
        }
        // cm -> ft and REV
        if( ((slF == 2) && (slT == 6)) || ((slF == 6) && (slT == 2)) ) {
            if(slF == 2) {
                output = input * 0.032808;
            }
            else if(slF == 6) {
                output = input * 30.48;
            }
        }
        // cm -> yd and REV
        if( ((slF == 2) && (slT == 7)) || ((slF == 7) && (slT == 2)) ) {
            if(slF == 2) {
                output = input * 0.010936;
            }
            else if(slF == 7) {
                output = input * 91.44;
            }
        }
        // cm -> mile and REV
        if( ((slF == 2) && (slT == 8)) || ((slF == 8) && (slT == 2)) ) {
            if(slF == 2) {
                output = input * 0.00000621;
            }
            else if(slF == 8) {
                output = input * 160934.4;
            }
        }
        // m -> km and REV
        if( ((slF == 3) && (slT == 4)) || ((slF == 4) && (slT == 3)) ) {
            if (slF == 3) {
                output = input * 0.001;
            } else if (slF == 4) {
                output = input * 1000;
            }
        }
        // m -> inch and REV
        if( ((slF == 3) && (slT == 5)) || ((slF == 5) && (slT == 3)) ) {
            if (slF == 3) {
                output = input * 39.370079;
            } else if (slF == 5) {
                output = input * 0.0254;
            }
        }
        // m -> ft and REV
        if( ((slF == 3) && (slT == 6)) || ((slF == 6) && (slT == 3)) ) {
            if (slF == 3) {
                output = input * 3.28084;
            } else if (slF == 6) {
                output = input * 0.3048;
            }
        }
        // m -> yard and REV
        if( ((slF == 3) && (slT == 7)) || ((slF == 7) && (slT == 3)) ) {
            if (slF == 3) {
                output = input * 1.093613;
            } else if (slF == 7) {
                output = input * 0.9144;
            }
        }
        // m -> mile and REV
        if( ((slF == 3) && (slT == 8)) || ((slF == 8) && (slT == 3)) ) {
            if (slF == 3) {
                output = input * 0.00062137;
            } else if (slF == 8) {
                output = input * 1609.344;
            }
        }
        // km -> inch and REV
        if( ((slF == 4) && (slT == 5)) || ((slF == 5) && (slT == 4)) ) {
            if (slF == 4) {
                output = input * 39370.0787;
            } else if (slF == 5) {
                output = input * 0.000025;
            }
        }
        // km -> ft and REV
        if( ((slF == 4) && (slT == 6)) || ((slF == 6) && (slT == 4)) ) {
            if (slF == 4) {
                output = input * 3280.8399;
            } else if (slF == 6) {
                output = input * 0.000305;
            }
        }
        // km -> yd and REV
        if( ((slF == 4) && (slT == 7)) || ((slF == 7) && (slT == 4)) ) {
            if (slF == 4) {
                output = input * 1093.6133;
            } else if (slF == 7) {
                output = input * 0.000914;
            }
        }
        // km -> mile and REV
        if( ((slF == 4) && (slT == 8)) || ((slF == 8) && (slT == 4)) ) {
            if (slF == 4) {
                output = input * 0.621371;
            } else if (slF == 8) {
                output = input * 1.609344;
            }
        }
        // inch -> ft and REV
        if( ((slF == 5) && (slT == 6)) || ((slF == 6) && (slT == 5)) ) {
            if (slF == 5) {
                output = input * 0.083333;
            } else if (slF == 6) {
                output = input * 12;
            }
        }
        // inch -> yd and REV
        if( ((slF == 5) && (slT == 7)) || ((slF == 7) && (slT == 5)) ) {
            if (slF == 5) {
                output = input * 0.027778;
            } else if (slF == 7) {
                output = input * 36;
            }
        }
        // inch -> mile and REV
        if( ((slF == 5) && (slT == 8)) || ((slF == 8) && (slT == 5)) ) {
            if (slF == 5) {
                output = input * 0.000016;
            } else if (slF == 8) {
                output = input * 63360;
            }
        }
        // ft -> yd and REV
        if( ((slF == 6) && (slT == 7)) || ((slF == 7) && (slT == 6)) ) {
            if (slF == 6) {
                output = input * 0.333333;
            } else if (slF == 7) {
                output = input * 3;
            }
        }
        // ft -> mile and REV
        if( ((slF == 6) && (slT == 8)) || ((slF == 8) && (slT == 6)) ) {
            if (slF == 6) {
                output = input * 0.000189;
            } else if (slF == 8) {
                output = input * 5280;
            }
        }
        // yd -> mile and REV
        if( ((slF == 7) && (slT == 8)) || ((slF == 8) && (slT == 7)) ) {
            if (slF == 7) {
                output = input * 0.000568;
            } else if (slF == 8) {
                output = input * 1760;
            }
        }

        tv_length_output.setText(output+"");
    }

    /**
     * 넓이를 변환하는 함수
     * @param saF
     * @param saT
     */
    public void convertArea(int saF, int saT) {

        // length sign is
        // 1 is m^2,  2 is km^2,  3 is ft^2,  4 is yd^2,  5 is a,  6 is ha,  7 is ac, 8 is 평
        // 기준 단위는 제곱미터(m^2)

        double input = 0;
        double output = 0;
        if( !(editText_area.getText().toString().equals("")) ) {
            input = Double.parseDouble(editText_area.getText().toString());
        }

        if( saF == saT ) {
            output = input;
        }
        // m^2 -> km^2 and REV
        if( ((saF == 1) && (saT == 2)) || ((saF == 2) && (saT == 1)) ) {
            if(saF == 1) {
                output = input * 0.000001;
            }
            else if(saF == 2) {
                output = input * 1000000;
            }
        }
        // m^2 -> ft^2 and REV
        if( ((saF == 1) && (saT == 3)) || ((saF == 3) && (saT == 1)) ) {
            if (saF == 1) {
                output = input * 10.76391;
            } else if (saF == 3) {
                output = input * 0.092903;
            }
        }
        // m^2 -> yd^2 and REV
        if( ((saF == 1) && (saT == 4)) || ((saF == 4) && (saT == 1)) ) {
            if (saF == 1) {
                output = input * 1.19599;
            } else if (saF == 4) {
                output = input * 0.836127;
            }
        }
        // m^2 -> a and REV
        if( ((saF == 1) && (saT == 5)) || ((saF == 5) && (saT == 1)) ) {
            if (saF == 1) {
                output = input * 0.01;
            } else if (saF == 5) {
                output = input * 100;
            }
        }
        // m^2 -> ha and REV
        if( ((saF == 1) && (saT == 6)) || ((saF == 6) && (saT == 1)) ) {
            if (saF == 1) {
                output = input * 0.0001;
            } else if (saF == 6) {
                output = input * 10000;
            }
        }
        // m^2 -> ac and REV
        if( ((saF == 1) && (saT == 7)) || ((saF == 7) && (saT == 1)) ) {
            if (saF == 1) {
                output = input * 0.000247;
            } else if (saF == 7) {
                output = input * 4046.85642;
            }
        }
        // km^2 -> ft^2 and REV
        if( ((saF == 2) && (saT == 3)) || ((saF == 3) && (saT == 2)) ) {
            if (saF == 2) {
                output = input * 10763910.4;
            } else if (saF == 3) {
                output = input * 0.000000093;
            }
        }
        // km^2 -> yd^2 and REV
        if( ((saF == 2) && (saT == 4)) || ((saF == 4) && (saT == 2)) ) {
            if (saF == 2) {
                output = input * 1195990.05;
            } else if (saF == 4) {
                output = input * 0.000000836;
            }
        }
        // km^2 -> a and REV
        if( ((saF == 2) && (saT == 5)) || ((saF == 5) && (saT == 2)) ) {
            if (saF == 2) {
                output = input * 10000;
            } else if (saF == 5) {
                output = input * 0.0001;
            }
        }
        // km^2 -> ha and REV
        if( ((saF == 2) && (saT == 6)) || ((saF == 6) && (saT == 2)) ) {
            if (saF == 2) {
                output = input * 100;
            } else if (saF == 6) {
                output = input * 0.01;
            }
        }
        // km^2 -> a and REV
        if( ((saF == 2) && (saT == 7)) || ((saF == 7) && (saT == 2)) ) {
            if (saF == 2) {
                output = input * 10000;
            } else if (saF == 7) {
                output = input * 0.0001;
            }
        }
        // ft^2 -> yd^2 and REV
        if( ((saF == 3) && (saT == 4)) || ((saF == 4) && (saT == 3)) ) {
            if (saF == 3) {
                output = input * 0.111111;
            } else if (saF == 4) {
                output = input * 9;
            }
        }
        // ft^2 -> a and REV
        if( ((saF == 3) && (saT == 5)) || ((saF == 5) && (saT == 3)) ) {
            if (saF == 3) {
                output = input * 0.000929;
            } else if (saF == 5) {
                output = input * 1076.39104;
            }
        }
        // ft^2 -> ha and REV
        if( ((saF == 3) && (saT == 6)) || ((saF == 6) && (saT == 3)) ) {
            if (saF == 3) {
                output = input * 0.00000929;
            } else if (saF == 6) {
                output = input * 107639.104;
            }
        }
        // ft^2 -> ac and REV
        if( ((saF == 3) && (saT == 7)) || ((saF == 7) && (saT == 3)) ) {
            if (saF == 3) {
                output = input * 0.000023;
            } else if (saF == 7) {
                output = input * 43560;
            }
        }
        // yd^2 -> a and REV
        if( ((saF == 4) && (saT == 5)) || ((saF == 5) && (saT == 4)) ) {
            if (saF == 4) {
                output = input * 0.008361;
            } else if (saF == 5) {
                output = input * 119.599005;
            }
        }
        // yd^2 -> ha and REV
        if( ((saF == 4) && (saT == 6)) || ((saF == 6) && (saT == 4)) ) {
            if (saF == 4) {
                output = input * 0.000084;
            } else if (saF == 6) {
                output = input * 11959.9005;
            }
        }
        // yd^2 -> ac and REV
        if( ((saF == 4) && (saT == 7)) || ((saF == 7) && (saT == 4)) ) {
            if (saF == 4) {
                output = input * 0.000207;
            } else if (saF == 7) {
                output = input * 4840;
            }
        }
        // a -> ha and REV
        if( ((saF == 5) && (saT == 6)) || ((saF == 6) && (saT == 5)) ) {
            if (saF == 5) {
                output = input * 0.01;
            } else if (saF == 6) {
                output = input * 100;
            }
        }
        // a -> ac and REV
        if( ((saF == 5) && (saT == 7)) || ((saF == 7) && (saT == 5)) ) {
            if (saF == 5) {
                output = input * 0.024711;
            } else if (saF == 7) {
                output = input * 40.468564;
            }
        }
        // ha -> ac and REV
        if( ((saF == 6) && (saT == 7)) || ((saF == 7) && (saT == 6)) ) {
            if (saF == 6) {
                output = input * 2.471054;
            } else if (saF == 7) {
                output = input * 0.404686;
            }
        }
        // 평 -> m^2 and REV
        if( ((saF == 8) && (saT == 1)) || ((saF == 1) && (saT == 8)) ) {
            if (saF == 8) {
                output = input * 3.305785;
            } else if (saF == 1) {
                output = input * 0.3025;
            }
        }
        // 평 -> km^2 and REV
        if( ((saF == 8) && (saT == 2)) || ((saF == 2) && (saT == 8)) ) {
            if (saF == 8) {
                output = input * 0.0000033;
            } else if (saF == 2) {
                output = input * 302500;
            }
        }
        // 평 -> ft^2 and REV
        if( ((saF == 8) && (saT == 3)) || ((saF == 3) && (saT == 8)) ) {
            if (saF == 8) {
                output = input * 3.305785;
            } else if (saF == 3) {
                output = input * 0.3025;
            }
        }
        // 평 -> yd^2 and REV
        if( ((saF == 8) && (saT == 4)) || ((saF == 4) && (saT == 8)) ) {
            if (saF == 8) {
                output = input * 35.583175;
            } else if (saF == 4) {
                output = input * 0.028103;
            }
        }
        // 평 -> a and REV
        if( ((saF == 8) && (saT == 5)) || ((saF == 5) && (saT == 8)) ) {
            if (saF == 8) {
                output = input * 0.033058;
            } else if (saF == 5) {
                output = input * 30.25;
            }
        }
        // 평 -> ha and REV
        if( ((saF == 8) && (saT == 6)) || ((saF == 6) && (saT == 8)) ) {
            if (saF == 8) {
                output = input * 0.000331;
            } else if (saF == 6) {
                output = input * 3025;
            }
        }
        // 평 -> ac and REV
        if( ((saF == 8) && (saT == 7)) || ((saF == 7) && (saT == 8)) ) {
            if (saF == 8) {
                output = input * 0.000817;
            } else if (saF == 7) {
                output = input * 1224.17407;
            }
        }

        tv_area_output.setText(output+"");
    }

    /**
     * 무게를 변환하는 함수
     * @param swF
     * @param swT
     */
    public void convertWeight(int swF, int swT) {

        // weight sign is
        // 1 is mg,  2 is g,  3 is kg,  4 is t,  5 is kt,  6 is lb,  7 is gr,  8 is oz
        // 기준 단위는 밀리그램(mg)

        double input = 0;
        double output = 0;
        if( !(editText_weight.getText().toString().equals("")) ) {
            input = Double.parseDouble(editText_weight.getText().toString());
        }

        if( swF == swT ) {
            output = input;
        }
        // mg -> g and REV
        if( ((swF == 1) && (swT == 2)) || ((swF == 2) && (swT == 1)) ) {
            if(swF == 1) {
                output = input * 0.001;
            }
            else if(swF == 2) {
                output = input * 1000;
            }
        }
        // mg -> kg and REV
        if( ((swF == 1) && (swT == 3)) || ((swF == 3) && (swT == 1)) ) {
            if (swF == 1) {
                output = input * 0.000001;
            } else if (swF == 3) {
                output = input * 1000000;
            }
        }
        // mg -> t and REV
        if( ((swF == 1) && (swT == 4)) || ((swF == 4) && (swT == 1)) ) {
            if (swF == 1) {
                output = input * 0.000000001;
            } else if (swF == 4) {
                output = input * 1000000000;
            }
        }
        // mg -> kt and REV
        if( ((swF == 1) && (swT == 5)) || ((swF == 5) && (swT == 1)) ) {
            if (swF == 1) {
                output = input * 0.000000000001;
            } else if (swF == 5) {
                output = input * 1000000000000L;
            }
        }
        // mg -> lb and REV
        if( ((swF == 1) && (swT == 6)) || ((swF == 6) && (swT == 1)) ) {
            if (swF == 1) {
                output = input * 0.0000022;
            } else if (swF == 6) {
                output = input * 453592.37;
            }
        }
        // mg -> gr and REV
        if( ((swF == 1) && (swT == 7)) || ((swF == 7) && (swT == 1)) ) {
            if (swF == 1) {
                output = input * 0.015432;
            } else if (swF == 7) {
                output = input * 64.79891;
            }
        }
        // mg -> oz and REV
        if( ((swF == 1) && (swT == 8)) || ((swF == 8) && (swT == 1)) ) {
            if (swF == 1) {
                output = input * 0.000035;
            } else if (swF == 8) {
                output = input * 28349.5231;
            }
        }
        // g -> kg and REV
        if( ((swF == 2) && (swT == 3)) || ((swF == 3) && (swT == 2)) ) {
            if (swF == 2) {
                output = input * 0.001;
            } else if (swF == 3) {
                output = input * 1000;
            }
        }
        // g -> t and REV
        if( ((swF == 2) && (swT == 4)) || ((swF == 4) && (swT == 2)) ) {
            if (swF == 2) {
                output = input * 0.000001;
            } else if (swF == 4) {
                output = input * 1000000;
            }
        }
        // g -> kt and REV
        if( ((swF == 2) && (swT == 5)) || ((swF == 5) && (swT == 2)) ) {
            if (swF == 2) {
                output = input * 0.000000001;
            } else if (swF == 5) {
                output = input * 1000000000;
            }
        }
        // g -> lb and REV
        if( ((swF == 2) && (swT == 6)) || ((swF == 6) && (swT == 2)) ) {
            if (swF == 2) {
                output = input * 0.002205;
            } else if (swF == 6) {
                output = input * 453.59237;
            }
        }
        // g -> gr and REV
        if( ((swF == 2) && (swT == 7)) || ((swF == 7) && (swT == 2)) ) {
            if (swF == 2) {
                output = input * 15.432358;
            } else if (swF == 7) {
                output = input * 0.064799;
            }
        }
        // g -> oz and REV
        if( ((swF == 2) && (swT == 8)) || ((swF == 8) && (swT == 2)) ) {
            if (swF == 2) {
                output = input * 0.035274;
            } else if (swF == 8) {
                output = input * 28.349523;
            }
        }
        // kg -> t and REV 4 is t,  5 is kt,  6 is lb,  7 is gr,  8 is oz
        if( ((swF == 3) && (swT == 4)) || ((swF == 4) && (swT == 3)) ) {
            if (swF == 3) {
                output = input * 0.001;
            } else if (swF == 4) {
                output = input * 1000;
            }
        }
        // kg -> kt and REV
        if( ((swF == 3) && (swT == 5)) || ((swF == 5) && (swT == 3)) ) {
            if (swF == 3) {
                output = input * 0.000001;
            } else if (swF == 5) {
                output = input * 1000000;
            }
        }
        // kg -> lb and REV
        if( ((swF == 3) && (swT == 6)) || ((swF == 6) && (swT == 3)) ) {
            if (swF == 3) {
                output = input * 2.204623;
            } else if (swF == 6) {
                output = input * 0.453592;
            }
        }
        // kg -> gr and REV
        if( ((swF == 3) && (swT == 7)) || ((swF == 7) && (swT == 3)) ) {
            if (swF == 3) {
                output = input * 15432.3584;
            } else if (swF == 7) {
                output = input * 0.000065;
            }
        }
        // kg -> oz and REV
        if( ((swF == 3) && (swT == 8)) || ((swF == 8) && (swT == 3)) ) {
            if (swF == 3) {
                output = input * 35.273962;
            } else if (swF == 8) {
                output = input * 0.02835;
            }
        }
        // t -> kt and REV 5 is kt,  6 is lb,  7 is gr,  8 is oz
        if( ((swF == 4) && (swT == 5)) || ((swF == 5) && (swT == 4)) ) {
            if (swF == 4) {
                output = input * 0.001;
            } else if (swF == 5) {
                output = input * 1000;
            }
        }
        // t -> lb and REV 5 is kt,  6 is lb,  7 is gr,  8 is oz
        if( ((swF == 4) && (swT == 6)) || ((swF == 6) && (swT == 4)) ) {
            if (swF == 4) {
                output = input * 2204.62262;
            } else if (swF == 6) {
                output = input * 0.000454;
            }
        }
        // t -> gr and REV  8 is oz
        if( ((swF == 4) && (swT == 7)) || ((swF == 7) && (swT == 4)) ) {
            if (swF == 4) {
                output = input * 15432358.4;
            } else if (swF == 7) {
                output = input * 0.000000065;
            }
        }
        // t -> oz and REV
        if( ((swF == 4) && (swT == 8)) || ((swF == 8) && (swT == 4)) ) {
            if (swF == 4) {
                output = input * 35273.9619;
            } else if (swF == 8) {
                output = input * 0.000028;
            }
        }
        // kt -> lb and REV 6 is lb,  7 is gr,  8 is oz
        if( ((swF == 5) && (swT == 6)) || ((swF == 6) && (swT == 5)) ) {
            if (swF == 5) {
                output = input * 2204622.62;
            } else if (swF == 6) {
                output = input * 0.000000454;
            }
        }
        // kt -> gr and REV  8 is oz
        if( ((swF == 5) && (swT == 7)) || ((swF == 7) && (swT == 5)) ) {
            if (swF == 5) {
                output = input * 15432358400L;
            } else if (swF == 7) {
                output = input * 0.000000000065;
            }
        }
        // kt -> oz and REV
        if( ((swF == 5) && (swT == 8)) || ((swF == 8) && (swT == 5)) ) {
            if (swF == 5) {
                output = input * 35273961.9;
            } else if (swF == 8) {
                output = input * 0.000000028;
            }
        }
        // lb -> gr and REV
        if( ((swF == 6) && (swT == 7)) || ((swF == 7) && (swT == 6)) ) {
            if (swF == 6) {
                output = input * 7000;
            } else if (swF == 7) {
                output = input * 0.000143;
            }
        }
        // lb -> oz and REV
        if( ((swF == 6) && (swT == 8)) || ((swF == 8) && (swT == 6)) ) {
            if (swF == 6) {
                output = input * 16;
            } else if (swF == 8) {
                output = input * 0.0625;
            }
        }
        // gr -> oz and REV
        if( ((swF == 7) && (swT == 8)) || ((swF == 8) && (swT == 7)) ) {
            if (swF == 7) {
                output = input * 0.002286;
            } else if (swF == 8) {
                output = input * 437.5;
            }
        }

        tv_weight_output.setText(output+"");
    }

}
