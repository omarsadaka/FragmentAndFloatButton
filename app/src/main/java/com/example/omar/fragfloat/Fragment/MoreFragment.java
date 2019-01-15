package com.example.omar.fragfloat.Fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.omar.fragfloat.R;


public class MoreFragment extends Fragment implements View.OnClickListener {
    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,
            btn_Add,btn_Sub,btn_Mul,btn_Div,btn_calc,btn_dec,btn_clear;
    EditText ed1;

    float Value1, Value2;
    boolean mAddition, mSubtract, mMultiplication, mDivision ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getLayoutInflater().inflate(R.layout.fragment_more, container, false);
        createView(view);
        createClick();
        return view;
    }
      public void createView(View v){
          btn_0 = v.findViewById(R.id.btn_0);
          btn_1 = v.findViewById(R.id.btn_1);
          btn_2 = v.findViewById(R.id.btn_2);
          btn_3 = v.findViewById(R.id.btn_3);
          btn_4 = v.findViewById(R.id.btn_4);
          btn_5 = v.findViewById(R.id.btn_5);
          btn_6 = v.findViewById(R.id.btn_6);
          btn_7 = v.findViewById(R.id.btn_7);
          btn_8 = v.findViewById(R.id.btn_8);
          btn_9 = v.findViewById(R.id.btn_9);
          btn_Add = v.findViewById(R.id.btn_Add);
          btn_Div = v.findViewById(R.id.btn_Div);
          btn_Sub = v.findViewById(R.id.btn_Sub);
          btn_Mul = v.findViewById(R.id.btn_Mul);
          btn_calc = v.findViewById(R.id.btn_calc);
          btn_dec = v.findViewById(R.id.btn_dec);
          btn_clear = v.findViewById(R.id.btn_clear);
          ed1 = v.findViewById(R.id.edText1);
      }
      public void createClick(){
          btn_0.setOnClickListener(this);
          btn_1.setOnClickListener(this);
          btn_2.setOnClickListener(this);
          btn_3.setOnClickListener(this);
          btn_4.setOnClickListener(this);
          btn_5.setOnClickListener(this);
          btn_6.setOnClickListener(this);
          btn_7.setOnClickListener(this);
          btn_8.setOnClickListener(this);
          btn_9.setOnClickListener(this);
          btn_Add.setOnClickListener(this);
          btn_calc.setOnClickListener(this);
          btn_clear.setOnClickListener(this);
          btn_dec.setOnClickListener(this);
          btn_Div.setOnClickListener(this);
          btn_Mul.setOnClickListener(this);
          btn_Sub.setOnClickListener(this);

      }

    @Override
    public void onClick(View v) {
          int id = v.getId();
          switch (id){
              case R.id.btn_0:
                  ed1.setText(ed1.getText()+"0");
                  break;
              case R.id.btn_1:
                  ed1.setText(ed1.getText()+"1");
                  break;
              case R.id.btn_2:
                  ed1.setText(ed1.getText()+"2");
                  break;
              case R.id.btn_3:
                  ed1.setText(ed1.getText()+"3");
                  break;
              case R.id.btn_4:
                  ed1.setText(ed1.getText()+"4");
                  break;
              case R.id.btn_5:
                  ed1.setText(ed1.getText()+"5");
                  break;
              case R.id.btn_6:
                  ed1.setText(ed1.getText()+"6");
                  break;
              case R.id.btn_7:
                  ed1.setText(ed1.getText()+"7");
                  break;
              case R.id.btn_8:
                  ed1.setText(ed1.getText()+"8");
                  break;
              case R.id.btn_9:
                  ed1.setText(ed1.getText()+"9");
                  break;
              case R.id.btn_Add:
                  if (ed1 == null){
                      ed1.setText("");
                  }else {
                      Value1 = Float.parseFloat(ed1.getText() + "");
                      mAddition = true;
                      ed1.setText(null);
                  }
                  break;
              case R.id.btn_Sub:
                  Value1 = Float.parseFloat(ed1.getText() + "");
                  mSubtract = true ;
                  ed1.setText(null);
                  break;
              case R.id.btn_Mul:
                  Value1 = Float.parseFloat(ed1.getText() + "");
                  mMultiplication = true ;
                  ed1.setText(null);
                  break;
              case R.id.btn_Div:
                  Value1 = Float.parseFloat(ed1.getText()+"");
                  mDivision = true ;
                  ed1.setText(null);
                  break;
              case R.id.btn_calc:
                  Value2 = Float.parseFloat(ed1.getText() + "");

                  if (mAddition == true){

                      ed1.setText(Value1 + Value2 +"");
                      mAddition=false;
                  }


                  if (mSubtract == true){
                      ed1.setText(Value1 - Value2 +"");
                      mSubtract=false;
                  }

                  if (mMultiplication == true){
                      ed1.setText(Value1 * Value2 + "");
                      mMultiplication=false;
                  }

                  if (mDivision == true){
                      ed1.setText(Value1 / Value2+"");
                      mDivision=false;
                  }
                  break;
              case R.id.btn_clear:
                  ed1.setText("");
                  break;
              case R.id.btn_dec:
                  ed1.setText(ed1.getText()+".");
                  break;
          }

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
