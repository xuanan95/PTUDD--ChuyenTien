package com.example.buixuanan.moneyconvert.ui;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.buixuanan.moneyconvert.R;

import java.text.DecimalFormat;

import butterknife.BindView;

public class Activity3 extends AppCompatActivity {
    TextView txtMoney1;
    TextView txtMoney2;
    EditText edtMoney1;
    EditText edtMoney2;
    Button btnClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String name1;
        final String name2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        Intent data = getIntent();
        final Bundle bundle = data.getBundleExtra("data1");
        txtMoney1 = (TextView)findViewById(R.id.txtMoney1);
        txtMoney2 = (TextView)findViewById(R.id.txtMoney2);
        edtMoney1 = (EditText)findViewById(R.id.edtMoney1);
        edtMoney2 = (EditText)findViewById(R.id.ediMoney2);
        btnClick = (Button)findViewById(R.id.btnConvert);
        name1 = bundle.getString("name");
        name2 = bundle.getString("name1");

        txtMoney1.setText(name1);
        txtMoney2.setText(name2);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double number1 = Double.parseDouble(edtMoney1.getText().toString());
                Double rate = Double.parseDouble(bundle.getString(name1));

                if(name2.equals("VND")){
                    Double vnd = rate*number1;
                    DecimalFormat ft = new DecimalFormat("#,###.00");
                    edtMoney2.setText(String.valueOf(ft.format(vnd)));
                }
                else{
                    Integer rate2 = Integer.parseInt(bundle.getString(name2));
                    Double rate3 = (double)rate/rate2;
                    Double rateTotal = (double)number1*rate3;
                    DecimalFormat ft = new DecimalFormat("#,###.00");
                    edtMoney2.setText(String.valueOf(ft.format(rateTotal)));
                }
            }
        });
    }
}
